package de.tudarmstadt.ukp.jwktl.parser.util;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;

public interface ITemplateParameterHandler {
	
	public void reset();

	public boolean canHandle(String label, String value, WiktionaryWordForm wordForm, ParsingContext context);

	public void handle(String label, String value, WiktionaryWordForm wordForm, ParsingContext context);
}
