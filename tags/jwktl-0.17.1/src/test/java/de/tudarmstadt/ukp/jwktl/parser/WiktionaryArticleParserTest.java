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

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import de.tudarmstadt.ukp.jwktl.WiktionaryTestCase;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.util.IDumpInfo;

/**
 * Test case for {@link WiktionaryArticleParser}.
 */
public class WiktionaryArticleParserTest extends WiktionaryTestCase {

	protected static class MyWiktionaryDumpParser extends WiktionaryDumpParser
			implements IWiktionaryEntryParser {
		
		protected Map<WiktionaryPage, String> pages;
		
		public MyWiktionaryDumpParser() throws WiktionaryException {
			pages = new LinkedHashMap<WiktionaryPage, String>();
			register(new WiktionaryArticleParser(null, this));
		}

		public Map<WiktionaryPage, String> getPages() {
			return pages;
		}

		public void parse(final WiktionaryPage page, final String text)
				throws WiktionaryException {
			pages.put(page, text);
		}

	}
	

	protected File testDump;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		testDump = new File(RESOURCE_PATH, "WiktionaryDumpParserTest.xml");
	}
	
	/***/
	public void testParsedInformation() {
		MyWiktionaryDumpParser parser = new MyWiktionaryDumpParser();
		parser.parse(testDump);
		IDumpInfo dumpInfo = parser.getDumpInfo();
		assertEquals(2, dumpInfo.getProcessedPages());
		assertEquals(2, parser.getPages().size());
				
		Iterator<WiktionaryPage> pageIter = parser.getPages().keySet().iterator();
		WiktionaryPage page = pageIter.next();
		assertEquals("Page 1", page.getTitle());
		assertEquals(9L, page.getId());
		assertEquals(10763, page.getRevision());
		assertEquals("Fri Sep 17 08:23:57 CEST 2004", page.getTimestamp().toString());
		assertEquals("TJ", page.getAuthor());
		assertEquals("Text 1", parser.getPages().get(page));
		
		page = pageIter.next();
		assertEquals("Page 2", page.getTitle());
		assertEquals(10L, page.getId());
		assertEquals(10764, page.getRevision());
		assertEquals("Fri Sep 17 08:34:29 CEST 2004", page.getTimestamp().toString());
		assertEquals("TJ", page.getAuthor());
		assertEquals("Text 2\n\n      Test Test", parser.getPages().get(page));
		
		assertFalse(pageIter.hasNext());
	}
	
	/***/
	public void testDatabaseCreation() throws InterruptedException {
		WritableBerkeleyDBWiktionaryEdition db;
		
		// Parse to empty directory.
		File emptyDir = new File(workDir, "empty/");
		emptyDir.mkdir();
		db = new WritableBerkeleyDBWiktionaryEdition(emptyDir, false);
		new WiktionaryDumpParser(new WiktionaryArticleParser(db)).parse(testDump);
		assertParsedData(emptyDir);
		
		// Parse to non-existing directory.
		File newDir = new File(workDir, "new/");
		db = new WritableBerkeleyDBWiktionaryEdition(newDir, false);
		new WiktionaryDumpParser(new WiktionaryArticleParser(db)).parse(testDump);
		assertParsedData(newDir);
		
		// Parse to existing, non-empty directory; allow overwrite.
		File overwriteDir = new File(workDir, "overwrite/");
		db = new WritableBerkeleyDBWiktionaryEdition(overwriteDir, true);
		new WiktionaryDumpParser(new WiktionaryArticleParser(db)).parse(testDump);
		long version = new File(overwriteDir, "wiktionary.properties").lastModified();		
		assertParsedData(overwriteDir);
		Thread.sleep(1000); // necessary to compare timestamps!
		db = new WritableBerkeleyDBWiktionaryEdition(overwriteDir, true);
		new WiktionaryDumpParser(new WiktionaryArticleParser(db)).parse(testDump);
		assertParsedData(overwriteDir);
		long newVersion = new File(overwriteDir, "wiktionary.properties").lastModified();
		assertTrue(version < newVersion);
		
		// Parse to existing, non-empty directory; permit overwrite.
		version = newVersion;
		try {
			db = new WritableBerkeleyDBWiktionaryEdition(overwriteDir, false);
			new WiktionaryDumpParser(new WiktionaryArticleParser(db)).parse(testDump);
			fail("WiktionaryException expected");
		} catch (WiktionaryException e) {}
		assertParsedData(overwriteDir);
		newVersion = new File(overwriteDir, "wiktionary.properties").lastModified();
		assertTrue(version == newVersion);
		
		// Parsing more than once with the same parser is (currently) not allowed.
		File multipleDir = new File(workDir, "multiple/");
		db = new WritableBerkeleyDBWiktionaryEdition(multipleDir, true);
		WiktionaryDumpParser parser = new WiktionaryDumpParser(new WiktionaryArticleParser(db));
		parser.parse(testDump);
		assertParsedData(multipleDir);
		try {
			parser.parse(testDump);
			fail("IllegalStateException expected");
		} catch (IllegalStateException e) {}
		assertParsedData(multipleDir);
		
		// Parse to erroneous path.
		try {
			db = new WritableBerkeleyDBWiktionaryEdition(new File("/../:"), true);
			new WiktionaryDumpParser(new WiktionaryArticleParser(db)).parse(testDump);
			fail("WiktionaryException expected");
		} catch (WiktionaryException e) {}
	}
	
	/***/
	public void testLargeDump() {
		MyWiktionaryDumpParser parser = new MyWiktionaryDumpParser();
		parser.parse(new File(RESOURCE_PATH, "WiktionaryDumpParserLargeFileTest.xml"));
		assertEquals(30000, parser.getPages().size());
	}
		
	/***/
	public void testResolveLanguage() {
		assertEquals(Language.ENGLISH, WiktionaryDumpParser.resolveLanguage(
				"http://en.wiktionary.org/wiki/Wiktionary:Main_Page"));
		assertEquals(Language.GERMAN, WiktionaryDumpParser.resolveLanguage(
				"http://de.wiktionary.org/wiki/Wiktionary:Hauptseite"));
		assertEquals(Language.RUSSIAN, WiktionaryDumpParser.resolveLanguage(
				"http://ru.wiktionary.org/wiki/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0"));
		assertNull(WiktionaryDumpParser.resolveLanguage(
				"foobar"));
	}
	
	
	protected static void assertParsedData(final File parsedData) {
		assertTrue(new File(parsedData, "00000000.jdb").exists());
		assertTrue(new File(parsedData, "je.lck").exists());
		assertTrue(new File(parsedData, "je.info.0").exists());
		assertTrue(new File(parsedData, "wiktionary.properties").exists());
	}
	
}
