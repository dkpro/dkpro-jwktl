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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEGenderText;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public class DEWordFormNounTableExtractor {

	private static final Logger logger = Logger.getLogger(DEWordFormNounTableExtractor.class.getName());

	public void reset() {
		this.genera = new HashMap<>();
	}

	private List<DEWordFormNounTablePatternIndexParameterHandler> handlers = Arrays.asList(
			// Genus
			new DEWordFormNounTableGenusHandler(this),
			// Singular
			new DEWordFormNounTableSingularHandler(this),
			// Einzahl
			new DEWordFormNounTableEinzahlHandler(this),
			// Plural
			new DEWordFormNounTablePluralHandler(this),
			// Mehrzahl
			new DEWordFormNounTableMehrzahlHandler(this));

	protected Map<Integer, DEGenderText> genera = new HashMap<>();

	DEGenderText findGenusByIndex(Integer index) {
		DEGenderText genderText = this.genera.get(index);
		if (genderText == null) {
			if (index == null) {
				genderText = this.genera.get(1);
			} else if (index.intValue() == 1) {
				genderText = this.genera.get(null);
			}
		}
		return genderText;
	}

	void setGenus(DEGenderText genderText, Integer index) {
		this.genera.put(index, genderText);
	}

	public boolean extractNounTable(WiktionaryWordForm wordForm, String label, String value, ParsingContext context) {

		for (DEWordFormNounTablePatternIndexParameterHandler handler : this.handlers) {
			if (handler.canHandle(wordForm, label, value, context)) {
				handler.handle(wordForm, label, value, context);
				return true;
			}
		}
		logger.warning(MessageFormat.format("Page for word [{0}] has an unrecognized label [{1}].",
				context.getPage().getTitle(), label));
		return false;
	}
}