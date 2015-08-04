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
package de.tudarmstadt.ukp.jwktl.parser.en.components;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.parser.en.ENWiktionaryEntryParserTest;

public class ENUsageNotesHandlerTest extends ENWiktionaryEntryParserTest {
	public void testParseBatsman() throws Exception {
		final IWiktionaryPage page = parse("batsman.txt");
		final IWiktionaryEntry entry = page.getEntries().get(1);
		assertEquals("The term batsman is applied to both male and female cricketers; [[batswoman]] is much rarer.", entry.getUsageNotes().getText());
	}

	public void testParsePound() throws Exception {
		final IWiktionaryPage page = parse("pound.txt");
		final IWiktionaryEntry entry = page.getEntries().get(0);
		assertEquals(
			"* Internationally, the \"pound\" has most commonly referred to the UK pound. The other currencies were usually distinguished in some way, e.g., the \"Irish pound\" or the \"punt\".\n" +
			"* In the vicinity of each other country calling its currency the pound among English speakers the local currency would be the \"pound\", with all others distinguished, e.g., the \"British pound\".",
			entry.getUsageNotes().getText());

		assertEquals("Manx English uses this word uncountably.", page.getEntries().get(1).getUsageNotes().getPlainText());
		assertNull(page.getEntries().get(2).getUsageNotes());
		assertNull(page.getEntries().get(3).getUsageNotes());
	}

	public void testDreier() throws Exception {
		final IWiktionaryPage page = parse("dreier.txt");
		final IWiktionaryEntry entry = page.getEntries().get(0);
		assertEquals("Only in adjectival use and only when no article or pronoun is preceding. More at {{term|drei|lang=de}}.", entry.getUsageNotes().getText());
		assertNull(page.getEntries().get(1).getUsageNotes());
	}
}
