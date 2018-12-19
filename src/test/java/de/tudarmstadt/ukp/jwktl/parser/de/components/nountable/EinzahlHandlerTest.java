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
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import junit.framework.TestCase;

public class EinzahlHandlerTest extends TestCase {
	
	private DEWordFormNounTableHandler nounTableHandler;
	private GenusHandler genusHandler;
	private EinzahlHandler einzahlHandler;
	private ParsingContext parsingContext;
	
	@Override
	protected void setUp() throws Exception {
		nounTableHandler = new DEWordFormNounTableHandler();
		genusHandler = new GenusHandler(nounTableHandler);
		einzahlHandler = new EinzahlHandler(nounTableHandler);
		parsingContext = new ParsingContext(new WiktionaryPage(), new DEEntryFactory());
	}
	
	public void testCanHandle() {
		assertFalse(einzahlHandler.canHandle(null, null, null, parsingContext));
		assertFalse(einzahlHandler.canHandle("Wer oder was?", null, null, parsingContext));
		assertTrue(einzahlHandler.canHandle("Wer oder was? (Einzahl)", null, null, parsingContext));
		assertTrue(einzahlHandler.canHandle("Wer oder was? (Einzahl 1)", null, null, parsingContext));
		assertTrue(einzahlHandler.canHandle("Wer oder was? (Einzahl 2)", null, null, parsingContext));
		assertTrue(einzahlHandler.canHandle("Wer oder was? (Einzahl 3)", null, null, parsingContext));
		assertTrue(einzahlHandler.canHandle("Wer oder was? (Einzahl 4)", null, null, parsingContext));
		assertFalse(einzahlHandler.canHandle("Wer oder was? (Einzahl 5)", null, null, parsingContext));
	}
	
	public void testEinzahlWithGenus() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus", "m", wordForm, parsingContext);
		einzahlHandler.handle("Wer oder was? (Einzahl)", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertEquals(GrammaticalGender.MASCULINE, wordForm.getGender());
	}

	public void testEinzahlWithGenus_1() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 1", "n", wordForm, parsingContext);
		einzahlHandler.handle("Wer oder was? (Einzahl)", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertEquals(GrammaticalGender.NEUTER, wordForm.getGender());
	}
	
	public void testEinzahl_1WithGenus_1() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 1", "f", wordForm, parsingContext);
		einzahlHandler.handle("Wer oder was? (Einzahl 1)", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertEquals(GrammaticalGender.FEMININE, wordForm.getGender());
	}
	
	public void testEinzahl_2WithGenus_2() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 2", "x", wordForm, parsingContext);
		einzahlHandler.handle("Wer oder was? (Einzahl 2)", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}
	
	public void testEinzahl_3WithGenus_3() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 3", "m", wordForm, parsingContext);
		einzahlHandler.handle("Wer oder was? (Einzahl 3)", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertEquals(GrammaticalGender.MASCULINE, wordForm.getGender());
	}

	public void testEinzahl_3WithGenus_2() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 2", "m", wordForm, parsingContext);
		einzahlHandler.handle("Wer oder was? (Einzahl 3)", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}
	
	
	public void testEinzahl_4WithGenus_4() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 4", "n", wordForm, parsingContext);
		einzahlHandler.handle("Wer oder was? (Einzahl 4)", "test", wordForm, parsingContext);
		assertEquals(GrammaticalNumber.SINGULAR, wordForm.getNumber());
		assertEquals(GrammaticalGender.NEUTER, wordForm.getGender());
	}
}