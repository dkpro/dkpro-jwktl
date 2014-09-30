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

import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Parser component for extracting etymological information from the 
 * German Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class DEEtymologyHandler extends DEBlockHandler {
	
	protected StringBuilder etymology;
	
	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public DEEtymologyHandler() {
		super("Herkunft");
	}
	
	@Override
	public boolean processHead(final String textLine, final ParsingContext context) {	
		etymology = new StringBuilder();
		return super.processHead(textLine, context);
	}

	@Override
	public boolean processBody(String textLine, final ParsingContext context) {
		textLine = textLine.trim();
		if (!textLine.isEmpty())			
			etymology.append(textLine);
		return false;
	}

	public void fillContent(final ParsingContext context) {		
		if (etymology.length() > 0) {
			WiktionaryEntry posEntry = context.findEntry();
			posEntry.setWordEtymology(new WikiString(etymology.toString()));
		}
	}
	
}
