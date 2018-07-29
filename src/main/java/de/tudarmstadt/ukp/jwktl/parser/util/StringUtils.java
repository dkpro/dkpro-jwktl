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
package de.tudarmstadt.ukp.jwktl.parser.util;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Set of static string-processing methods used throughout the software.
 * @author Christian M. Meyer
 */
public /*static*/ final class StringUtils {

	// Avoid instanciation.
	private StringUtils() {}
	
	/** Creates a new string array from the given string, in which each 
	 *  field corresponds to a token of the input text. Tokens are separated
	 *  from each other by the specified divider character. The method thus
	 *  corresponds to its perl sibling. It may be used for processing
	 *  tabular- or comme-separated files. */
	public static String[] split(String text, char divider) {
		int fieldCount = 1;
		for (char c : text.toCharArray())
			if (c == divider)
				fieldCount++;
		
		String[] result = new String[fieldCount];
		int i;
		int idx = 0;
		do {
			i = text.indexOf(divider);
			if (i >= 0) {
				result[idx++] = text.substring(0, i);
				text = text.substring(i + 1);
			}
		} while (i >= 0);
		result[idx] = text;
		return result;
	}

	/** Removes the specified characters from the left and right of the 
	 *  specified text. The method thus corresponds to the trim function,
	 *  but with arbitrary characters. */
	public static String strip(final String text, final String characters) {
		String result = text;
		while (result.length() > 0 && characters.indexOf(result.charAt(0)) >= 0)
			result = result.substring(1);
		while (result.length() > 0 && characters.indexOf(result.charAt(result.length() - 1)) >= 0)
			result = result.substring(0, result.length() - 1);
		return result;
	}

	private static final Pattern LITERATURE_PATTERN = Pattern.compile("\\{\\{Literatur\\|[^}]+\\}\\}|<ref[^>]*>.*?</ref>");
	
	/** Removes all references or literature patterns from the given input 
	 *  text. */
	public static String removeReferences(final String text) {
		return LITERATURE_PATTERN.matcher(text).replaceAll("");
	}

	/** Parses a string representation of index numbers and returns a set of
	 *  integers containing all numbers of the specified range. The method 
	 *  is capable of handling single numbers ("23" -&gt; {23}), multiple comma-,
	 *  or full-stop-separated numbers ("1,2. 4" -&gt; {1,2,4}", and number
	 *  ranges ("1-4" -&gt; {1,2,3,4}). */
	public static Set<Integer> compileIndexSet(final String indexedStr) {
		Set<Integer> result = new TreeSet<>();
		int lastIdx = -1;
		int currIdx = -1;
		for (char c : indexedStr.toCharArray()) {
			if (c >= '0' && c <= '9') {
				if (currIdx < 0)
					currIdx = 0;
				currIdx = currIdx * 10 + (c - '0');
			} else if (c == ',' || c == '.') {
				if (lastIdx >= 0) {
					for (int idx = lastIdx; idx <= currIdx; idx++)
						result.add(idx);
					lastIdx = -1;
				} else
					result.add(currIdx);
				currIdx = 0;
			} else if (c == '-' || c == '–') {
				lastIdx = currIdx;
				currIdx = 0;
			}
		}
		if (lastIdx >= 0) {
			for (int idx = lastIdx; idx <= currIdx; idx++)
				result.add(idx);
			lastIdx = -1;
		} else
			result.add(currIdx);
		return result;
	}

	public static String cleanText(final String text) {
		String result = stripMarkup(text);
		return result.trim();
	}

	public static String stripMarkup(String text) {
		String result = text;
		result = result.replace("[[", "");
		result = result.replace("]]", "");
		result = result.replace("'''", "");
		result = result.replace("''", "");
		return result;
	}
}
