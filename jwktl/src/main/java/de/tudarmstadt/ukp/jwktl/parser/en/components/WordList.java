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
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.parser.util.WordListProcessor;

class WordList implements Iterable<String> {
	@Deprecated
	private static final Pattern PATTERN_WORD = Pattern.compile("\\w+");

	final String comment;
	final List<String> words;

	WordList(String comment, List<String> words) {
		this.comment = isValid(comment) ? comment : null;
		this.words = words;
	}

	public int size() {
		return words.size();
	}


	public static WordList parse(final String text) {
		String comment = null;
		List<String> result = new ArrayList<String>();

		int braceStartIndex = text.indexOf("(''");
		int braceEndIndex = -1;
		int curlyStartIndex = text.indexOf("{{");
		int curlyEndIndex = text.indexOf("}}");
		int startIndex = -1;
		int endIndex = -1;
		if((braceStartIndex != -1 && curlyStartIndex == -1) || (braceStartIndex != -1 && curlyStartIndex != -1 && braceStartIndex < curlyStartIndex)){
			int endOffset = 3;
			braceEndIndex = text.indexOf("'')", braceStartIndex);
			if(braceEndIndex == -1){
				braceEndIndex = text.indexOf(")", braceStartIndex);
				endOffset = 1;
			}
			if(braceEndIndex == -1){
				braceEndIndex = text.indexOf("''", braceStartIndex+3);
				endOffset = 2;
			}
			if(braceStartIndex + 3 < braceEndIndex){
				String s = text.substring(braceStartIndex+3,braceEndIndex);
				//startIndex = braceStartIndex;
				endIndex = braceEndIndex + endOffset;
				comment = s;
			}
		}else{
			//CM for preventing bug added third
			if(curlyStartIndex != -1 && curlyEndIndex != -1 && (curlyEndIndex >= curlyStartIndex)){
				int midIndex = text.indexOf('|',curlyStartIndex);
				if(midIndex != -1 && midIndex < curlyEndIndex){
					String templateName = text.substring(curlyStartIndex + 2, midIndex);
					if ("l".equals(templateName) || templateName.startsWith("l/")) {
						startIndex = -1;
						endIndex = -1;
						curlyEndIndex = -1;
					} else
						comment = text.substring(midIndex+1,curlyEndIndex);
				}else{
					comment = text.substring(curlyStartIndex+2,curlyEndIndex);
				}
				//startIndex = curlyStartIndex;		// bug fix: this would cause to jump into (startIndex>0) and parse the words before the curly brackets, i.e. empty syns or nothing
				endIndex = curlyEndIndex + 2;
			}
		}

		WordListProcessor wordListFilter = new WordListProcessor();
		String relationStr = text;
		if (startIndex > 0)
			relationStr = text.substring(0, startIndex);
		else
		if (endIndex > 0 && endIndex < text.length())
			relationStr = text.substring(endIndex);
		else
		if (startIndex == -1 && endIndex == -1)
			relationStr = text;
		else
			return new WordList(comment, result);

		result.addAll(wordListFilter.splitWordList(relationStr));
		return new WordList(comment, result);
	}

	@Override
	public Iterator<String> iterator() {
		return words.iterator();
	}

	private static boolean isValid(String comment) {
		return comment != null && PATTERN_WORD.matcher(comment).find();
	}
}
