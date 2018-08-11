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

public class GenitiveHandlerTest extends TestCase {
	
	private GenitiveHandler genitiveHandler;
	
	@Override
	protected void setUp() throws Exception {
		genitiveHandler = new GenitiveHandler();
	}
	
	public void testCanHandle() {
		assertFalse(genitiveHandler.canHandle(null, null, null, null));
		assertFalse(genitiveHandler.canHandle("Vitineg", null, null, null));
		assertTrue(genitiveHandler.canHandle("Genitiv Singular", null, null, null));
		assertTrue(genitiveHandler.canHandle("Genitiv", null, null, null));
		assertFalse(genitiveHandler.canHandle(" Genitiv Singular", null, null, null));
		assertTrue(genitiveHandler.canHandle("Wessen? (Einzahl)", null, null, null));
		assertTrue(genitiveHandler.canHandle("Wessen?(Einzahl)", null, null, null));
		assertFalse(genitiveHandler.canHandle(" Wessen? (Einzahl)", null, null, null));
	}
	
	public void testGenitivSingular() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genitiveHandler.handle("Genitiv Singular", "test", wordForm, null);
		assertEquals(GrammaticalCase.GENITIVE, wordForm.getCase());
	}
	
	public void testWerOderWasEinzahl() {
		WiktionaryWordForm wordForm = new WiktionaryWordForm("test");
		genitiveHandler.handle("Wessen? (Einzahl)", "test", wordForm, null);
		assertEquals(GrammaticalCase.GENITIVE, wordForm.getCase());
	}
}