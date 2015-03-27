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

import java.util.Iterator;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.en.ENWiktionaryEntryParserTest;

import static de.tudarmstadt.ukp.jwktl.api.RelationType.SYNONYM;
import static de.tudarmstadt.ukp.jwktl.parser.en.components.ENSemanticRelationHandler.findMatchingSense;

public class ENSemanticRelationHandlerTest extends ENWiktionaryEntryParserTest {
	/***/
	public void testSynonymsRainCatsAndDogs() throws Exception {
		IWiktionaryPage page = parse("rain_cats_and_dogs.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "bucket", iter.next());
		assertRelation(SYNONYM, "bucket down", iter.next());
		assertRelation(SYNONYM, "chuck it down", iter.next());
		assertRelation(SYNONYM, "rain buckets", iter.next());
		assertRelation(SYNONYM, "rain pitchforks", iter.next());
		assertRelation(SYNONYM, "pelt", iter.next());
		assertRelation(SYNONYM, "piss down", iter.next());
		assertRelation(SYNONYM, "pour", iter.next());
		assertRelation(SYNONYM, "stream", iter.next());
		assertRelation(SYNONYM, "teem", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsDictionary() throws Exception {
		IWiktionaryPage page = parse("dictionary.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getUnassignedSense().getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "wordbook", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsGoitrogenic() throws Exception {
		IWiktionaryPage page = parse("goitrogenic.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "antithyroid", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsPound() throws Exception {
		IWiktionaryPage page = parse("pound.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(5).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "£", iter.next());
		assertRelation(SYNONYM, "pound sterling", iter.next());
		assertRelation(SYNONYM, "punt", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsCallously() throws Exception {
		IWiktionaryPage page = parse("callously.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "carelessly", iter.next());
		assertRelation(SYNONYM, "hardheartedly", iter.next());
		assertRelation(SYNONYM, "indifferently", iter.next());
		assertRelation(SYNONYM, "unfeelingly", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsWallpaper() throws Exception {
		IWiktionaryPage page = parse("wallpaper.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(3).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "desktop image", iter.next());
		assertRelation(SYNONYM, "desktop pattern", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsLung() throws Exception {
		IWiktionaryPage page = parse("lung.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "bellows", iter.next());
		assertRelation(SYNONYM, "lights", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsDrink() throws Exception {
		IWiktionaryPage page = parse("drink.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(2).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "gulp", iter.next());
		assertRelation(SYNONYM, "imbibe", iter.next());
		assertRelation(SYNONYM, "quaff", iter.next());
		assertRelation(SYNONYM, "sip", iter.next());
		assertRelation(SYNONYM, "Wikisaurus:drink", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsShutUp() throws Exception {
		IWiktionaryPage page = parse("shut_up.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(6).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "be quiet", iter.next());
		assertRelation(SYNONYM, "be silent", iter.next());
		assertRelation(SYNONYM, "fall silent", iter.next());
		assertRelation(SYNONYM, "hush", iter.next());
		assertRelation(SYNONYM, "quieten down", iter.next());
		assertRelation(SYNONYM, "shush", iter.next());
		assertRelation(SYNONYM, "be quiet!", iter.next());
		assertRelation(SYNONYM, "can it!", iter.next());
		assertRelation(SYNONYM, "hush!", iter.next());
		assertRelation(SYNONYM, "put a sock in it!", iter.next());
		assertRelation(SYNONYM, "quiet!", iter.next());
		assertRelation(SYNONYM, "sh!", iter.next());
		assertRelation(SYNONYM, "shush!", iter.next());
		assertRelation(SYNONYM, "shut it!", iter.next());
		assertRelation(SYNONYM, "shut your face!", iter.next());
		assertRelation(SYNONYM, "shaddap", iter.next());
		assertRelation(SYNONYM, "silence!", iter.next());
		assertRelation(SYNONYM, "st!", iter.next());
		assertRelation(SYNONYM, "STFU", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsXray() throws Exception {
		IWiktionaryPage page = parse("X-ray.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(2).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "Röntgen radiation", iter.next());
		assertRelation(SYNONYM, "Rontgen radiation", iter.next());
		assertRelation(SYNONYM, "Roentgen radiation", iter.next());
		assertRelation(SYNONYM, "Röntgen rays", iter.next());
		assertRelation(SYNONYM, "Rontgen rays", iter.next());
		assertRelation(SYNONYM, "Roentgen rays", iter.next());
		assertRelation(SYNONYM, "X-ray radiation", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsAbdominal() throws Exception {
		IWiktionaryPage page = parse("abdominal.txt");
		IWiktionaryEntry entry = page.getEntry(1);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "Cypriniformes", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsAbreast() throws Exception {
		IWiktionaryPage page = parse("abreast.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "apprised", iter.next());
		assertRelation(SYNONYM, "up to date/up-to-date", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsHead() throws Exception {
		IWiktionaryPage page = parse("head.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(13).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "headmaster", iter.next());
		assertRelation(SYNONYM, "headmistress", iter.next());
		assertRelation(SYNONYM, "principal", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsTermination() throws Exception {
		IWiktionaryPage page = parse("termination.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "concluding", iter.next());
		assertRelation(SYNONYM, "ending", iter.next());
		assertRelation(SYNONYM, "finishing", iter.next());
		assertRelation(SYNONYM, "stoping", iter.next());
		assertRelation(SYNONYM, "terminating", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(2).getRelations(SYNONYM).iterator();
		assertFalse(iter.hasNext());
		iter = entry.getSense(3).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "close", iter.next());
		assertRelation(SYNONYM, "conclusion", iter.next());
		assertRelation(SYNONYM, "end", iter.next());
		assertRelation(SYNONYM, "finale", iter.next());
		assertRelation(SYNONYM, "finish", iter.next());
		assertRelation(SYNONYM, "stop", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(4).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "border", iter.next());
		assertRelation(SYNONYM, "edge", iter.next());
		assertRelation(SYNONYM, "end", iter.next());
		assertRelation(SYNONYM, "limit", iter.next());
		assertRelation(SYNONYM, "lip", iter.next());
		assertRelation(SYNONYM, "rim", iter.next());
		assertRelation(SYNONYM, "tip", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(5).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "consequence", iter.next());
		assertRelation(SYNONYM, "outcome", iter.next());
		assertRelation(SYNONYM, "result", iter.next());
		assertRelation(SYNONYM, "upshot", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(6).getRelations(SYNONYM).iterator();
		assertFalse(iter.hasNext());
		iter = entry.getSense(7).getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "abortion", iter.next());
		assertRelation(SYNONYM, "induced abortion", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getUnassignedSense().getRelations(SYNONYM).iterator();
		assertFalse(iter.hasNext());
	}

	/***/
	public void testAntonymsKiefer() throws Exception {
		IWiktionaryPage page = parse("cow.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(RelationType.ANTONYM).iterator();
		assertRelation(RelationType.ANTONYM, "bull", iter.next());
		assertRelation(RelationType.ANTONYM, "ox", iter.next());
		assertRelation(RelationType.ANTONYM, "steer", iter.next());
		assertRelation(RelationType.ANTONYM, "heifer", iter.next());
		assertFalse(iter.hasNext());
	}

	public void testSynonymsCasa() throws Exception {
		IWiktionaryPage page = parse("casa.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		final List<IWiktionaryRelation> synonyms = entry.getRelations(SYNONYM);
		assertEquals(2, synonyms.size());
	}

	public void testSynonymsGranada() throws Exception {
		IWiktionaryPage page = parse("granada.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		final List<IWiktionaryRelation> synonyms = entry.getRelations(SYNONYM);
		assertEquals(3, synonyms.size());

		List<IWiktionaryRelation> relations = entry.getSense(1).getRelations(SYNONYM); // pomegranate
		assertEquals(1, relations.size());
		assertEquals("romã", relations.get(0).getTarget());

		relations = entry.getSense(2).getRelations(SYNONYM); // hand grenade
		assertEquals(1, relations.size());
		assertEquals("granada de mão", relations.get(0).getTarget());

		relations = entry.getSense(3).getRelations(SYNONYM); // shell
		assertEquals(0, relations.size());

		relations = entry.getSense(4).getRelations(SYNONYM); // garnet
		assertEquals(1, relations.size());
		assertEquals("granate", relations.get(0).getTarget());
	}

	protected static void assertRelation(final RelationType relationType,
										 final String target, final IWiktionaryRelation actual) {
		assertEquals(relationType, actual.getRelationType());
		assertEquals(target, actual.getTarget());
	}

	public void testFindMatchingSenseWithMatch() throws Exception {
		IWiktionaryPage page = parse("casa.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		final WiktionarySense sense = findMatchingSense((WiktionaryEntry) entry, "home");
		assertNotNull(sense);
		assertEquals("{{l/en|home}} {{gloss|one’s own dwelling place}}", sense.getGloss().getText());
	}

	public void testFindMatchingSenseReturnsNullWithoutMatch() throws Exception {
		IWiktionaryPage page = parse("casa.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		assertNull(findMatchingSense((WiktionaryEntry) entry, "froobaz"));
	}

	public void testFindMatchingSenseWithoutMatchButMonosemousEntryReturnsEntry() throws Exception {
		IWiktionaryPage page = parse("goitrogenic.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		assertNotNull(findMatchingSense((WiktionaryEntry) entry, "froobaz"));
	}

	public void testFindMatchingSenseWithNullButMonosemousEntryReturnsEntry() throws Exception {
		IWiktionaryPage page = parse("goitrogenic.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		assertNotNull(findMatchingSense((WiktionaryEntry) entry, null));
	}
}
