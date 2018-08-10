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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.PatternUtils;

public class DEWordFormNounTableExtractor {

	private static final Logger logger = Logger.getLogger(DEWordFormNounTableExtractor.class.getName());

	public void reset() {
		this.genera = new HashMap<>();
	}

	protected static final Pattern GENUS_PATTERN = Pattern.compile(
			// equals("Genus")
			"^Genus$|" +
			// equals("Genus 1") || equals("Genus 2") ||
			// equals("Genus 3") || equals("Genus 4") ||
					"^Genus\\s([1-4])$");

	protected static final Pattern SINGULAR_PATTERN = Pattern.compile(
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
					"\\sSingular\\*\\s([1-4])$");

	protected static final Pattern EINZAHL_PATTERN = Pattern.compile(
			// endsWith(" (Einzahl)")
			" \\(Einzahl\\)$|" +
			// endsWith(" (Einzahl 1)") || endsWith(" (Einzahl 2)") ||
			// endsWith(" (Einzahl 3)") || endsWith(" (Einzahl 4)")
					" \\(Einzahl\\s([1-4])\\)$"

	);

	protected static final Pattern PLURAL_PATTERN = Pattern.compile(
			// equals("Plural")
			"^Plural$|" +
			// equals("PLURAL")
					"^PLURAL$|" +
					// endsWith(" Plural")
					"\\sPlural$|" +
					// endsWith(" Plural*")
					"\\sPlural\\*$|" +
					// endsWith(" Plural**")
					"\\sPlural\\*\\*$|" +
					// endsWith(" Plural?")
					"\\sPlural\\?$|" +
					// endsWith(" Plural *")
					"\\sPlural\\s\\*$|" +
					// endsWith(" Plural 1") || endsWith(" Plural 2") ||
					// endsWith(" Plural 3") || endsWith(" Plural 4")
					"\\sPlural\\s([1-4])$|" +
					// endsWith(" Plural 1*") || endsWith(" Plural 2*") ||
					// endsWith(" Plural 3*") || endsWith(" Plural 4*")
					"\\sPlural\\s([1-4])\\*$|" +
					// endsWith(" Plural 1**") || endsWith(" Plural 2**") ||
					// endsWith(" Plural 3**") || endsWith(" Plural 4**")
					"\\sPlural\\s([1-4])\\*\\*$|" +
					// endsWith(" Plural* 1") || endsWith(" Plural* 2") ||
					// endsWith(" Plural* 3") || endsWith(" Plural* 4")
					"\\sPlural\\*\\s([1-4])$");

	protected static final Pattern MEHRZAHL_PATTERN = Pattern.compile(
			// endsWith(" (Mehrzahl)")
			" \\(Mehrzahl\\)$|" +
			// endsWith(" (Mehrzahl 1)") || endsWith(" (Mehrzahl 2)") ||
			// endsWith(" (Mehrzahl 3)") || endsWith(" (Mehrzahl 4)")
					" \\(Mehrzahl\\s([1-4])\\)$");

	protected Map<Integer, DEGenderText> genera = new HashMap<>();

	DEGenderText findGenusByIndex(Integer index) {
		DEGenderText genderText = this.genera.get(index);
		if (genderText == null) {
			if (index == null) {
				genderText = this.genera.get(1);
				if (genderText != null) {
					System.out.println("here");
				}
			} else if (index.intValue() == 1) {
				genderText = this.genera.get(null);
			}
		}
		return genderText;
	}

	private void setGenus(DEGenderText genderText, Integer index) {
		this.genera.put(index, genderText);
	}

	public boolean extractNounTable(WiktionaryWordForm wordForm, String label, String value,
			ParsingContext context) {

		if (handleGenus(wordForm, label, value, context))
			return true;
		if (handleSingular(wordForm, label, value, context))
			return true;
		if (handleEinzahl(wordForm, label, value, context))
			return true;
		if (handlePlural(wordForm, label, value, context))
			return true;
		if (handleMehrzahl(wordForm, label, value, context))
			return true;

		logger.warning(MessageFormat.format("Page for word [{0}] has an unrecognized label [{1}].",
				context.getPage().getTitle(), label));
		return false;
	}

	private boolean handleGenus(WiktionaryWordForm wordForm, String label, String value,
			ParsingContext context) {
		final Matcher genusMatcher = GENUS_PATTERN.matcher(label);
		if (genusMatcher.find()) {
			final Integer index = PatternUtils.extractIndex(genusMatcher);
			extractGenus(label, index, value, context);
			return true;
		} else {
			return false;
		}
	}

	private boolean handleSingular(WiktionaryWordForm wordForm, String label, String value,
			ParsingContext context) {
		final Matcher singularPatternMatcher = SINGULAR_PATTERN.matcher(label);
		if (singularPatternMatcher.find()) {
			wordForm.setNumber(GrammaticalNumber.SINGULAR);
			final DEGenderText genderText = extractGenderText(singularPatternMatcher);
			if (genderText != null) {
				wordForm.setGender(genderText.asGrammaticalGender());
			} else {
				logger.warning(MessageFormat.format("Page for word [{0}] has a label [{1}] without genus entry.",
						context.getPage().getTitle(), label));
			}
			return true;
		} else {
			return false;
		}
	}

	private boolean handleEinzahl(WiktionaryWordForm wordForm, String label, String value,
			ParsingContext context) {
		final Matcher einzahlPatternMatcher = EINZAHL_PATTERN.matcher(label);
		if (einzahlPatternMatcher.find()) {
			wordForm.setNumber(GrammaticalNumber.SINGULAR);
			final DEGenderText genderText = extractGenderText(einzahlPatternMatcher);
			if (genderText != null) {
				wordForm.setGender(genderText.asGrammaticalGender());
			}
			return true;
		} else {
			return false;
		}
	}

	private boolean handlePlural(WiktionaryWordForm wordForm, String label, String value,
			ParsingContext context) {
		final Matcher pluralPatternMatcher = PLURAL_PATTERN.matcher(label);
		if (pluralPatternMatcher.find()) {
			wordForm.setNumber(GrammaticalNumber.PLURAL);
			return true;
		} else {
			return false;
		}
	}

	private boolean handleMehrzahl(WiktionaryWordForm wordForm, String label, String value,
			ParsingContext context) {
		final Matcher mehrzahlPatternMatcher = MEHRZAHL_PATTERN.matcher(label);
		if (mehrzahlPatternMatcher.find()) {
			wordForm.setNumber(GrammaticalNumber.PLURAL);
			return true;
		} else {
			return false;
		}
	}

	private void extractGenus(String label, Integer index, String value, ParsingContext context) {
		try {
			setGenus(DEGenderText.of(value), index);
		} catch (IllegalArgumentException unrecognizedGenderTextException) {
			logger.warning(MessageFormat.format("Page for word [{0}] has an unrecognized genus [{1}].",
					context.getPage().getTitle(), value));
		} catch (NullPointerException nullGenderTextException) {
			logger.warning(MessageFormat.format("Page for word [{0}] has a null genus [{1}].",
					context.getPage().getTitle(), value));
		}
	}

	private DEGenderText extractGenderText(final Matcher patternMatcher) {
		final Integer index = PatternUtils.extractIndex(patternMatcher);
		final DEGenderText genderText = findGenusByIndex(index);
		return genderText;
	}
}