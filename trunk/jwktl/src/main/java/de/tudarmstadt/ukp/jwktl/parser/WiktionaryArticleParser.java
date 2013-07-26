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

import java.util.logging.Logger;

import com.sleepycat.je.DatabaseException;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.de.DEWiktionaryEntryParser;
import de.tudarmstadt.ukp.jwktl.parser.en.ENWiktionaryEntryParser;
import de.tudarmstadt.ukp.jwktl.parser.ru.RUWiktionaryEntryParser;
import de.tudarmstadt.ukp.jwktl.parser.util.IDumpInfo;

/**
 * Parses a Wiktionary XML dump and stores the parsed information as a 
 * Berkeley DB within a specified directory. The parsed Wiktionary dump
 * can then be accessed using the main JWKTL API. This implementation
 * parses only article pages within the main namespace; discussions, user
 * pages, revisions, etc. are not handled. An article page's text is 
 * passed to an implementation of {@link IWiktionaryEntryParser}, which
 * is either automatically detected from the Wiktionary's base URL, or
 * specified in the constructor. Note that each directory can only 
 * contain one Wiktionary database. 
 * @author Christian M. Meyer 
 */
public class WiktionaryArticleParser extends WiktionaryPageParser<WiktionaryPage> {

	private static final Logger logger = Logger.getLogger(WiktionaryArticleParser.class.getName());
	
	protected IWritableWiktionaryEdition wiktionaryDB;
	protected IWiktionaryEntryParser entryParser;
	
	/** Creates a caching article parser that saves the parsed Wiktionary
	 *  data into a Berkeley DB within the given target directory. A 
	 *  previously parsed Wiktionary database is replaced if overwriteExisting 
	 *  is true. The entry parser will be created based on the dump's 
	 *  base URL.
	 *  @throws WiktionaryException if the target dictionary is not empty
	 *    	and overwriteExisting was set to false. */
	public WiktionaryArticleParser(final IWritableWiktionaryEdition wiktionaryDB) 
					throws WiktionaryException {
		this(wiktionaryDB, null);
	}

	/** Creates a caching article parser that saves the parsed Wiktionary
	 *  data into a Berkeley DB within the given target directory. A 
	 *  previously parsed Wiktionary database is replaced if overwriteExisting 
	 *  is true. The specified entry parser is used rather than auto detecting
	 *  the language specific parser.
	 *  @throws WiktionaryException if the target dictionary is not empty
	 *    	and overwriteExisting was set to false. */
	public WiktionaryArticleParser(final IWritableWiktionaryEdition wiktionaryDB, 
			final IWiktionaryEntryParser entryParser)
			throws WiktionaryException {
		this.wiktionaryDB = wiktionaryDB;
		this.entryParser = entryParser;
	}
	
	@Override
	public void onSiteInfoComplete(final IDumpInfo dumpInfo) {
		super.onSiteInfoComplete(dumpInfo);
		ILanguage language = dumpInfo.getDumpLanguage();
		if (wiktionaryDB != null)
			wiktionaryDB.setLanguage(language);
		
		if (entryParser != null)
			return;
		if (Language.ENGLISH.equals(language)) {
			entryParser = new ENWiktionaryEntryParser();
		} else
		if (Language.GERMAN.equals(language)) {
			entryParser = new DEWiktionaryEntryParser();
		} else
		if (Language.RUSSIAN.equals(language)) {
			entryParser = new RUWiktionaryEntryParser();
		} else 	
			throw new WiktionaryException("Language " + language 
					+ " is not supported");
		
		logger.info("Automatically determined dump format: " + language);
	}
	
	@Override
	public void onPageEnd() {
		saveParsedWiktionaryPage();
		super.onPageEnd();
	}
	
	@Override
	public void onClose(final IDumpInfo dumpInfo) {
		super.onClose(dumpInfo);
		if (wiktionaryDB == null)
			return;
		
		wiktionaryDB.saveProperties(dumpInfo);
		
		// It is important to close the Berkeley DB handler to avoid data loss.
		try {			
			wiktionaryDB.close();
		} catch (DatabaseException e) {
			throw new WiktionaryException("Unable to close Wiktionary DB", e);
		}
	}
	
	protected WiktionaryPage createPage() {
		return new WiktionaryPage();
	}
	
	@Override
	public void setText(String text) {
//		long time = System.nanoTime();
		if (isAllowed(page))
			entryParser.parse(page, text);
//		time = System.nanoTime() - time;
//		System.out.println("parse " + (time / 1000) + "ms");
	}
	
	protected void saveParsedWiktionaryPage() {
		if (!isAllowed(page)) {
			logger.finer("Ignoring page " + page.getTitle());
			return;
		}
		
		if (wiktionaryDB == null)
			return;
		
		try {
//			long time = System.nanoTime();
			wiktionaryDB.savePage(page);
//			time = System.nanoTime() - time;
//			System.out.println("saveWiktionaryPage " + (time / 1000) + "ms");
			
			if (dumpInfo.getProcessedPages() % 25000 == 0)
				wiktionaryDB.commit();
		} catch (DatabaseException e) {
			throw new WiktionaryException("Unable to save page " + page.getTitle(), e);
		}
	}

	protected boolean isAllowed(final IWiktionaryPage page) {
		return (currentNamespace == null);
	}
	
}
