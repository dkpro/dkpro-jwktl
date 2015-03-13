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
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Parser component for extracting translations from the English Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class ENTranslationHandler extends ENSenseIndexedBlockHandler {
	
//	private static final String UNCATEGORIZED_TRANSLATIONS = "translations to be categorised";

	protected static final Pattern TRANSLATION_PATTERN = Pattern.compile(
			"^\\*:?\\s*" + "(.*?):\\s*"
			+ "(.*?)" + "(?:\\{\\{(t.*?)\\}\\}|\\[\\[(.*?)\\]\\])" + "(.*)$");
	protected static final Pattern ADDITIONAL_TRANSLATION_PATTERN = Pattern.compile(
			"^\\s*"
			+ "(.*?)" + "(?:\\{\\{(t.*?)\\}\\}|\\[\\[(.*?)\\]\\])" + "(.*)$");
	protected static final Pattern NEXT_TRANSLATION_PATTERN = Pattern.compile(
			"^(.*?)(?:[,;]+|(\\{\\{t\\S*\\|))(.*)$");
	
	protected String currentSense; 		
	protected Map<String, List<IWiktionaryTranslation>> sensNum2trans;

	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public ENTranslationHandler() {
		super("Translations");
	}

	@Override
	public boolean processHead(final String text, final ParsingContext context) {
		currentSense = "";
		sensNum2trans = new TreeMap<String, List<IWiktionaryTranslation>>();
		return true;			
	}
	
	@Override
	public boolean processBody(String text, final ParsingContext context) {
		text = text.trim();
				
		if (text.startsWith("{{trans-mid}}") || text.startsWith("{{mid}}"))
			return true;
		if (text.startsWith("{{trans-top|")) {
			currentSense = text.substring(10, text.length() - 2);
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
		
//		if (text.startsWith("*")) {
		Matcher matcher = TRANSLATION_PATTERN.matcher(text);
		if (matcher.find()) {
			boolean usesTemplate = true;
			String language = matcher.group(1);
			String prefix = matcher.group(2);
			String translation = matcher.group(3);
			if (translation == null) {
				translation = matcher.group(4);
				usesTemplate = false;
			}
			String additionalInformation = matcher.group(5);
						
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

				// Prepare the translation.
				language = language.replace("[", "");
				language = language.replace("]", "");
				language = language.replace("*", "");		
				language = language.trim();
				ILanguage translatedLang = Language.findByName(language);
				
				Map<String, String> namedParams = new TreeMap<String, String>();
				String[] fields = null;
				String translationText = translation;
				if (usesTemplate) {
					fields = reorderTemplateParams(StringUtils.split(translation, '|'), namedParams);
					if (fields != null && fields.length >= 3) {
						if (translatedLang == null)
							translatedLang = Language.findByCode(fields[1]);
						translationText = fields[2].trim();
					} else
						translationText = null;
				} else {
					int i = translation.indexOf('|'); // [[...|...]]
					if (i >= 0)
						translationText = cleanText(translation.substring(i + 1));
				}

				if (translationText != null && !translationText.isEmpty()) {
					//TODO: sub languages.
//					if (translatedLang == null && !language.isEmpty() && !"Roman".equals(language) && !"Cyrillic".equals(language))
//						System.err.println("Unknown language for translation: " + language + " (" + context.getPage().getTitle() + ")");
//						translatedLang = Language.UNKNOWN;

					/*if (!usesTemplate && !additionalInformation.contains("{")) {
						translationText = translationText + additionalInformation;
						translationText = translationText.replace("[", "");
						translationText = translationText.replace("]", "");
//							translationText = CLEANUP_PATTERN.matcher(translationText).replaceAll("");
						translationText = translationText.trim();
						additionalInformation = "";
					}*/

					WiktionaryTranslation trans = new WiktionaryTranslation(translatedLang, translationText);

					String transliteration = namedParams.get("tr");
					if (transliteration != null)
						trans.setTransliteration(cleanText(transliteration));
					
					String gender = null;
					if (fields != null && fields.length > 3 && !fields[3].contains("="))
						gender = "{{" + fields[3] + "}}";
					if (gender != null)
						additionalInformation = gender + " " + additionalInformation;
					
					additionalInformation = cleanText(prefix + " " + additionalInformation);
					trans.setAdditionalInformation(additionalInformation);

					// Save the translation
					List<IWiktionaryTranslation> translations = sensNum2trans.get(currentSense);
					if (translations == null) {
						translations = new ArrayList<IWiktionaryTranslation>();
						sensNum2trans.put(currentSense, translations);
					}
					translations.add(trans);
				}
			
				// Perform another iteration if there is remaining text.
				if (remainingText != null) {
					do {
						matcher = ADDITIONAL_TRANSLATION_PATTERN.matcher(remainingText);
						if (matcher.find()) {
							usesTemplate = true;
							prefix = matcher.group(1);
							translation = matcher.group(2);
							if (translation == null) {
								translation = matcher.group(3);
								usesTemplate = false;
							}
							additionalInformation = matcher.group(4);
							break;
						} else {
							matcher = NEXT_TRANSLATION_PATTERN.matcher(remainingText);
							if (matcher.find()) {
								String tmp = remainingText;
								remainingText = matcher.group(2) + matcher.group(3);
								if (tmp.equals(remainingText))
									remainingText = null; // avoid infinite loop!
							} else
								remainingText = null;
						}
					} while (remainingText != null);
				}				
			} while (remainingText != null);			
		}
		
		return true;
	}
	
	protected String[] reorderTemplateParams(final String[] fields,
			Map<String, String> namedParams) {
		if (fields == null)
			return null;
		
		String[] result = new String[fields.length];
		int idx = 0;
		for (int i = 0; i < fields.length; i++) {
			int j = fields[i].indexOf('=');
			if (j >= 0) {
				String key = fields[i].substring(0, j);
				String value = fields[i].substring(j + 1);
				
//				namedParams.put(key, value);
				if (namedParams.containsKey(key)) {
					int suffix = 2;
					while (namedParams.containsKey(key + suffix))
						suffix++;
					namedParams.put(key + suffix, value);
				} else
				namedParams.put(key, value);
			} else
				result[idx++] = fields[i];
		}
		
		for (Entry<String, String> e : namedParams.entrySet())
			result[idx++] = e.getKey() + "=" + e.getValue();
		return result;
	}

	protected String cleanText(final String text) {
		String result = stripMarkup(text);
		return result.trim();
	}

	private String stripMarkup(String text) {
		String result = text;
		result = result.replace("[[", "");
		result = result.replace("]]", "");
		result = result.replace("'''", "");
		result = result.replace("''", "");
		return result;
	}

	/**
	 * Add parsed translation into senseEntry. If no mapping is found, the translation is added to posEntry.
	 */
	public void fillContent(final ParsingContext context) {
		WiktionaryEntry posEntry = context.findEntry();
		
		if (posEntry != null) {
			for (Entry<String, List<IWiktionaryTranslation>> trans : sensNum2trans.entrySet()) {
				WiktionarySense sense = findMatchingSense(trans.getKey(), posEntry);
				if (sense == null)
					sense = posEntry.getUnassignedSense();
				
				for (IWiktionaryTranslation translation : trans.getValue())
					sense.addTranslation(translation);
			}
		}
	}

}
