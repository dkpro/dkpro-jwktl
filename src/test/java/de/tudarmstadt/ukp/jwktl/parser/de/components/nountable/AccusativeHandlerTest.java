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

public class AccusativeHandlerTest extends TestCase {

	private AccusativeHandler accusativeHandler;

	@Override
	protected void setUp() throws Exception {
		accusativeHandler = new AccusativeHandler();
	}

	public void testCanHandle() {
		assertFalse(accusativeHandler.canHandle(null, null, null, null));
		assertFalse(accusativeHandler.canHandle("Vitasukka", null, null, null));
		assertTrue(accusativeHandler.canHandle("Akkusativ Singular", null, null, null));
		assertTrue(accusativeHandler.canHandle("Akkusativ", null, null, null));
		assertFalse(accusativeHandler.canHandle(" Akkusativ Singular", null, null, null));
		assertTrue(accusativeHandler.canHandle("Wen? (Einzahl)", null, null, null));
		assertTrue(accusativeHandler.canHandle("Wen?(Einzahl)", null, null, null));
		assertFalse(accusativeHandler.canHandle(" Wen? (Einzahl)", null, null, null));
	}

	public void testAkkusativSingular() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		accusativeHandler.handle("Akkusativ Singular", "test", wordForm, null);
		assertEquals(GrammaticalCase.ACCUSATIVE, wordForm.getCase());
	}

	public void testWenEinzahl() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		accusativeHandler.handle("Wen? (Einzahl)", "test", wordForm, null);
		assertEquals(GrammaticalCase.ACCUSATIVE, wordForm.getCase());
	}
}