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

import java.util.Arrays;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Parser component for extracting etymological information from 
 * the English Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class ENEtymologyHandler extends ENBlockHandler {	
	private static List<String> SPELLINGS = Arrays.asList("etymology", "etymolgy",
			"eytomology", "etmology", "eymology");
	protected StringBuffer contentBuffer;

	public boolean canHandle(String blockHeader) {
		return SPELLINGS.contains(StringUtils.strip(blockHeader, "{}=: 1234567890").toLowerCase());
	}
	
	@Override
	public boolean processHead(String textLine, ParsingContext context) {	
		contentBuffer = new StringBuffer();
		return true;
	}

	@Override
	public boolean processBody(String textLine, ParsingContext context) {
		if (!textLine.isEmpty() && !textLine.startsWith("===")) {
			contentBuffer.append(textLine);
		}
		return false;
	}

	public void fillContent(final ParsingContext context) {
		if (!contentBuffer.toString().trim().isEmpty()) {
			context.setEtymology(new WikiString(contentBuffer.toString().trim()));
		} else {
			context.setEtymology(null);
		}
	}
}
