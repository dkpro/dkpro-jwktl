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
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEEntryFactory;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEGenderText;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import junit.framework.TestCase;

public class SingularHandlerTest extends TestCase {
	
	private DEWordFormNounTableHandler nounTableHandler;
	private GenusHandler genusHandler;
	private SingularHandler singularHandler;
	private ParsingContext parsingContext;
	
	@Override
	protected void setUp() throws Exception {
		nounTableHandler = new DEWordFormNounTableHandler();
		genusHandler = new GenusHandler(nounTableHandler);
		singularHandler = new SingularHandler(nounTableHandler);
		parsingContext = new ParsingContext(new WiktionaryPage(), new DEEntryFactory());
	}
	
	public void testCanHandle() {
		assertFalse(singularHandler.canHandle(null, null, null, parsingContext));
		assertFalse(singularHandler.canHandle("Ralugnis", null, null, parsingContext));
		assertTrue(singularHandler.canHandle("Singular", null, null, parsingContext));
		assertFalse(singularHandler.canHandle("Singular ", null, null, parsingContext));

		assertTrue(singularHandler.canHandle("SINGULAR", null, null, parsingContext));
		assertFalse(singularHandler.canHandle(" SINGULAR", null, null, parsingContext));
		assertFalse(singularHandler.canHandle("SINGULAR ", null, null, parsingContext));
		
		assertTrue(singularHandler.canHandle(" Singular", null, null, parsingContext));
		assertFalse(singularHandler.canHandle(" Singular ", null, null, parsingContext));

		assertTrue(singularHandler.canHandle(" Singular*", null, null, parsingContext));
		assertFalse(singularHandler.canHandle("Singular*", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular**", null, null, parsingContext));
		assertFalse(singularHandler.canHandle("Singular**", null, null, parsingContext));

		assertTrue(singularHandler.canHandle(" Singular?", null, null, parsingContext));
		assertFalse(singularHandler.canHandle("Singular? ", null, null, parsingContext));
		assertFalse(singularHandler.canHandle(" Singular? ", null, null, parsingContext));

		assertTrue(singularHandler.canHandle(" Singular *", null, null, parsingContext));
		assertFalse(singularHandler.canHandle("Singular *", null, null, parsingContext));

		assertTrue(singularHandler.canHandle(" Singular 1", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular 2", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular 3", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular 4", null, null, parsingContext));

		assertTrue(singularHandler.canHandle(" Singular 1*", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular 2*", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular 3*", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular 4*", null, null, parsingContext));

		assertTrue(singularHandler.canHandle(" Singular 1**", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular 2**", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular 3**", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular 4**", null, null, parsingContext));

		assertTrue(singularHandler.canHandle(" Singular* 1", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular* 2", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular* 3", null, null, parsingContext));
		assertTrue(singularHandler.canHandle(" Singular* 4", null, null, parsingContext));
	}
	
	public void testSingularWithGenus() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus", "m", wordForm, parsingContext);
		singularHandler.handle("Nominativ Singular", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertEquals(GrammaticalGender.MASCULINE, wordForm.getGender());
	}
	
	public void testSINGULARWithGenus_1() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 1", "n", wordForm, parsingContext);
		singularHandler.handle("SINGULAR", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertEquals(GrammaticalGender.NEUTER, wordForm.getGender());
	}
	
	public void testSingular_2_SternWithGenus_2() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 2", "f", wordForm, parsingContext);
		singularHandler.handle("Nominativ Singular 2*", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertEquals(GrammaticalGender.FEMININE, wordForm.getGender());
	}
	
	public void testSingular_3_Stern_SternWithGenus_3() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 3", "x", wordForm, parsingContext);
		singularHandler.handle("Nominativ Singular 3**", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertEquals(DEGenderText.X, nounTableHandler.getGenusByIndex(3));
		assertNull(wordForm.getGender());
	}
	
	public void testSingular_Stern_4WithGenus_4() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 4", "pl", wordForm, parsingContext);
		singularHandler.handle("Nominativ Singular* 4", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertEquals(DEGenderText.PL, nounTableHandler.getGenusByIndex(4));
		assertNull(wordForm.getGender());
	}
}