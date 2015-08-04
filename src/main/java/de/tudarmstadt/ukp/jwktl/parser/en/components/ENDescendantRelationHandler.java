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

import de.tudarmstadt.ukp.jwktl.api.RelationType;

public class ENDescendantRelationHandler extends ENRelationHandler {
	public ENDescendantRelationHandler(String... labels) {
		super(RelationType.DESCENDANT, labels);
	}

	@Override
	protected WordList parseWordList(String text) {
		WordList list =  super.parseWordList(text);
		if (list.size() > 1) {
			return new WordList(list.comment, fixDescendantWordList(list.words));
		} else {
			return list;
		}
	}

	private static List<String> fixDescendantWordList(List<String> wordList) {
		String firstWord = wordList.get(0);
		final int colon = (firstWord == null ? -1 : firstWord.indexOf(':'));
		if (colon != -1) {
			List<String> fixed = new ArrayList<String>(wordList.size());
			fixed.add(firstWord);

			String language = firstWord.substring(0, colon);
			for (int i = 1; i < wordList.size(); i++) {
				String word = wordList.get(i);
				if (word.indexOf(':') == -1)
					fixed.add(language + ": " + word);
				else
					fixed.add(word);
			}
			return fixed;
		} else {
			return wordList;
		}
	}
}
