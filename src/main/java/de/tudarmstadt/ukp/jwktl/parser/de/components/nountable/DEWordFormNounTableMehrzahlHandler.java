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
package de.tudarmstadt.ukp.jwktl.parser.de.components.nountable;

import java.util.regex.Matcher;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public class DEWordFormNounTableMehrzahlHandler extends DEWordFormNounTablePatternIndexParameterHandler {

	protected static final String MEHRZAHL_PATTERN =
			// endsWith(" (Mehrzahl)")
			"\\s\\(Mehrzahl\\)$|" +
			// endsWith(" (Mehrzahl 1)") || endsWith(" (Mehrzahl 2)") ||
			// endsWith(" (Mehrzahl 3)") || endsWith(" (Mehrzahl 4)")
					"\\s\\(Mehrzahl\\s([1-4])\\)$";

	public DEWordFormNounTableMehrzahlHandler(DEWordFormNounTableExtractor nounTableHandler) {
		super(nounTableHandler, MEHRZAHL_PATTERN);
	}

	@Override
	public void handleIfFound(WiktionaryWordForm wordForm, String label, Integer index, String value, Matcher matcher,
			ParsingContext context) {
		wordForm.setNumber(GrammaticalNumber.PLURAL);
	}
}
