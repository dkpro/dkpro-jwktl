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

import java.text.MessageFormat;
import java.util.Objects;

import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;

public enum DEGenderText {
	
	NULL(null, null),
	M("m", GrammaticalGender.MASCULINE),
	F("f", GrammaticalGender.FEMININE),
	N("n", GrammaticalGender.NEUTER),
	X("x", null),
	_0("0", null),
	PL("pl", null),
	P_L("Pl", null);
	
	private final String genderText;
	private final GrammaticalGender gender;

	private DEGenderText(String genderText, GrammaticalGender gender) {
		this.genderText = genderText;
		this.gender = gender;
	}
	
	public GrammaticalGender asGrammaticalGender() {
		return this.gender;
	}

	public static DEGenderText of(String genderText) {
		Objects.requireNonNull(genderText, "genderText must not be null");
		for (DEGenderText value : values()) {
			if (Objects.equals(genderText, value.genderText)) {
				return value;
			}
		}
		throw new IllegalArgumentException(MessageFormat.format("Unrecognized gender text [{0}].", genderText));
	}
}