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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryCollection;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.entry.BerkeleyDBWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryCollection;
import de.tudarmstadt.ukp.jwktl.parser.IWritableWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.parser.WiktionaryArticleParser;
import de.tudarmstadt.ukp.jwktl.parser.WiktionaryDumpParser;
import de.tudarmstadt.ukp.jwktl.parser.WritableBerkeleyDBWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.parser.wikisaurus.WikisaurusArticleParser;

/**
 * Main entry point of the JWKTL API. Use this class to initiate the parsing 
 * of a Wiktionary XML dump file and for accessing already parsed dump files. 
 * @author Christian M, Meyer
 */
public class JWKTL {

	/** @return The software version as string. */
	public static String getVersion() {
		try {
			Properties properties = new Properties();
			try (InputStream stream = JWKTL.class.getResourceAsStream("/META-INF/jwktl-version.properties")) {
				properties.load(stream);
				return properties.getProperty("jwktl.version");
			}			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
	
	
	// -- Open parsed editions --

	/** Opens the parsed Wiktionary language edition stored at the given 
	 *  locations and aggregated them in a {@link IWiktionaryCollection}. 
	 *  This method uses a default cache size for the Berkeley DB.
	 *  @param targetDirectories target directories with the parsed dumps.
	 *  @throws WiktionaryException in case of any JWKTL-related error.
	 *  @return Aggregated Wiktionary collection containing Wiktionary
	 *  language editions stored at the given locations. 
	 */
	public static IWiktionaryCollection openCollection(
			final File... targetDirectories) {
		return openCollection(null, targetDirectories);
	}
	
	/** Opens the parsed Wiktionary language edition stored at the given 
	 *  locations and aggregated them in a {@link IWiktionaryCollection}. 
	 *  This method uses the given cache size for connecting to the 
	 *  Berkeley DB.
	 * 	@param cacheSize the memory (in Bytes) that is used as database
	 *     cache, which can be used to speed up the DB access. Use
	 *     null as a default value.
	 *  @param targetDirectories target directories with the parsed dumps.
	 *  @throws WiktionaryException in case of any JWKTL-related error.
	 *  @return Aggregated and parsed Wiktionary language edition stored at the given 
	 *  locations.
	 */
	public static IWiktionaryCollection openCollection(
			final Long cacheSize, final File... targetDirectories) {
		IWiktionaryCollection result = new WiktionaryCollection();
		for (File targetDirectory : targetDirectories)
			result.addEdition(openEdition(targetDirectory, cacheSize));
		return result;
	}
	
	/** Opens the parsed Wiktionary language edition stored at the given 
	 *  location. This method uses a default cache size for the 
	 *  Berkeley DB.
	 *  @param targetDirectory target directory with the parsed dump.
	 *  @throws WiktionaryException in case of any JWKTL-related error.
	 *  @return Parsed Wiktionary language edition stored at the given location.
	 */
	public static IWiktionaryEdition openEdition(final File targetDirectory) {
		return openEdition(targetDirectory, null);
	}
	
	/** Opens the parsed Wiktionary language edition stored at the given 
	 *  location. This method uses the given cache size for connecting 
	 *  to the Berkeley DB.
	 *  @param targetDirectory target directory with the parsed dump.
	 * 	@param cacheSize the memory (in Bytes) that is used as database
	 *     cache, which can be used to speed up the DB access. Use
	 *     null as a default value.
	 *  @throws WiktionaryException in case of any JWKTL-related error.
	 *  @return Parsed Wiktionary language edition stored at the given location.
	 */
	public static IWiktionaryEdition openEdition(final File targetDirectory,
			final Long cacheSize) {
		return new BerkeleyDBWiktionaryEdition(targetDirectory, cacheSize);
	}
	
	
	// -- Parse dump files --
	
	/** Parses the given XML dump file of Wiktionary and stores the parsed data 
	 *  within the specified target directory. Note that each target 
	 *  directory can only contain one parsed Wiktionary database. This 
	 *  method is equivalent to {@link WiktionaryDumpParser#parse(File)} using
	 *  a registered {@link WiktionaryArticleParser}. The parsing does not 
	 *  include information from Wikisaurus.
	 *  @param dumpFile file name of the Wiktionary dump in XML format.
	 * 	@param targetDirectory directory for storing the parsed data. 
	 * 	@param overwriteExisting if <code>true</code>, previously parsed 
	 * 		Wiktionary data files are removed from the targetDirectory.
	 * 	@throws WiktionaryException in case of any parser errors. */
	public static void parseWiktionaryDump(final File dumpFile,
			final File targetDirectory, boolean overwriteExisting) {
		parseWiktionaryDump(dumpFile, targetDirectory, overwriteExisting, false);
	}

	/** Parses the given XML dump file of Wiktionary and stores the parsed data 
	 *  within the specified target directory. Note that each target 
	 *  directory can only contain one parsed Wiktionary database. This 
	 *  method is equivalent to {@link WiktionaryDumpParser#parse(File)} using
	 *  a registered {@link WiktionaryArticleParser}. Optionally, information 
	 *  from Wikisaurus is added to the parsed database using the 
	 *  {@link WikisaurusArticleParser}.
	 *  @param dumpFile file name of the Wiktionary dump in XML format.
	 * 	@param targetDirectory directory for storing the parsed data. 
	 * 	@param overwriteExisting if <code>true</code>, previously parsed 
	 * 		Wiktionary data files are removed from the targetDirectory.
	 * @param parseWikiSaurus parses Wikisaurus pages and adds the parsed
	 * 		information to the corresponding articles.
	 * 	@throws WiktionaryException in case of any parser errors. */	 
	public static void parseWiktionaryDump(final File dumpFile,
			final File targetDirectory, boolean overwriteExisting,
			boolean parseWikiSaurus) {
		IWritableWiktionaryEdition wiktionaryDB = new WritableBerkeleyDBWiktionaryEdition(
				targetDirectory, overwriteExisting);
		WiktionaryDumpParser parser = new WiktionaryDumpParser();
		parser.register(new WiktionaryArticleParser(wiktionaryDB));
		if (parseWikiSaurus)
			parser.register(new WikisaurusArticleParser(wiktionaryDB));
		parser.parse(dumpFile);
	}
	
	/** Deletes all files from a previously parsed Wiktionary from the
	 *  specified directory. This method is equivalent to
	 *  {@link BerkeleyDBWiktionaryEdition#deleteParsedWiktionary(File)}.
	 *  @param targetDirectory target directory with the parsed dump.
	 */
	public static void deleteEdition(final File targetDirectory) {
		BerkeleyDBWiktionaryEdition.deleteParsedWiktionary(targetDirectory);
	}

}
