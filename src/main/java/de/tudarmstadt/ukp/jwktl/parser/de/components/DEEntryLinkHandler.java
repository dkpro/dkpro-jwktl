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

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Parser component for extracting entry links from the German Wiktionary.
 * Entry links are defined to refer to the information of another dictionary
 * article (e.g., for alternative spellings). 
 * @author Christian M. Meyer
 */
public class DEEntryLinkHandler extends DEBlockHandler {

	static final String[] ENTRY_LINK_TEMPLATES = new String[]{
		"Lemmaverweis",
		"Grundformverweis",
		"Alte Schreibweise",
		"Schweizer und Liechtensteiner Schreibweise"
	};
	
	protected String entryLink;
	protected String entryLinkType;
	
	/** Initalizes the handler. */
	public DEEntryLinkHandler() {
		entryLink = null;
	}

	public boolean canHandle(final String blockHeader) {
		if (blockHeader == null || blockHeader.isEmpty())
			return false;
		if (!blockHeader.startsWith("{{"))
			return false;
			
		for (int i = 0; i < ENTRY_LINK_TEMPLATES.length; i++) {
			String entryLinkTemplate = "{{" + ENTRY_LINK_TEMPLATES[i] + "|";
			if (blockHeader.startsWith(entryLinkTemplate)) {
				entryLink = blockHeader.substring(entryLinkTemplate.length(), 
						blockHeader.length() - 2);
				int idx = entryLink.indexOf('|');
				if (idx >= 0)
					entryLink = entryLink.substring(0, idx);
				entryLinkType = ENTRY_LINK_TEMPLATES[i];				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean processHead(final String textLine, final ParsingContext context) {
		if (entryLink != null) {
			WiktionaryEntry entry = context.findEntry();
			entry.setEntryLink(entryLink, entryLinkType);
		}
		return false;
	}

}
