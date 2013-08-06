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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Parser component for extracting the lemma and its language from the 
 * German Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class DEWordLanguageHandler extends DEBlockHandler {

	/** language regular expression pattern*/
	private static final Pattern LANGUAGE_PATTERN = Pattern.compile("^==\\s*([^=].*)\\s*\\(?\\{\\{\\s*Sprache\\s*\\|\\s*([^}]+?)\\s*\\}\\}");
	
	protected String lemma;
	protected ILanguage language;
	
	/** Determine if the text line contains the language pattern. If the 
	 *  language pattern is found, the entry's word and its language will 
	 *  be extracted from the text line. */
	public boolean canHandle(final String blockHeader) {
		if (blockHeader == null)
			return false;
		
		lemma = null;
		language = null;
		Matcher matcher = LANGUAGE_PATTERN.matcher(blockHeader);
		if (!matcher.find()) 
			return false;
		
		lemma = matcher.group(1);
		language = Language.findByName(matcher.group(2));
		return true;
	}
		
	/** Store the word and its language in the parsing context. */
	public void fillContent(final ParsingContext context) {
		context.setLanguage(language);
		context.setHeader(lemma);
	}
	
}
