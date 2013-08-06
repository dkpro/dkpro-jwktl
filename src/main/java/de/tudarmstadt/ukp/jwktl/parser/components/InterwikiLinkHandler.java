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
package de.tudarmstadt.ukp.jwktl.parser.components;

import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Generic parser component for extracting interwiki links (e.g., [de:dog])
 * from the Wiktionary article pages.
 * @author Christian M. Meyer
 * @author Lizhen Qu
 *
 */
public class InterwikiLinkHandler extends BlockHandler {
	
	protected String categoryHead;
	protected String language;
	
	/** Initializes the handler for the specified category head 
	 *  (e.g., "Category"). The category head is required for distinugishing
	 *  between categories and interwiki links. */
	public InterwikiLinkHandler(final String categoryHead) {
		this.categoryHead = categoryHead;
	}

	public boolean canHandle(String blockHeader) {
		// Check if the line encodes an interwiki link.
		String line = blockHeader.trim();
		boolean isBracketed = line.startsWith("[[") && line.endsWith("]]");		
		return (line.contains(":") && !line.contains(categoryHead) && isBracketed);
	}

	@Override
	public boolean processHead(String textLine, ParsingContext context) {
		// Extract the language of the interwiki link.
		language = null;
		String line = textLine.trim();
		int colonIndex = line.indexOf(":");
		if (colonIndex != -1) {
			language = line.substring(2, colonIndex).trim();
		}
		return true;
	}

	public void fillContent(final ParsingContext context) {
		// Add the interwiki link to the current page.
		if (language != null)
			context.getPage().addInterWikiLink(language);
	}

}
