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
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryFormatter;

/**
 * Simple example for opening a parsed Wiktionary database and printing a 
 * number of information types for the word <i>boat</i>.
 * @author Yevgen Chebotar
 * @author Christian M. Meyer
 */
public class Example2_AccessParsedWiktionaryData {

	/** Runs the example.
	 *  @param args name of the directory of parsed Wiktionary data. */
	public static void main(String[] args) {
		if (args.length != 1)
			throw new IllegalArgumentException("Too few arguments. "
						+ "Required arguments: <PARSED-WIKTIONARY>");
		
		// Create new IWiktionaryEdition for our parsed data.
		String wktPath = args[0];
		IWiktionaryEdition wkt = JWKTL.openEdition(new File(wktPath));
		WiktionaryFormatter formatter = WiktionaryFormatter.instance();
		
		// Retrieve single article pages.
		IWiktionaryPage page = wkt.getPageForId(7377);
		System.out.println(formatter.formatHeader(page));
		page = wkt.getPageForWord("boat");
		System.out.println(formatter.formatHeader(page));
		
		// Retrieve entries.
		List<IWiktionaryEntry> entries = page.getEntries();
		for (IWiktionaryEntry entry : entries)
			System.out.println(WiktionaryFormatter.instance().formatHeader(entry));
		entries = wkt.getEntriesForWord("boat");
		for (IWiktionaryEntry entry : entries)
			System.out.println(WiktionaryFormatter.instance().formatHeader(entry));
		entries = wkt.getEntriesForWord("rom", true);
		for (IWiktionaryEntry entry : entries)
			System.out.println(WiktionaryFormatter.instance().formatHeader(entry));
		
		// Retrieve senses.
		IWiktionaryEntry entry = entries.get(0);
		for (IWiktionarySense sense : entry.getSenses())
			System.out.println(WiktionaryFormatter.instance().formatHeader(sense));	
		IWiktionarySense sense = entry.getSense(1);
		System.out.println(WiktionaryFormatter.instance().formatHeader(sense));
		sense = entry.getSense(2);
		System.out.println(WiktionaryFormatter.instance().formatHeader(sense));
		sense = entry.getUnassignedSense();
		System.out.println(WiktionaryFormatter.instance().formatHeader(sense));
		
		// Close the Wiktionary edition.
		wkt.close();	
	}

}
