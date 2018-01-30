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

import de.tudarmstadt.ukp.jwktl.api.IPronunciation;
import de.tudarmstadt.ukp.jwktl.api.IPronunciation.PronunciationType;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.parser.en.ENWiktionaryEntryParserTest;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Test case for {@link ENPronunciationHandler}.
 * @author Christian M. Meyer
 */
public class ENPronunciationHandlerTest extends ENWiktionaryEntryParserTest {

	/***/
	public void testDictionary() throws Exception {
		IWiktionaryPage page = parse("dictionary.txt");
		final List<IPronunciation> pronunciations = page.getEntry(0).getPronunciations();
		Iterator<IPronunciation> iter = pronunciations.iterator();
		assertPronunciation(PronunciationType.IPA, "/ˈdɪkʃən(ə)ɹi/", "UK", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "/\"dIkS@n(@)ri/", "UK", iter.next());
		assertPronunciation(PronunciationType.IPA, "/ˈdɪkʃənɛɹi/", "North America", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "/\"dIkS@nEri/", "North America", iter.next());
		assertPronunciation(PronunciationType.AUDIO, "en-us-dictionary.ogg", "Audio (US)", iter.next());
		assertPronunciation(PronunciationType.AUDIO, "en-uk-dictionary.ogg", "Audio (UK)", iter.next());
		assertFalse(iter.hasNext());
	}
	
	/***/
	public void testWomen() throws Exception {
		IWiktionaryPage page = parse("women.txt");
//		printPronunciations(page);
		Iterator<? extends IWiktionaryEntry> entryIter = page.getEntries().iterator();
		Iterator<IPronunciation> pronIter = entryIter.next().getPronunciations().iterator();		
		assertPronunciation(PronunciationType.IPA, "/ˈwɪmɪn/", "RP", pronIter.next());
		assertPronunciation(PronunciationType.IPA, "/ˈwɪmən/", "US", pronIter.next());
		assertPronunciation(PronunciationType.AUDIO, "en-uk-women.ogg", "Audio (UK)", pronIter.next());
		assertPronunciation(PronunciationType.RHYME, "ɪmɪn", "", pronIter.next());
		assertFalse(pronIter.hasNext());
		pronIter = entryIter.next().getPronunciations().iterator();
		assertPronunciation(PronunciationType.AUDIO, "zh-wǒmen.ogg", "audio", pronIter.next());
		assertPronunciation(PronunciationType.IPA, "[ wo˨˩mən ]", "", pronIter.next());
		assertFalse(pronIter.hasNext());
		assertFalse(entryIter.hasNext());
	}

	/***/
	public void testBass() throws Exception {
		IWiktionaryPage page = parse("bass.txt");
//		printPronunciations(page);
		Iterator<? extends IWiktionaryEntry> entryIter = page.getEntries().iterator();
		Iterator<IPronunciation> pronIter = entryIter.next().getPronunciations().iterator();		
		assertPronunciation(PronunciationType.IPA, "/beɪs/", "", pronIter.next());
		assertPronunciation(PronunciationType.SAMPA, "/beIs/", "", pronIter.next());
		assertPronunciation(PronunciationType.AUDIO, "en-us-bass-low.ogg", "Audio (US)", pronIter.next());
		assertFalse(pronIter.hasNext());
		pronIter = entryIter.next().getPronunciations().iterator();
		assertPronunciation(PronunciationType.IPA, "/beɪs/", "", pronIter.next());
		assertPronunciation(PronunciationType.SAMPA, "/beIs/", "", pronIter.next());
		assertPronunciation(PronunciationType.AUDIO, "en-us-bass-low.ogg", "Audio (US)", pronIter.next());
		assertFalse(pronIter.hasNext());
		pronIter = entryIter.next().getPronunciations().iterator();
//		assertPronunciation(PronunciationType.enPR, "/băs/", "", pronIter.next());
		assertPronunciation(PronunciationType.IPA, "/bæs/", "", pronIter.next());
		assertPronunciation(PronunciationType.SAMPA, "/b{s/", "", pronIter.next());
		assertPronunciation(PronunciationType.AUDIO, "en-us-bass.ogg", "Audio (US)", pronIter.next());
		assertFalse(pronIter.hasNext());
		assertNull(entryIter.next().getPronunciations());
		assertFalse(entryIter.hasNext());
	}

	/***/
	public void testWord() throws Exception {
		IWiktionaryPage page = parse("word.txt");
//		printPronunciations(page);
		Iterator<? extends IWiktionaryEntry> entryIter = page.getEntries().iterator();
		Iterator<IPronunciation> pronIter;
		for (int i = 0; i < 3; i++) {
			pronIter = entryIter.next().getPronunciations().iterator();
			assertPronunciation(PronunciationType.IPA, "/wɜːd/", "RP", pronIter.next());
			assertPronunciation(PronunciationType.SAMPA, "/w3:d/", "RP", pronIter.next());
//			assertPronunciation(PronunciationType.IPA, "wûrd", "US", pronIter.next()); //enPR
			assertPronunciation(PronunciationType.IPA, "/wɝd/", "US", pronIter.next());
			assertPronunciation(PronunciationType.SAMPA, "/w3`d/", "US", pronIter.next());		
			assertPronunciation(PronunciationType.AUDIO, "en-us-word.ogg", "Audio (US)", pronIter.next());
			assertPronunciation(PronunciationType.RHYME, "ɜː(r)d", "", pronIter.next());
			assertFalse(pronIter.hasNext());
		}
		pronIter = entryIter.next().getPronunciations().iterator();
		assertPronunciation(PronunciationType.AUDIO, "Nl-word.ogg", "audio", pronIter.next());
		assertFalse(pronIter.hasNext());
		pronIter = entryIter.next().getPronunciations().iterator();
		assertPronunciation(PronunciationType.IPA, "/word/", "", pronIter.next());
		assertFalse(pronIter.hasNext());
		assertFalse(entryIter.hasNext());		
	}
	
	/***/
	public void testWorker() {
		ENPronunciationHandler w = new ENPronunciationHandler();
		w.processHead("", new ParsingContext(null));
		w.processBody("* {{a|UK}} {{IPA|/ˈdɪkʃən(ə)ɹi/}}, {{SAMPA|/&quot;dIkS@n(@)ri/}}", null);
		w.processBody("* {{a|North America}} {{enPR|dĭk'shə-nĕr-ē}}, {{IPA|/ˈdɪkʃənɛɹi/}}, {{SAMPA|/&quot;dIkS@nEri/}}", null);
		w.processBody("* {{audio|en-us-dictionary.ogg|Audio (US)}}", null);
		w.processBody("* {{audio|en-uk-dictionary.ogg|Audio (UK)}}", null);

		w.processBody("* {{IPA|/fɹiː/}}, {{SAMPA|/fri:/}}", null);
		w.processBody("* {{audio|en-us-free.ogg|Audio (US)|lang=en}}", null);
		w.processBody("* {{audio|En-uk-free.ogg|Audio (UK)|lang=en}}", null);
		w.processBody("*: {{rhymes|iː}}", null);

		w.processBody("* {{a|RP}} {{IPA|/dɒɡ/}}, {{SAMPA|/dQg/}}", null);
		w.processBody("* {{a|US}} {{IPA|/dɔɡ/}}, {{SAMPA|/dOg/}}", null);
		w.processBody("* {{a|US, in accents with the [[cot-caught merger]]}} {{IPA|/dɑɡ/}}, {{SAMPA|/dAg/}}", null);
		w.processBody("* {{audio|en-us-dog.ogg|Audio (US)}}", null);
		w.processBody("* {{audio|En-uk-a dog.ogg|Audio (UK)}}", null);
		w.processBody("*: {{rhymes|ɒɡ}}", null);

		w.processBody("* {{IPA|lang=es|/pje/}}", null);

		w.processBody("* {{IPA|/ɑː/|/a/|lang=mul}} {{qualifier|most languages}}", null);

		w.processBody("* {{qualifier|letter name}}", null);
		w.processBody("** {{a|RP|GenAm}} {{IPA|/eɪ̯/}}, {{SAMPA|/eI/}}", null);
		w.processBody("** {{audio|en-us-a.ogg|Audio (US)}}", null);
		w.processBody("** {{a|AusE}} {{IPA|/æɪ/}}, {{SAMPA|/{I/}}", null);
		w.processBody("* {{rhymes|eɪ}}", null);
		w.processBody("*: The current pronunciation is a comparatively modern sound, and has taken the place of what, till about the early part of the 15th century, was similar to that in other languages.", null); 

		w.processBody("* {{sense|letter name}} {{IPA|/a/|lang=eo}}", null);
		w.processBody("* {{sense|phoneme}} {{IPA|/a/|lang=eo}}", null);
		
		Iterator<IPronunciation> iter = w.getPronunciations().iterator();
		assertPronunciation(PronunciationType.IPA, "/ˈdɪkʃən(ə)ɹi/", "UK", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "/&quot;dIkS@n(@)ri/", "UK", iter.next());
//		assertPronunciation(PronunciationType.IPA, "dĭk'shə-nĕr-ē", "North America", iter.next()); //enPR
		assertPronunciation(PronunciationType.IPA, "/ˈdɪkʃənɛɹi/", "North America", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "/&quot;dIkS@nEri/", "North America", iter.next());
		assertPronunciation(PronunciationType.AUDIO, "en-us-dictionary.ogg", "Audio (US)", iter.next());
		assertPronunciation(PronunciationType.AUDIO, "en-uk-dictionary.ogg", "Audio (UK)", iter.next());
		
		assertPronunciation(PronunciationType.IPA, "/fɹiː/", "", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "/fri:/", "", iter.next());
		assertPronunciation(PronunciationType.AUDIO, "en-us-free.ogg", "Audio (US)", iter.next());
		assertPronunciation(PronunciationType.AUDIO, "En-uk-free.ogg", "Audio (UK)", iter.next());
		assertPronunciation(PronunciationType.RHYME, "iː", "", iter.next());

		assertPronunciation(PronunciationType.IPA, "/dɒɡ/", "RP", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "/dQg/", "RP", iter.next());
		assertPronunciation(PronunciationType.IPA, "/dɔɡ/", "US", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "/dOg/", "US", iter.next());
		assertPronunciation(PronunciationType.IPA, "/dɑɡ/", "US, in accents with the [[cot-caught merger]]", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "/dAg/", "US, in accents with the [[cot-caught merger]]", iter.next());		
		assertPronunciation(PronunciationType.AUDIO, "en-us-dog.ogg", "Audio (US)", iter.next());
		assertPronunciation(PronunciationType.AUDIO, "En-uk-a dog.ogg", "Audio (UK)", iter.next());
		assertPronunciation(PronunciationType.RHYME, "ɒɡ", "", iter.next());
		
		//TODO: Pronunciation cannot be extracted properly yet.
		assertPronunciation(PronunciationType.IPA, "/pje/", "", iter.next());
		iter.next(); //assertPronunciation(PronunciationType.IPA, "/ɑː/", "{{qualifier|most languages}}", iter.next());
		iter.next(); //assertPronunciation(PronunciationType.IPA, "/eɪ̯/", "RP|GenAm {{qualifier|letter name}}", iter.next());
		iter.next(); //assertPronunciation(PronunciationType.SAMPA, "/eI/", "RP|GenAm {{qualifier|letter name}}", iter.next());
		iter.next(); //assertPronunciation(PronunciationType.AUDIO, "en-us-a.ogg", "Audio (US) {{qualifier|letter name}}", iter.next());
		iter.next(); //assertPronunciation(PronunciationType.IPA, "/æɪ/", "AusE {{qualifier|letter name}}", iter.next());
		iter.next(); //assertPronunciation(PronunciationType.SAMPA, "/{I/", "AusE {{qualifier|letter name}}", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "/{I/", "AusE", iter.next());
		assertPronunciation(PronunciationType.RHYME, "eɪ", "", iter.next());

		iter.next(); //assertPronunciation(PronunciationType.IPA, "/a/", "{{sense|letter name}}", iter.next());
		iter.next(); //assertPronunciation(PronunciationType.IPA, "/a/", "{{sense|phoneme}} ", iter.next());		
		assertFalse(iter.hasNext());
	}

	public void testFlippedLanguageParameter() {
		ENPronunciationHandler w = new ENPronunciationHandler();
		w.processHead("", new ParsingContext(null));
		w.processBody("* {{a|Brazil}} {{IPA|lang=pt|/ˈfa.siw/}}", null);
		Iterator<IPronunciation> iter = w.getPronunciations().iterator();
		assertPronunciation(PronunciationType.IPA, "/ˈfa.siw/", "Brazil", iter.next());
		assertFalse(iter.hasNext());
	}

	public void testRawPronunciationType() {
		final List<IPronunciation> pronunciations = process("* {{IPA|/budɛ̃/|lang=fr}}", "* {{fr-IPA|cauboille}}", "* {{zh-pron}}");
		assertEquals(3, pronunciations.size());

		assertEquals("/budɛ̃/", pronunciations.get(0).getText());
		assertEquals(PronunciationType.IPA, pronunciations.get(0).getType());

		assertEquals("{{fr-IPA|cauboille}}", pronunciations.get(1).getText());
		assertEquals(PronunciationType.RAW, pronunciations.get(1).getType());

		assertEquals("{{zh-pron}}", pronunciations.get(2).getText());
		assertEquals(PronunciationType.RAW, pronunciations.get(2).getType());
	}

	public void testFiltersEmptyPronunciations() {
		final List<IPronunciation> pronunciations = process("* {{IPA|/ma.dam wa.ta.na.be/||lang=fr}}");

		assertEquals(1, pronunciations.size());
		assertEquals("/ma.dam wa.ta.na.be/", pronunciations.get(0).getText());
	}

	protected static void assertPronunciation(final PronunciationType type,
											  final String text, final String note, final IPronunciation actual) {
		assertEquals("type does not match", type, actual.getType());
		assertEquals("text does not match", text, actual.getText());
		assertEquals("note does not match", note, actual.getNote());
	}

	protected List<IPronunciation> process(String... body) {
		final WiktionaryEntry entry = new WiktionaryEntry();
		ParsingContext context = new ParsingContext(new WiktionaryPage(), new ENEntryFactory() {
			@Override
			public WiktionaryEntry findEntry(ParsingContext context) {
				return entry;
			}
		});
		ENPronunciationHandler handler = new ENPronunciationHandler();
		handler.processHead("testing", context);
		for (String line : body) {
			handler.processBody(line, context);
		}
		return handler.getPronunciations();
	}
}
