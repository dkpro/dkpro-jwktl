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
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;

import de.tudarmstadt.ukp.jwktl.JWKTL;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.entry.BerkeleyDBWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.parser.util.IDumpInfo;

/**
 * Extends the Berkeley DB implementation by providing the possibility for 
 * modifying the contents. This is required by the parsers which need writing
 * access to the database, but not by the querying and iterating interface. 
 * @author Christian M. Meyer
 */
public class WritableBerkeleyDBWiktionaryEdition extends BerkeleyDBWiktionaryEdition
		implements IWritableWiktionaryEdition {

	protected long pageCount;
	protected long entryCount;
	protected long senseCount;
	
	// default = false; old behaviour of before 0.15.1; this needs significantly longer time!
	protected boolean entryIndexByTitle;
	
	/** Shorthand for {@link #WritableBerkeleyDBWiktionaryEdition(File, 
	 *  boolean, Long)} with a cacheSize set to half the size of the the +
	 *  current JWM max memory. */
	public WritableBerkeleyDBWiktionaryEdition(final File dbPath,
			boolean overwriteExisting) {
		this(dbPath, overwriteExisting, 
				Runtime.getRuntime().maxMemory() / 2);
	}
	
	/** Instanciates the writable Wiktionary database for the given 
	 *  database path. 
	 *  @param overwriteExisting if set to <code>false</code>, parsing a 
	 *  	Wiktionary dump using this database will cause an exception if the 
	 *  	database path is not empty. Otherwise, an existing parsed Wiktionary
	 *  	database will be overwritten. 
	 *  @param cacheSize denotes the size of the cache (in Bytes) used by the
	 *  	Berkeley DB. */
	public WritableBerkeleyDBWiktionaryEdition(final File dbPath, 
			boolean overwriteExisting, final Long cacheSize) {
		super(dbPath, false, true, overwriteExisting, cacheSize);
		pageCount = 0;
		entryCount = 0;
		senseCount = 0;
	}

	@Override
	protected void connect(boolean isReadOnly, boolean allowCreateNew,
			boolean overwriteExisting, final Long cacheSize) throws DatabaseException {
		if (allowCreateNew)
			prepareTargetDirectory(dbPath, overwriteExisting);
		super.connect(isReadOnly, allowCreateNew, overwriteExisting, cacheSize);
	}
	
	/** Returns the setting if {@link IWiktionaryEntry}s should be ordered 
   *  alphabetically. */
	public boolean getEntryIndexByTitle() {
		return entryIndexByTitle;
	}
	
	public void setEntryIndexByTitle(boolean entryIndexByTitle) {
		this.entryIndexByTitle = entryIndexByTitle;
	}

	public void setLanguage(final ILanguage language) {
		this.language = language;
	}
	
	public void commit() throws WiktionaryException {
		boolean isReadOnly = env.getConfig().getReadOnly();
		Long cacheSize = env.getConfig().getCacheSize();
		//env.sync();
		doClose();
		connect(isReadOnly, false, false, cacheSize);
	}
	
//	public void saveProperties(final WiktionaryArticleParser parser)
	public void saveProperties(final IDumpInfo dumpInfo)
			throws WiktionaryException {
		// Assign numeric id's to the WiktionaryEntry:s in alphabetical
		// order. This used to be the case in old versions and thus
		// is done for compatibility reasons.
		if (entryIndexByTitle) {
			long pageId = 0;
			long entryId = 0;
			entryById = null;
			EntityCursor<WiktionaryPage> pageCursor = pageByTitle.entities();
			while (pageCursor.next() != null) {
				WiktionaryPage page = pageCursor.current();
				if (page.getEntryCount() > 0) {
					for (WiktionaryEntry entry : page.entries())
						entry.setId(entryId++);
					//pageCursor.update(page);
					//pageById.put(page); // Save
					pageById.putNoReturn(page);
				}

				pageId++;
				if (pageId % 100000 == 0) {
					String lastKey = page.getTitle();
					pageCursor.close();
					commit();
					pageCursor = pageByTitle.entities(lastKey, false, null, false);
					System.err.println("Indexed " + pageId + " pages");
				}
			}
			pageCursor.close();
			entryById = store.getSecondaryIndex(entryByKey, Long.class, "entryId");
		}
		
		try {
			// Saves a property file along with the database storage files that
			// contains information about language and size of the parsed data.
			Map<String, String> props = new LinkedHashMap<String, String>();
			props.put("wiktionary.language", language.getCode());
			props.put("wiktionary.dumpfile", dumpInfo.getDumpFile().toString());
			
			props.put("database.creation", new SimpleDateFormat("yyyy-MM-dd kk:mm").format(new Date()));
			props.put("database.path", dbPath.toString());
			props.put("database.pages", Long.toString(pageCount));
			props.put("database.entries", Long.toString(entryCount));
			props.put("database.sense", Long.toString(senseCount));
			
			props.put("jwktl.version", JWKTL.getVersion());
			int i = 1;
			for (IWiktionaryPageParser pageParser : dumpInfo.getParser().getPageParsers()) {
				props.put("jwktl.parser_" + i, pageParser.getClass().getName());
				i++;
			}
			
			PrintWriter writer = new PrintWriter(new File(dbPath, PROPERTY_FILE_NAME), "UTF-8");
			writer.println("# JWKTL " + JWKTL.getVersion() + " parsed dump file.");
			String lastKey = null;
			try {
				for (Entry<String, String> prop : props.entrySet()) {
					String keyGroup = prop.getKey();
					int idx = keyGroup.indexOf('.');
					if (idx >= 0)
						keyGroup = keyGroup.substring(0, idx);
					if (!keyGroup.equals(lastKey)) {
						writer.println();
						lastKey = keyGroup;
					}
					writer.println(prop.getKey() + "=" + prop.getValue());
					properties.put(prop.getKey(), prop.getValue());
				}
    		} finally {
    			writer.close();
    		}
				/*
			properties.put("wiktionary.language", language.getCode());
			properties.put("wiktionary.dumpfile", dumpInfo.getDumpFile().toString());
			
			properties.put("database.creation", new SimpleDateFormat("yyyy-MM-dd kk:mm").format(new Date()));
			properties.put("database.path", dbPath.toString());
			properties.put("database.pages", Long.toString(pageCount));
			properties.put("database.entries", Long.toString(entryCount));
			properties.put("database.sense", Long.toString(senseCount));
			
			properties.put("jwktl.version", JWKTL.getVersion());
			int i = 1;
			for (IWiktionaryPageParser pageParser : dumpInfo.getParser().getPageParsers()) {
				properties.put("jwktl.parser_" + i, pageParser.getClass().getName());
				i++;
			}
			
			FileOutputStream stream = new FileOutputStream(
					new File(dbPath, PROPERTY_FILE_NAME));
    		try {
    			properties.store(stream, null);
    		} finally {
    			stream.close();
    		}*/
		} catch (IOException e) {
			throw new WiktionaryException("Unable to save property file", e);
		}
	}
	
	/** Adds the given Wiktionary page to the database. 
	 *  @throws DatabaseException if the page could not be stored, which is,
	 *      i.e. the case if the DB is in read-only mode. */
	public void savePage(final WiktionaryPage page) throws DatabaseException {
		WiktionaryPage existing = pageById.put(page);
		if (existing == null) {
			for (WiktionaryEntry entry : page.entries()) {
				entryByKey.put(new WiktionaryEntryProxy(entry));
				for (WiktionarySense sense : entry.senses()) {
					senseByKey.put(new WiktionarySenseProxy(sense));
					senseCount++;
				}
				entryCount++;
			}
			pageCount++;
		}
	}
	
}
