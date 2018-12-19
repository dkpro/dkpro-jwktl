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

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEEntryFactory;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEGenderText;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import junit.framework.TestCase;

public class PluralHandlerTest extends TestCase {
	
	private DEWordFormNounTableHandler nounTableHandler;
	private GenusHandler genusHandler;
	private PluralHandler pluralHandler;
	private ParsingContext parsingContext;
	
	@Override
	protected void setUp() throws Exception {
		nounTableHandler = new DEWordFormNounTableHandler();
		genusHandler = new GenusHandler(nounTableHandler);
		pluralHandler = new PluralHandler(nounTableHandler);
		parsingContext = new ParsingContext(new WiktionaryPage(), new DEEntryFactory());
	}
	
	public void testCanHandle() {
		assertFalse(pluralHandler.canHandle(null, null, null, parsingContext));
		assertFalse(pluralHandler.canHandle("Larulp", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle("Plural", null, null, parsingContext));
		assertFalse(pluralHandler.canHandle("Plural ", null, null, parsingContext));

		assertTrue(pluralHandler.canHandle("PLURAL", null, null, parsingContext));
		assertFalse(pluralHandler.canHandle(" PLURAL", null, null, parsingContext));
		assertFalse(pluralHandler.canHandle("PLURAL ", null, null, parsingContext));
		
		assertTrue(pluralHandler.canHandle(" Plural", null, null, parsingContext));
		assertFalse(pluralHandler.canHandle(" Plural ", null, null, parsingContext));

		assertTrue(pluralHandler.canHandle(" Plural*", null, null, parsingContext));
		assertFalse(pluralHandler.canHandle("Plural*", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural**", null, null, parsingContext));
		assertFalse(pluralHandler.canHandle("Plural**", null, null, parsingContext));

		assertTrue(pluralHandler.canHandle(" Plural?", null, null, parsingContext));
		assertFalse(pluralHandler.canHandle("Plural? ", null, null, parsingContext));
		assertFalse(pluralHandler.canHandle(" Plural? ", null, null, parsingContext));

		assertTrue(pluralHandler.canHandle(" Plural *", null, null, parsingContext));
		assertFalse(pluralHandler.canHandle("Plural *", null, null, parsingContext));

		assertTrue(pluralHandler.canHandle(" Plural 1", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural 2", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural 3", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural 4", null, null, parsingContext));

		assertTrue(pluralHandler.canHandle(" Plural 1*", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural 2*", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural 3*", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural 4*", null, null, parsingContext));

		assertTrue(pluralHandler.canHandle(" Plural 1**", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural 2**", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural 3**", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural 4**", null, null, parsingContext));

		assertTrue(pluralHandler.canHandle(" Plural* 1", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural* 2", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural* 3", null, null, parsingContext));
		assertTrue(pluralHandler.canHandle(" Plural* 4", null, null, parsingContext));
	}
	
	public void testPluralWithGenus() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus", "m", wordForm, parsingContext);
		pluralHandler.handle("Nominativ Plural", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}
	
	public void testPLURALWithGenus_1() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 1", "n", wordForm, parsingContext);
		pluralHandler.handle("PLURAL", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}
	
	public void testPlural_2_SternWithGenus_2() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 2", "f", wordForm, parsingContext);
		pluralHandler.handle("Nominativ Plural 2*", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}
	
	public void testPlural_3_Stern_SternWithGenus_3() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 3", "x", wordForm, parsingContext);
		pluralHandler.handle("Nominativ Plural 3**", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertEquals(DEGenderText.X, nounTableHandler.getGenusByIndex(3));
		assertNull(wordForm.getGender());
	}
	
	public void testPlural_Stern_4WithGenus_4() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 4", "pl", wordForm, parsingContext);
		pluralHandler.handle("Nominativ Plural* 4", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertEquals(DEGenderText.PL, nounTableHandler.getGenusByIndex(4));
		assertNull(wordForm.getGender());
	}
}