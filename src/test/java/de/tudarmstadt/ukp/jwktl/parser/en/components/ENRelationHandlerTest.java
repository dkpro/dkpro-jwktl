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
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.parser.en.ENWiktionaryEntryParserTest;

import static de.tudarmstadt.ukp.jwktl.api.RelationType.DERIVED_TERM;
import static de.tudarmstadt.ukp.jwktl.api.RelationType.DESCENDANT;

/**
 * Test case for {@link ENRelationHandler}.
 * @author Christian M. Meyer
 */
public class ENRelationHandlerTest extends ENWiktionaryEntryParserTest {

	public void testDerivedTermsCasa() throws Exception {
		IWiktionaryPage page = parse("casa.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		final List<IWiktionaryRelation> derived = entry.getUnassignedSense().getRelations(DERIVED_TERM);
		assertEquals(33, derived.size());
		// make sure we don't have derived terms assigned to a sense
		for (IWiktionarySense sense : entry.getSenses()) {
			assertEquals(0, sense.getRelations(DERIVED_TERM).size());
		}
	}

	public void testRelationsDescendantsDid() throws Exception {
		IWiktionaryPage page = parse("did.txt");
		IWiktionaryEntry entry = page.getEntry(2);
		Iterator<IWiktionaryRelation> iter = entry.getUnassignedSense().getRelations(DESCENDANT).iterator();
		assertTrue(iter.hasNext());
		assertRelation(DESCENDANT, "Welsh: dydd", iter.next());
		assertFalse(iter.hasNext());
	}

	public void testRelationsDescendantVaranda() throws Exception {
		IWiktionaryPage page = parse("varanda.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getUnassignedSense().getRelations(DESCENDANT).iterator();
		assertRelation(DESCENDANT, "Hindi: बरामदा", iter.next());
		assertRelation(DESCENDANT, "Hindi: बरण्डा", iter.next());
		assertRelation(DESCENDANT, "English: veranda", iter.next());
		assertRelation(DESCENDANT, "English: verandah", iter.next());
		assertRelation(DESCENDANT, "French: véranda", iter.next());
		assertFalse(iter.hasNext());
	}

	protected static void assertRelation(final RelationType relationType,
										 final String target, final IWiktionaryRelation actual) {
		assertEquals(relationType, actual.getRelationType());
		assertEquals(target, actual.getTarget());
	}
}
