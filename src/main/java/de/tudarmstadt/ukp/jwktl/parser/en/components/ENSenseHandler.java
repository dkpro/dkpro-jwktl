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
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.entry.Quotation;
import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * <p>Extract POS, gloss and inner-definition quotations. It checks if a string is in a predefined pos set,
 * if it is, the section is the pos section.
 * @author  Lizhen Qu
 */
public class ENSenseHandler extends ENBlockHandler {
		
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
	
//	protected ENWordFormHandler wordFormHandler;
	
//	protected List<IWiktionaryWordForm> wordForms
	protected ENWordFormHandler wordFormHandler;
	
	protected String lastPrefix;
	
	/**
	 * Init attributes
	 */
	public ENSenseHandler() {
		entryFactory = new ENEntryFactory();
//		wordFormHandler = new ENWordFormHandler();
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
//		context.setAttr(Context.CURRENT_POS, partOfSpeech.name());
		context.setPartOfSpeech(partOfSpeech);
		glossEntryList = new ArrayList<EnGlossEntry>();
//		wordForms = new ArrayList<IWiktionaryWordForm>();
//		quotationList = new ArrayList<Quotation_Impl>();
		String lemma = context.getPage().getTitle();
		wordFormHandler = new ENWordFormHandler(lemma);
		takeControl = true;
		quotationHandler.processHead(text, context);
		lastPrefix = null;
		return true;
	}

	/**
	 * Extract example, gloss and in-definition quotation
	 */
	public boolean processBody(final String text, final ParsingContext context) {
		String line = text.trim();
		if (line.isEmpty())
			return takeControl;
		if ("----".equals(line))
			return false;
		
		boolean additionalLine = false;
		if (!line.startsWith("#") && !line.startsWith("{")) {
			line = lastPrefix + line;
			additionalLine = true;
		}
		
		if (line.startsWith("#:")){
			// Example sentence.
			String example = line.substring(2).trim();
			if (!glossEntryList.isEmpty()) {
				EnGlossEntry glossEntry = glossEntryList.get(glossEntryList.size() - 1);
				if (additionalLine)
					glossEntry.appendExample(example);
				else
					glossEntry.addExample(example);
			}
			lastPrefix = "#:";
			takeControl = false;
			
		} else 
		if (line.startsWith("#*")) {
			// Quotation.
//			quotationHandler.processBody(line.substring(1), context);
			quotationHandler.extractQuotation(line.substring(1), additionalLine, context);
			/*int index = 2;
			if(index < line.length()){
				if(line.charAt(index) != ':'){
					Quotation_Impl quotationEntry = new Quotation_Impl();
					String quotation = line.substring(index).trim();					
					quotationEntry.setSource(new WikiString(quotationHandler.replaceTemplate(quotation)));
					quotationList.add(quotationEntry);
				}else{
					for(int i = 2 ; i < line.length() ; i++){
						if(line.charAt(i) != ':'){
							index = i;
							break;
						}
					}
					String quotation = line.substring(index).trim();
					if(glossEntryList.size() > 0 && quotationList.size() > 0){
						Quotation_Impl quotationEntry = quotationList.get(quotationList.size() - 1);
						quotationEntry.addLine(new WikiString(quotation));
					}
				}
			}*/
			lastPrefix = "#*";
			takeControl = false;
			
		} else
		if (line.startsWith("##")) {
			// Subsense.
			String subsense = line.substring(2).trim();
			if (!glossEntryList.isEmpty()) {
				EnGlossEntry glossEntry = glossEntryList.get(glossEntryList.size() - 1);
				if (subsense.startsWith(":")) {
					if (additionalLine)
						glossEntry.appendExample(subsense.substring(1).trim());
					else
						glossEntry.addExample(subsense.substring(1).trim());
					lastPrefix = "##:";
				} else
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
			
		} else 
		if (line.startsWith("{{en-")) {
			// Part of speech header.
//			String title = (String) context.getAttr(Context.CURRENT_TITLE);
//			String title = context.getPage().getTitle();
//			ENWordFormHandler wordFormHandler = new ENWordFormHandler(title, wordForms);
			wordFormHandler.parse(line.trim());
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
		
//		ILanguage lang = (ILanguage) context.getAttr(Context.CURRENT_LANG);
//		ILanguage language = context.getLanguage(); 
//		WiktionaryEntry entry = entryFactory.createPosEntry(context);		
//		entry.addPartOfSpeech(partOfSpeech);
//		posEntry.setWordEtymology((IWikiString) context.getAttr(Context.CURRENT_ETYMOLOGY));
//		entry.setWordEtymology(context.getEtymology());
//		List<Pronunciation> pronunciations = (List<Pronunciation>) context.getAttr(Context.CURRENT_PRONUNCIATIONS);
		List<IPronunciation> pronunciations = context.getPronunciations(); 
		if (pronunciations != null)
			for (IPronunciation pronunciation : pronunciations)
				entry.addPronunciation(pronunciation);
		for (EnGlossEntry senseEntry : glossEntryList){
			WiktionarySense sense = entry.createSense();
			sense.setGloss(new WikiString(senseEntry.getDefinition()));
			for (String exp : senseEntry.getExampleList())
				sense.addExample(new WikiString(exp));
			for(Quotation quotation : senseEntry.getQuotations())
				sense.addQuotation(quotation);
			entry.addSense(sense);
		}
		for (IWiktionaryWordForm wordForm : wordFormHandler.getWordForms())
			entry.addWordForm(wordForm);
//		word.addPosEntry(posEntry);
		
//		context.getPage().addEntry(entry);
	}

}
