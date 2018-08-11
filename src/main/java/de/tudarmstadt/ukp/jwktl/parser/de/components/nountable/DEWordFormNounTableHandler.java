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
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEGenderText;
import de.tudarmstadt.ukp.jwktl.parser.util.ITemplateParameterHandler;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public class DEWordFormNounTableHandler implements ITemplateParameterHandler {

	public void reset() {
		this.genera = new DEGenderText[4];
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

	protected DEGenderText[] genera = new DEGenderText[4];

	/**
	 * Returns genus by index.
	 * @param index index of the genus, must be between 1 and 4.
	 * @return Genus by index or <code>null</code> if genus by this index was not set yet.
	 * @throws IllegalArgumentException If index is not between 1 and 4.
	 */
	DEGenderText getGenusByIndex(int index) {
		if (index < 1 || index > 4) {
			throw new IllegalArgumentException("Genus index must be 1, 2, 3 or 4.");
		}
		return genera[index - 1];
	}

	/**
	 * Sets genus by index
	 * @param genderText genus.
	 * @param index index of the genus, must be between 1 and 4.
	 * @throws IllegalArgumentException If index is not between 1 and 4.
	 */
	void setGenusByIndex(DEGenderText genderText, Integer index) {
		if (index < 1 || index > 4) {
			throw new IllegalArgumentException("Genus index must be 1, 2, 3 or 4.");
		}
		this.genera[index - 1] = genderText;
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