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
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEGenderText;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public class DEWordFormNounTableEinzahlHandler extends DEWordFormNounTablePatternIndexParameterHandler {

	protected static final String EINZAHL_PATTERN =
			// endsWith(" (Einzahl)")
			" \\(Einzahl\\)$|" +
			// endsWith(" (Einzahl 1)") || endsWith(" (Einzahl 2)") ||
			// endsWith(" (Einzahl 3)") || endsWith(" (Einzahl 4)")
					" \\(Einzahl\\s([1-4])\\)$";

	public DEWordFormNounTableEinzahlHandler(DEWordFormNounTableExtractor nounTableHandler) {
		super(nounTableHandler, EINZAHL_PATTERN);
	}

	@Override
	public void handleIfFound(WiktionaryWordForm wordForm, String label, Integer index, String value, Matcher matcher,
			ParsingContext context) {
		wordForm.setNumber(GrammaticalNumber.SINGULAR);
		final DEGenderText genderText = this.nounTableHandler.findGenusByIndex(index);
		if (genderText != null) {
			wordForm.setGender(genderText.asGrammaticalGender());
		}
	}
}
