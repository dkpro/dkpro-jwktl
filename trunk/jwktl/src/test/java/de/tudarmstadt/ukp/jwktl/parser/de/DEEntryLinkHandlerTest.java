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

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEEntryLinkHandler;

/**
 * Test case for {@link DEEntryLinkHandler}.
 * @author Christian M. Meyer
 */
public class DEEntryLinkHandlerTest extends DEWiktionaryEntryParserTest {

	/***/
	public void testAbschlusz() throws Exception {
		IWiktionaryPage page = parse("Abschlusz.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		assertEquals("Abschluss", entry.getEntryLink());
	}
	
}
