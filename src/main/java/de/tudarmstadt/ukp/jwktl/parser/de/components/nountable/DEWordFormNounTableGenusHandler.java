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
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEGenderText;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public class DEWordFormNounTableGenusHandler extends DEWordFormNounTablePatternIndexParameterHandler {

	private static final Logger logger = Logger.getLogger(DEWordFormNounTableGenusHandler.class.getName());

	protected static final String GENUS_PATTERN =
			// equals("Genus")
			"^Genus$|" +
			// equals("Genus 1") || equals("Genus 2") ||
			// equals("Genus 3") || equals("Genus 4") ||
					"^Genus\\s([1-4])$";

	public DEWordFormNounTableGenusHandler(DEWordFormNounTableExtractor nounTableHandler) {
		super(nounTableHandler, GENUS_PATTERN);
	}

	@Override
	public void handleIfFound(WiktionaryWordForm wordForm, String label, Integer index, String value, Matcher matcher,
			ParsingContext context) {
		try {
			final DEGenderText genderText = DEGenderText.of(value);
			nounTableHandler.setGenus(genderText, index);
		} catch (IllegalArgumentException unrecognizedGenderTextException) {
			logger.warning(MessageFormat.format("Page for word [{0}] has an unrecognized genus [{1}].",
					context.getPage().getTitle(), value));
		} catch (NullPointerException nullGenderTextException) {
			logger.warning(MessageFormat.format("Page for word [{0}] has a null genus [{1}].",
					context.getPage().getTitle(), value));
		}
	}
}
