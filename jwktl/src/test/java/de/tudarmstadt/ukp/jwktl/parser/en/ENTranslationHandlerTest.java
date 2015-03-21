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
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENTranslationHandler;

/**
 * Test case for {@link ENTranslationHandler}.
 * @author Christian M. Meyer
 */
public class ENTranslationHandlerTest extends ENWiktionaryEntryParserTest {

	/***/
	public void testAborted() throws Exception {
		IWiktionaryPage page = parse("aborted.txt");
		Iterator<IWiktionaryTranslation> iter = page.getEntry(0).getUnassignedSense().getTranslations().iterator();
		assertTranslation("Catalan", "avortat", null, iter.next());
		assertTranslation("French", "avorté", null, iter.next());
		assertTranslation("Interlingua", "abortate", null, iter.next());
		assertTranslation("Italian", "terminato", null, iter.next());
		assertTranslation("Portuguese", "abortado", null, iter.next());
		assertTranslation("Spanish", "abortado", null, iter.next());
		assertTranslation("Swedish", "aborterad", null, iter.next());
		assertFalse(iter.hasNext());
	}
	
	/***/
	public void testBass() throws Exception {
		IWiktionaryPage page = parse("bass.txt");		
		Iterator<IWiktionaryTranslation> iter = page.getEntry(0).getSense(1).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "bass", "low in pitch", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(1).getSense(1).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "Baß", "low spectrum of sound", iter.next());
		assertTranslation("German", "Bass", "low spectrum of sound", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(1).getSense(2).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "Baß", "section of musical group", iter.next());
		assertTranslation("German", "Bass", "section of musical group", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(1).getSense(3).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "Baß", "singer", iter.next());
		assertTranslation("German", "Bass", "singer", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(1).getSense(4).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "Baß", "musical instrument", iter.next());
		assertTranslation("German", "Bass", "musical instrument", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(1).getSense(5).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "Baßschlüssel", "clef sign", iter.next());
		assertTranslation("German", "Bassschlüssel", "clef sign", iter.next());
		assertFalse(iter.hasNext());
		
		iter = page.getEntry(2).getSense(1).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "Barsch", "perch", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testBe() throws Exception {
		IWiktionaryPage page = parse("be.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		assertEquals(19, entry.getSenseCount());
		Iterator<IWiktionaryTranslation> iter = entry.getSense(1).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "occupy a place", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(2).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "stattfinden", "occur, take place", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(3).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "exist", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(4).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "elliptical form of &quot;be here&quot;, or similar", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(5).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to indicate that the subject and object are the same", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(6).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to indicate that the values on either side of an equation are the same", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(7).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to indicate that the subject plays the role of the predicate nominative", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(8).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to connect a noun to an adjective that describes it", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(9).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to indicate that the subject has the qualities described by a noun or noun phrase", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(10).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "werden", "used to form the passive voice", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(11).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to form the continuous forms of various tenses", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(12).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "(archaic) used to form the perfect aspect with certain intransitive verbs", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(13).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to form future tenses, especially the future subjunctive", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(14).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to indicate age", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(15).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to indicate height", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(16).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to indicate time of day, day of the week, or date", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(17).getTranslations(Language.GERMAN).iterator();
		assertFalse(iter.hasNext());
		iter = entry.getSense(18).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "sein", "used to indicate weather, air quality, or the like", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(19).getTranslations(Language.GERMAN).iterator();
		assertFalse(iter.hasNext());
	}
	
	/***/
	public void testBoat() throws Exception {
		IWiktionaryPage page = parse("boat.txt");
		Iterator<IWiktionaryTranslation> iter = page.getEntry(0).getSense(1).getTranslations(Language.RUSSIAN).iterator();
		assertTranslation("Russian", "лодка", "water craft", iter.next());
		assertTranslation("Russian", "шлюпка", "water craft", iter.next());
		assertTranslation("Russian", "корабль", "water craft", iter.next());
		assertFalse(iter.hasNext());
		iter = page.getEntry(0).getSense(1).getTranslations(Language.GERMAN).iterator();
		assertTranslation("German", "Boot", "water craft", iter.next());
		assertTranslation("German", "Schiff", "water craft", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testDictionary() throws Exception {
		IWiktionaryPage page = parse("dictionary.txt");
		/*for (IWiktionaryTranslation t : page.getEntry(0).getSense(1).getTranslations())
			System.out.println(t.getLanguage() + ":" + t.getTranslation());*/
		
		Iterator<IWiktionaryTranslation> iter = page.getEntry(0).getSense(1).getTranslations(Language.findByName("Mandarin Chinese")).iterator();
		assertTranslation("Mandarin Chinese", "字典", "publication that explains the meanings of an ordered list of words", iter.next());
		assertTranslation("Mandarin Chinese", "詞典", "publication that explains the meanings of an ordered list of words", iter.next());
		assertTranslation("Mandarin Chinese", "词典", "publication that explains the meanings of an ordered list of words", iter.next());
		assertFalse(iter.hasNext());
		
		iter = page.getEntry(0).getSense(1).getTranslations(Language.findByName("ZULU")).iterator();
		assertTranslation("Zulu", "isichazimazwi", "publication that explains the meanings of an ordered list of words", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testEncyclopedia() throws Exception {
		IWiktionaryPage page = parse("encyclopedia.txt");
		Iterator<IWiktionaryTranslation> iter = page.getEntry(0).getSense(1).getTranslations(Language.findByName("LOW SAXON")).iterator();
		assertTranslation("Low German", "nokieksel", "comprehensive reference with articles on a range of topic", iter.next());
		assertFalse(iter.hasNext());
		
		iter = page.getEntry(0).getSense(1).getTranslations(null).iterator();
		assertTranslation(null, "енциклопедија", "comprehensive reference with articles on a range of topic", iter.next());
		assertTranslation(null, "enciklopedija", "comprehensive reference with articles on a range of topic", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testNonsense() throws Exception {
		IWiktionaryPage page = parse("nonsense.txt");
		Iterator<IWiktionaryTranslation> iter = page.getEntry(0).getSense(1).getTranslations(Language.findByName("ICELANDIC")).iterator();
		assertTranslation("Icelandic", "rugl", "meaningless words", iter.next());
		assertTranslation("Icelandic", "bull", "meaningless words", iter.next());
		iter = page.getEntry(0).getSense(1).getTranslations(Language.findByName("PORTUGUESE")).iterator();
		assertTranslation("Portuguese", "besteira", "meaningless words", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSeawater() throws Exception {
		IWiktionaryPage page = parse("seawater.txt");
		Iterator<IWiktionaryTranslation> iter = page.getEntry(0).getSense(1).getTranslations().iterator();
		assertTranslation("Finnish", "merivesi", null, iter.next());
		assertTranslation("French", "eau de mer", null, iter.next());
		assertTranslation("German", "Meerwasser", null, iter.next());
		assertTranslation("German", "Salzwasser", null, iter.next());
		assertTranslation("Japanese", "海水", null, iter.next());
		assertTranslation("Spanish", "agua salada", null, iter.next());
//		* Japanese: [[海水]] ([[かいすい]], kaisui)
		assertFalse(iter.hasNext());
	}

	/***/
	public void testAdditionalInformationExtraction() throws Exception {
		IWiktionaryPage page = parse("dictionary.txt");
		List<IWiktionaryTranslation> trans = page.getEntry(0).getSense(1).getTranslations(Language.RUSSIAN);
		assertEquals(1, trans.size());
		assertTranslation("Russian", "словарь", "publication that explains the meanings of an ordered list of words", trans.get(0));
		assertEquals("slovár’", trans.get(0).getTransliteration());
		assertEquals("{{m}}", trans.get(0).getAdditionalInformation());
		
		trans = page.getEntry(0).getSense(1).getTranslations(Language.findByName("Mandarin CHINESE"));
		assertEquals(3, trans.size());
		assertTranslation("Mandarin Chinese", "字典", "publication that explains the meanings of an ordered list of words", trans.get(0));
		assertEquals("zìdiǎn", trans.get(0).getTransliteration());
		assertEquals("{{qualifier|character dictionary}}", trans.get(0).getAdditionalInformation());
		assertTranslation("Mandarin Chinese", "詞典", "publication that explains the meanings of an ordered list of words", trans.get(1));
		assertEquals(null, trans.get(1).getTransliteration());
		assertEquals("", trans.get(1).getAdditionalInformation());
		assertTranslation("Mandarin Chinese", "词典", "publication that explains the meanings of an ordered list of words", trans.get(2));
		assertEquals("cídiǎn", trans.get(2).getTransliteration());
		assertEquals("", trans.get(2).getAdditionalInformation());
	}

	public void testAdditionalInformationExtractionDoesNotStripTooMuchContent() throws Exception {
		IWiktionaryPage page = parse("gumbo.txt");

		final List<IWiktionaryTranslation> trans = page.getEntry(0).getSense(1).getTranslations(Language.findByCode("por"));
		assertEquals(2, trans.size());

		final IWiktionaryTranslation t1 = trans.get(0);
		final IWiktionaryTranslation t2 = trans.get(1);

		assertEquals("quiabeiro", t1.getTranslation());
		assertEquals("quiabo", t2.getTranslation());
		assertEquals("{{g|m}} (plant)", t1.getAdditionalInformation());
		assertEquals("{{g|m}} (pods)", t2.getAdditionalInformation());
	}


	protected static void assertTranslation(final String language,
			final String translation,
			final String rawSense,
			final IWiktionaryTranslation actual) {
		if (actual.getLanguage() == null) {
			assertNull(language);
		} else {
			assertEquals(language, actual.getLanguage().getName());
		}
		assertEquals(rawSense, actual.getRawSense());
		assertEquals(translation, actual.getTranslation());
	}

}
