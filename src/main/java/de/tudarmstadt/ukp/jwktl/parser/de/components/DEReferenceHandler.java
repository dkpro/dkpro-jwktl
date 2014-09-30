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

import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Parser component for extracting references from the German Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class DEReferenceHandler extends DESenseIndexedBlockHandler<IWikiString> {
	
	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public DEReferenceHandler() {
		super("Referenzen");
	}

	@Override
	public boolean processBody(String textLine, final ParsingContext context) {
		return super.processBody(textLine, context);
	}
	
	protected List<IWikiString> extract(int index, final String text) {
		String reference = text;
		reference = StringUtils.strip(reference, "*: ");
//		reference = reference.replace('\n', ' ');
//		reference = StringUtils.removeReferences(reference);
//		reference = reference.trim();
		if (reference.isEmpty())
			return null;
		
		List<IWikiString> result = new ArrayList<IWikiString>();
		result.add(new WikiString(reference));
		return result;
	}

	protected void updateSense(final WiktionarySense sense, final IWikiString reference) {
		sense.addReference(reference);
	}

	protected void updatePosEntry(final WiktionaryEntry posEntry, final IWikiString reference) {
		posEntry.getUnassignedSense().addReference(reference);
	}

}
