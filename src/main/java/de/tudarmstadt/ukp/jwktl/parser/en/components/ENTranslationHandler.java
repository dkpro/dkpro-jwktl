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
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.api.util.TemplateParser;
import de.tudarmstadt.ukp.jwktl.api.util.TemplateParser.Template;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

import static de.tudarmstadt.ukp.jwktl.api.entry.WikiString.removeWikiLinks;
import static de.tudarmstadt.ukp.jwktl.parser.en.components.ENSemanticRelationHandler.findMatchingSense;
import static de.tudarmstadt.ukp.jwktl.parser.util.StringUtils.cleanText;

/**
 * Parser component for extracting translations from the English Wiktionary.
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class ENTranslationHandler extends ENBlockHandler {
	private static final Pattern LANGUAGE = Pattern.compile("^\\*:?\\s*(.*?):\\s*");
	private static final Pattern SEPARATOR = Pattern.compile("(?:]]|}}|\\))\\s*([;,])");

	private static final Pattern WIKILINK_TRANSLATION = Pattern.compile("(?:\\[\\[.*?]]\\s*)+");
	private static final Pattern TEMPLATE_TRANSLATION = Pattern.compile("\\{\\{t.*?}}");

	private static final Pattern TRANSLATION = Pattern.compile(
		"^" +
		"(?<prefix>.*\\s+)??" +
		"(?<content>" + WIKILINK_TRANSLATION + "|" + TEMPLATE_TRANSLATION + ")" +
		"(?<postfix>.*)" +
		"$"
	);

	private String currentSense;
	private Map<String, List<IWiktionaryTranslation>> sensNum2trans;

	/** Initializes the block handler for parsing all sections starting with
	 *  one of the specified labels. */
	public ENTranslationHandler() {
		super("Translations");
	}

	@Override
	public boolean processHead(final String text, final ParsingContext context) {
		currentSense = "";
		sensNum2trans = new TreeMap<>();
		return true;
	}

	@Override
	public boolean processBody(String text, final ParsingContext context) {
		text = text.trim();

		if (text.startsWith("{{trans-mid}}") || text.startsWith("{{mid}}"))
			return true;
		if (text.startsWith("{{trans-top|") && text.contains("}}")) {
			final Template template = TemplateParser.parseTemplate(text.substring(2, text.indexOf("}}")));
			if (template != null && template.getNumberedParamsCount() >= 1) {
				currentSense = template.getNumberedParam(0);
			}
			return true;
		}
		if (text.startsWith("{{top}}")) {
			currentSense = "";
			return true;
		}
		if (text.startsWith("{{trans-bottom}}") || text.startsWith("{{bottom}}")) // This template indicates the end of the translation block
			return false;
		if (text.startsWith("{{") || text.startsWith("==")) // Indicates that a new block has just started.
			return false;

		Matcher matcher = LANGUAGE.matcher(text);
		if (!matcher.find()) {
			return false;
		}
		final String languageText = removeWikiLinks(matcher.group(1).trim());
		final ILanguage language = Language.findByName(languageText);

		final int endOffSet = matcher.end();
		if (endOffSet > text.length() - 1) {
			return false;
		}
		final String remainingText = text.substring(endOffSet);

		for (String part : splitTranslationParts(remainingText)) {
			final IWiktionaryTranslation translation = parseTranslation(language, part);
			if (translation != null) {
				// Save the translation
				List<IWiktionaryTranslation> translations = sensNum2trans.computeIfAbsent(currentSense, k -> new ArrayList<>());
				translations.add(translation);
			}
		}
		return true;
	}

	private IWiktionaryTranslation parseTranslation(ILanguage languageHeader, String text) {
		Matcher matcher = TRANSLATION.matcher(text);
		if (!matcher.matches()) {
			return null;
		}
		final String prefix = matcher.group("prefix");
		final String content = matcher.group("content");
		final String postfix = matcher.group("postfix");

		WiktionaryTranslation translation;
		if (content.startsWith("{{")) {
			translation = parseTemplate(content);
		} else {
			translation = new WiktionaryTranslation(languageHeader, cleanText(removeWikiLinks(content)));
		}

		if (translation != null) {
			String additionalInformation = "";
			if (prefix != null) {
				additionalInformation += prefix.trim();
			}
			if (translation.getGender() != null) {
				additionalInformation += " {{" + translation.getGender() + "}} ";
			}
			additionalInformation += postfix;
			translation.setAdditionalInformation(cleanText(additionalInformation.trim()));
			if (currentSense != null && currentSense.trim().length() > 0) {
				translation.setRawSense(currentSense.trim());
			}
			return translation;
		} else {
			return null;
		}
	}

	static List<String> splitTranslationParts(String text) {
		List<String> results = new ArrayList<>();
		Matcher m = SEPARATOR.matcher(text);
		int lastStart = 0;
		while (m.find()) {
			final String candidate = text.substring(lastStart, m.start(1)).trim();
			if (TRANSLATION.matcher(candidate).matches()) {
				results.add(candidate);
				lastStart = m.end(1);
			}
		}
		results.add(text.substring(lastStart).trim());
		return results;
	}

	private WiktionaryTranslation parseTemplate(String templateString) {
		Template template = TemplateParser.parseTemplate(templateString.substring(2, templateString.length() - 2));
		if (template == null || template.getNumberedParamsCount() <= 1) {
			return null;
		}
		String translationText = cleanText(removeWikiLinks(template.getNumberedParam(1)));
		if (translationText.isEmpty()) {
			return null;
		}
		String languageCode = template.getNumberedParam(0);
		String transliteration = template.getNamedParam("tr");
		WiktionaryTranslation translation = new WiktionaryTranslation(Language.findByCode(languageCode), translationText);
		if (template.getNumberedParamsCount() > 2 && !template.getNumberedParam(2).contains("=")) {
			translation.setGender(template.getNumberedParam(2));
		}
		translation.setCheckNeeded(template.getName().contains("check"));
		if (transliteration != null) {
			translation.setTransliteration(cleanText(transliteration));
		}
		return translation;
	}

	/**
	 * Add parsed translation into senseEntry. If no mapping is found, the translation is added to posEntry.
	 */
	public void fillContent(final ParsingContext context) {
		WiktionaryEntry posEntry = context.findEntry();
		if (posEntry != null) {
			for (Entry<String, List<IWiktionaryTranslation>> trans : sensNum2trans.entrySet()) {
				WiktionarySense targetSense = findSense(posEntry, trans.getKey());

				trans.getValue().forEach(targetSense::addTranslation);
			}
		}
	}

	private WiktionarySense findSense(WiktionaryEntry entry, String marker) {
		WiktionarySense sense = findMatchingSense(entry, marker);
		if (sense != null) {
			return sense;
		} else {
			return entry.getUnassignedSense();
		}
	}
}
