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

import java.util.Iterator;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.en.ENWiktionaryEntryParserTest;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

import static java.util.stream.Collectors.toList;

/**
 * Test case for {@link ENTranslationHandler}.
 * @author Christian M. Meyer
 */
public class ENTranslationHandlerTest extends ENWiktionaryEntryParserTest {
	public void testHandleT() throws Exception {
		IWiktionaryTranslation translation = processFirst("* German: {{t|de|german-translation}}");
		assertEquals("german-translation", translation.getTranslation());
		assertEquals(Language.GERMAN, translation.getLanguage());
		assertFalse(translation.isCheckNeeded());
	}

	public void testHandleTPlus() throws Exception {
		IWiktionaryTranslation translation = processFirst("* German: {{t+|de|german-translation}}");
		assertEquals("german-translation", translation.getTranslation());
		assertEquals(Language.GERMAN, translation.getLanguage());
		assertFalse(translation.isCheckNeeded());
	}

	public void testHandleTMinusCheck() throws Exception {
		assertTrue(processFirst("* German: {{t-check|de|german-translation-needs-checking}}").isCheckNeeded());
	}

	public void testHandleTPlusCheck() throws Exception {
		assertTrue(processFirst("* German: {{t+check|de|german-translation-needs-checking}}").isCheckNeeded());
	}

	public void testRawSenseIsParsed() throws Exception {
		assertEquals("raw-sense", processFirst(
				"{{trans-top|raw-sense}}",
				"* German: {{t-check|de|german-translation-needs-checking}}",
				"{{trans-bottom}}"
		).getRawSense());
	}

	public void testWater() throws Exception {
		IWiktionaryPage page = parse("water.txt");
		IWiktionaryEntry verb = page.getEntries().stream()
				.filter(e -> Language.ENGLISH.equals(e.getWordLanguage()))
				.filter(e -> e.getPartOfSpeech() == PartOfSpeech.VERB).findFirst().get();

		List<IWiktionaryTranslation> translations = verb.getTranslations();
		assertEquals(157, translations.size());

		List<IWiktionaryTranslation> needChecking =
				translations.stream().filter(IWiktionaryTranslation::isCheckNeeded).collect(toList());

		assertEquals(7, needChecking.size());
	}

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

	public void testRemovesWikiLinks() throws Exception {
		IWiktionaryPage page = parse("as_much_as_possible.txt");

		final List<IWiktionaryTranslation> trans = page.getEntry(0).getSense(1).getTranslations(Language.findByCode("por"));
		assertEquals(2, trans.size());

		final IWiktionaryTranslation t1 = trans.get(0);
		final IWiktionaryTranslation t2 = trans.get(1);

		assertEquals("todo o possível", t1.getTranslation());
		assertEquals("o máximo possível", t2.getTranslation());
	}

	// Where no idiomatic translation exists, a translation may be given as separate, square bracketed links instead of using the {{t+}} templates
	// https://en.wiktionary.org/wiki/Wiktionary:Translations
	public void testWikiLinkFormattedTranslations() {
		final List<IWiktionaryTranslation> translations = process("* Finnish: [[kun]] [[itse]] [[tekee]], [[tietää]] [[mitä]] [[saa]]");
		assertEquals(2, translations.size());

		assertEquals("fin", translations.get(0).getLanguage().getCode());
		assertEquals("kun itse tekee", translations.get(0).getTranslation());

		assertEquals("fin", translations.get(1).getLanguage().getCode());
		assertEquals("tietää mitä saa", translations.get(1).getTranslation());
	}

	public void testSplitTranslationsSingleItem() {
		final List<String> results = ENTranslationHandler.splitTranslationParts("{{t+|fr|bas}}");
		assertEquals(1, results.size());
		assertEquals("{{t+|fr|bas}}", results.get(0));
	}

	public void testSplitTranslations() {
		final List<String> results = ENTranslationHandler.splitTranslationParts("{{t+|fr|bas}}, {{t+|fr|grave}}");

		assertEquals(2, results.size());
		assertEquals("{{t+|fr|bas}}", results.get(0));
		assertEquals("{{t+|fr|grave}}", results.get(1));
	}

	public void testSplitTranslations2() {
		final List<String> results = ENTranslationHandler.splitTranslationParts("[[quiabeiro]] {{g|m}} (''plant''), [[quiabo]] {{g|m}} (''pods'')");

		assertEquals(2, results.size());
		assertEquals("[[quiabeiro]] {{g|m}} (''plant'')", results.get(0));
		assertEquals("[[quiabo]] {{g|m}} (''pods'')", results.get(1));
	}

	public void testSplitTranslations3() {
		final List<String> results = ENTranslationHandler.splitTranslationParts("{{t|ja|舟|tr=[[ふね]], fúne|sc=Jpan}}, {{t|ja|ボート|tr=bōto|sc=Jpan}}");
		assertEquals(2, results.size());
		assertEquals("{{t|ja|舟|tr=[[ふね]], fúne|sc=Jpan}}", results.get(0));
		assertEquals("{{t|ja|ボート|tr=bōto|sc=Jpan}}", results.get(1));
	}

	public void testTransTopWithIdParameter() throws Exception {
		assertEquals("raw-sense", processFirst(
				"{{trans-top|id=Q12345|raw-sense}}",
				"* German: {{t-check|de|german-translation-needs-checking}}",
				"{{trans-bottom}}"
		).getRawSense());
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
		assertEquals(false, actual.isCheckNeeded());
	}

	protected IWiktionaryTranslation processFirst(String... body) {
		List<IWiktionaryTranslation> translations = process(body);
		assertFalse(translations.isEmpty());
		return translations.get(0);
	}

	protected List<IWiktionaryTranslation> process(String... body) {
		final WiktionarySense sense = new WiktionarySense();
		final WiktionaryEntry entry = new WiktionaryEntry();
		entry.addSense(sense);
		ParsingContext context = new ParsingContext(new WiktionaryPage(), new ENEntryFactory() {
			@Override
			public WiktionaryEntry findEntry(ParsingContext context) {
				return entry;
			}
		});
		ENTranslationHandler handler = new ENTranslationHandler();
		handler.processHead("testing", context);
		for (String line : body) {
			handler.processBody(line, context);
		}
		handler.fillContent(context);
		return entry.getTranslations();
	}
}
