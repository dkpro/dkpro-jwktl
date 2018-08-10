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

public class DEWordFormNounTablePluralHandler extends DEWordFormNounTablePatternIndexParameterHandler {

	protected static final String PLURAL_PATTERN =
			// equals("Plural")
			"^Plural$|" +
			// equals("PLURAL")
					"^PLURAL$|" +
					// endsWith(" Plural")
					"\\sPlural$|" +
					// endsWith(" Plural*")
					"\\sPlural\\*$|" +
					// endsWith(" Plural**")
					"\\sPlural\\*\\*$|" +
					// endsWith(" Plural?")
					"\\sPlural\\?$|" +
					// endsWith(" Plural *")
					"\\sPlural\\s\\*$|" +
					// endsWith(" Plural 1") || endsWith(" Plural 2") ||
					// endsWith(" Plural 3") || endsWith(" Plural 4")
					"\\sPlural\\s([1-4])$|" +
					// endsWith(" Plural 1*") || endsWith(" Plural 2*") ||
					// endsWith(" Plural 3*") || endsWith(" Plural 4*")
					"\\sPlural\\s([1-4])\\*$|" +
					// endsWith(" Plural 1**") || endsWith(" Plural 2**") ||
					// endsWith(" Plural 3**") || endsWith(" Plural 4**")
					"\\sPlural\\s([1-4])\\*\\*$|" +
					// endsWith(" Plural* 1") || endsWith(" Plural* 2") ||
					// endsWith(" Plural* 3") || endsWith(" Plural* 4")
					"\\sPlural\\*\\s([1-4])$";

	public DEWordFormNounTablePluralHandler(DEWordFormNounTableExtractor nounTableHandler) {
		super(nounTableHandler, PLURAL_PATTERN);
	}

	@Override
	public void handleIfFound(WiktionaryWordForm wordForm, String label, Integer index, String value, Matcher matcher,
			ParsingContext context) {
		wordForm.setNumber(GrammaticalNumber.PLURAL);
	}
}
