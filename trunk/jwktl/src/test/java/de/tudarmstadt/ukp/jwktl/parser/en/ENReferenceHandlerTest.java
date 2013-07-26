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
package de.tudarmstadt.ukp.jwktl.parser.en;

import java.util.Iterator;

import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENReferenceHandler;

/**
 * Test case for {@link ENReferenceHandler}.
 * @author Christian M. Meyer
 */
public class ENReferenceHandlerTest extends ENWiktionaryEntryParserTest {

	/***/
	public void testAbalone() throws Exception {
		IWiktionaryPage page = parse("abalone.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWikiString> actualIter = entry.getReferences().iterator();
		assertEquals("{{wikisource1911Enc|Abalone}}", actualIter.next().getText());
		assertEquals("{{pedialite|Abalone}}", actualIter.next().getText());
		assertEquals("{{R:American Heritage 2000|abalone}}", actualIter.next().getText());
		assertEquals("{{R:Dictionary.com|abalone}}", actualIter.next().getText());
		assertEquals("{{R:WordNet 2003|abalone}}", actualIter.next().getText());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testAbate() throws Exception {
		IWiktionaryPage page = parse("abate.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWikiString> actualIter = entry.getReferences().iterator();
		assertEquals("{{R:OneLook}}", actualIter.next().getText());
		assertEquals("{{R:Webster 1913}}", actualIter.next().getText());
		assertFalse(actualIter.hasNext());
		
		entry = page.getEntry(1);
		actualIter = entry.getReferences().iterator();
		assertEquals("{{R:OneLook}}", actualIter.next().getText());
		assertEquals("{{R:Webster 1913}}", actualIter.next().getText());
		assertFalse(actualIter.hasNext());
		
		entry = page.getEntry(2);
		actualIter = entry.getReferences().iterator();
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testBoat() throws Exception {
		IWiktionaryPage page = parse("boat.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWikiString> actualIter = entry.getReferences().iterator();
		assertEquals("Weisenberg, Michael (2000) ''[http://www.poker1.com/mcu/pokerdictionary/mculib_dictionary_info.asp The Official Dictionary of Poker].''  MGI/Mike Caro University.  ISBN 978-1880069523", actualIter.next().getText());
		assertFalse(actualIter.hasNext());
		
		entry = page.getEntry(1);
		actualIter = entry.getReferences().iterator();
		assertFalse(actualIter.hasNext());
	}

}
