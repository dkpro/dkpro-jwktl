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

	public void testParseRelation() throws Exception {
		ENRelationHandler handler = new ENRelationHandler(DERIVED_TERM, "Derived terms");
		IWiktionaryRelation relation = processFirst(handler, "* {{l|en|target}}");
		assertEquals(DERIVED_TERM, relation.getRelationType());
		assertEquals("target", relation.getTarget());
		assertNull(relation.getLinkType());
	}

	public void testParseRelationNoWhitespace() throws Exception {
		ENRelationHandler handler = new ENRelationHandler(DERIVED_TERM, "Derived terms");
		assertEquals("target", processFirst(handler, "*{{l|en|target}}").getTarget());
		assertEquals("target", processFirst(handler, "*[[target]]").getTarget());
	}

	public void testParseRelationExtraWhitespace() throws Exception {
		ENRelationHandler handler = new ENRelationHandler(DERIVED_TERM, "Derived terms");
		assertEquals("target", processFirst(handler, "*   {{l|en|target}}").getTarget());
		assertEquals("target", processFirst(handler, "*  [[target]]").getTarget());
	}

	public void testParseRelationLinkedWithTemplate() throws Exception {
		ENRelationHandler handler = new ENRelationHandler(DERIVED_TERM, "Derived terms");
		assertEquals("target", processFirst(handler, "* {{l|en|target}}").getTarget());
	}

	public void testParseRelationLinkedWithSquareBrackets() throws Exception {
		ENRelationHandler handler = new ENRelationHandler(DERIVED_TERM, "Derived terms");
		assertEquals("target", processFirst(handler, "* [[target]]").getTarget());
	}

	public void testParseRelationList() throws Exception {
		ENRelationHandler handler = new ENRelationHandler(DERIVED_TERM, "Derived terms");
		List<IWiktionaryRelation> relations = process(handler,
			"* {{l|en|target1}}",
			"* [[target2]]"
		).getRelations();
		assertEquals(2, relations.size());
		assertEquals("target1", relations.get(0).getTarget());
		assertEquals("target2", relations.get(1).getTarget());
	}

	public void testParseRelationListWithTemplateHeaderAndFooter() throws Exception {
		ENRelationHandler handler = new ENRelationHandler(DERIVED_TERM, "Derived terms");
		List<IWiktionaryRelation> relations = process(handler,
			"{{rel-top3|Terms derived from ''foo''}}",
			"* {{l|en|target1}}",
			"* [[target2]]",
			"{{rel-mid3}}",
			"* [[target3]]",
			"* [[target4]]",
			"{{rel-bottom}}"
		).getRelations();
		assertEquals(4, relations.size());
	}

	public void testDerivedTermsMonday() throws Exception {
		IWiktionaryPage page = parse("Monday.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		final List<IWiktionaryRelation> relations = entry.getRelations(DERIVED_TERM);
		assertEquals(46, relations.size());
		assertEquals("Ash Monday", relations.get(0).getTarget());
		assertEquals("Whitsun Monday", relations.get(relations.size()-1).getTarget());
	}

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

	protected IWiktionaryRelation processFirst(ENRelationHandler handler, String... body) {
		List<IWiktionaryRelation> relations = process(handler, body).getRelations();
		assertFalse(relations.isEmpty());
		return relations.get(0);
	}
}
