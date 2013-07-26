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
package de.tudarmstadt.ukp.jwktl.parser.en;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import junit.framework.TestCase;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.parser.WiktionaryEntryParser;

/**
 * Abstract test case for English Wiktionary parsers.
 * @author Christian M. Meyer
 */
public abstract class ENWiktionaryEntryParserTest extends TestCase {

	protected IWiktionaryPage parse(final String fileName) throws IOException {
		StringBuilder text = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(
						new File("src/test/resources/articles-en/" + fileName)), 
						"UTF-8"));
		String line;
		while ((line = reader.readLine()) != null)
			text.append(line).append("\n");
		reader.close();
		WiktionaryPage result = new WiktionaryPage();
		result.setTitle(fileName.replace(".txt", ""));
		WiktionaryEntryParser parser = new ENWiktionaryEntryParser();
		parser.parse(result, text.toString());
		return result;
	}
	
}
