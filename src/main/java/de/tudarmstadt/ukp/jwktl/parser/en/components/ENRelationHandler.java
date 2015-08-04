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
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Parser component for extracting relations from the English Wiktionary.
 * @author Christian M. Meyer
 * @author Christof Müller
 * @author Lizhen Qu
 */
public class ENRelationHandler extends ENBlockHandler {
	/**
	 * Initializes the block handler for the given relation type and
	 * section headers.
	 */
	public ENRelationHandler(final RelationType relationType, final String... labels) {
		super(labels);
		this.relationType = relationType;
	}

	protected RelationType relationType;
	protected List<WordList> relationList;

	@Override
	public boolean processHead(String textLine, ParsingContext context) {
		relationList = new ArrayList<WordList>();
		return super.processHead(textLine, context);
	}

	/**
	 * Extract word list from the given text line
	 */
	@Override
	public boolean processBody(final String text, final ParsingContext context) {
		final String line = text.trim();
		if (!line.isEmpty() && line.startsWith("*")) {
			relationList.add(parseWordList(line.substring(1)));
		}
		return false;
	}

	/**
	 * Add word list to senseEntry.
	 */
	@Override
	public void fillContent(final ParsingContext context) {
		WiktionaryEntry posEntry = context.findEntry();
		if (posEntry == null) {
			throw new RuntimeException("posEntry is null " + context.getPartOfSpeech());
		}

		for (WordList wordList : relationList) {
			WiktionarySense matchingSense = findMatchingSense(posEntry, wordList);
			for (String target : wordList) {
				matchingSense.addRelation(new WiktionaryRelation(target, relationType));
			}
		}
	}

	/**
	 * @return the target sense to use for this wordList. Defaults to the unassigned sense, subclasses
	 * should override if needed.
	 */
	protected WiktionarySense findMatchingSense(WiktionaryEntry posEntry, WordList wordList) {
		return posEntry.getUnassignedSense();
	}

	/**
	 * @return the parsed word list
	 */
	protected WordList parseWordList(String text) {
		return WordList.parse(text);
	}
}
