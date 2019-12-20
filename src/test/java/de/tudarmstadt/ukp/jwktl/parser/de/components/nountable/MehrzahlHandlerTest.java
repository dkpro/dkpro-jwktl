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

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import junit.framework.TestCase;

public class MehrzahlHandlerTest extends TestCase {

	private DEWordFormNounTableHandler nounTableHandler;
	private GenusHandler genusHandler;
	private MehrzahlHandler einzahlHandler;

	@Override
	protected void setUp() throws Exception {
		nounTableHandler = new DEWordFormNounTableHandler();
		genusHandler = new GenusHandler(nounTableHandler);
		einzahlHandler = new MehrzahlHandler(nounTableHandler);
	}

	public void testCanHandle() {
		assertFalse(einzahlHandler.canHandle(null, null, null, null));
		assertFalse(einzahlHandler.canHandle("Wer oder was?", null, null, null));
		assertTrue(einzahlHandler.canHandle("Wer oder was? (Mehrzahl)", null, null, null));
		assertTrue(einzahlHandler.canHandle("Wer oder was? (Mehrzahl 1)", null, null, null));
		assertTrue(einzahlHandler.canHandle("Wer oder was? (Mehrzahl 2)", null, null, null));
		assertTrue(einzahlHandler.canHandle("Wer oder was? (Mehrzahl 3)", null, null, null));
		assertTrue(einzahlHandler.canHandle("Wer oder was? (Mehrzahl 4)", null, null, null));
		assertFalse(einzahlHandler.canHandle("Wer oder was? (Mehrzahl 5)", null, null, null));
	}

	public void testMehrzahlWithGenus() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus", "m", wordForm, null);
		einzahlHandler.handle("Wer oder was? (Mehrzahl)", "test", wordForm, null);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}

	public void testMehrzahlWithGenus_1() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 1", "n", wordForm, null);
		einzahlHandler.handle("Wer oder was? (Mehrzahl)", "test", wordForm, null);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}

	public void testMehrzahl_1WithGenus_1() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 1", "f", wordForm, null);
		einzahlHandler.handle("Wer oder was? (Mehrzahl 1)", "test", wordForm, null);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}

	public void testMehrzahl_2WithGenus_2() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 2", "x", wordForm, null);
		einzahlHandler.handle("Wer oder was? (Mehrzahl 2)", "test", wordForm, null);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}

	public void testMehrzahl_3WithGenus_3() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 3", "m", wordForm, null);
		einzahlHandler.handle("Wer oder was? (Mehrzahl 3)", "test", wordForm, null);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}

	public void testMehrzahl_3WithGenus_2() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 2", "m", wordForm, null);
		einzahlHandler.handle("Wer oder was? (Mehrzahl 3)", "test", wordForm, null);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}

	public void testMehrzahl_4WithGenus_4() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genusHandler.handle("Genus 4", "n", wordForm, null);
		einzahlHandler.handle("Wer oder was? (Mehrzahl 4)", "test", wordForm, null);
		assertEquals(GrammaticalNumber.PLURAL, wordForm.getNumber());
		assertNull(wordForm.getGender());
	}
}