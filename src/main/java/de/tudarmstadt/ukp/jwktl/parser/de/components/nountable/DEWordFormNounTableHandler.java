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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEGenderText;
import de.tudarmstadt.ukp.jwktl.parser.util.ITemplateParameterHandler;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public class DEWordFormNounTableHandler implements ITemplateParameterHandler {

	public void reset() {
		this.genera = new HashMap<>();
	}

	private List<? extends ITemplateParameterHandler> handlers = Arrays.asList(
			// Genus
			new GenusHandler(this),
			// Singular
			new SingularHandler(this),
			// Einzahl
			new EinzahlHandler(this),
			// Plural
			new PluralHandler(this),
			// Mehrzahl
			new MehrzahlHandler(this),
			// Nominative
			new NominativeHandler(),
			// Genitive
			new GenitiveHandler(),
			// Dative
			new DativeHandler(),
			// Accusative
			new AccusativeHandler());

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

	@Override
	public boolean canHandle(String label, String value, WiktionaryWordForm wordForm, ParsingContext context) {
		return this.handlers.stream().anyMatch(handler -> handler.canHandle(label, value, wordForm, context));
	}
	
	@Override
	public void handle(String label, String value, WiktionaryWordForm wordForm, ParsingContext context) {
		for (ITemplateParameterHandler handler : this.handlers) {
			if (handler.canHandle(label, value, wordForm, context)) {
				handler.handle(label, value, wordForm, context);
			}
		}
	}
}