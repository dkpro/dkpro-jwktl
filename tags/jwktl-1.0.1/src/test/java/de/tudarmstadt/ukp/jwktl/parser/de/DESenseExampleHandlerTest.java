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
package de.tudarmstadt.ukp.jwktl.parser.de;

import java.util.Iterator;

import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DESenseExampleHandler;

/**
 * Test case for {@link DESenseExampleHandler}.
 * @author Christian M. Meyer
 */
public class DESenseExampleHandlerTest extends DEWiktionaryEntryParserTest {
	
	/***/
	public void testRuettelstreifen() throws Exception {
		IWiktionaryPage page = parse("Ruettelstreifen.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWikiString> exampleIter = entry.getSense(1).getExamples().iterator();
		assertEquals("„Eine wirksame Maßnahme die Verkehrssicherheit zu steigern, sind z.B.: profilierte Fahrbahnmarkierungen oder ''Rüttelstreifen'' auf der Standspur.“", exampleIter.next().getText());
		assertEquals("„''Rüttelstreifen'' am Fahrbahnrand von Autobahnen können die Zahl übermüdungsbedingter Verkehrsunfälle deutlich reduzieren.“", exampleIter.next().getText());
		assertEquals("„Schwere Autobahn-Unfälle können mit Hilfe von sogenannten ''Rüttelstreifen'' deutlich verringert werden.“", exampleIter.next().getText());
		assertFalse(exampleIter.hasNext());
	}

}
