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
package de.tudarmstadt.ukp.jwktl.examples;

import java.io.File;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.JWKTL;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryFormatter;

/**
 * Simple example for parsing a Wiktionary dump file and printing the 
 * entries for the word <i>Wiktionary</i>.
 * @author Yevgen Chebotar
 * @author Christian M. Meyer
 */
public class Example1_ParseWiktionaryDump {

	/** Runs the example.
	 *  @param args name of the dump file, output directory for parsed data, 
	 *    boolean value that specifies if existing parsed data should 
	 *    be deleted. */
	public static void main(String[] args) {
		if (args.length != 3)
			throw new IllegalArgumentException("Too few arguments. "
						+ "Required arguments: <DUMP_FILE> <OUTPUT_DIRECTORY> "
						+ "<OVERWRITE_EXISTING_DATA>");
		
		File dumpFile = new File(args[0]);	
		File outputDirectory = new File(args[1]);
		boolean overwriteExisting = Boolean.valueOf(args[2]);
		
		// parse dump file
		JWKTL.parseWiktionaryDump(dumpFile, outputDirectory, overwriteExisting);
		
		// Create new IWiktionaryEdition for our parsed data.
		IWiktionaryEdition wkt = JWKTL.openEdition(outputDirectory);
		
		// Retrieve all IWiktionaryEntries for the word "Wiktionary".
		List<IWiktionaryEntry> entries = wkt.getEntriesForWord("Wiktionary");
		
		// Print the information of the parsed entries.
		for (IWiktionaryEntry entry : entries)
			System.out.println(WiktionaryFormatter.instance().formatHeader(entry));
		
		// Close the Wiktionary edition.
		wkt.close();	
	}

}
