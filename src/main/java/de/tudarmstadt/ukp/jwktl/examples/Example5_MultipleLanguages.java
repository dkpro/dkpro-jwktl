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
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryCollection;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;

/**
 * Example for combining information from multiple Wiktionary language editions
 * in a so-called collection.
 * @author Yevgen Chebotar
 * @author Christian M. Meyer
 */
public class Example5_MultipleLanguages {

	/** Runs the example.
	 *  @param args two names of a directory containing parsed Wiktionary data
	 *    (German and English in the example). */
	public static void main(String[] args) {
		if (args.length != 2)
			throw new IllegalArgumentException("Too few arguments. "
					+ "Required arguments: <PARSED-WIKTIONARY-EN> " 
					+ "<PARSED-WIKTIONARY-DE>");
				
		// Create new IWiktionaryCollection for the parsed databases.
		IWiktionaryCollection wktColl = JWKTL.openCollection(new File(args[0]), new File(args[1]));
		
		// Query for "arm" in both language editions and print the resulting entries.
		for (IWiktionaryEntry entry : wktColl.getEntriesForWord("arm")) {
			// Print the language of the defining language edition.
			System.out.println(entry.getPage().getEntryLanguage() + ":");
			
			// Print the word and its language and part of speech.
			System.out.println("  " + entry.getWord() 
					+ "/" + entry.getPartOfSpeech() 
					+ "/" + entry.getWordLanguage());
		}
		
		// Close the Wiktionary edition (closes all attached editions).
		wktColl.close();	
	}
	
}
