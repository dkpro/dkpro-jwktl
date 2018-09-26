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
package de.tudarmstadt.ukp.jwktl.parser;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.IntegrationTest;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryEntryFilter;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryPageFilter;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionarySenseFilter;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.IWiktionaryIterator;
import de.tudarmstadt.ukp.jwktl.parser.util.IDumpInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static de.tudarmstadt.ukp.jwktl.api.util.Language.ENGLISH;
import static de.tudarmstadt.ukp.jwktl.api.util.Language.GERMAN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(IntegrationTest.class)
public class WiktionaryDumpParserIntegrationTest {

	private TestDb testDb;
	private WiktionaryDumpParser parser;

	private static final URL DE_DUMP_URL;
	private static final URL EN_DUMP_URL;

	static {
		try {
			DE_DUMP_URL = new URL("https://dumps.wikimedia.org/dewiktionary/20180801/dewiktionary-20180801-pages-articles.xml.bz2");
			EN_DUMP_URL = new URL("https://dumps.wikimedia.org/enwiktionary/20180801/enwiktionary-20180801-pages-articles.xml.bz2");
		} catch (MalformedURLException e) {
			throw new RuntimeException();
		}
	}

	@Before
	public void setUp() throws Exception {
		testDb = new TestDb();
		parser = new WiktionaryDumpParser(new WiktionaryArticleParser(testDb));
	}

	@Test
	public void testParseGermanWiktionaryDump() throws Exception {
		parser.parse(downloadDumpIfNecessary(DE_DUMP_URL));
		assertEquals(GERMAN, testDb.dumpInfo.getDumpLanguage());
		assertEquals(482660, testDb.dumpInfo.getProcessedPages());
		assertEquals(447959, testDb.savedPages);
	}

	@Test
	public void testParseEnglishWiktionaryDump() throws Exception {
		parser.parse(downloadDumpIfNecessary(EN_DUMP_URL));
		assertEquals(ENGLISH, testDb.dumpInfo.getDumpLanguage());
		assertEquals(4496906, testDb.dumpInfo.getProcessedPages());
		assertEquals(4261074, testDb.savedPages);
	}

	private File downloadDumpIfNecessary(URL url) throws IOException {
		final String[] segments = url.getPath().split("/");
		final String file = segments[segments.length-1];

		File dumpFile = new File(file);
		if (!dumpFile.exists()) {
			System.err.println("downloading "+url);
			final File tempFile = File.createTempFile("download-" + file, "jwktl");
			OutputStream fos = new BufferedOutputStream(new FileOutputStream(tempFile));
			InputStream is = url.openStream();

			byte[] buffer = new byte[8192];
			int n;

			while ((n = is.read(buffer)) != -1) {
				fos.write(buffer, 0, n);
			}

			is.close();
			fos.close();

			assertTrue(tempFile.renameTo(dumpFile));
		}
		return dumpFile;
	}

	static class TestDb implements IWritableWiktionaryEdition {
		IDumpInfo dumpInfo;
		int savedPages;

		@Override
		public ILanguage getLanguage() {
			return null;
		}

		@Override
		public File getDBPath() {
			return null;
		}

		@Override
		public WiktionaryPage getPageForId(long id) {
			return null;
		}

		@Override
		public WiktionaryPage getPageForWord(String word) {
			return null;
		}

		@Override
		public IWiktionaryEntry getEntryForId(long entryId) {
			return null;
		}

		@Override
		public IWiktionaryEntry getEntryForId(long pageId, int entryIdx) {
			return null;
		}

		@Override
		public IWiktionaryEntry getEntryForWord(String word, int entryIdx) {
			return null;
		}

		@Override
		public IWiktionarySense getSenseForKey(String id) {
			return null;
		}

		@Override
		public IWiktionarySense getSenseForId(long entryId, int senseIdx) {
			return null;
		}

		@Override
		public IWiktionarySense getSenseForId(long pageId, int entryIdx, int senseIdx) {
			return null;
		}

		@Override
		public List<IWiktionarySense> getSensesForWord(String word, int entryIdx) {
			return null;
		}

		@Override
		public IWiktionarySense getSensesForWord(String word, int entryIdx, int senseIdx) {
			return null;
		}

		@Override
		public void saveProperties(IDumpInfo dumpInfo) throws WiktionaryException {
			this.dumpInfo = dumpInfo;
		}

		@Override
		public void savePage(WiktionaryPage page) {
			savedPages++;
		}

		@Override
		public void commit() {
		}

		@Override
		public void setLanguage(ILanguage language) {
		}

		@Override
		public void setEntryIndexByTitle(boolean entryIndexByTitle) {
		}

		@Override
		public List<IWiktionaryPage> getPagesForWord(String word, boolean normalize) {
			return null;
		}

		@Override
		public List<IWiktionaryPage> getPagesForWord(String word, IWiktionaryPageFilter filter, boolean normalize) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryPage> getAllPages() {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryPage> getAllPages(boolean sortByTitle) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryPage> getAllPages(boolean sortByTitle, boolean normalize) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryPage> getAllPages(IWiktionaryPageFilter filter) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryPage> getAllPages(IWiktionaryPageFilter filter, boolean sortByTitle) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryPage> getAllPages(IWiktionaryPageFilter filter, boolean sortByTitle, boolean normalize) {
			return null;
		}

		@Override
		public List<IWiktionaryEntry> getEntriesForWord(String word) {
			return null;
		}

		@Override
		public List<IWiktionaryEntry> getEntriesForWord(String word, boolean normalize) {
			return null;
		}

		@Override
		public List<IWiktionaryEntry> getEntriesForWord(String word, IWiktionaryEntryFilter filter) {
			return null;
		}

		@Override
		public List<IWiktionaryEntry> getEntriesForWord(String word, IWiktionaryEntryFilter filter, boolean normalize) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryEntry> getAllEntries() {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(boolean sortByTitle) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(boolean sortByTitle, boolean normalize) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(IWiktionaryEntryFilter filter) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(IWiktionaryEntryFilter filter, boolean sortByTitle) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(IWiktionaryEntryFilter filter, boolean sortByTitle, boolean normalize) {
			return null;
		}

		@Override
		public List<IWiktionarySense> getSensesForWord(String word) {
			return null;
		}

		@Override
		public List<IWiktionarySense> getSensesForWord(String word, boolean normalize) {
			return null;
		}

		@Override
		public List<IWiktionarySense> getSensesForWord(String word, IWiktionarySenseFilter filter) {
			return null;
		}

		@Override
		public List<IWiktionarySense> getSensesForWord(String word, IWiktionarySenseFilter filter, boolean normalize) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionarySense> getAllSenses() {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionarySense> getAllSenses(boolean sortByTitle) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionarySense> getAllSenses(boolean sortByTitle, boolean normalize) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionarySense> getAllSenses(IWiktionarySenseFilter filter) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionarySense> getAllSenses(IWiktionarySenseFilter filter, boolean sortByTitle) {
			return null;
		}

		@Override
		public IWiktionaryIterator<IWiktionarySense> getAllSenses(IWiktionarySenseFilter filter, boolean sortByTitle, boolean normalize) {
			return null;
		}

		@Override
		public void close() {
		}

		@Override
		public boolean isClosed() {
			return false;
		}
	}
}
