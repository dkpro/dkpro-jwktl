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
package de.tudarmstadt.ukp.jwktl.parser.de;

import java.util.Arrays;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEPartOfSpeechHandler;

/**
 * Test case for {@link DEPartOfSpeechHandler}.
 * @author Christian M. Meyer
 */
public class DEPartOfSpeechHandlerTest extends DEWiktionaryEntryParserTest {

	/***/
	public void testBar() throws Exception {
		IWiktionaryPage page = parse("bar.txt");
		assertEquals(3, page.getEntryCount());
	}

	/***/
	public void testApril() throws Exception {
		IWiktionaryPage page = parse("April.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		assertEquals(GrammaticalGender.MASCULINE, entry.getGender());
		assertEquals(1, entry.getGenders().size());
		assertEquals(GrammaticalGender.MASCULINE, entry.getGenders().get(0));
	}

	/***/
	public void testLiter() throws Exception {
		IWiktionaryPage page = parse("Liter.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		assertEquals(GrammaticalGender.NEUTER, entry.getGender());
		assertEquals(2, entry.getGenders().size());
		assertEquals(GrammaticalGender.NEUTER, entry.getGenders().get(0));
		assertEquals(GrammaticalGender.MASCULINE, entry.getGenders().get(1));
	}

	/***/
	public void testNutella() throws Exception {
		IWiktionaryPage page = parse("Nutella.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		assertEquals(GrammaticalGender.MASCULINE, entry.getGender());
		assertEquals(3, entry.getGenders().size());
		assertEquals(GrammaticalGender.MASCULINE, entry.getGenders().get(0));
		assertEquals(GrammaticalGender.FEMININE, entry.getGenders().get(1));
		assertEquals(GrammaticalGender.NEUTER, entry.getGenders().get(2));
	}

	/***/
	public void testTetragraph() throws Exception {
		IWiktionaryPage page = parse("Tetragraph.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		assertEquals(GrammaticalGender.MASCULINE, entry.getGender());
		assertEquals(Arrays.asList(GrammaticalGender.MASCULINE, GrammaticalGender.NEUTER), entry.getGenders());
	}
	
	/***/
	public void testFlipchart() throws Exception {
		IWiktionaryPage page = parse("Flipchart.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		assertEquals(GrammaticalGender.MASCULINE, entry.getGender());
		assertEquals(Arrays.asList(GrammaticalGender.MASCULINE, GrammaticalGender.FEMININE, GrammaticalGender.NEUTER), entry.getGenders());
	}
}