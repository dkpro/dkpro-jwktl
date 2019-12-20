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
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase;
import junit.framework.TestCase;

public class NominativeHandlerTest extends TestCase {

	private NominativeHandler nominativeHandler;

	@Override
	protected void setUp() throws Exception {
		nominativeHandler = new NominativeHandler();
	}

	public void testCanHandle() {
		assertFalse(nominativeHandler.canHandle(null, null, null, null));
		assertFalse(nominativeHandler.canHandle("Vitanimon", null, null, null));
		assertTrue(nominativeHandler.canHandle("Nominativ Singular", null, null, null));
		assertTrue(nominativeHandler.canHandle("Nominativ", null, null, null));
		assertFalse(nominativeHandler.canHandle(" Nominativ Singular", null, null, null));
		assertTrue(nominativeHandler.canHandle("Wer oder was? (Einzahl)", null, null, null));
		assertTrue(nominativeHandler.canHandle("Wer oder was?(Einzahl)", null, null, null));
		assertFalse(nominativeHandler.canHandle(" Wer oder was? (Einzahl)", null, null, null));
	}

	public void testNominativSingular() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		nominativeHandler.handle("Nominativ Singular", "test", wordForm, null);
		assertEquals(GrammaticalCase.NOMINATIVE, wordForm.getCase());
	}

	public void testWerOderWasEinzahl() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		nominativeHandler.handle("Wer oder was? (Einzahl)", "test", wordForm, null);
		assertEquals(GrammaticalCase.NOMINATIVE, wordForm.getCase());
	}

}