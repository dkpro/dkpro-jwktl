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

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENEtymologyHandler;

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

}
