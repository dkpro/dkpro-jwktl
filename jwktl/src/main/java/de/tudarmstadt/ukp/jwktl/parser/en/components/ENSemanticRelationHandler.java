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

import java.util.Locale;

import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.util.SimilarityUtils;

import static de.tudarmstadt.ukp.jwktl.api.entry.WikiString.removeWikiLinks;

/**
 * Parser component for extracting
 * <a href="https://en.wiktionary.org/wiki/Wiktionary:Semantic_relations">semantic relations</a>
 * from the English Wiktionary.
 * <p/>
 * A semantic relation ship can specify a target sense. This handler tries to determine the target
 * sense using {@link #findMatchingSense(WiktionaryEntry, String)}.
 */
public class ENSemanticRelationHandler extends ENRelationHandler {
	public ENSemanticRelationHandler(RelationType relationType, String... labels) {
		super(relationType, labels);
	}

	@Override
	protected WiktionarySense findMatchingSense(WiktionaryEntry posEntry, WordList wordList) {
		WiktionarySense matchingSense = findMatchingSense(posEntry, wordList.comment);

		if (matchingSense != null) {
			return matchingSense;
		} else {
			return super.findMatchingSense(posEntry, wordList);
		}
	}

	/**
	 * @return the word sense whose sense definition
	 * corresponds to the specified comment (sense marker). The matching
	 * of the corresponding word sense is achieved by word similarity
	 * metrics. Returns <code>null</code> if no matching word sense
	 * could be found.
	 */
	public static WiktionarySense findMatchingSense(final WiktionaryEntry entry, final String marker) {
		// Monosemous entries.
		if (entry.getSenseCount() == 1)
			return entry.getSense(1);

		// Empty sense marker.
		if (marker == null || marker.isEmpty())
			return null;

		WiktionarySense best1Gram = null;
		WiktionarySense best3Gram = null;
		double best1GramScore = -1;
		double best3GramScore = -1;

		for (WiktionarySense sense : entry.senses()) {
			if (sense.getIndex() <= 0)
				continue; // Skip unassigned sense.

			String gloss = removeWikiLinks(sense.getGloss().getText()).toLowerCase(Locale.ENGLISH);
			double similarity = SimilarityUtils.wordSim(marker, gloss);
			if (similarity > best1GramScore){
				best1GramScore = similarity;
				best1Gram = sense;
			}
			similarity = SimilarityUtils.similarity(marker, gloss);
			if (similarity > best3GramScore){
				best3GramScore = similarity;
				best3Gram = sense;
			}
		}

		if (best1Gram == null && best3Gram == null) {
			return null;
		}

		if (best1GramScore <= 0 && best3GramScore <= 0) {
			return null;
		}

		if (best1GramScore > best3GramScore) {
			return best1Gram;
		} else {
			return best3Gram;
		}
	}
}
