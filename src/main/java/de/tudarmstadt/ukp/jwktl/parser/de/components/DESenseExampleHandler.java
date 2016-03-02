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

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryExample;
import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryExample;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Parser component for extracting example sentences from the German Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class DESenseExampleHandler extends DESenseIndexedBlockHandler<IWiktionaryExample> {

	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public DESenseExampleHandler() {
		super("Beispiele");
	}
	

	protected List<IWiktionaryExample> extract(int index, final String text) {
		String example = text;
		example = example.replace('\n', ' ');
		example = StringUtils.removeReferences(example);
		example = example.trim();
		if (example.isEmpty())
			return null;

		List<IWiktionaryExample> result = new ArrayList<>();
		result.add(new WiktionaryExample(new WikiString(example)));
		return result;
	}

	protected void updateSense(final WiktionarySense sense, final IWiktionaryExample example) {
		sense.addExample(example);
	}

	protected void updatePosEntry(final WiktionaryEntry posEntry, final IWiktionaryExample example) {
		posEntry.getUnassignedSense().addExample(example);
	}
	
}
