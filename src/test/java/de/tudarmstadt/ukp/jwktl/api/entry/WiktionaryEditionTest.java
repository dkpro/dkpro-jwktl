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
package de.tudarmstadt.ukp.jwktl.api.entry;

import java.io.File;
import java.util.Iterator;

import com.sleepycat.persist.EntityStore;

import de.tudarmstadt.ukp.jwktl.WiktionaryDataTestCase;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.util.Language;

/**
 * Test case for {@link BerkeleyDBWiktionaryEdition}.
 * @author Christian M. Meyer
 */
public class WiktionaryEditionTest extends WiktionaryDataTestCase {
	
	protected BerkeleyDBWiktionaryEdition wkt;

	@Override
	protected void tearDown() throws Exception {
		if (wkt != null)
			wkt.close();
		wkt = null;
		super.tearDown();
	}
	
	/***/
	public void testInstanciation() {
		wkt = new BerkeleyDBWiktionaryEdition(wktDE.getParsedData());
		assertEquals(Language.GERMAN, wktDE.getLanguage());
		wkt.close();
		
		wkt = new BerkeleyDBWiktionaryEdition(wktEN.getParsedData());
		assertEquals(Language.ENGLISH, wktEN.getLanguage());
		wkt.close();
	}

	/***/
	public void testGetPage() {
		// German Language.
		wkt = new BerkeleyDBWiktionaryEdition(wktDE.getParsedData());
		assertEquals(DE_FRANCA1.getPage(), wkt.getPageForId(49261).getId());
		assertEquals(DE_MOENCH.getPage(), wkt.getPageForId(10662).getId());
		assertEquals(DE_PARAMETER.getPage(), wkt.getPageForId(29502).getId());
		assertEquals(DE_PLATZ.getPage(), wkt.getPageForId(11094).getId());
		
		assertEquals(DE_FRANCA1.getPage(), wkt.getPageForWord("França").getId());
		assertEquals(DE_MOENCH.getPage(), wkt.getPageForWord("Mönch").getId());
		assertEquals(DE_PARAMETER.getPage(), wkt.getPageForWord("Parameter").getId());
		assertEquals(DE_PLATZ.getPage(), wkt.getPageForWord("Platz").getId());
		wkt.close();


		// English Language.
		wkt = new BerkeleyDBWiktionaryEdition(wktEN.getParsedData());
		assertEquals(EN_PARAMETER.getPage(), wkt.getPageForId(11095).getId());
		assertEquals(DE_PLATZ.getPage(), wkt.getPageForId(11094).getId());
		
		assertEquals(EN_PARAMETER.getPage(), wkt.getPageForWord("parameter").getId());
		assertEquals(EN_PLACE1.getPage(), wkt.getPageForWord("place").getId());


		// Missing.
		assertNull(wkt.getPageForId(0));
		assertNull(wkt.getPageForId(-1));
		assertNull(wkt.getPageForId(100));
		
		assertNull(wkt.getPageForWord(null));
		assertNull(wkt.getPageForWord(""));
		assertNull(wkt.getPageForWord("foo"));		
	}

	/***/
	public void testGetPageNormalized() {
		// German Language.
		wkt = new BerkeleyDBWiktionaryEdition(wktDE.getParsedData());
		assertEquals(DE_MOENCH.getPage(), wkt.getPagesForWord("Mönch", false).get(0).getId());
		assertEquals(0, wkt.getPagesForWord("mönch", false).size());
		assertEquals(0, wkt.getPagesForWord("MÖNCH", false).size());
		assertEquals(0, wkt.getPagesForWord("mönCH", false).size());
		assertEquals(0, wkt.getPagesForWord("Monch", false).size());
		
		assertEquals(DE_MOENCH.getPage(), wkt.getPagesForWord("Mönch", true).get(0).getId());
		assertEquals(DE_MOENCH.getPage(), wkt.getPagesForWord("mönch", true).get(0).getId());
		assertEquals(DE_MOENCH.getPage(), wkt.getPagesForWord("MÖNCH", true).get(0).getId());
		assertEquals(DE_MOENCH.getPage(), wkt.getPagesForWord("mönCH", true).get(0).getId());
		assertEquals(DE_MOENCH.getPage(), wkt.getPagesForWord("Monch", true).get(0).getId());
		wkt.close();
		
		
		// English Language.
		wkt = new BerkeleyDBWiktionaryEdition(wktEN.getParsedData());
		assertEquals(EN_PLACE1.getPage(), wkt.getPagesForWord("place", false).get(0).getId());
		assertEquals(0, wkt.getPagesForWord("Place", false).size());
		assertEquals(0, wkt.getPagesForWord("PLACE", false).size());
		assertEquals(0, wkt.getPagesForWord("plaCE", false).size());
		
		assertEquals(EN_PLACE1.getPage(), wkt.getPagesForWord("place", true).get(0).getId());
		assertEquals(EN_PLACE1.getPage(), wkt.getPagesForWord("Place", true).get(0).getId());
		assertEquals(EN_PLACE1.getPage(), wkt.getPagesForWord("PLACE", true).get(0).getId());
		assertEquals(EN_PLACE1.getPage(), wkt.getPagesForWord("plaCE", true).get(0).getId());
		
		//TODO: test entry which returns more than one, e.g. Café Cafe cafe...
		

		// Missing.
		assertNull(wkt.getPagesForWord(null, false));
		assertEquals(0, wkt.getPagesForWord("", false).size());
		assertEquals(0, wkt.getPagesForWord("foo", false).size());
		
		assertNull(wkt.getPagesForWord(null, true));
		assertEquals(0, wkt.getPagesForWord("", true).size());
		assertEquals(0, wkt.getPagesForWord("foo", true).size());
	}

	/***/
	public void testPageIteration() {
		// Complete iteration.
		wkt = new BerkeleyDBWiktionaryEdition(wktDE.getParsedData());
		Iterator<IWiktionaryPage> iter = wkt.getAllPages().iterator();
		assertEquals(DE_MOENCH.getPage(), iter.next().getId());
		assertEquals(DE_PLATZ.getPage(), iter.next().getId());
		assertEquals(DE_PARAMETER.getPage(), iter.next().getId());
		assertEquals(DE_FRANCA1.getPage(), iter.next().getId());
		assertFalse(iter.hasNext());
		
		iter = wkt.getAllPages(true).iterator();
		assertEquals(DE_FRANCA1.getPage(), iter.next().getId());
		assertEquals(DE_MOENCH.getPage(), iter.next().getId());
		assertEquals(DE_PARAMETER.getPage(), iter.next().getId());
		assertEquals(DE_PLATZ.getPage(), iter.next().getId());
		assertFalse(iter.hasNext());
		
		iter = wkt.getAllPages(true, true).iterator(); //TODO: example that shows the difference.
		assertEquals(DE_FRANCA1.getPage(), iter.next().getId());
		assertEquals(DE_MOENCH.getPage(), iter.next().getId());
		assertEquals(DE_PARAMETER.getPage(), iter.next().getId());
		assertEquals(DE_PLATZ.getPage(), iter.next().getId());
		assertFalse(iter.hasNext());
		wkt.close();
		
		
		// Complete iteration.
		wkt = new BerkeleyDBWiktionaryEdition(wktEN.getParsedData());
		iter = wkt.getAllPages().iterator();
		assertEquals(EN_PLACE1.getPage(), iter.next().getId());
		assertEquals(EN_PARAMETER.getPage(), iter.next().getId());
		assertFalse(iter.hasNext());
		
		iter = wkt.getAllPages(true).iterator();
		assertEquals(EN_PARAMETER.getPage(), iter.next().getId());
		assertEquals(EN_PLACE1.getPage(), iter.next().getId());
		assertFalse(iter.hasNext());
		
		iter = wkt.getAllPages(true, true).iterator();
		assertEquals(EN_PARAMETER.getPage(), iter.next().getId());
		assertEquals(EN_PLACE1.getPage(), iter.next().getId());
		assertFalse(iter.hasNext());
	}
	
	/***/
	public void testClose() {
		// Multiple close.
		wkt = new BerkeleyDBWiktionaryEdition(wktDE.getParsedData());
		assertFalse(wkt.isClosed());
		wkt.close();
		assertTrue(wkt.isClosed());
		wkt.close();
		assertTrue(wkt.isClosed());
		
		// Null.
		wkt = new BerkeleyDBWiktionaryEdition(wktDE.getParsedData());
		EntityStore store = wkt.store;
		wkt.store = null;
		wkt.close();
		assertTrue(wkt.isClosed());
		store.close();
		
		// Invoke after close.
		wkt = new BerkeleyDBWiktionaryEdition(wktEN.getParsedData());
		assertFalse(wkt.isClosed());		
		assertTrue(wkt.getAllPages().hasNext());
		wkt.close();
		assertTrue(wkt.isClosed());
		
		try {
			wkt.getAllPages().next();
			fail("IllegalStateException expected");
		} catch (IllegalStateException e) {}
	}
	
	/***/
	public void testError() {
		try {
			new BerkeleyDBWiktionaryEdition(new File("/dev/null/missing_parse:"));
			fail("WiktionaryException expected!");
		} catch (WiktionaryException e) {}
	}

}
