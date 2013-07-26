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

import de.tudarmstadt.ukp.jwktl.JWKTL;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.filter.WiktionaryEntryFilter;
import de.tudarmstadt.ukp.jwktl.api.util.Language;

/**
 * Simple example for iterating over all pages, entries, and senses in 
 * Wiktionary and printing their count.
 * @author Yevgen Chebotar
 * @author Christian M. Meyer
 */
public class Example3_IterateEntries {

	/** Runs the example.
	 *  @param args name of the directory of parsed Wiktionary data. */
	public static void main(String[] args) {
		if (args.length != 1)
			throw new IllegalArgumentException("Too few arguments. "
						+ "Required arguments: <PARSED-WIKTIONARY>");
				
		// Create new IWiktionaryEdition for our parsed data.
		String wktPath = args[0];
		IWiktionaryEdition wkt = JWKTL.openEdition(new File(wktPath));
		
		// Iterate over all pages and count the pages, entries, and senses.
		int pageCount = 0;
		int entryCount = 0;
		int senseCount = 0;
		for (IWiktionaryPage page : wkt.getAllPages()) {
			for (IWiktionaryEntry entry : page.getEntries()) {
				senseCount += entry.getSenseCount();
				entryCount++;
			}
			pageCount++;
		}
		System.out.println("Pages: " + pageCount);
		System.out.println("Entries: " + entryCount);
		System.out.println("Senses: " + senseCount);
		
		// Iterate over all German adjectives, print and count them.
		WiktionaryEntryFilter filter = new WiktionaryEntryFilter();
		filter.setAllowedWordLanguages(Language.GERMAN);
		filter.setAllowedPartsOfSpeech(PartOfSpeech.ADJECTIVE);
		int deAdjectiveCount = 0;
		for (IWiktionaryEntry entry : wkt.getAllEntries(filter)) {
			System.out.println(entry.getWord());
			deAdjectiveCount++;
		}
		System.out.println("German adjectives: " + deAdjectiveCount);
		
		// Close the Wiktionary edition.
		wkt.close();	
	}
	
}
