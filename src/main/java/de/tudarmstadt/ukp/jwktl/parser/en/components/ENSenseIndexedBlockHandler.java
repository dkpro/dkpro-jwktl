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

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.util.SimilarityUtils;

/**
 * Abstract base class for extracting information types associated with
 * a certain word sense.
 * @author Christian M. Meyer
 */
public abstract class ENSenseIndexedBlockHandler extends ENBlockHandler {

	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public ENSenseIndexedBlockHandler(final String... labels) {
		super(labels);
	}

	/** Returns the word sense of the given entry whose sense definition 
	 *  corresponds to the specified comment (sense marker). The matching
	 *  of the corresponding word sense is achieved by word similarity 
	 *  metrics. Returns <code>null</code> if no maching word sense
	 *  could be found. */
	public static WiktionarySense findMatchingSense(final String comment,
			final WiktionaryEntry entry) {
		// Monosemous entries.
		if (entry.getSenseCount() == 1)
			return entry.getSense(1);

		// Empty sense marker.
		if (comment == null || comment.isEmpty())
			return null;
		
		WiktionarySense best1Gram = null;
		WiktionarySense best3Gram = null;
		double best1GramScore = -1;
		double best3GramScore = -1;

		for (WiktionarySense sense : entry.senses()) {
			if (sense.getIndex() <= 0)
				continue; // Skip unassigned sense.

			String gloss = sense.getGloss().getText();
			double similarity = SimilarityUtils.wordSim(comment, gloss);
			if (similarity > best1GramScore){
				best1GramScore = similarity;
				best1Gram = sense;
			}
			similarity = SimilarityUtils.similarity(comment, gloss);
			if (similarity > best3GramScore){
				best3GramScore = similarity;
				best3Gram = sense;
			}
		}

		if (best1Gram == null && best3Gram == null)
			return null;
		
		if (best1GramScore <= 0 && best3GramScore <= 0)
			return null;
		
		if (best1GramScore > best3GramScore)
			return best1Gram;
		else
			return best3Gram;
	}

}
