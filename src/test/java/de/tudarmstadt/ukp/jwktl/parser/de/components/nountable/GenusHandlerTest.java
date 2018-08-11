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

import de.tudarmstadt.ukp.jwktl.parser.de.components.DEGenderText;
import junit.framework.TestCase;

public class GenusHandlerTest extends TestCase {
	
	private DEWordFormNounTableHandler nounTableHandler;
	private GenusHandler genusHandler;
	
	@Override
	protected void setUp() throws Exception {
		nounTableHandler = new DEWordFormNounTableHandler();
		genusHandler = new GenusHandler(nounTableHandler);
	}
	
	public void testCanHandle() {
		assertFalse(genusHandler.canHandle(null, null, null, null));
		assertFalse(genusHandler.canHandle("Suneg", null, null, null));
		assertTrue(genusHandler.canHandle("Genus", null, null, null));
		assertFalse(genusHandler.canHandle("Genus ", null, null, null));
		assertFalse(genusHandler.canHandle("Genus 0", null, null, null));
		assertTrue(genusHandler.canHandle("Genus 1", null, null, null));
		assertTrue(genusHandler.canHandle("Genus 2", null, null, null));
		assertTrue(genusHandler.canHandle("Genus 3", null, null, null));
		assertTrue(genusHandler.canHandle("Genus 4", null, null, null));
		assertFalse(genusHandler.canHandle("Genus 4.5", null, null, null));
		assertFalse(genusHandler.canHandle("Genus 5", null, null, null));
	}
	
	public void testGenus() {
		genusHandler.handle("Genus", "m", null, null);
		assertEquals(DEGenderText.M, nounTableHandler.findGenusByIndex(null));
		assertEquals(DEGenderText.M, nounTableHandler.findGenusByIndex(1));
		assertNull(nounTableHandler.findGenusByIndex(2));
	}

	public void testGenus1() {
		genusHandler.handle("Genus 1", "n", null, null);
		assertEquals(DEGenderText.N, nounTableHandler.findGenusByIndex(null));
		assertEquals(DEGenderText.N, nounTableHandler.findGenusByIndex(1));
		assertNull(nounTableHandler.findGenusByIndex(2));
	}

	public void testGenus2() {
		genusHandler.handle("Genus 2", "pl", null, null);
		assertNull(nounTableHandler.findGenusByIndex(1));
		assertEquals(DEGenderText.PL, nounTableHandler.findGenusByIndex(2));
		assertNull(nounTableHandler.findGenusByIndex(3));
	}
	
	public void testGenus3() {
		genusHandler.handle("Genus 3", "0", null, null);
		assertNull(nounTableHandler.findGenusByIndex(2));
		assertEquals(DEGenderText._0, nounTableHandler.findGenusByIndex(3));
		assertNull(nounTableHandler.findGenusByIndex(4));
	}
	public void testGenus4() {
		genusHandler.handle("Genus 4", "x", null, null);
		assertNull(nounTableHandler.findGenusByIndex(3));
		assertEquals(DEGenderText.X, nounTableHandler.findGenusByIndex(4));
		assertNull(nounTableHandler.findGenusByIndex(5));
	}
}
