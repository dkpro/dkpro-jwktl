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
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEEntryFactory;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEGenderText;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import junit.framework.TestCase;

public class GenusHandlerTest extends TestCase {
	
	private DEWordFormNounTableHandler nounTableHandler;
	private GenusHandler genusHandler;
	private ParsingContext parsingContext;
	
	@Override
	protected void setUp() throws Exception {
		nounTableHandler = new DEWordFormNounTableHandler();
		genusHandler = new GenusHandler(nounTableHandler);
		parsingContext = new ParsingContext(new WiktionaryPage(), new DEEntryFactory());
	}
	
	public void testCanHandle() {
		assertFalse(genusHandler.canHandle(null, null, null, parsingContext));
		assertFalse(genusHandler.canHandle("Suneg", null, null, parsingContext));
		assertTrue(genusHandler.canHandle("Genus", null, null, parsingContext));
		assertFalse(genusHandler.canHandle("Genus ", null, null, parsingContext));
		assertFalse(genusHandler.canHandle("Genus 0", null, null, parsingContext));
		assertTrue(genusHandler.canHandle("Genus 1", null, null, parsingContext));
		assertTrue(genusHandler.canHandle("Genus 2", null, null, parsingContext));
		assertTrue(genusHandler.canHandle("Genus 3", null, null, parsingContext));
		assertTrue(genusHandler.canHandle("Genus 4", null, null, parsingContext));
		assertFalse(genusHandler.canHandle("Genus 4.5", null, null, parsingContext));
		assertFalse(genusHandler.canHandle("Genus 5", null, null, parsingContext));
	}
	
	public void testGenus() {
		genusHandler.handle("Genus", "m", null, parsingContext);
		assertEquals(DEGenderText.M, nounTableHandler.getGenusByIndex(1));
		assertNull(nounTableHandler.getGenusByIndex(2));
	}

	public void testGenus1() {
		genusHandler.handle("Genus 1", "n", null, parsingContext);
		assertEquals(DEGenderText.N, nounTableHandler.getGenusByIndex(1));
		assertNull(nounTableHandler.getGenusByIndex(2));
	}

	public void testGenus2() {
		genusHandler.handle("Genus 2", "pl", null, parsingContext);
		assertNull(nounTableHandler.getGenusByIndex(1));
		assertEquals(DEGenderText.PL, nounTableHandler.getGenusByIndex(2));
		assertNull(nounTableHandler.getGenusByIndex(3));
	}
	
	public void testGenus3() {
		genusHandler.handle("Genus 3", "0", null, parsingContext);
		assertNull(nounTableHandler.getGenusByIndex(2));
		assertEquals(DEGenderText._0, nounTableHandler.getGenusByIndex(3));
		assertNull(nounTableHandler.getGenusByIndex(4));
	}
	public void testGenus4() {
		genusHandler.handle("Genus 4", "x", null, parsingContext);
		assertNull(nounTableHandler.getGenusByIndex(3));
		assertEquals(DEGenderText.X, nounTableHandler.getGenusByIndex(4));
	}
}
