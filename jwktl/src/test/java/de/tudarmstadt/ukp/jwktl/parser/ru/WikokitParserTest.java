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
package de.tudarmstadt.ukp.jwktl.parser.ru;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import junit.framework.TestCase;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.parser.WiktionaryEntryParser;

/**
 * Test case for {@link RUWiktionaryEntryParser}.
 * @author Christian M. Meyer
 */
public class WikokitParserTest extends TestCase {

	/***/
	public void testLodka() throws Exception {
		IWiktionaryPage page = parse("lodka.txt", "лодка");
		IWiktionaryEntry entry;
		
		assertEquals(1, page.getEntryCount());
		entry = page.getEntry(0);
		assertEquals(PartOfSpeech.NOUN, entry.getPartOfSpeech());
		assertEquals(1, entry.getSenseCount());
		assertEquals("водное транспортное средство, небольшое судно, идущее на вёслах, под парусом или на моторной тяге", entry.getSense(1).getGloss().getPlainText());
	}

	/***/
	public void testLechu() throws Exception {
		IWiktionaryPage page = parse("lechu.txt", "лечу");
		IWiktionaryEntry entry;
		
		assertEquals(2, page.getEntryCount());
		entry = page.getEntry(0);
		//TODO: assertEquals(PartOfSpeech.VERB, entry.getPartOfSpeech());
		assertEquals(null, entry.getPartOfSpeech());
		//assertEquals(PartOfSpeech.WORD_FORM, entry.getPartsOfSpeech().get(1));
		assertEquals(0, entry.getSenseCount());
		entry = page.getEntry(1);
		//assertEquals(PartOfSpeech.VERB, entry.getPartOfSpeech());
		assertEquals(null, entry.getPartOfSpeech());
		//assertEquals(PartOfSpeech.WORD_FORM, entry.getPartsOfSpeech().get(1));
		assertEquals(0, entry.getSenseCount());
	}
	

	protected IWiktionaryPage parse(final String fileName, final String title) throws IOException {
		StringBuilder text = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(
						new File("src/test/resources/articles-ru/" + fileName)), 
						"UTF-8"));
		String line;
		while ((line = reader.readLine()) != null)
			text.append(line).append("\n");
		reader.close();
		WiktionaryPage result = new WiktionaryPage();
		result.setTitle(title);
		WiktionaryEntryParser parser = new RUWiktionaryEntryParser();
		parser.parse(result, text.toString());
		return result;
	}
	
}
