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

import java.util.Objects;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public abstract class CaseHandler extends PatternBasedParameterHandler {

	private GrammaticalCase grammaticalCase;

	public CaseHandler(String regex, GrammaticalCase grammaticalCase) {
		super(regex);
		Objects.requireNonNull(grammaticalCase, "grammaticalCase must not be null");
		this.grammaticalCase = grammaticalCase;
	}

	@Override
	public void handle(String label, String value, WiktionaryWordForm wordForm, ParsingContext context) {
		wordForm.setCase(grammaticalCase);
	}
}
