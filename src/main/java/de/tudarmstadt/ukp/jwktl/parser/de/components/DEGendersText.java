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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;

public enum DEGendersText {
	
	M("m", GrammaticalGender.MASCULINE),
	F("f", GrammaticalGender.FEMININE),
	W("w", GrammaticalGender.FEMININE),
	N("n", GrammaticalGender.NEUTER),
	SAECHLICH("sächlich", GrammaticalGender.NEUTER),
	MF("mf", GrammaticalGender.MASCULINE, GrammaticalGender.FEMININE),
	MN_DOT("mn.", GrammaticalGender.MASCULINE, GrammaticalGender.NEUTER),
	MN("mn", GrammaticalGender.MASCULINE, GrammaticalGender.NEUTER),
	FM("fm", GrammaticalGender.FEMININE, GrammaticalGender.MASCULINE),
	FN("fn", GrammaticalGender.FEMININE, GrammaticalGender.NEUTER),
	NM("nm", GrammaticalGender.NEUTER, GrammaticalGender.MASCULINE),
	NF("nf", GrammaticalGender.NEUTER, GrammaticalGender.FEMININE),
	MFN("mfn", GrammaticalGender.MASCULINE, GrammaticalGender.FEMININE, GrammaticalGender.NEUTER);
	
	private final String genderText;
	private final List<GrammaticalGender> genders;

	private DEGendersText(String genderText, GrammaticalGender... genders) {
		this.genderText = genderText;
		this.genders = Collections.unmodifiableList(Arrays.asList(genders));
	}
	
	public List<GrammaticalGender> asGrammaticalGenders() {
		return this.genders;
	}

	public static DEGendersText of(String genderText) {
		Objects.requireNonNull(genderText, "genderText must not be null");
		for (DEGendersText value : values()) {
			if (Objects.equals(genderText, value.genderText)) {
				return value;
			}
		}
		throw new IllegalArgumentException(MessageFormat.format("Unrecognized gender text [{0}].", genderText));
	}
}