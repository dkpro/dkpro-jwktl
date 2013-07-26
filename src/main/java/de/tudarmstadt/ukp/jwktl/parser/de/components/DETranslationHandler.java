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
package de.tudarmstadt.ukp.jwktl.parser.de.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Parser component for extracting translations from the German Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class DETranslationHandler extends DEBlockHandler {	

	private static final Pattern LITERATURE_PATTERN = Pattern.compile("\\{\\{Literatur\\|[^}]+\\}\\}|<ref[^>]*>.*?</ref>");

	//*{{en}}: [1–3] {{Ü|en|aberration}}
	//[1] {{Ü|pl|aberracja światła}} {{f}}
	protected static final Pattern TRANSLATION_PATTERN = Pattern.compile(
			"^\\*\\s*" + "\\{\\{([^}]*).*?\\}\\}" + "[: ;]*" 
			+ "\\[([^\\[\\]]+?)\\]" + "\\s*" + "(.*?)" 
			+ "(?:\\{\\{(Ü.*?)\\}\\}|\\[\\[(.*?)\\]\\])" + "(.*)$");
	protected static final Pattern ADDITIONAL_TRANSLATION_PATTERN = Pattern.compile("^\\s*"
			+ "(?:\\[([^\\[\\]]+?)\\])?" + "\\s*" + "(.*?)"
			+ "(?:\\{\\{(Ü.*?)\\}\\}|\\[\\[(.*?)\\]\\])" + "(.*)$");
	protected static final Pattern NEXT_TRANSLATION_PATTERN = Pattern.compile(
			"^(.*?)(?:[,;]+|(\\{\\{Ü\\S*\\|))(.*)$");
	protected static final Pattern PREPARATION_PATTERN = Pattern.compile("(\\s\\[([^\\[\\]]+?)\\]\\s)");
	
	protected Map<Integer, List<IWiktionaryTranslation>> sensNum2trans;
	
	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public DETranslationHandler() {
		super("Übersetzungen");
	}
	
	@Override
	public boolean processHead(String text, ParsingContext context) {	
		sensNum2trans = new TreeMap<Integer, List<IWiktionaryTranslation>>();
		return true;
	}
	
	@Override
	public boolean processBody(String text, final ParsingContext context) {
		// start of a translation line		
		text = text.trim();
				
		if (text.startsWith("{{Ü-links}}"))
			return true;
		if (text.startsWith("{{Ü-Abstand}}"))
			return true;
		if (text.startsWith("{{Ü-rechts}}")) // This template indicates the end of the translation block
			return false;
		if (text.startsWith("{{")) // Indicates that a new block has just started.
			return false;

		text = LITERATURE_PATTERN.matcher(text).replaceAll("");
		text = PREPARATION_PATTERN.matcher(text).replaceAll(";$1");
		
		Matcher matcher = TRANSLATION_PATTERN.matcher(text);
		if (matcher.find()) {
			boolean usesTemplate = true;
			String language = matcher.group(1);
			String index = matcher.group(2);
			String prefix = matcher.group(3);
			String translation = matcher.group(4);
			if (translation == null) {
				translation = matcher.group(5);
				usesTemplate = false;
			}
			String additionalInformation = matcher.group(6);
						
			String remainingText;
			do {
				// Check for additional translations.
				remainingText = null;
				matcher = NEXT_TRANSLATION_PATTERN.matcher(additionalInformation);
				if (matcher.find()) {
					additionalInformation = matcher.group(1).trim();
					remainingText = matcher.group(2);
					if (remainingText == null)
						remainingText = matcher.group(3);
					else
						remainingText += matcher.group(3);
					
					if (additionalInformation.endsWith(":")) {
						remainingText = additionalInformation + remainingText;
						additionalInformation = "";
					}
				}			
				
				// Compile index set.
				Set<Integer> indexSet = StringUtils.compileIndexSet(index);

				// Prepare the translation.
				if (language != null) {
					int i = language.indexOf('|');
					if (i > 0)
						language = language.substring(0, i);
					language = language.trim();
				}
				ILanguage translatedLang = Language.findByCode(language);
				if (translatedLang == null)
					translatedLang = Language.findByName(language);
//				if (translatedLang == null && !"UNSPECIFIED".equals(language))
//					System.err.println("Unknown language of translation: " + language + " (" + context.getPage().getTitle() + ")");
//					translatedLang = Language.UNKNOWN;
				
				String[] fields = null;
				String translationText = translation;
				if (usesTemplate) {
					fields = StringUtils.split(translation, '|');
					if (fields != null && fields.length > 0)
						translationText = fields[fields.length - 1].trim();
					else
						translationText = null;
				} else {
					int i = translation.indexOf('|'); // [[...|...]]
					if (i >= 0)
						translationText = translation.substring(i + 1);
				}
				
				if (translationText != null && !translationText.isEmpty()) {
					if (!usesTemplate && !additionalInformation.contains("{")) {
						translationText = cleanText(translationText + additionalInformation);
						additionalInformation = "";
					}

					WiktionaryTranslation trans = new WiktionaryTranslation(translatedLang, translationText);

					additionalInformation = cleanText(prefix + " " + additionalInformation);
					trans.setAdditionalInformation(additionalInformation);

					if (fields != null && fields.length >= 4 && fields[0].equals("Üxx"))
						trans.setTransliteration(cleanText(fields[2]));

					// Save the translation
					for (Integer i : indexSet) {
						List<IWiktionaryTranslation> translations = sensNum2trans.get(i);
						if (translations == null) {
							translations = new ArrayList<IWiktionaryTranslation>();
							sensNum2trans.put(i, translations);
						}
						translations.add(trans);
					}
				}
				
				// Perform another iteration if there is remaining text.
				if (remainingText != null) {
					do {
						matcher = ADDITIONAL_TRANSLATION_PATTERN.matcher(remainingText);
						if (matcher.find()) {
							usesTemplate = true;
							String indexTmp = matcher.group(1);
							if (indexTmp != null)
								index = indexTmp; // otherwise, use the index of the previous translation! 
							prefix = matcher.group(2);
							translation = matcher.group(3);
							if (translation == null) {
								translation = matcher.group(4);
								usesTemplate = false;
							}
							additionalInformation = matcher.group(5);
							break;
						} else {
							matcher = NEXT_TRANSLATION_PATTERN.matcher(remainingText);
							if (matcher.find())
								remainingText = matcher.group(2) + matcher.group(3);
							else
								remainingText = null;
						}
					} while (remainingText != null);
				}
			} while (remainingText != null);
		}			
		return true;
	}
	
	protected String cleanText(final String text) {
		String result = text;
		result = result.replace("[[", "");
		result = result.replace("]]", "");
		result = result.replace("'''", "");
		result = result.replace("''", "");
		int startIdx = 0;
		while (startIdx < result.length() && ":() ".indexOf(result.charAt(startIdx)) >= 0)
			startIdx++;
		int endIdx = result.length();
		while (endIdx > startIdx && ":() ".indexOf(result.charAt(endIdx - 1)) >= 0)
			endIdx--;
		if (endIdx >= 0 && startIdx < endIdx)
			result = result.substring(startIdx, endIdx);
		else
			result = "";
		return result;
	}

	/**
	 * Set translations to wordEntry object
	 * If no sense mapping is found, they are set as unclassified translations.
	 */
	public void fillContent(final ParsingContext context) {
		WiktionaryEntry posEntry = context.findEntry();
		if (posEntry != null) {
			for (Entry<Integer, List<IWiktionaryTranslation>> trans : sensNum2trans.entrySet()) {
				WiktionarySense sense = null;
				if (trans.getKey() >= 0)
					sense = posEntry.findSenseByMarker(Integer.toString(trans.getKey()));				
				if (sense == null)
					sense = posEntry.getUnassignedSense();
				
				for (IWiktionaryTranslation translation : trans.getValue())
					sense.addTranslation(translation);		
			}
		}
	}
	
}
