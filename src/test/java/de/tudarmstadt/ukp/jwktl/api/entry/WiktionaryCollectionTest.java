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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.JWKTL;
import de.tudarmstadt.ukp.jwktl.WiktionaryDataTestCase;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryCollection;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.filter.WiktionaryEntryFilter;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;

/**
 * Test case for {@link WiktionaryCollection}.
 * @author Christian M. Meyer
 */
public class WiktionaryCollectionTest extends WiktionaryDataTestCase {

	protected IWiktionaryCollection wkt;

	@Override
	protected void tearDown() throws Exception {
		if (wkt != null)
			wkt.close();
		wkt = null;
		super.tearDown();
	}
	
	/***/
	public void testOpenClose() {
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktDE.getParsedData()));
		wkt.close();
		
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktEN.getParsedData()));
		wkt.close();
		
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktDE.getParsedData()));
		wkt.addEdition(JWKTL.openEdition(wktEN.getParsedData()));
		wkt.close();
	}
	
	/***/
	public void testGetWordEntry() {
		// German Language.
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktDE.getParsedData()));
		assertEntries(wkt.getEntriesForWord("França"), DE_FRANCA1, DE_FRANCA2);
		assertEntries(wkt.getEntriesForWord("Mönch"), DE_MOENCH);
		assertEntries(wkt.getEntriesForWord("Parameter"), DE_PARAMETER);
		assertEntries(wkt.getEntriesForWord("Platz"), DE_PLATZ);
		
		// English Language.
		wkt.addEdition(JWKTL.openEdition(wktEN.getParsedData()));
		assertEntries(wkt.getEntriesForWord("parameter"), EN_PARAMETER);
		assertEntries(wkt.getEntriesForWord("place"), EN_PLACE1, EN_PLACE2,
				EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		
		// Combined.
		assertEntries(wkt.getEntriesForWord("França"), DE_FRANCA1, DE_FRANCA2);
		assertEntries(wkt.getEntriesForWord("Mönch"), DE_MOENCH);
		assertEntries(wkt.getEntriesForWord("Parameter"), DE_PARAMETER);
		assertEntries(wkt.getEntriesForWord("Platz"), DE_PLATZ);

		// Missing.
		assertEquals(0, wkt.getEntriesForWord(null).size());
		assertEquals(0, wkt.getEntriesForWord("").size());
		assertEquals(0, wkt.getEntriesForWord("foo").size());
	}
	
	/***/
	public void testEntryIteration() {
		// Complete iteration.
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktDE.getParsedData()));
		Iterator<DumpEntry> expected = wktDE.getEntries().iterator();
		Iterator<IWiktionaryEntry> actual = wkt.getAllEntries(true).iterator();
		while (expected.hasNext()) {
			assertTrue(actual.hasNext());
			assertEntry(expected.next(), actual.next());
			
			try {
				actual.remove();
				fail("UnsupportedOperationException expected");
			} catch (UnsupportedOperationException e) {}
		}
		assertFalse(actual.hasNext());
		
		// Combined Wiktionary.
		wkt.addEdition(JWKTL.openEdition(wktEN.getParsedData()));
		expected = wktDE.getEntries().iterator();
		actual = wkt.getAllEntries(true).iterator();
		while (expected.hasNext()) {
			assertTrue(actual.hasNext());
			assertEntry(expected.next(), actual.next());
		}
		expected = wktEN.getEntries().iterator();
		while (expected.hasNext()) {
			assertTrue(actual.hasNext());
			assertEntry(expected.next(), actual.next());
		}
		assertFalse(actual.hasNext());	
	}
	
	/***/
	public void testEntryLanguageFilter() {
		// Filter.
		WiktionaryEntryFilter filter = new WiktionaryEntryFilter();
		assertIterable(filter.getAllowedEntryLanguages());
		filter.setAllowedEntryLanguages(Language.GERMAN);
		assertIterable(filter.getAllowedEntryLanguages(), Language.GERMAN);
		filter.setAllowedEntryLanguages(Language.ENGLISH);
		assertIterable(filter.getAllowedEntryLanguages(), Language.ENGLISH);
		filter.setAllowedEntryLanguages(new HashSet<ILanguage>());
		assertIterable(filter.getAllowedEntryLanguages());
		filter.setAllowedEntryLanguages(Language.GERMAN, Language.ENGLISH, Language.findByName("Romanian"));
		assertIterable(filter.getAllowedEntryLanguages(), Language.GERMAN, Language.ENGLISH, Language.findByName("Romanian"));
		filter.clearAllowedEntryLanguages();
		assertIterable(filter.getAllowedEntryLanguages());
		filter.setAllowedEntryLanguages(Language.GERMAN);
		assertIterable(filter.getAllowedEntryLanguages(), Language.GERMAN);
		filter.clear();
		assertIterable(filter.getAllowedEntryLanguages());
		
		// Access.
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktDE.getParsedData()));
		wkt.addEdition(JWKTL.openEdition(wktEN.getParsedData()));
		
		// Single language.
		filter.clearAllowedEntryLanguages();
		assertEntries(wkt.getEntriesForWord("parameter", filter, true), DE_PARAMETER, EN_PARAMETER);
		filter.setAllowedEntryLanguages(Language.GERMAN);
		assertEntries(wkt.getEntriesForWord("parameter", filter, true), DE_PARAMETER);
		filter.setAllowedEntryLanguages(Language.ENGLISH);
		assertEntries(wkt.getEntriesForWord("parameter", filter, true), EN_PARAMETER);
		
		// Multiple languages.
		filter.setAllowedEntryLanguages(Language.GERMAN, Language.ENGLISH, Language.findByName("Romanian"));
		assertEntries(wkt.getEntriesForWord("parameter", filter, true), DE_PARAMETER, EN_PARAMETER);
		filter.setAllowedEntryLanguages(Language.GERMAN, Language.ENGLISH);
		assertEntries(wkt.getEntriesForWord("parameter", filter, true), DE_PARAMETER, EN_PARAMETER);
		filter.setAllowedEntryLanguages(Language.GERMAN);
		assertEntries(wkt.getEntriesForWord("parameter", filter, true), DE_PARAMETER);
		filter.setAllowedEntryLanguages(Language.ENGLISH);
		assertEntries(wkt.getEntriesForWord("parameter", filter, true), EN_PARAMETER);
		filter.setAllowedEntryLanguages();
		assertEntries(wkt.getEntriesForWord("parameter", filter, true), DE_PARAMETER, EN_PARAMETER);
	}
	
	/***/
	public void testWordLanguageFilter() {
		// Filter.
		WiktionaryEntryFilter filter = new WiktionaryEntryFilter();
		assertIterable(filter.getAllowedWordLanguages());
		filter.setAllowedWordLanguages(Language.GERMAN);
		assertIterable(filter.getAllowedWordLanguages(), Language.GERMAN);
		filter.setAllowedWordLanguages(Language.ENGLISH);
		assertIterable(filter.getAllowedWordLanguages(), Language.ENGLISH);
		filter.setAllowedWordLanguages(new HashSet<ILanguage>());
		assertIterable(filter.getAllowedWordLanguages());
		filter.setAllowedWordLanguages(Language.GERMAN, Language.ENGLISH, Language.findByName("Romanian"));
		assertIterable(filter.getAllowedWordLanguages(), Language.GERMAN, Language.ENGLISH, Language.findByName("Romanian"));
		filter.clearAllowedWordLanguages();
		assertIterable(filter.getAllowedWordLanguages());
		filter.setAllowedWordLanguages(Language.GERMAN);
		assertIterable(filter.getAllowedWordLanguages(), Language.GERMAN);
		filter.clear();
		assertIterable(filter.getAllowedWordLanguages());

		// Access.
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktDE.getParsedData()));
		wkt.addEdition(JWKTL.openEdition(wktEN.getParsedData()));
		
		// Single language.
		filter.clearAllowedWordLanguages();
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2, EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		filter.setAllowedWordLanguages(Language.ENGLISH);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2);
		filter.setAllowedWordLanguages(Language.findByName("French"));
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE3, 
				EN_PLACE4);

		// Multiple languages.
		filter.setAllowedWordLanguages(Language.GERMAN, Language.ENGLISH, Language.findByName("Romanian"));
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2, EN_PLACE6);
		filter.setAllowedWordLanguages(Language.GERMAN, Language.ENGLISH);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2);
		filter.setAllowedWordLanguages(Language.GERMAN);
		assertEntries(wkt.getEntriesForWord("place", filter, true));
		filter.setAllowedWordLanguages(Language.ENGLISH);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2);
		filter.setAllowedWordLanguages();
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2, EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
	}

	//TODO: test iteration for filters!!
	
	/***/
	public void testPartOfSpeechFilter() {
		// Filter.
		WiktionaryEntryFilter filter = new WiktionaryEntryFilter();
		assertIterable(filter.getAllowedPartsOfSpeech());
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN);
		assertIterable(filter.getAllowedPartsOfSpeech(), PartOfSpeech.NOUN);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.VERB);
		assertIterable(filter.getAllowedPartsOfSpeech(), PartOfSpeech.VERB);
		filter.setAllowedPartsOfSpeech(new HashSet<PartOfSpeech>());
		assertIterable(filter.getAllowedPartsOfSpeech());
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN, PartOfSpeech.VERB, PartOfSpeech.ABBREVIATION);
		assertIterable(filter.getAllowedPartsOfSpeech(), PartOfSpeech.NOUN, PartOfSpeech.VERB, PartOfSpeech.ABBREVIATION);
		filter.clearAllowedPartsOfSpeech();
		assertIterable(filter.getAllowedPartsOfSpeech());
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN);
		assertIterable(filter.getAllowedPartsOfSpeech(), PartOfSpeech.NOUN);
		filter.clear();
		assertIterable(filter.getAllowedPartsOfSpeech());

		// Access.
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktDE.getParsedData()));
		wkt.addEdition(JWKTL.openEdition(wktEN.getParsedData()));
		
		// Single part of speech.
		filter.clearAllowedPartsOfSpeech();
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2, EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE3, EN_PLACE5);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.VERB);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE2, 
				EN_PLACE6, EN_PLACE7);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.ABBREVIATION);
		assertEntries(wkt.getEntriesForWord("place", filter, true));

		// Multiple parts of speech.
		filter.setAllowedPartsOfSpeech();
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2, EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE3, EN_PLACE5);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.VERB);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE2, 
				EN_PLACE6, EN_PLACE7);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.ABBREVIATION);
		assertEntries(wkt.getEntriesForWord("place", filter, true));

		// Multiple parts of speech.
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN, PartOfSpeech.VERB, PartOfSpeech.ABBREVIATION);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2, EN_PLACE3, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN, PartOfSpeech.VERB);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2, EN_PLACE3, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN, PartOfSpeech.ABBREVIATION);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE3, EN_PLACE5);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.ABBREVIATION);
		assertEntries(wkt.getEntriesForWord("place", filter, true));
		filter.setAllowedPartsOfSpeech();
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1, 
				EN_PLACE2, EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
	}
	
	/***/
	public void testCombinedFilters() {
		// Access.
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktDE.getParsedData()));
		wkt.addEdition(JWKTL.openEdition(wktEN.getParsedData()));		
		
		WiktionaryEntryFilter filter = new WiktionaryEntryFilter();
		filter.setAllowedWordLanguages(Language.ENGLISH);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1);
		filter.setAllowedWordLanguages(Language.ENGLISH);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.VERB);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE2);
		filter.setAllowedWordLanguages(Language.findByName("French"));
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE3);
		filter.setAllowedWordLanguages(Language.findByName("French"));
		filter.setAllowedPartsOfSpeech(PartOfSpeech.WORD_FORM);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE4);
		filter.setAllowedWordLanguages(Language.GERMAN, Language.ENGLISH, Language.findByName("French"));
		filter.setAllowedPartsOfSpeech(PartOfSpeech.NOUN, PartOfSpeech.VERB, PartOfSpeech.ABBREVIATION);
		assertEntries(wkt.getEntriesForWord("place", filter, true), EN_PLACE1,
				EN_PLACE2, EN_PLACE3);
	}
	
	/***/
	public void testCaseSensitivity() {
		// Access.
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktDE.getParsedData()));
		wkt.addEdition(JWKTL.openEdition(wktEN.getParsedData()));
				
		// Case sensitive.
		assertEntries(wkt.getEntriesForWord("Mönch", false), DE_MOENCH);
		assertEntries(wkt.getEntriesForWord("mönch", false));
		assertEntries(wkt.getEntriesForWord("MÖNCH", false));
		assertEntries(wkt.getEntriesForWord("mönCH", false));
		assertEntries(wkt.getEntriesForWord("Monch", false));
		
		assertEntries(wkt.getEntriesForWord("place", false), EN_PLACE1, EN_PLACE2, 
				EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		assertEntries(wkt.getEntriesForWord("Place", false));
		assertEntries(wkt.getEntriesForWord("PLACE", false));
		assertEntries(wkt.getEntriesForWord("plaCE", false));
		
		assertEntries(wkt.getEntriesForWord("parameter", false), EN_PARAMETER);
				
		// Case insensitive.
		assertEntries(wkt.getEntriesForWord("Mönch", true), DE_MOENCH);
		assertEntries(wkt.getEntriesForWord("mönch", true), DE_MOENCH);
		assertEntries(wkt.getEntriesForWord("MÖNCH", true), DE_MOENCH);
		assertEntries(wkt.getEntriesForWord("mönCH", true), DE_MOENCH);
		assertEntries(wkt.getEntriesForWord("Monch", true), DE_MOENCH);
		
		assertEntries(wkt.getEntriesForWord("place", true), EN_PLACE1, EN_PLACE2, 
				EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		assertEntries(wkt.getEntriesForWord("Place", true), EN_PLACE1, EN_PLACE2, 
				EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		assertEntries(wkt.getEntriesForWord("PLACE", true), EN_PLACE1, EN_PLACE2, 
				EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		assertEntries(wkt.getEntriesForWord("plaCE", true), EN_PLACE1, EN_PLACE2, 
				EN_PLACE3, EN_PLACE4, EN_PLACE5, EN_PLACE6, EN_PLACE7);
		
		assertEntries(wkt.getEntriesForWord("parameter", true), DE_PARAMETER, EN_PARAMETER);
		
		// Filter.
		WiktionaryEntryFilter filter = new WiktionaryEntryFilter();
		filter.setAllowedPartsOfSpeech(PartOfSpeech.VERB);
		assertEntries(wkt.getEntriesForWord("plaCE", filter, true), EN_PLACE2, 
				EN_PLACE6, EN_PLACE7);
		filter.clear();
		filter.setAllowedWordLanguages(Language.GERMAN);
		assertEntries(wkt.getEntriesForWord("parameter", filter, true), DE_PARAMETER);
	}
	
	/***/
	public void testClose() {
		wkt = new WiktionaryCollection();
		assertTrue(wkt.isClosed()); // There are no editions!
		wkt.close();
		assertTrue(wkt.isClosed());
		wkt.close();
		assertTrue(wkt.isClosed());
		
		wkt = new WiktionaryCollection();
		wkt.addEdition(JWKTL.openEdition(wktDE.getParsedData()));
		assertFalse(wkt.isClosed());		
		assertTrue(wkt.getAllEntries().iterator().hasNext());
		wkt.close();
		assertTrue(wkt.isClosed());
		
		try {
			wkt.getAllEntries().hasNext();
			fail("IllegalStateException expected");
		} catch (IllegalStateException e) {}
		
		try {
			wkt.getEntriesForWord("foo");			
			fail("IllegalStateException expected");
		} catch (IllegalStateException e) {}		
//		try {
//			wkt.getWordEntries("foo", PartOfSpeech.NOUN);			
//			fail("IllegalStateException expected");
//		} catch (IllegalStateException e) {}
//		try {
//			wkt.getWordEntries("foo", Language.GERMAN);			
//			fail("IllegalStateException expected");
//		} catch (IllegalStateException e) {}
//		try {
//			wkt.getWordEntries("foo", Language.GERMAN, PartOfSpeech.NOUN);			
//			fail("IllegalStateException expected");
//		} catch (IllegalStateException e) {}
//		try {
//			wkt.getWordEntries("foo", new HashSet<ILanguage>(), 
//					new HashSet<PartOfSpeech>());
//			fail("IllegalStateException expected");
//		} catch (IllegalStateException e) {}
		
		IWiktionaryEdition en = JWKTL.openEdition(wktEN.getParsedData()); 
		try {
			wkt.addEdition(en);
			fail("IllegalStateException expected");
		} catch (IllegalStateException e) {}
		en.close();
	}
	
	/***/
	public void testError() {
		try {
			wkt = new WiktionaryCollection();
			wkt.addEdition(JWKTL.openEdition(new File("/dev/null/missing_parse")));
			fail("WiktionaryException expected!");
		} catch (WiktionaryException e) {} // Used to be IllegalArgumentException
	}

	protected static void assertEntry(final DumpEntry expected,
			final IWiktionaryEntry actual) {
//		System.out.println(expected.getId() + "/" + expected.getWord() + " " + actual.getKey() + "/" + actual.getWord());
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getWord(), actual.getWord());
		assertEquals(expected.getLanguage(), actual.getPage().getEntryLanguage());
	}
	
	protected static void assertEntries(final List<IWiktionaryEntry> actual,
			final DumpEntry... expected) {
		assertEquals(expected.length, actual.size());
		int i = 0;
		for (IWiktionaryEntry entry : actual)
			assertEntry(expected[i++], entry);
	}
	
}
