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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryPageFilter;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.api.util.WiktionaryIterator;

/**
 * Implementation of the {@link IWiktionaryEdition} interface, which makes
 * use of a Berkeley DB to store and retrieve the parsed Wiktionary 
 * information.
 * @author Christian M. Meyer
 */
public class BerkeleyDBWiktionaryEdition extends WiktionaryEdition {

	/**
	 * Proxy object for referencing to a {@link IWiktionaryEntry}. The proxy 
	 * objects serve as a secondary database index. 
	 * @author Christian M. Meyer
	 */
	@Entity
	public static class WiktionaryEntryProxy {

		@PrimaryKey
		protected String entryKey;

		@SecondaryKey(relate = Relationship.MANY_TO_ONE)
		protected long entryId;

		protected long pageId;		
		protected int entryIndex;
		
		 /** Instanciates an entry proxy with dangling reference. This 
		  *  constructor should not be called directly; it is solely used
		  *  by the database API. */
		public WiktionaryEntryProxy() {}
		
		/** Instanciates an entry proxy to the given referenced entry. */
		public WiktionaryEntryProxy(final IWiktionaryEntry entry) {
			entryId = entry.getId();
			entryKey = entry.getKey();
			pageId = entry.getPageId();
			entryIndex = entry.getIndex();
		}

		/** Return the entry key of the referenced {@link IWiktionaryEntry}. */
		public String getEntryKey() {
			return entryKey;
		}
		
		/** Return the entry ID of the referenced {@link IWiktionaryEntry}. */
		public long getEntryId() {
			return entryId;
		}
		
		/** Return the page ID of the referenced {@link IWiktionaryEntry}. */
		public long getPageId() {
			return pageId;
		}
		
		/** Return the entry index of the referenced {@link IWiktionaryEntry}. */
		public int getEntryIndex() {
			return entryIndex;
		}

	}
	
	/**
	 * Proxy object for referencing to a {@link IWiktionarySense}. The proxy 
	 * objects serve as a secondary database index. 
	 * @author Christian M. Meyer
	 */
	@Entity
	public static class WiktionarySenseProxy {
		
		@PrimaryKey
		protected String senseKey;
		
		protected long pageId;		
		protected int entryIndex;
		protected int senseIndex;
		
		/** Instanciates an sense proxy with dangling reference. This 
		  *  constructor should not be called directly; it is solely used
		  *  by the database API. */
		public WiktionarySenseProxy() {}
		
		/** Instanciates an sense proxy to the given referenced sense. */
		public WiktionarySenseProxy(final IWiktionarySense sense) {
			senseKey = sense.getKey();
			pageId = sense.getPage().getId();
			entryIndex = sense.getEntry().getIndex();
			senseIndex = sense.getIndex();
		}
		
		/** Return the sense key of the referenced {@link IWiktionarySense}. */
		public String getSenseKey() {
			return senseKey;
		}
		
		/** Return the page ID of the referenced {@link IWiktionarySense}. */
		public long getPageId() {
			return pageId;
		}
		
		/** Return the entry index of the referenced {@link IWiktionarySense}. */
		public int getEntryIndex() {
			return entryIndex;
		}
		
		/** Return the sense index of the referenced {@link IWiktionarySense}. */
		public int getSenseIndex() {
			return senseIndex;
		}
		
	}

	
	private static final Logger logger = Logger.getLogger(BerkeleyDBWiktionaryEdition.class.getName());
		
	/** The internal name of the parsed Wiktionary database. */
	public static final String DATABASE_NAME = "WIKTIONARY";
	/** The name of the property file containing info about the parsed DB. */
	public static final String PROPERTY_FILE_NAME = "wiktionary.properties";

	protected Environment env;
	protected EntityStore store;
	protected File dbPath;
	protected Properties properties;
	protected ILanguage language;
	
	protected PrimaryIndex<Long, WiktionaryPage> pageById;
	protected SecondaryIndex<String, Long, WiktionaryPage> pageByTitle;
	protected SecondaryIndex<String, Long, WiktionaryPage> pageByNormalizedTitle;
	protected PrimaryIndex<String, WiktionaryEntryProxy> entryByKey;
	protected SecondaryIndex<Long, String, WiktionaryEntryProxy> entryById;
	protected PrimaryIndex<String, WiktionarySenseProxy> senseByKey;
	protected Set<EntityCursor<?>> openCursors;
		
	/** Connects to the parsed Wiktionary contained in the specified directory.
	 * 	@param dbPath the path of the database files. 
	 *  @throws WiktionaryException if the connection could not 
	 *  	be established. */
	public BerkeleyDBWiktionaryEdition(final File dbPath) {
		this(dbPath, true, false, false, null);
	}

	/** Connects to the parsed Wiktionary contained in the specified directory.
	 * 	@param dbPath the path of the database files. 
	 * 	@param cacheSize the memory (in Bytes) that is used as database
	 *     cache, which can be used to speed up the DB access. Use
	 *     null as a default value.
	 *  @throws WiktionaryException if the connection could not 
	 *  	be established. */
	public BerkeleyDBWiktionaryEdition(final File dbPath, Long cacheSize) {
		this(dbPath, true, false, false, cacheSize);
	}
	
	/** Configures the database adapter and connects to the DB files at the 
	 *  specified path.
	 * 	@param parsedWiktionaryDump the path of the database files. 
	 * 	@param isReadOnly controls write permissions on the DB files.
	 * 	@param allowCreateNew if true, a new DB will be created if none
	 *     exists at the specified path.
	 * 	@param cacheSize the memory (in Bytes) that is used as database
	 *     cache, which can be used to speed up the DB access. Use
	 *     null as a default value.
	 * @throws WiktionaryException if the connection could not 
	 *  	be established. */
	protected BerkeleyDBWiktionaryEdition(final File parsedWiktionaryDump,
			boolean isReadOnly, boolean allowCreateNew, boolean overwriteExisting,
			Long cacheSize) {
		this.dbPath = parsedWiktionaryDump;
		try {
			connect(isReadOnly, allowCreateNew, overwriteExisting, cacheSize);
		} catch (DatabaseException e) {
			throw new WiktionaryException("Unable to establish a db connection", e);
		} catch (IllegalArgumentException e) {
			throw new WiktionaryException("Unable to establish a db connection", e);
		}
	}

	protected void connect(boolean isReadOnly, boolean allowCreateNew,
			boolean overwriteExisting, final Long cacheSize) throws DatabaseException {
		// Configure DB environment.
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(allowCreateNew);
		envConfig.setReadOnly(isReadOnly);
		envConfig.setTransactional(false);
		if (cacheSize != null)
			envConfig.setCacheSize(cacheSize);
		env = new Environment(dbPath, envConfig);

		// Configure store.
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setAllowCreate(allowCreateNew);
		storeConfig.setTransactional(false);
		storeConfig.setReadOnly(isReadOnly);
		store = new EntityStore(env, DATABASE_NAME, storeConfig);

		// Load properties.
		properties = new Properties();
		File propFile = new File(dbPath, PROPERTY_FILE_NAME);
		if (propFile.exists()) {
			try {
				Reader reader = new InputStreamReader(new FileInputStream(propFile), "UTF-8");
				try {
					properties.load(reader);
				} finally {
					reader.close();
				}
			} catch (IOException e) {
				throw new DatabaseException("Unable to load property file", e){};
			}

			String lang = properties.getProperty("wiktionary.language");
			if (lang == null)
				lang = properties.getProperty("entry_language");
			language = Language.get(lang);
		}

		// Load index.
		pageById = store.getPrimaryIndex(Long.class, WiktionaryPage.class);
		pageByTitle = store.getSecondaryIndex(pageById, String.class, "title");
		pageByNormalizedTitle = store.getSecondaryIndex(pageById, String.class, "normalizedTitle");

		entryByKey = store.getPrimaryIndex(String.class, WiktionaryEntryProxy.class);
		entryById = store.getSecondaryIndex(entryByKey, Long.class, "entryId");
		senseByKey = store.getPrimaryIndex(String.class, WiktionarySenseProxy.class);

		openCursors = new HashSet<EntityCursor<?>>();
	}

	/** Creates the given target dictionary if necessary. Removes a previously
	 *  parsed Wiktionary database from the target folder if there exists
	 *  one and overwriteExisting is set to true. 
	 *  @throws WiktionaryException if the target dictionary is not empty
	 *    	and overwriteExisting was set to false. */
	protected void prepareTargetDirectory(final File targetDirectory,
			final boolean overwriteExisting) throws WiktionaryException {
		if (!targetDirectory.exists()) {
			logger.info("Creating target directory " + targetDirectory.getAbsolutePath());
			if (!targetDirectory.mkdirs())
				throw new WiktionaryException("Unable to create target directory");
		} else {
			if (!overwriteExisting && targetDirectory.list().length > 0)
				throw new WiktionaryException("Target directory is not empty");
			
			deleteParsedWiktionary(targetDirectory);
		}
	}

	/** Removes all files belonging to a previously parsed Wiktionary database
	 *  from the given target directory. If not Wiktionary could be found
	 *  there, nothing is changed. */
	public static void deleteParsedWiktionary(final File targetDirectory) {
		logger.info("Removing parsed Wiktionary from " + targetDirectory);
		File[] files = targetDirectory.listFiles(new FileFilter(){
			public boolean accept(File file) {
				String name = file.getName();
				if (name.endsWith(".jdb"))
					return true;
				if (name.equals("je.lck"))
					return true;
				if (name.equals("wiktionary.properties"))
					return true;

				return false;
			}			
		});
		for (File file : files)
			if (!file.delete())
				logger.warning("Unable to delete file: " + file.toString());
	}
	
	
	// -- Pages --
	
	public WiktionaryPage getPageForId(long id) {
		ensureOpen();
		try {
			return loadPage(pageById.get(id), null);
		} catch (DatabaseException e) {
			throw new WiktionaryException(e);
		}
	}
	
	public WiktionaryPage getPageForWord(final String word) {
		ensureOpen();
		if (word == null)
			return null;
		
		try {
			/*if (normalize)
				return loadPage(pageByNormalizedTitle.get(
						WiktionaryPage.normalizeTitle(word)), null);
			else*/
			return loadPage(pageByTitle.get(word), null);
		} catch (DatabaseException e) {
			throw new WiktionaryException(e);
		}
	}
	
	public List<IWiktionaryPage> getPagesForWord(final String word,
			final IWiktionaryPageFilter filter, boolean normalize) {
		ensureOpen();
		if (word == null)
			return null;
		
		try {
			List<IWiktionaryPage> result = new ArrayList<IWiktionaryPage>();
			if (normalize) {
				String t = WiktionaryPage.normalizeTitle(word);
				EntityCursor<WiktionaryPage> cursor = pageByNormalizedTitle
						.entities(null, t, true, t, true, CursorConfig.DEFAULT);
				while (cursor.next() != null) {
					IWiktionaryPage page = loadPage(cursor.current(), filter);
					if (page != null)
						result.add(page);
				}
				cursor.close();
			} else {
				IWiktionaryPage page = loadPage(pageByTitle.get(word), filter);
				if (page != null)
					result.add(page); // can be only one result!
			}
			return result;
		} catch (DatabaseException e) {
			throw new WiktionaryException(e);
		}
	}
	
	public WiktionaryIterator<IWiktionaryPage> getAllPages(
			final IWiktionaryPageFilter filter, boolean sortByTitle, 
			boolean normalize) {
		ensureOpen();
		try {
			EntityCursor<WiktionaryPage> cursor;
			if (sortByTitle)
				cursor = (normalize 
						? pageByNormalizedTitle.entities() 
						: pageByTitle.entities());
			else
				cursor = pageById.entities();
			
			return new BerkeleyDBWiktionaryIterator<IWiktionaryPage, WiktionaryPage>(
					this, cursor){
				
				@Override
				protected IWiktionaryPage loadEntity(final WiktionaryPage entity) {
					return loadPage(entity, filter);
				}
				
			};
		} catch (DatabaseException e) {
			throw new WiktionaryException(e);
		}
	}

	protected WiktionaryPage loadPage(final WiktionaryPage page,
			final IWiktionaryPageFilter filter) {
		if (page != null && (filter == null || filter.accept(page))) {
			page.init();
			return page;
		} else
			return null;
	}


	// -- Entries --

	public IWiktionaryEntry getEntryForId(long entryId) {
		ensureOpen();
		WiktionaryEntryProxy entry = entryById.get(entryId);
		return getEntryForId(entry.getPageId(), entry.getEntryIndex());
	}

	
	// -- Senses --

	public IWiktionarySense getSenseForKey(final String key) {
		ensureOpen();
		WiktionarySenseProxy sense = senseByKey.get(key);
		return getSenseForId(sense.getPageId(), sense.getEntryIndex(), sense.getSenseIndex());
	}

	
	// -- Properties --

	/** Returns the internal name of the Berkeley DB. */
	public String getDBName() {
		return DATABASE_NAME;
	}
	
	public File getDBPath() {
		return dbPath;
	}

	public ILanguage getLanguage() {
		return language;
	}

	
	// -- Close --
	
	/** Hotspot for closing the connection. 
	 *  @throws WiktionaryException if the connection could not be closed. */
	protected void doClose() {
		if (store == null) 
			return; // DB already closed.
				
		try {
			for (EntityCursor<?> cursor : openCursors)
				cursor.close();
			openCursors.clear();

			store.close();
			env.close();
			
			env = null;
			store = null;
		} catch (DatabaseException e) {
			throw new WiktionaryException("Unable to close database", e);
		}
	}

}
