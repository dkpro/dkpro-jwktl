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

import java.text.MessageFormat;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEGenderText;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public class DEWordFormNounTableSingularHandler extends DEWordFormNounTablePatternIndexParameterHandler {

	private static final Logger logger = Logger.getLogger(DEWordFormNounTableSingularHandler.class.getName());

	protected static final String SINGULAR_PATTERN =
			// equals("Singular")
			"^Singular$|" +
			// equals("SINGULAR")
					"^SINGULAR$|" +
					// endsWith(" Singular")
					"\\sSingular$|" +
					// endsWith(" Singular*")
					"\\sSingular\\*$|" +
					// endsWith(" Singular**")
					"\\sSingular\\*\\*$|" +
					// This was a mistake in Freischurf.
					// endsWith(" Singular?")
					"\\sSingular\\?$|" +
					// endsWith(" Singular *")
					"\\sSingular\\s\\*$|" +
					// endsWith(" Singular 1") || endsWith(" Singular 2") ||
					// endsWith(" Singular 3") || endsWith(" Singular 4")
					"\\sSingular\\s([1-4])$|" +
					// endsWith(" Singular 1*") || endsWith(" Singular 2*") ||
					// endsWith(" Singular 3*") || endsWith(" Singular 4*")
					"\\sSingular\\s([1-4])\\*$|" +
					// endsWith(" Singular 1**") || endsWith(" Singular 2**") ||
					// endsWith(" Singular 3**") || endsWith(" Singular 4**")
					"\\sSingular\\s([1-4])\\*\\*$|" +
					// This was a mistake in Unschlitt and Einzelteil.
					// endsWith(" Singular* 1") || endsWith(" Singular* 2") ||
					// endsWith(" Singular* 3") || endsWith(" Singular* 4")
					"\\sSingular\\*\\s([1-4])$";

	public DEWordFormNounTableSingularHandler(DEWordFormNounTableExtractor nounTableHandler) {
		super(nounTableHandler, SINGULAR_PATTERN);
	}

	@Override
	public void handleIfFound(WiktionaryWordForm wordForm, String label, Integer index, String value, Matcher matcher,
			ParsingContext context) {
		wordForm.setNumber(GrammaticalNumber.SINGULAR);
		final DEGenderText genderText = this.nounTableHandler.findGenusByIndex(index);
		if (genderText != null) {
			wordForm.setGender(genderText.asGrammaticalGender());
		} else {
			logger.warning(MessageFormat.format("Page for word [{0}] has a label [{1}] without genus entry.",
					context.getPage().getTitle(), label));
		}
	}
}
