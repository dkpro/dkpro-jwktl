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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Extract categories.
 * @author Lizhen Qu
 * @author Christian M. Meyer
 */
public class CategoryHandler extends BlockHandler {
	
	protected Pattern categoryPattern;
	protected String category;
	
	/** Instanciate the category worker using the given category head. This
	 *  is to be the prefix appearing in category templates (e.g., 
	 *  "Kategorie" for German). */
	public CategoryHandler(final String categoryHead) {
		categoryPattern = Pattern.compile(
			"^\\[\\[" + categoryHead + ":(.+)\\]\\]$");
	}
	
	public boolean canHandle(String blockHeader) {
		// Check if the category pattern matches.
		return categoryPattern.matcher(blockHeader).matches();
	}
	
	public boolean processHead(final String textLine, final ParsingContext context) {
		Matcher matcher = categoryPattern.matcher(textLine);
		if (matcher.find()) {
			category = matcher.group(1);
			return true;
		} else
			throw new IllegalStateException("Category pattern did not match");		
	}

	public void fillContent(final ParsingContext context) {
		// Add the category to the current page.
		if (category != null && !category.isEmpty())
			context.getPage().addCategory(category);
	}
	
}
