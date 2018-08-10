package de.tudarmstadt.ukp.jwktl.parser.de.components.nountable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public abstract class PatternBasedParameterHandler {

	protected final Pattern pattern;

	public PatternBasedParameterHandler(String regex) {
		Objects.requireNonNull(regex, "regex must not be null.");
		this.pattern = Pattern.compile(regex);
	}

	public boolean canHandle(WiktionaryWordForm wordForm, String label, String value, ParsingContext context) {
		Matcher matcher = pattern.matcher(label);
		return matcher.find();
	}

	public abstract void handle(WiktionaryWordForm wordForm, String label, String value, ParsingContext context);
}