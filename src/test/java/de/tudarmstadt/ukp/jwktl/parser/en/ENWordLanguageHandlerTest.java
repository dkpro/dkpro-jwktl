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

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENWordLanguageHandler;

/**
 * Test case for {@link ENWordLanguageHandler}.
 * @author Christian M. Meyer
 */
public class ENWordLanguageHandlerTest extends ENWiktionaryEntryParserTest {

	/***/
	public void testAbele() throws Exception {
		IWiktionaryPage page = parse("abele.txt");
		Iterator<IWiktionaryEntry> entryIter = page.getEntries().iterator();
		assertEntry(Language.ENGLISH, PartOfSpeech.NOUN, 1, entryIter.next());
		assertEntry(Language.findByName("Novial"), PartOfSpeech.NOUN, 1, entryIter.next());
		assertFalse(entryIter.hasNext());
	}

	/***/
	public void testBass() throws Exception {
		IWiktionaryPage page = parse("bass.txt");
		Iterator<IWiktionaryEntry> entryIter = page.getEntries().iterator();
		assertEntry(Language.ENGLISH, PartOfSpeech.ADJECTIVE, 1, entryIter.next());
		assertEntry(Language.ENGLISH, PartOfSpeech.NOUN, 5, entryIter.next());
		assertEntry(Language.ENGLISH, PartOfSpeech.NOUN, 1, entryIter.next());
		assertEntry(Language.findByName("Romansch"), PartOfSpeech.ADJECTIVE, 1, entryIter.next());
		assertFalse(entryIter.hasNext());
	}
	
	/***/
	public void testDid() throws Exception {
		IWiktionaryPage page = parse("did.txt");
		Iterator<IWiktionaryEntry> entryIter = page.getEntries().iterator();
		assertEntry(Language.findByName("Translingual"), PartOfSpeech.NUMBER, 1, entryIter.next());
		assertEntry(Language.ENGLISH, PartOfSpeech.VERB, 1, entryIter.next());
		assertEntry(Language.findByName("Old Welsh"), PartOfSpeech.NOUN, 1, entryIter.next());
		assertFalse(entryIter.hasNext());
	}

	/***/
	public void testIt_s() throws Exception {
		/*BufferedReader r = new BufferedReader(
				new InputStreamReader(new FileInputStream(
						new File("src/test/resources/articles-en/it_s.txt")), 
						"UTF-8"));
		String line;
		while ((line = r.readLine()) != null)
			System.out.println(line);
		r.close();
		System.out.println();
		System.out.println();*/
		
		IWiktionaryPage page = parse("it_s.txt");
		Iterator<IWiktionaryEntry> entryIter = page.getEntries().iterator();
		assertEntry(Language.ENGLISH, PartOfSpeech.CONTRACTION, 2, entryIter.next());
		assertFalse(entryIter.hasNext());
	}
	
	/***/
	public void testMay() throws Exception {
		IWiktionaryPage page = parse("may.txt");
		Iterator<IWiktionaryEntry> entryIter = page.getEntries().iterator();
		/*while (entryIter.hasNext()) {
			IWiktionaryEntry e = entryIter.next();
			System.out.println(e.getWord() + ":" + e.getPartOfSpeech() + "/" + e.getWordLanguage());
			for (IWiktionarySense s : e.getSenses())
				System.out.println("  " + s.getIndex() + ": " + s.getGloss());
		}*/
		assertEntry(Language.ENGLISH, PartOfSpeech.VERB, 4, entryIter.next());
		assertEntry(Language.ENGLISH, PartOfSpeech.NOUN, 1, entryIter.next());
		assertEntry(Language.ENGLISH, PartOfSpeech.VERB, 1, entryIter.next());
		assertEntry(Language.findByName("Crimean Tatar"), PartOfSpeech.NOUN, 1, entryIter.next());    
    	assertEntry(Language.findByName("Kurdish"), PartOfSpeech.NOUN, 1, entryIter.next());
    	assertEntry(Language.findByName("Mapudungun"), PartOfSpeech.ADVERB, 1, entryIter.next());
		assertEntry(Language.findByName("Tagalog"), PartOfSpeech.VERB, 1, entryIter.next());
		assertEntry(Language.findByName("Tatar"), PartOfSpeech.NOUN, 1, entryIter.next());
		assertFalse(entryIter.hasNext());
	}

	/***/
	public void testPortmanteau() throws Exception {
		IWiktionaryPage page = parse("portmanteau.txt");
		Iterator<IWiktionaryEntry> entryIter = page.getEntries().iterator();
		assertEntry(Language.ENGLISH, PartOfSpeech.NOUN, 1, entryIter.next());
		assertEntry(Language.ENGLISH, PartOfSpeech.NOUN, 1, entryIter.next());
		assertEntry(Language.ENGLISH, PartOfSpeech.ADJECTIVE, 1, entryIter.next());
		assertFalse(entryIter.hasNext());
	}
	
	protected static void assertEntry(final ILanguage language, 
			final PartOfSpeech partOfSpeech, int senseCount, 
			final IWiktionaryEntry entry) {
		assertEquals(language, entry.getWordLanguage());
		assertEquals(partOfSpeech, entry.getPartOfSpeech());
		assertEquals(senseCount, entry.getSenseCount());
	}

}
