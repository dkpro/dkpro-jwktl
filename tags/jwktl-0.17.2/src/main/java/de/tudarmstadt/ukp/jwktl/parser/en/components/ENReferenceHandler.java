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

import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Parser component for extracting references and external links from 
 * the English Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class ENReferenceHandler extends ENBlockHandler {
	
	protected List<IWikiString> references;
	protected boolean inTemplate;
	
	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public ENReferenceHandler() {
		super("References", "External links", "External lnks");
	}

	@Override
	public boolean processHead(String textLine, ParsingContext context) {
		references = new ArrayList<IWikiString>();
		inTemplate = false;
		return true;
	}
	
	@Override
	public boolean processBody(String textLine, ParsingContext context) {
		textLine = textLine.trim();
		if (textLine.startsWith("{{quote-")) {
			inTemplate = true;
		} else
		if (inTemplate || textLine.startsWith("|")) {
			if (textLine.contains("}}"))
				inTemplate = false;
		} else
		if (textLine.startsWith("{{")) {
			references.add(new WikiString(textLine.trim()));
		} else
		if (textLine.startsWith("*")) {
			textLine = textLine.substring(1);
			references.add(new WikiString(textLine.trim()));
		} else
			return false;
		
		return true;
	}

	/**
	 * Add external links to WordEntry
	 */
	public void fillContent(final ParsingContext context) {
		// Add references to all previous entries of the same language.
		WiktionaryEntry entry = context.findEntry();
		for (WiktionaryEntry prevEntry : context.getPage().entries())
			if (Language.equals(prevEntry.getWordLanguage(), entry.getWordLanguage()))
				for (IWikiString reference : references)
					prevEntry.getUnassignedSense().addReference(reference);
	}
	
}
