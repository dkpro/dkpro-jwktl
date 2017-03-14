/*******************************************************************************
 * Copyright 2013
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tudarmstadt.ukp.jwktl.parser.en.components;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.IPronunciation;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.entry.Quotation;
import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryExample;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.api.util.TemplateParser;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

import static de.tudarmstadt.ukp.jwktl.parser.util.StringUtils.cleanText;

/**
 * <p>Extract POS, gloss and inner-definition quotations. It checks if a string is in a predefined pos set,
 * if it is, the section is the pos section.
 * @author  Lizhen Qu
 */
public class ENSenseHandler extends ENBlockHandler {
	protected static final Pattern EXAMPLE_PATTERN = Pattern.compile("^#+:+");
	protected static final Pattern POS_PATTERN = Pattern.compile(
			"^====?\\s*(?:"
			+ "\\{\\{([^\\}\\|]+)(?:\\|[^\\}\\|]*)?\\}\\}|"
			+ "\\[\\[(?:[^\\]\\|]+\\|)?([^\\]\\|]+)\\]\\]|"
			+ "([^=]+?)"
			+ ")\\s*\\d*\\s*=?===$");

	/**
	 * Extracted pos string
	 */
	protected PartOfSpeech partOfSpeech;
	/**
	 * A list of gloss entries
	 */
	protected List<EnGlossEntry> glossEntryList;
	/**
	 * a instance of PosEntryFactory
	 */
	protected ENEntryFactory entryFactory;
	/**
	 * If the worker takes control of parsing or let parser decide it.
	 */
	protected boolean takeControl;

	protected ENQuotationHandler quotationHandler;
	protected IWordFormHandler wordFormHandler;

	protected String lastPrefix;

	/**
	 * Init attributes
	 */
	public ENSenseHandler() {
		entryFactory = new ENEntryFactory();
		quotationHandler = new ENQuotationHandler();
	}

	/**
	 * Check if the label of section is a predefined POS label.
	 */
	public boolean canHandle(final String blockHeader) {
		partOfSpeech = null;
		String posLabel = blockHeader.trim();
		if (!posLabel.startsWith("===") || !posLabel.endsWith("==="))
			return false;

		Matcher matcher = POS_PATTERN.matcher(blockHeader);
		if (!matcher.find())
			return false;

		if (matcher.group(1) != null)
			posLabel = matcher.group(1);
		else
		if (matcher.group(2) != null)
			posLabel = matcher.group(2);
		else
			posLabel = matcher.group(3);

		partOfSpeech = PartOfSpeech.findByName(posLabel, ENEntryFactory.posMap);
		return (partOfSpeech != null);
	}

	/**
	 * Process head
	 */
	public boolean processHead(String text, ParsingContext context) {
		context.setPartOfSpeech(partOfSpeech);
		glossEntryList = new ArrayList<>();
		wordFormHandler = getWordFormHandler(context);
		takeControl = true;
		quotationHandler.processHead(text, context);
		lastPrefix = null;
		return true;
	}

    private IWordFormHandler getWordFormHandler(ParsingContext context) {
        if (Language.ENGLISH.equals(context.getLanguage())) {
            return new ENWordFormHandler(context.getPage().getTitle());
        } else {
            return new ENNonEngWordFormHandler();
        }
    }

    /**
	 * Extract example, gloss and in-definition quotation
	 */
	public boolean processBody(final String text, final ParsingContext context) {
		String line = text.trim();
		if (line.isEmpty())
			return takeControl;

		boolean additionalLine = false;
		if (lastPrefix != null && !line.startsWith("#") && !line.startsWith("{")) {
			line = lastPrefix + line;
			additionalLine = true;
		}
		final Matcher exampleMatcher = EXAMPLE_PATTERN.matcher(line);
		if (exampleMatcher.find()) {
			processExampleLine(line, exampleMatcher.group(), additionalLine);
		} else
		if (line.startsWith("#*")) {
			// Quotation.
			quotationHandler.extractQuotation(line.substring(1), additionalLine, context);
			lastPrefix = "#*";
			takeControl = false;

		} else
		if (line.startsWith("##")) {
			// Subsense.
			String subsense = line.substring(2).trim();
			if (!glossEntryList.isEmpty()) {
				EnGlossEntry glossEntry = glossEntryList.get(glossEntryList.size() - 1);
				if (subsense.startsWith("*")) {
					quotationHandler.extractQuotation(subsense, additionalLine, context);
					lastPrefix = "##*";
				} else {
					glossEntry.setGloss(glossEntry.getDefinition() + "\n" + subsense);
					lastPrefix = "##";
				}
			}
			takeControl = false;

		} else
		if (line.startsWith("#") && line.length() > 2) {
			// Sense definition.
			saveQuotations();
			String gloss = line.substring(1).trim();
			EnGlossEntry glossEntry = new EnGlossEntry(gloss);
			glossEntryList.add(glossEntry);
			lastPrefix = "#";
			takeControl = false;

        } else if (wordFormHandler.parse(line)) {
            lastPrefix = null;
            takeControl = true;
        }
		return takeControl;
	}

	protected void saveQuotations() {
		List<Quotation> quotations = quotationHandler.getQuotations();
		if (quotations.size() == 0 || glossEntryList.size() == 0)
			return;

		EnGlossEntry glossEntry = glossEntryList.get(glossEntryList.size() - 1);
		for (Quotation quotation : quotations)
			glossEntry.getQuotations().add(quotation);
		quotationHandler.getQuotations().clear();
	}

	/**
	 * Store POS, examples, quotations in WordEntry object
	 */
	public void fillContent(final ParsingContext context) {
		saveQuotations();

		// In the special case when article constituents have been found before
		// the first entry, do not create a new entry, but use the automatically
		// created one.
		WiktionaryEntry entry;
		if (context.getPage().getEntryCount() == 1
				&& context.getPage().getEntry(0).getPartOfSpeech() == null) {
			entry = context.getPage().getEntry(0);
			entry.setWordLanguage(context.getLanguage());
			entry.addPartOfSpeech(context.getPartOfSpeech());
			if (context.getHeader() != null)
				entry.setHeader(context.getHeader());
			entry.setWordEtymology(context.getEtymology());
		} else {
			entry = entryFactory.createEntry(context);
			context.getPage().addEntry(entry);
		}

		List<IPronunciation> pronunciations = context.getPronunciations();
		if (pronunciations != null)
			pronunciations.forEach(entry::addPronunciation);
		for (EnGlossEntry senseEntry : glossEntryList){
			WiktionarySense sense = entry.createSense();
			sense.setGloss(new WikiString(senseEntry.getDefinition()));
			for (String exp : senseEntry.getExampleList()) {
				String translation = senseEntry.getExampleTranslation(exp);
				sense.addExample(new WiktionaryExample(new WikiString(exp), translation == null ? null : new WikiString(translation)));
			}
			senseEntry.getQuotations().forEach(sense::addQuotation);
			entry.addSense(sense);
			senseEntry.getRelations()
				.entrySet()
				.stream()
				.flatMap(e ->  e.getValue().stream().map(target -> new WiktionaryRelation(target, e.getKey())))
				.forEach(sense::addRelation);
		}
		wordFormHandler.getWordForms().forEach(entry::addWordForm);
		entry.setRawHeadwordLine(wordFormHandler.getRawHeadwordLine());

		List<GrammaticalGender> genders = wordFormHandler.getGenders();
		if (genders != null)
			genders.forEach(entry::addGender);
	}

	private boolean isNym(String line) {
		return line.contains("{{syn") || line.contains("{{ant");
	}

	private void processExampleLine(String line, String currentPrefix, boolean additionalLine) {
		final String lineContent = line.substring(currentPrefix.length()).trim();
		if (!glossEntryList.isEmpty()) {
			EnGlossEntry glossEntry = glossEntryList.get(glossEntryList.size() - 1);
			if (isNym(lineContent)) {
				parseNym(lineContent, glossEntry);
			} else {
				parseExample(lineContent, currentPrefix, additionalLine, glossEntry);
			}
		}
		lastPrefix = currentPrefix;
		takeControl = false;
	}

	private void parseExample(String lineContent, String currentPrefix, boolean additionalLine, EnGlossEntry glossEntry) {
		final boolean translatedExample =  lastPrefix != null &&
			EXAMPLE_PATTERN.matcher(lastPrefix).matches() &&
			currentPrefix.length() > lastPrefix.length();

		if (additionalLine) {
			glossEntry.appendExample(lineContent, " ");
		} else if (translatedExample) {
			glossEntry.appendExampleTranslation(lineContent);
		} else {
			glossEntry.addExample(lineContent);
		}
	}

	private void parseNym(String line, EnGlossEntry glossEntry) {
		TemplateParser.parse(line, template -> {
			RelationType type = getRelationType(template);
			if (type != null) {
				for (int i=1; i<template.getNumberedParamsCount(); i++) {
					glossEntry.addRelation(type, cleanText(template.getNumberedParam(i)));
				}
			}
			return null;
		});
	}

	private RelationType getRelationType(TemplateParser.Template template) {
		// https://en.wiktionary.org/wiki/Template:synonyms
		// https://en.wiktionary.org/wiki/Template:antonyms
		switch (template.getName()) {
			case "syn":
			case "synonyms":
				return RelationType.SYNONYM;
			case "ant":
			case "antonyms":
				return RelationType.ANTONYM;
			default:
				return null;
		}
	}
}
