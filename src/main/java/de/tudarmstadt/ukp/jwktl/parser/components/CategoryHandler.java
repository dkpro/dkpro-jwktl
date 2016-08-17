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

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Extract categories.
 * @author Lizhen Qu
 * @author Christian M. Meyer
 */
public class CategoryHandler extends BlockHandler {
	private Pattern categoryPattern;
	private Set<String> categories = new HashSet<>();

	/** Instantiate the categories worker using the given categories head. This
	 *  is to be the prefix appearing in categories templates (e.g.,
	 *  "Kategorie" for German). */
	public CategoryHandler(final String categoryHead) {
		categoryPattern = Pattern.compile(
			"\\[\\[:?" + categoryHead + ":([^\\]]+)\\]\\]");
	}
	
	public boolean canHandle(String blockHeader) {
		return blockHeader != null && categoryPattern.matcher(blockHeader).find();
	}
	
	public boolean processHead(final String textLine, final ParsingContext context) {
		if (textLine == null) {
			throw new IllegalStateException();
		}
		Matcher matcher = categoryPattern.matcher(textLine);
		while (matcher.find()) {
			final String category = matcher.group(1);
			final int sortKeyStart = category.indexOf("|");
			if (sortKeyStart != -1) {
				categories.add(category.substring(0, sortKeyStart));
			} else {
				categories.add(category);
			}
		}
		if (categories.isEmpty()) {
			throw new IllegalStateException("Category pattern did not match");
		}
		return true;
	}

	public void fillContent(final ParsingContext context) {
		for (String category : categories) {
			context.getPage().addCategory(category);
		}
		categories.clear();
	}
}
