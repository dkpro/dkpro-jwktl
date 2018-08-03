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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;

// TODO Is there some interface for this kind of component?
public class DEGrammaticalGenderParser {

	public List<GrammaticalGender> parseGenders(String genderText) throws IllegalArgumentException {
		Objects.requireNonNull(genderText, "genderText must not be null.");
		List<GrammaticalGender> genders = new ArrayList<>(3);
		if ("m".equals(genderText))
			genders.add(GrammaticalGender.MASCULINE);
		else if ("f".equals(genderText) || "w".equals(genderText))
			genders.add(GrammaticalGender.FEMININE);
		else if ("n".equals(genderText) || "sächlich".equals(genderText))
			genders.add(GrammaticalGender.NEUTER);
		else if ("mf".equals(genderText)) {
			genders.add(GrammaticalGender.MASCULINE);
			genders.add(GrammaticalGender.FEMININE);
		} else if ("mn".equals(genderText) || "mn.".equals(genderText)) {
			genders.add(GrammaticalGender.MASCULINE);
			genders.add(GrammaticalGender.NEUTER);
		} else if ("fm".equals(genderText)) {
			genders.add(GrammaticalGender.FEMININE);
			genders.add(GrammaticalGender.MASCULINE);
		} else if ("fn".equals(genderText)) {
			genders.add(GrammaticalGender.FEMININE);
			genders.add(GrammaticalGender.NEUTER);
		} else if ("nm".equals(genderText)) {
			genders.add(GrammaticalGender.NEUTER);
			genders.add(GrammaticalGender.MASCULINE);
		} else if ("nf".equals(genderText)) {
			genders.add(GrammaticalGender.NEUTER);
			genders.add(GrammaticalGender.FEMININE);
		} else if ("mfn".equals(genderText)) {
			genders.add(GrammaticalGender.MASCULINE);
			genders.add(GrammaticalGender.NEUTER);
			genders.add(GrammaticalGender.FEMININE);
		} else {
			throw new IllegalArgumentException(MessageFormat.format("Unsupported gender text [{0}].", genderText));
		}
		return genders;
	}
}
