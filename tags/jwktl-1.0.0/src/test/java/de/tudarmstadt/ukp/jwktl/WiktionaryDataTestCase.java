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
package de.tudarmstadt.ukp.jwktl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryCollection;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.IWiktionaryDumpParser;
import de.tudarmstadt.ukp.jwktl.parser.IWritableWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.parser.WiktionaryArticleParser;
import de.tudarmstadt.ukp.jwktl.parser.WiktionaryDumpParser;
import de.tudarmstadt.ukp.jwktl.parser.WritableBerkeleyDBWiktionaryEdition;

/**
 * Abstract test case for {@link IWiktionaryEdition} and 
 * {@link IWiktionaryCollection}.
 * @author Christian M. Meyer
 */
public abstract class WiktionaryDataTestCase extends WiktionaryTestCase {
	
	protected static final DumpEntry DE_FRANCA1 = new DumpEntry(0, "França", Language.GERMAN, 49261);
	protected static final DumpEntry DE_FRANCA2 = new DumpEntry(1, "França", Language.GERMAN, 49261);
	protected static final DumpEntry DE_MOENCH = new DumpEntry(2, "Mönch", Language.GERMAN, 10662);
	protected static final DumpEntry DE_PARAMETER = new DumpEntry(3, "Parameter", Language.GERMAN, 29502);	
	protected static final DumpEntry DE_PLATZ = new DumpEntry(4, "Platz", Language.GERMAN, 11094);
	
	protected static final DumpEntry EN_PARAMETER = new DumpEntry(0, "parameter", Language.ENGLISH, 11095);
	protected static final DumpEntry EN_PLACE1 = new DumpEntry(1, "place", Language.ENGLISH, 11094);
	protected static final DumpEntry EN_PLACE2 = new DumpEntry(2, "place", Language.ENGLISH, 11094);
	protected static final DumpEntry EN_PLACE3 = new DumpEntry(3, "place", Language.ENGLISH, 11094);
	protected static final DumpEntry EN_PLACE4 = new DumpEntry(4, "place", Language.ENGLISH, 11094);
	protected static final DumpEntry EN_PLACE5 = new DumpEntry(5, "place", Language.ENGLISH, 11094);
	protected static final DumpEntry EN_PLACE6 = new DumpEntry(6, "place", Language.ENGLISH, 11094);
	protected static final DumpEntry EN_PLACE7 = new DumpEntry(7, "place", Language.ENGLISH, 11094);
	
	protected static WiktionaryTestData getSimpleDEDump() {
		List<DumpEntry> entries = new ArrayList<DumpEntry>();
		entries.add(DE_FRANCA1);
		entries.add(DE_FRANCA2);
		entries.add(DE_MOENCH);
		entries.add(DE_PARAMETER);
		entries.add(DE_PLATZ);
		return new WiktionaryTestData(new File(WiktionaryTestCase.RESOURCE_PATH, 
				"WiktionaryTestData_de_20080617.xml"),
				Language.GERMAN, "de", entries);
	}

	protected static WiktionaryTestData getSimpleENDump() {
		List<DumpEntry> entries = new ArrayList<DumpEntry>();
		entries.add(EN_PARAMETER);
		entries.add(EN_PLACE1);
		entries.add(EN_PLACE2);
		entries.add(EN_PLACE3);
		entries.add(EN_PLACE4);
		entries.add(EN_PLACE5);
		entries.add(EN_PLACE6);
		entries.add(EN_PLACE7);
		return new WiktionaryTestData(new File(WiktionaryTestCase.RESOURCE_PATH, 
					"WiktionaryTestData_en_20080613.xml"),
				Language.ENGLISH, "en", entries);
	}
	
	
	protected static class DumpEntry {
		
		protected int id;
		protected String word;
		protected ILanguage language;
		protected long page;
		
		protected DumpEntry(int id, final String word, final ILanguage language,
				long page) {
			this.id = id;
			this.word = word;
			this.language = language;
			this.page = page;
		}
		
		public int getId() {
			return id;
		}
		
		public String getWord() {
			return word;
		}
		
		public ILanguage getLanguage() {
			return language;
		}
				
		public long getPage() {
			return page;
		}
		
	}
	
	protected static class WiktionaryTestData {

		protected File dumpFile;
		protected File parsedData;
		protected ILanguage language;
		protected String langCode;
		protected List<DumpEntry> entries;

		public WiktionaryTestData(final File dumpFile, final ILanguage language,
				final String langCode, final List<DumpEntry> entries) {
			this.dumpFile = dumpFile;
			this.language = language;
			this.langCode = langCode;
			this.entries = entries;
		}

		public File getDumpFile() {
			return dumpFile;
		}

		public ILanguage getLanguage() {
			return language;
		}

		public List<DumpEntry> getEntries() {
			return entries;
		}

		public void parse(final File targetDirectory) {
			this.parsedData = targetDirectory;
			IWritableWiktionaryEdition db = new WritableBerkeleyDBWiktionaryEdition(
					targetDirectory, true);
			db.setEntryIndexByTitle(true);
			IWiktionaryDumpParser parser = new WiktionaryDumpParser(
					new WiktionaryArticleParser(db));
			parser.parse(dumpFile);
		}

		public File getParsedData() {
			return parsedData;
		}
	
	}


	protected WiktionaryTestData wktDE;
	protected WiktionaryTestData wktEN;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		wktDE = getSimpleDEDump();
		wktDE.parse(new File(workDir, "/de"));
		wktEN = getSimpleENDump();
		wktEN.parse(new File(workDir, "/en"));
	}
	
	@Override
	protected void tearDown() throws Exception {
		JWKTL.deleteEdition(wktDE.getParsedData());
		JWKTL.deleteEdition(wktEN.getParsedData());
		super.tearDown();
	}

	protected <IterableType> void assertIterable(
			final Iterable<IterableType> actual, 
			final IterableType... expected) {
		Iterator<IterableType> iterator = actual.iterator();
		for (IterableType e : expected) {
			assertTrue(iterator.hasNext());
			assertEquals(e, iterator.next());
		}
		assertFalse(iterator.hasNext());
	}
	
}