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
package de.tudarmstadt.ukp.jwktl.parser.en.components;

import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.en.ENWiktionaryEntryParserTest;

import static org.junit.Assert.assertNotEquals;

/**
 * Test case for {@link ENEtymologyHandler}.
 * @author Christian M. Meyer
 */
public class ENEtymologyHandlerTest extends ENWiktionaryEntryParserTest {
	/***/
	public void testBass() throws Exception {
		IWiktionaryPage page = parse("bass.txt");
		IWiktionaryEntry entry;
		
		entry = page.getEntry(0);
		assertEquals(Language.ENGLISH, entry.getWordLanguage());
		assertEquals(PartOfSpeech.ADJECTIVE, entry.getPartOfSpeech());
		assertEquals("{{etyl|it}} {{term|basso||low|lang=it}}, from {{etyl|la}} {{term|bassus||low|lang=la}}.", entry.getWordEtymology().getText());

		entry = page.getEntry(1);
		assertEquals(Language.ENGLISH, entry.getWordLanguage());
		assertEquals(PartOfSpeech.NOUN, entry.getPartOfSpeech());
		assertEquals("{{etyl|it}} {{term|basso||low|lang=it}}, from {{etyl|la}} {{term|bassus||low|lang=la}}.", entry.getWordEtymology().getText());
		
		entry = page.getEntry(2);
		assertEquals(Language.ENGLISH, entry.getWordLanguage());
		assertEquals(PartOfSpeech.NOUN, entry.getPartOfSpeech());
		assertEquals("From {{etyl|enm}} {{term|bas|lang=enm}}, alteration of {{term|bars|lang=enm}}, from {{etyl|ang}} {{term|bærs||a fish, perch|lang=ang}}, from {{proto|Germanic|barsaz|perch\", literally \"prickly fish|lang=en}}, from {{proto|Indo-European|bhars-||bharst-|prickle, thorn, scale|lang=en}}. Cognate with {{etyl|nl|-}} {{term|baars||baars|perch, bass|lang=nl}}, {{etyl|de|-}} {{term|Barsch||perch|lang=de}}. More at {{l|en|barse}}.", entry.getWordEtymology().getText());
	}
	
	/***/
	public void testPlant() throws Exception {
		IWiktionaryPage page = parse("plant.txt");		
		IWiktionaryEntry entry;
		
		entry = page.getEntry(0);
		assertEquals(Language.ENGLISH, entry.getWordLanguage());
		assertEquals(PartOfSpeech.NOUN, entry.getPartOfSpeech());
		assertEquals("From {{etyl|la}} {{term|planta|lang=la}}, later influenced by French {{term|plante}}.", entry.getWordEtymology().getText());
		
		entry = page.getEntry(1);
		assertEquals(Language.ENGLISH, entry.getWordLanguage());
		assertEquals(PartOfSpeech.VERB, entry.getPartOfSpeech());
		assertEquals("{{etyl|la}} {{term|plantare|lang=la}}, later influenced by Old French {{term|planter}}.", entry.getWordEtymology().getText());
		
		entry = page.getEntry(2);
		assertEquals("Danish", entry.getWordLanguage().getName());
		assertEquals(PartOfSpeech.VERB, entry.getPartOfSpeech());
		assertNull(entry.getWordEtymology());
		
		entry = page.getEntry(3);
		assertEquals("Dutch", entry.getWordLanguage().getName());
		assertEquals(PartOfSpeech.NOUN, entry.getPartOfSpeech());
		assertEquals("{{etyl|fr|nl}} ''[[plante]]'', from {{etyl|la|nl}} ''[[planta]]''", entry.getWordEtymology().getText());
	}

	public void testMangueira() throws Exception {
		// has etymology header, but no content
		IWiktionaryPage page = parse("mangueira.txt");
		final IWiktionaryEntry entry = page.getEntry(1);
		assertNull(entry.getWordEtymology());

		final IWiktionaryEntry entry2 = page.getEntry(2);
		assertNull(entry2.getWordEtymology());
	}

	public void testSumo() throws Exception {
		// 5 entries with 4 different etymologies, last entry has empty one
		IWiktionaryPage page = parse("sumo.txt");
		assertEquals(5, page.getEntryCount());
		// adj, noun
		assertNotNull(page.getEntry(0).getWordEtymology());
		assertEquals(page.getEntry(1).getWordEtymology(), page.getEntry(0).getWordEtymology());
		// noun
		assertNotNull(page.getEntry(2).getWordEtymology());
		assertNotEquals(page.getEntry(2).getWordEtymology(), page.getEntry(1).getWordEtymology());
		// noun
		assertNotNull(page.getEntry(3).getWordEtymology());
		assertNotEquals(page.getEntry(3).getWordEtymology(), page.getEntry(2).getWordEtymology());
		// verb, no etymology set
		assertNull(page.getEntry(4).getWordEtymology());
	}

	/***/
	public void testWater() throws Exception {
		IWiktionaryPage page = parse("water.txt");
		IWiktionaryEntry entry;

		entry = page.getEntry(0);
		assertEquals(Language.ENGLISH, entry.getWordLanguage());
		assertEquals(PartOfSpeech.NOUN, entry.getPartOfSpeech());
		assertEquals("{{PIE root|en|wed}}\n" +
			"From {{etyl|enm|en}} {{m|enm|water|sc=Latn}}, from {{etyl|ang|en}} {{m|ang|wæter||water|sc=Latn}}, from " +
			"{{etyl|gem-pro|en}} {{m|gem-pro|*watōr||water}}, from {{etyl|ine-pro|en}} {{m|ine-pro|*wódr̥||water}}.\n" +
			"{{rel-top|cognates}}\n" +
			"Cognate with {{etyl|sco|-}} {{m|sco|wattir|sc=Latn}}, {{m|sco|watir||water|sc=Latn}}, " +
			"{{etyl|frr|-}} {{m|frr|weeter||water|sc=Latn}}, {{etyl|stq|-}} {{m|stq|woater||water|sc=Latn}}, " +
			"{{etyl|fy|-}} {{m|fy|wetter||water|sc=Latn}}, {{etyl|nl|-}} {{m|nl|water||water|sc=Latn}}, " +
			"{{etyl|nds|-}} {{m|nds|Water||water|sc=Latn}}, {{etyl|de|-}} {{m|de|Wasser|sc=Latn}}, {{etyl|sv|-}} " +
			"{{m|sv|vatten||water|sc=Latn}}, {{etyl|is|-}} {{m|is|vatn||water|sc=Latn}}, {{etyl|sga|-}} coin " +
			"{{m|sga|fodorne||otters|lit=water-dogs|sc=Latn}}, {{etyl|la|-}} {{m|la|unda||wave|sc=Latn}}, " +
			"{{etyl|lt|-}} {{m|lt|vanduõ||water|sc=Latn}}, {{etyl|ru|-}} {{m|ru|вода́||water}}, {{etyl|sq|-}} " +
			"{{m|sq|ujë||water|sc=Latn}}, {{etyl|grc|-}} {{m|grc|ὕδωρ||water}}, {{etyl|hy|-}} {{m|hy|գետ||river}}, " +
			"{{etyl|sa|-}} {{m|sa|उदन्|tr=udán||wave, water|sc=Deva}}, {{etyl|hit|-}} " +
			"{{m|hit|\uD808\uDE7F\uD808\uDC00\uD808\uDEFB|wa-a-tar|sc=Xsux}}.\n" +
			"{{rel-bottom}}",
			entry.getWordEtymology().getText());
	}

	public void testWhitespaceIsPreserved() throws Exception {
		ENEtymologyHandler handler = new ENEtymologyHandler();
		WiktionaryEntry entry = process(handler, "Foo\n", "\n", "Baz\n");
		final IWikiString etymology = entry.getWordEtymology();
		assertNotNull(etymology);
		assertEquals("Foo\n\nBaz", etymology.getText());
	}
}
