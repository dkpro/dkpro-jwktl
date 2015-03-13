/*******************************************************************************
 * Copyright 2015
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
package de.tudarmstadt.ukp.jwktl.parser.en.components;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.api.util.TemplateParser;
import de.tudarmstadt.ukp.jwktl.api.util.TemplateParser.Template;


/**
 * Support for parsing word forms for non-English entries in the English Wiktionary.
 */
public class ENNonEngWordFormHandler implements IWordFormHandler, TemplateParser.ITemplateHandler {
	private static final Pattern HEAD_PATTERN = Pattern.compile("\\A\\{\\{head|");
	private static final Pattern NOUN_PATTERN = Pattern.compile("\\A\\{\\{(\\w+)\\-noun");
	private GrammaticalGender gender;

	@Override
	public boolean parse(String line) {
		if (HEAD_PATTERN.matcher(line).find() || NOUN_PATTERN.matcher(line).find()) {
			TemplateParser.parse(line, this);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<IWiktionaryWordForm> getWordForms() {
		return Collections.emptyList();
	}

	@Override
	public GrammaticalGender getGender() {
		return gender;
	}

	@Override
	public String handle(Template template) {
		if (template.getName().endsWith("-noun")) {
			handleGenericNounTemplate(template);
		} else if (template.getName().equals("g")) {
			handleGenericNounTemplate(template);
		} else if (template.getName().equals("head")) {
			handleHeadwordTemplate(template);
		}
		return null;
	}

	// pt-noun: m, f, mf, m-f, m-p, f-p, m-f-p, morf (deprecated in favour of m|g2=f), and ?. g2=The second gender, to be used when different people use the same word with different genders
	// fr-noun: m, f, m-p or f-p. It also accepts mf as a shortcut for m|g2=f. g2= The second gender, if any.
	// es-noun: m, f, m-p, f-p or mf
	// de-noun: Use m, f, n for neuter. To specify more than one gender, use g2= or g3=
	private void handleGenericNounTemplate(Template template) {
		if (template.getNumberedParamsCount() > 0) {
			String genderParam = template.getNumberedParam(0);
			gender = extractGender(genderParam);
		}
	}

	// g= and g2=, g3=...
	// m, m-p, m-an-p, f-d, m-p, m-p etc.
	private void handleHeadwordTemplate(Template template) {
		final String genderParam = template.getNamedParam("g");
		gender = extractGender(genderParam);
	}

	private static GrammaticalGender extractGender(String genderParam) {
		if (genderParam == null) {
			return null;
		}
		// TODO: properly parse gender combinations
		GrammaticalGender gender = null;
		if ("m".equals(genderParam) || genderParam.startsWith("m")) {
			gender = GrammaticalGender.MASCULINE;
		} else if ("f".equals(genderParam) || genderParam.startsWith("f")) {
			gender = GrammaticalGender.FEMININE;
		} else if ("n".equals(genderParam)) {
			gender = GrammaticalGender.NEUTER;
		}
		return gender;
	}
}
