package de.tudarmstadt.ukp.jwktl.parser.de.components;

import java.text.MessageFormat;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public class DEWordFormNounTableExtractor {

	private static final Logger logger = Logger.getLogger(DEWordFormNounTableExtractor.class.getName());

	protected DEGenderText genus;
	protected DEGenderText genus1;
	protected DEGenderText genus2;
	protected DEGenderText genus3;
	protected DEGenderText genus4;

	public void reset() {
		genus = null;
		genus1 = null;
		genus2 = null;
		genus3 = null;
		genus4 = null;
	}

	private DEGenderText getGenus(Integer index) {
		if (index == null) {
			if (genus != null) {
				return genus;
			} else if (genus1 != null) {
				return genus1;
			} else {
				return null;
			}
		}
		final int i = index.intValue();
		switch (i) {
		case 1:
			if (genus1 != null) {
				return genus1;
			} else if (genus != null) {
				return genus;
			} else {
				return null;
			}
		case 2:
			return genus2;
		case 3:
			return genus3;
		case 4:
			return genus4;
		default:
			throw new IllegalArgumentException(MessageFormat.format("Index [{0}] is not supported. Only indices 1-4 are supported.", i));
		}
	}

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
					// endsWith(" Singular* 1") || endsWith(" Singular* 2") ||
					// endsWith(" Singular* 3") || endsWith(" Singular* 4")
					"\\sSingular\\*\\s([1-4])$|" +
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
					"\\sPlural\\*\\s([1-4])$|" +
					// endsWith(" (Mehrzahl)")
					" \\(Mehrzahl\\)$|" +
					// endsWith(" (Mehrzahl 1)") || endsWith(" (Mehrzahl 2)") ||
					// endsWith(" (Mehrzahl 3)") || endsWith(" (Mehrzahl 4)")
					" \\(Mehrzahl\\s([1-4])\\)$"

	);

	public void extractNounTable(final WiktionaryWordForm wordForm, String label, String value,
			ParsingContext context) {

		if (label.contains("Genus")) {
			extractGenus(label, value, context);
			return;
		}

		final Matcher singularPatternMatcher = SINGULAR_PATTERN.matcher(label);
		if (singularPatternMatcher.find()) {
			wordForm.setNumber(GrammaticalNumber.SINGULAR);
			final Integer index = getGenusIndex(singularPatternMatcher);
			final DEGenderText genderText = getGenus(index);
			if (genderText != null) {
				wordForm.setGender(genderText.asGrammaticalGender());
			} else {
				logger.warning(MessageFormat.format("Page for word [{0}] has a label [{1}] without genus entry.",
						context.getPage().getTitle(), label));
			}
		} else {
			final Matcher pluralPatternMatcher = PLURAL_PATTERN.matcher(label);
			if (pluralPatternMatcher.find()) {
				wordForm.setNumber(GrammaticalNumber.PLURAL);
			} else {
				logger.warning(MessageFormat.format("Page for word [{0}] has an unrecognized case label [{1}].",
						context.getPage().getTitle(), label));
			}
		}
	}

	private static Integer getGenusIndex(Matcher labelMatcher) {
		for (int index = 1; index < labelMatcher.groupCount(); index++) {
			String group = labelMatcher.group(index);
			if (group != null) {
				return Integer.valueOf(group);
			}
		}
		return null;
	}

	private void extractGenus(String label, String value, ParsingContext context) {
		DEGenderText parsedGenus = null;
		try {
			parsedGenus = DEGenderText.of(value);
		} catch (IllegalArgumentException unrecognizedGenderTextException) {
			logger.warning(
					"Page for word [" + context.getPage().getTitle() + "] has an unrecognized genus [" + value + "].");
		} catch (NullPointerException nullGenderTextException) {
			logger.warning(MessageFormat.format("Page for word [{0}] has a null genus [{1}].",
					context.getPage().getTitle(), value));
		}

		if (label.equals("Genus")) {
			this.genus = parsedGenus;
		} else if (label.equals("Genus 1")) {
			this.genus1 = parsedGenus;
		} else if (label.equals("Genus 2")) {
			this.genus2 = parsedGenus;
		} else if (label.equals("Genus 3")) {
			this.genus3 = parsedGenus;
		} else if (label.equals("Genus 4")) {
			this.genus4 = parsedGenus;
		} else {
			logger.warning(MessageFormat.format("Page for word [{0}] has an unrecognized genus label [{1}].",
					context.getPage().getTitle(), label));
		}
	}
}