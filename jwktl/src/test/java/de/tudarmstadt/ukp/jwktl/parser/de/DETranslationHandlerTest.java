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

import java.util.Iterator;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DETranslationHandler;

/**
 * Test case for {@link DETranslationHandler}.
 * @author Christian M. Meyer
 */
public class DETranslationHandlerTest extends DEWiktionaryEntryParserTest {

	/** The entry "Tun, der" contains a section "Übersetzungen" but no
	 *  translation table, which caused the parser to skip anything behind
	 *  the translations (and maybe even merge two articles. */	
	public void testRespectEndOfSection() throws Exception {
		IWiktionaryPage page = parse("Tun.txt");
		assertEquals(2, page.getEntryCount());
		
		IWiktionarySense sense = page.getEntry(0).getSense(1); 
		assertEquals(0, sense.getRelations(RelationType.HYPERNYM).size());
		assertEquals(4, sense.getReferences().size());
		
		sense = page.getEntry(1).getSense(1);
		assertEquals(1, sense.getRelations(RelationType.HYPERNYM).size());
		assertEquals("Handlung", sense.getRelations(RelationType.HYPERNYM).get(0).getTarget());
		assertEquals(5, sense.getReferences().size());
	}

	/***/
	public void testTranslationExtraction() throws Exception {
		IWiktionaryPage page = parse("Aberration.txt");
		Iterator<IWiktionaryTranslation> iter = page.getEntry(0).getSense(1).getTranslations().iterator();
		assertTranslation("English", "aberration", iter.next());
		assertTranslation("French", "aberration", iter.next());
		assertTranslation("Icelandic", "ljósvilla", iter.next());
		assertTranslation("Catalan", "aberració", iter.next());
		assertTranslation("Polish", "aberracja", iter.next());
		assertTranslation("Polish", "aberracja światła", iter.next());
		assertTranslation("Russian", "аберрация", iter.next());
		assertTranslation("Russian", "отклонение", iter.next());
		assertTranslation("Swedish", "aberration", iter.next());
		assertTranslation("Spanish", "aberración", iter.next());
		assertFalse(iter.hasNext());
		
		iter = page.getEntry(0).getSense(2).getTranslations().iterator();
		assertTranslation("English", "aberration", iter.next());
		assertTranslation("French", "aberration", iter.next());
		assertTranslation("Icelandic", "myndskekkja", iter.next());
		assertTranslation("Icelandic", "spegilskekkja", iter.next());
		assertTranslation("Icelandic", "linsuskekkja", iter.next());
		assertTranslation("Polish", "aberracja", iter.next());
		assertTranslation("Polish", "aberracja optyczna", iter.next());
		assertTranslation("Russian", "аберрация", iter.next());
		assertTranslation("Russian", "отклонение", iter.next());
		assertTranslation("Swedish", "aberration", iter.next());
		assertTranslation("Spanish", "aberración", iter.next());
		assertFalse(iter.hasNext());
		
		page = parse("April.txt");
		iter = page.getEntry(1).getSense(1).getTranslations().iterator();
		assertTranslation("German", "April", iter.next());
		assertTranslation("French", "avril", iter.next());
		assertFalse(iter.hasNext());

		page = parse("milliard.txt");
		iter = page.getEntry(0).getSense(1).getTranslations().iterator();
		assertTranslation("German", "Milliarde", iter.next());
		assertTranslation("English", "billion", iter.next());
		assertTranslation("Finnish", "miljardi", iter.next());
		assertTranslation("French", "milliard", iter.next());
		assertTranslation("Japanese", "十億", iter.next());
		assertTranslation("Dutch", "miljard", iter.next());
		assertFalse(iter.hasNext());
		
		page = parse("boulder.txt");
		iter = page.getEntry(0).getSense(1).getTranslations().iterator();
		assertTranslation("German", "Felsbrocken", iter.next());
		assertTranslation("German", "Felsblock", iter.next());
		assertTranslation("German", "Stein", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(0).getSense(2).getTranslations().iterator();
		assertTranslation("German", "Geröll", iter.next());
		assertFalse(iter.hasNext());
		
		page = parse("robber_baron.txt");
		iter = page.getEntry(0).getSense(1).getTranslations().iterator();
		assertTranslation("German", "Raubritter", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(0).getSense(2).getTranslations().iterator();
		assertTranslation("German", "skrupelloser Kapitalist", iter.next());
		assertTranslation("German", "Räuber-Baron", iter.next());
		assertFalse(iter.hasNext());
		
		page = parse("harness.txt");
		iter = page.getEntry(0).getSense(1).getTranslations().iterator();
		assertTranslation("German", "Zuggeschirr", iter.next());
		assertTranslation("German", "Geschirr", iter.next());
		assertTranslation("German", "Gurtzeug", iter.next());
		assertTranslation("German", "Harnisch", iter.next());
		assertTranslation("German", "Beschirrung", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(0).getSense(2).getTranslations().iterator();
		assertTranslation("German", "Pferdegeschirr", iter.next());
		iter = page.getEntry(0).getSense(3).getTranslations().iterator();
		assertTranslation("German", "Zaumzeug", iter.next());
		iter = page.getEntry(0).getSense(4).getTranslations().iterator();
		assertTranslation("German", "Kabelstrang", iter.next());
		iter = page.getEntry(0).getSense(5).getTranslations().iterator();
		assertTranslation("German", "Klettergurt", iter.next());
		iter = page.getEntry(0).getSense(6).getTranslations().iterator();
		assertTranslation("German", "Webgeschirr", iter.next());
		iter = page.getEntry(0).getSense(7).getTranslations().iterator();
		assertTranslation("German", "Alltagstrott", iter.next());
		iter = page.getEntry(0).getSense(8).getTranslations().iterator();
		assertTranslation("German", "Harnisch", iter.next());
		assertTranslation("German", "Rüstung", iter.next());
		assertFalse(iter.hasNext());
		
		page = parse("Gynoeceum.txt");
		iter = page.getEntry(0).getSense(1).getTranslations(Language.ENGLISH).iterator();
		assertTranslation("English", "gynoecium", iter.next());
		assertTranslation("English", "gynaecium", iter.next());
		assertTranslation("English", "gynaeceum", iter.next());
		assertTranslation("English", "gynecium", iter.next());
		assertFalse(iter.hasNext());

		page = parse("mitreissen.txt");
//		iter = page.getEntry(0).getUnassignedSense().getTranslations().iterator();
//		assertTranslation("Norwegian", "dra", iter.next());
//		assertTranslation("Norwegian", "trekke", iter.next());
//		assertTranslation("Norwegian", "seg", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(0).getSense(1).getTranslations().iterator();
		assertTranslation("Swedish", "dra med sig", iter.next());
		assertTranslation("Swedish", "svepa med sig", iter.next());
		assertTranslation("Swedish", "rycka med sig", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(0).getSense(2).getTranslations().iterator();
		assertTranslation("Basque", "poztu", iter.next());
		assertTranslation("English", "fill with enthusiasm", iter.next());
		assertTranslation("French", "enthousiasmer", iter.next());
		assertTranslation("Norwegian", "rive", iter.next());
		assertTranslation("Norwegian", "med", iter.next());
		assertTranslation("Norwegian", "begeistre", iter.next());
		assertTranslation("Swedish", "rycka med sig", iter.next());
		assertTranslation("Spanish", "apasionar", iter.next());
		assertTranslation("Spanish", "arrebatar", iter.next());
//		*{{no}}: {{Ü|no|dra}}/{{Ü|no|trekke}} {{Ü|no|med}} {{Ü|no|seg}}; [2] {{Ü|no|rive}} {{Ü|no|med}}, {{Ü|no|begeistre}}
		assertFalse(iter.hasNext());
	}
	
	/***/
	public void testAdditionalInformationExtraction() throws Exception {
		IWiktionaryPage page = parse("Aberration.txt");
		List<IWiktionaryTranslation> trans = page.getEntry(0).getSense(1).getTranslations(Language.RUSSIAN);
		assertEquals(2, trans.size());
		assertTranslation("Russian", "аберрация", trans.get(0));
		assertEquals("aberrázija", trans.get(0).getTransliteration());
		assertEquals("{{f}}", trans.get(0).getAdditionalInformation());
		
		assertTranslation("Russian", "отклонение", trans.get(1));
		assertEquals("otklonénije", trans.get(1).getTransliteration());
		assertEquals("{{f}}", trans.get(1).getAdditionalInformation());
		
		trans = page.getEntry(0).getSense(2).getTranslations(Language.findByName("ICELANDIC"));
		assertEquals(3, trans.size());
		assertTranslation("Icelandic", "myndskekkja", trans.get(0));
		assertEquals("", trans.get(0).getAdditionalInformation());
		assertTranslation("Icelandic", "spegilskekkja", trans.get(1));
		assertEquals("bei Spiegeln", trans.get(1).getAdditionalInformation());
		assertTranslation("Icelandic", "linsuskekkja", trans.get(2));
		assertEquals("bei Linsen", trans.get(2).getAdditionalInformation());
	}

	/***/
	public void testBoot() throws Exception {
		IWiktionaryPage page = parse("Boot.txt");
		List<IWiktionaryTranslation> trans = page.getEntry(0).getSense(1).getTranslations(Language.RUSSIAN);
		assertEquals(1, trans.size());
		assertTranslation("Russian", "лодка", trans.get(0));
		assertEquals("lódka", trans.get(0).getTransliteration());
		assertEquals("", trans.get(0).getAdditionalInformation());

		trans = page.getEntry(0).getSense(1).getTranslations(Language.findByCode("fre"));
		assertEquals(1, trans.size());
		assertTranslation("French", "bateau", trans.get(0));
		assertEquals(null, trans.get(0).getTransliteration());
		assertEquals("{{m}}", trans.get(0).getAdditionalInformation());

		trans = page.getEntry(0).getSense(1).getTranslations(Language.findByCode("ita"));
		assertEquals(3, trans.size());
		assertTranslation("Italian", "battello", trans.get(0));
		assertEquals(null, trans.get(0).getTransliteration());
		assertEquals("", trans.get(0).getAdditionalInformation());
		assertTranslation("Italian", "barca", trans.get(1));
		assertEquals(null, trans.get(1).getTransliteration());
		assertEquals("{{f}}", trans.get(1).getAdditionalInformation());
		assertTranslation("Italian", "imbarcazione", trans.get(2));
		assertEquals(null, trans.get(2).getTransliteration());
		assertEquals("{{f}}", trans.get(2).getAdditionalInformation());
	}

	protected static void assertTranslation(final String language,
			final String translation, final IWiktionaryTranslation actual) {
		if (actual.getLanguage() == null)
			assertEquals(language, actual.getLanguage());
		else
			assertEquals(language, actual.getLanguage().getName());
		assertEquals(translation, actual.getTranslation());
	}
		
}
