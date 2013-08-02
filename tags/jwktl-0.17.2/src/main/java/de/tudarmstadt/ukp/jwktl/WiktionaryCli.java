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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryFormatter;

/**
 * Offers a command line interface to Wiktionary. You can type a word and 
 * after pressing &lt;enter&gt; the information of corresponding entries will 
 * be printed. In order to quit the interface just hit enter;
 */
public class WiktionaryCli {

	/**
	 * @param args path to parsed Wiktionary data
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 1)
			throw new IllegalArgumentException("Too few arguments. "
						+ "Required arguments: <PARSED-WIKTIONARY>");
		
		final String PROMPT = "> ";
		final String END = "";
		String wktPath = args[0];
		IWiktionaryEdition wkt = JWKTL.openEdition(new File(wktPath));
		WiktionaryFormatter formatter = WiktionaryFormatter.instance();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "UTF8"));
		System.out.print(PROMPT);
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if (line.equals(END))
					break;
				
				IWiktionaryPage page = wkt.getPageForWord(line);				
				if (page == null || page.getEntryCount() == 0)
					System.out.println(line + " is not in Wiktionary");
				else
					System.out.println(formatter.formatPage(page));
				
				System.out.print(PROMPT);
			}
		} finally {
			wkt.close();
		}
		System.out.println("exit");
	}

}
