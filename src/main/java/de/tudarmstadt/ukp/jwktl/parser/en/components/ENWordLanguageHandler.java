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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.util.IBlockHandler;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Parser component for extracting a words language from the English Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class ENWordLanguageHandler extends ENBlockHandler implements IBlockHandler {
	
	protected static final Pattern LANGUAGE_HEADER = Pattern.compile("^\\s*=+\\s*\\[*\\s*(.*?)\\s*\\]*\\s*=+");
	
	protected ILanguage language;
	
	public boolean canHandle(String blockHeader) {
		if ("----".equals(blockHeader)) {
			language = null;
			return true;
		}
		
		language = null;
//		System.out.println(textLine);
		Matcher matcher = LANGUAGE_HEADER.matcher(blockHeader);
		if (!matcher.find())
			return false;
				
//		System.out.println(matcher.group(1));
		language = Language.findByName(matcher.group(1));
		return (language != null);
	}

	@Override
	public boolean processHead(final String textLine, final ParsingContext context) {		
		return true;
	}

	@Override
	public boolean processBody(final String textLine, final ParsingContext context) {
		return false;
	}

	public void fillContent(final ParsingContext context) {
		context.setLanguage(language);
	}

}
