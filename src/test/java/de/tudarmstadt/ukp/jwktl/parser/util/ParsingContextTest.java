package de.tudarmstadt.ukp.jwktl.parser.util;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import junit.framework.TestCase;

public class ParsingContextTest extends TestCase {
	
	public void testGenderIndex() {
		final ParsingContext context = new ParsingContext(new WiktionaryPage());
		assertEquals(null, context.getDefaultGenderByIndex());
		assertEquals(null, context.getGenderByIndex(1));
		context.addGenderToIndex(GrammaticalGender.MASCULINE, 3);
		assertEquals(GrammaticalGender.MASCULINE, context.getGenderByIndex(3));
		context.addDefaultGenderToIndex(GrammaticalGender.NEUTER);
		assertEquals(GrammaticalGender.NEUTER, context.getDefaultGenderByIndex());
	}
}