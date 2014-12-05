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

import java.util.Iterator;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENRelationHandler;

/**
 * Test case for {@link ENRelationHandler}.
 * @author Christian M. Meyer
 */
public class ENRelationHandlerTest extends ENWiktionaryEntryParserTest {

	public void testRelationsDescendantsDid() throws Exception {
		IWiktionaryPage page = parse("did.txt");
		IWiktionaryEntry entry = page.getEntry(2);

		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(RelationType.DESCENDANT).iterator();
		assertRelation(RelationType.DESCENDANT, "Welsh: dydd", iter.next());
		assertFalse(iter.hasNext());
	}

	public void testRelationsDescendantVaranda() throws Exception {
		IWiktionaryPage page = parse("varanda.txt");
		IWiktionaryEntry entry = page.getEntry(0);

		Iterator<IWiktionaryRelation> iter = entry.getSense(0).getRelations(RelationType.DESCENDANT).iterator();
		assertRelation(RelationType.DESCENDANT, "Hindi: बरामदा", iter.next());
		assertRelation(RelationType.DESCENDANT, "Hindi: बरण्डा", iter.next());
		assertRelation(RelationType.DESCENDANT, "English: veranda", iter.next());
		assertRelation(RelationType.DESCENDANT, "English: verandah", iter.next());
		assertRelation(RelationType.DESCENDANT, "French: véranda", iter.next());
		assertFalse(iter.hasNext());
	}
		
	/***/
	public void testSynonymsRainCatsAndDogs() throws Exception {
		IWiktionaryPage page = parse("rain_cats_and_dogs.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "bucket", iter.next());
		assertRelation(RelationType.SYNONYM, "bucket down", iter.next());
		assertRelation(RelationType.SYNONYM, "chuck it down", iter.next());
		assertRelation(RelationType.SYNONYM, "rain buckets", iter.next());
		assertRelation(RelationType.SYNONYM, "rain pitchforks", iter.next());
		assertRelation(RelationType.SYNONYM, "pelt", iter.next());
		assertRelation(RelationType.SYNONYM, "piss down", iter.next());
		assertRelation(RelationType.SYNONYM, "pour", iter.next());
		assertRelation(RelationType.SYNONYM, "stream", iter.next());
		assertRelation(RelationType.SYNONYM, "teem", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsDictionary() throws Exception {
		IWiktionaryPage page = parse("dictionary.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getUnassignedSense().getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "wordbook", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsGoitrogenic() throws Exception {
		IWiktionaryPage page = parse("goitrogenic.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "antithyroid", iter.next());
		assertFalse(iter.hasNext());
	}	

	/***/
	public void testSynonymsPound() throws Exception {
		IWiktionaryPage page = parse("pound.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(5).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "£", iter.next());
		assertRelation(RelationType.SYNONYM, "pound sterling", iter.next());
		assertRelation(RelationType.SYNONYM, "punt", iter.next());
		assertFalse(iter.hasNext());
	}	

	/***/
	public void testSynonymsCallously() throws Exception {
		IWiktionaryPage page = parse("callously.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "carelessly", iter.next());
		assertRelation(RelationType.SYNONYM, "hardheartedly", iter.next());
		assertRelation(RelationType.SYNONYM, "indifferently", iter.next());
		assertRelation(RelationType.SYNONYM, "unfeelingly", iter.next());
		assertFalse(iter.hasNext());
	}	

	/***/
	public void testSynonymsWallpaper() throws Exception {
		IWiktionaryPage page = parse("wallpaper.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(3).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "desktop image", iter.next());
		assertRelation(RelationType.SYNONYM, "desktop pattern", iter.next());
		assertFalse(iter.hasNext());
	}	

	/***/
	public void testSynonymsLung() throws Exception {
		IWiktionaryPage page = parse("lung.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "bellows", iter.next());
		assertRelation(RelationType.SYNONYM, "lights", iter.next());
		assertFalse(iter.hasNext());
	}	

	/***/
	public void testSynonymsDrink() throws Exception {
		IWiktionaryPage page = parse("drink.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(2).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "gulp", iter.next());
		assertRelation(RelationType.SYNONYM, "imbibe", iter.next());
		assertRelation(RelationType.SYNONYM, "quaff", iter.next());
		assertRelation(RelationType.SYNONYM, "sip", iter.next());
		assertRelation(RelationType.SYNONYM, "Wikisaurus:drink", iter.next());
		assertFalse(iter.hasNext());
	}	

	/***/
	public void testSynonymsShutUp() throws Exception {
		IWiktionaryPage page = parse("shut_up.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(6).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "be quiet", iter.next());
		assertRelation(RelationType.SYNONYM, "be silent", iter.next());
		assertRelation(RelationType.SYNONYM, "fall silent", iter.next());
		assertRelation(RelationType.SYNONYM, "hush", iter.next());
		assertRelation(RelationType.SYNONYM, "quieten down", iter.next());
		assertRelation(RelationType.SYNONYM, "shush", iter.next());
		assertRelation(RelationType.SYNONYM, "be quiet!", iter.next());
		assertRelation(RelationType.SYNONYM, "can it!", iter.next());
		assertRelation(RelationType.SYNONYM, "hush!", iter.next());
		assertRelation(RelationType.SYNONYM, "put a sock in it!", iter.next());
		assertRelation(RelationType.SYNONYM, "quiet!", iter.next());
		assertRelation(RelationType.SYNONYM, "sh!", iter.next());
		assertRelation(RelationType.SYNONYM, "shush!", iter.next());
		assertRelation(RelationType.SYNONYM, "shut it!", iter.next());
		assertRelation(RelationType.SYNONYM, "shut your face!", iter.next());
		assertRelation(RelationType.SYNONYM, "shaddap", iter.next());
		assertRelation(RelationType.SYNONYM, "silence!", iter.next());
		assertRelation(RelationType.SYNONYM, "st!", iter.next());
		assertRelation(RelationType.SYNONYM, "STFU", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsXray() throws Exception {
		IWiktionaryPage page = parse("X-ray.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(2).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "Röntgen radiation", iter.next());
		assertRelation(RelationType.SYNONYM, "Rontgen radiation", iter.next());
		assertRelation(RelationType.SYNONYM, "Roentgen radiation", iter.next());
		assertRelation(RelationType.SYNONYM, "Röntgen rays", iter.next());
		assertRelation(RelationType.SYNONYM, "Rontgen rays", iter.next());
		assertRelation(RelationType.SYNONYM, "Roentgen rays", iter.next());
		assertRelation(RelationType.SYNONYM, "X-ray radiation", iter.next());
		assertFalse(iter.hasNext());
	}	

	/***/
	public void testSynonymsAbdominal() throws Exception {
		IWiktionaryPage page = parse("abdominal.txt");
		IWiktionaryEntry entry = page.getEntry(1);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "Cypriniformes", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsAbreast() throws Exception {
		IWiktionaryPage page = parse("abreast.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "apprised", iter.next());
		assertRelation(RelationType.SYNONYM, "up to date/up-to-date", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsHead() throws Exception {
		IWiktionaryPage page = parse("head.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(13).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "headmaster", iter.next());
		assertRelation(RelationType.SYNONYM, "headmistress", iter.next());
		assertRelation(RelationType.SYNONYM, "principal", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testSynonymsTermination() throws Exception {
		IWiktionaryPage page = parse("termination.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getSense(1).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "concluding", iter.next());
		assertRelation(RelationType.SYNONYM, "ending", iter.next());
		assertRelation(RelationType.SYNONYM, "finishing", iter.next());
		assertRelation(RelationType.SYNONYM, "stoping", iter.next());
		assertRelation(RelationType.SYNONYM, "terminating", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(2).getRelations(RelationType.SYNONYM).iterator();
		assertFalse(iter.hasNext());
		iter = entry.getSense(3).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "close", iter.next());
		assertRelation(RelationType.SYNONYM, "conclusion", iter.next());
		assertRelation(RelationType.SYNONYM, "end", iter.next());
		assertRelation(RelationType.SYNONYM, "finale", iter.next());
		assertRelation(RelationType.SYNONYM, "finish", iter.next());
		assertRelation(RelationType.SYNONYM, "stop", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(4).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "border", iter.next());
		assertRelation(RelationType.SYNONYM, "edge", iter.next());
		assertRelation(RelationType.SYNONYM, "end", iter.next());
		assertRelation(RelationType.SYNONYM, "limit", iter.next());
		assertRelation(RelationType.SYNONYM, "lip", iter.next());
		assertRelation(RelationType.SYNONYM, "rim", iter.next());
		assertRelation(RelationType.SYNONYM, "tip", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(5).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "consequence", iter.next());
		assertRelation(RelationType.SYNONYM, "outcome", iter.next());
		assertRelation(RelationType.SYNONYM, "result", iter.next());
		assertRelation(RelationType.SYNONYM, "upshot", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getSense(6).getRelations(RelationType.SYNONYM).iterator();
		assertFalse(iter.hasNext());
		iter = entry.getSense(7).getRelations(RelationType.SYNONYM).iterator();
		assertRelation(RelationType.SYNONYM, "abortion", iter.next());
		assertRelation(RelationType.SYNONYM, "induced abortion", iter.next());
		assertFalse(iter.hasNext());
		iter = entry.getUnassignedSense().getRelations(RelationType.SYNONYM).iterator();
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

	protected static void assertRelation(final RelationType relationType,
			final String target, final IWiktionaryRelation actual) {
		assertEquals(relationType, actual.getRelationType());
		assertEquals(target, actual.getTarget());
	}

}
