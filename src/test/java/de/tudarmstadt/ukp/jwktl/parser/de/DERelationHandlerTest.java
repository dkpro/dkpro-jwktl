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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DERelationHandler;

/**
 * Test case for {@link DERelationHandler}.
 * @author Christian M. Meyer
 */
public class DERelationHandlerTest extends DEWiktionaryEntryParserTest {
	
	/***/
	public void testSynonymsSubdivisio() throws Exception {
		IWiktionaryPage page = parse("Subdivisio.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Set<String> expected = new HashSet<String>();
		expected.add("Unterabteilung");
		List<IWiktionaryRelation> relations = entry.getSense(1).getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.SYNONYM == relation.getRelationType()) 
				assertTrue("Invalid relation: " + relation.getTarget(),
						expected.remove(relation.getTarget()));
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}

	/***/
	public void testSynonymsJanuar() throws Exception {
		IWiktionaryPage page = parse("Januar.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Set<String> expected = new HashSet<String>();
		expected.add("Jänner");
		expected.add("Hartung");
		expected.add("Eismonat");
		expected.add("Wintermonat");
		expected.add("Schneemonat");
		expected.add("Wolfsmonat");
		List<IWiktionaryRelation> relations = entry.getSense(1).getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.SYNONYM == relation.getRelationType()) 
				assertTrue("Invalid relation: " + relation.getTarget(),
						expected.remove(relation.getTarget()));
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}

	/***/
	public void testSynonymsAberration() throws Exception {
		IWiktionaryPage page = parse("Aberration.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		for (int i = 1; i <= 3; i++) {
			Set<String> expected = new HashSet<String>();
			expected.add("Aberratio");
			List<IWiktionaryRelation> relations = entry.getSense(i).getRelations();
			for (IWiktionaryRelation relation : relations) {
				if (RelationType.SYNONYM == relation.getRelationType()) 
					assertTrue("Invalid relation: " + relation.getTarget(),
							expected.remove(relation.getTarget()));
			}
			assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
		}
		
		Set<String> expected = new HashSet<String>();
		expected.add("geistige Verwirrung");
		List<IWiktionaryRelation> relations = entry.getSense(4).getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.SYNONYM == relation.getRelationType()) 
				assertTrue("Invalid relation: " + relation.getTarget(),
						expected.remove(relation.getTarget()));
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}

	/***/
	public void testSynonymsKartoffel() throws Exception {
		IWiktionaryPage page = parse("Kartoffel.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> relationIter = entry.getSense(1).getRelations(RelationType.SYNONYM).iterator();
		assertFalse(relationIter.hasNext());
		
		relationIter = entry.getSense(2).getRelations(RelationType.SYNONYM).iterator();
		assertEquals("Arpern", relationIter.next().getTarget());
		assertEquals("Bramburi", relationIter.next().getTarget());
		assertEquals("Bulwe", relationIter.next().getTarget());
		assertEquals("Erdapfel", relationIter.next().getTarget());
		assertEquals("Erdbirne", relationIter.next().getTarget());
		assertEquals("Erdbirne", relationIter.next().getTarget());
		assertEquals("Grundbeere", relationIter.next().getTarget());
		assertEquals("Grundbirne", relationIter.next().getTarget());
		assertEquals("Knolle", relationIter.next().getTarget());
		assertEquals("Pudel", relationIter.next().getTarget());
		assertFalse(relationIter.hasNext());

		relationIter = entry.getSense(3).getRelations(RelationType.SYNONYM).iterator();
		assertEquals("Kartoffelnase", relationIter.next().getTarget());
		assertEquals("Knollennase", relationIter.next().getTarget());
		assertFalse(relationIter.hasNext());

		relationIter = entry.getSense(4).getRelations(RelationType.SYNONYM).iterator();
		assertEquals("Zwiebel", relationIter.next().getTarget());
		assertFalse(relationIter.hasNext());

		relationIter = entry.getSense(5).getRelations(RelationType.SYNONYM).iterator();
		assertEquals("Kuhloch", relationIter.next().getTarget());
		assertFalse(relationIter.hasNext());
	}
	
	/***/
	public void testAntonymsKiefer() throws Exception {
		IWiktionaryPage page = parse("Kiefer.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Set<String> expected = new HashSet<String>();
		expected.add("Fichte");
		expected.add("Eibe");
		expected.add("Tanne");
		List<IWiktionaryRelation> relations = entry.getSense(1).getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.ANTONYM == relation.getRelationType()) 
				assertTrue("Invalid relation: " + relation.getTarget(),
						expected.remove(relation.getTarget()));
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}

	/***/
	public void testHypernymsSoziolekt() throws Exception {
		IWiktionaryPage page = parse("Soziolekt.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Set<String> expected = new HashSet<String>();
		expected.add("Lekt");
		expected.add("Sprachvarietät");
		expected.add("Varietät");
		List<IWiktionaryRelation> relations = entry.getSense(1).getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.HYPERNYM == relation.getRelationType()) 
				assertTrue("Invalid relation: " + relation.getTarget(),
						expected.remove(relation.getTarget()));
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}

	/***/
	public void testHypernymsDirham() throws Exception {
		IWiktionaryPage page = parse("Dirham.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> relationIter = entry.getSense(1).getRelations(RelationType.HYPERNYM).iterator();
		assertEquals("Währungseinheit", relationIter.next().getTarget());
		assertEquals("Währung", relationIter.next().getTarget());
		assertEquals("Riyal", relationIter.next().getTarget());
		assertEquals("Dinar", relationIter.next().getTarget());
		assertEquals("Somoni", relationIter.next().getTarget());
		assertEquals("Dinar", relationIter.next().getTarget());
		assertFalse(relationIter.hasNext());

		relationIter = entry.getSense(2).getRelations(RelationType.HYPERNYM).iterator();
		assertEquals("Gewichtseinheit", relationIter.next().getTarget());
		assertEquals("Gewicht", relationIter.next().getTarget());
		assertFalse(relationIter.hasNext());
	}
	
	/***/
	public void testHyponymsZug() throws Exception {
		IWiktionaryPage page = parse("Zug.txt");
		IWiktionaryEntry entry = page.getEntry(1);		
		Set<String> expected = new HashSet<String>();
		expected.add("Zug");
		List<IWiktionaryRelation> relations = entry.getSense(1).getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.HYPONYM == relation.getRelationType()) 
				assertTrue("Invalid relation: " + relation.getTarget(),
					expected.remove(relation.getTarget()));				
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}
	
	/***/
	public void testHyponymsVerbalsubstantiv() throws IOException {
		IWiktionaryPage page = parse("Verbalsubstantiv.txt");
		IWiktionaryEntry entry = page.getEntry(0);		
		Set<String> expected = new HashSet<String>();
		expected.add("Gerundium");
		expected.add("Gerundiv");
		expected.add("Gerundivum");
		expected.add("Nomen actionis");
		List<IWiktionaryRelation> relations = entry.getSense(1).getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.HYPONYM == relation.getRelationType()) 
				assertTrue("Invalid relation: " + relation.getTarget(),
					expected.remove(relation.getTarget()));				
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}
	
	/***/
	public void testDerivedTermsKiefer() throws Exception {
		IWiktionaryPage page = parse("Kiefer.txt");
		IWiktionaryEntry entry = page.getEntry(1);
		Set<String> expected = new HashSet<String>();
		expected.add("Kieferanomalie");
		expected.add("Kieferchirurgie");
		expected.add("Kiefergelenk");
		expected.add("Kieferhöhle");
		expected.add("Kieferklemme");
		expected.add("Kieferknochen");
		expected.add("Kieferorthopädie");
		expected.add("Kieferspalte");
		expected.add("Kiefersprerre");
		List<IWiktionaryRelation> relations = entry.getUnassignedSense().getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.DERIVED_TERM == relation.getRelationType()) 
				assertTrue(expected.remove(relation.getTarget()));
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}

	/***/
	public void testDerivedTermsTier() throws Exception {
		IWiktionaryPage page = parse("Tier.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Set<String> expected = new HashSet<String>();
		expected.add("Tierarzt");
		expected.add("tierisch");
		expected.add("tierlieb");
		expected.add("Tierfänger");
		expected.add("Tierfreund");
		expected.add("Tiergarten");
		expected.add("Tiergestalt");
		expected.add("Tierhaltung");
		expected.add("Tierhandlung");
		expected.add("Tierheim");
		expected.add("Tierkunde");
		expected.add("Tierliebe");
		expected.add("Tierpark");
		expected.add("Tierpfleger");
		expected.add("Tierquäler");
		expected.add("Tierquälerei");
		expected.add("Tierreich");
		expected.add("Tierschutz");
		expected.add("Tierschützer");
		expected.add("Tierwelt");
		expected.add("Tierversuch");
		expected.add("Tierzucht");
		List<IWiktionaryRelation> relations = entry.getUnassignedSense().getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.DERIVED_TERM == relation.getRelationType()) 
				assertTrue("Invalid relation: " + relation.getTarget(),
					expected.remove(relation.getTarget()));				
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}

	/***/
	public void testRelatedTermsAblehnung() throws Exception {
		IWiktionaryPage page = parse("Ablehnung.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Set<String> expected = new HashSet<String>();
		expected.add("Aversion");
		expected.add("Abscheu");
		expected.add("Unbehagen");
		List<IWiktionaryRelation> relations = entry.getSense(1).getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.ETYMOLOGICALLY_RELATED_TERM == relation.getRelationType()) 
				assertTrue("Invalid relation: " + relation.getTarget(),
						expected.remove(relation.getTarget()));
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}

	/***/
	public void testRelatedTermsHallo() throws Exception {
		IWiktionaryPage page = parse("Hallo.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Set<String> expected = new HashSet<String>();
		//expected.add("großes ''Hallo''");
		expected.add("großes Hallo");
		List<IWiktionaryRelation> relations = entry.getSense(1).getRelations();
		for (IWiktionaryRelation relation : relations) {
			if (RelationType.CHARACTERISTIC_WORD_COMBINATION == relation.getRelationType()) 
				assertTrue("Invalid relation: " + relation.getTarget(),
						expected.remove(relation.getTarget()));
		}
		assertTrue("Relations missing: " + Arrays.toString(expected.toArray()), expected.isEmpty());
	}

	/** In the article "Wisent", the section "Unterbegriffe" is immediately 
	 *  followed by "Verwandte Begriffe". Unfortunately no predefined text 
	 *  module was used for the latter, which causes the API to miss the 
	 *  actual end of the "Unterbegriffe" section. If this problem should 
	 *  be solved (apparently it's an error in WKT, not our API), a line that 
	 *  consists of only a bold faced string could be recognized as a 
	 *  section header. This works fine for extracting hyponyms but maybe 
	 *  not for general purpose API use. */
	public void testInvalidSectionHeader() throws Exception {
		IWiktionaryPage page = parse("Wisent.txt");
		assertEquals(6, page.getEntry(0).getSense(1).getRelations(RelationType.HYPERNYM).size());
		assertEquals(5, page.getEntry(0).getSense(1).getRelations(RelationType.HYPONYM).size());
	}

	/** Many WKT sections that are yet empty are commented out by using 
	 *  Html-style comments. These comments should be removed to ensure 
	 *  correct results of the parsing process. Because of the closing 
	 *  comment in the "Unterbegriffe" section, "--" is generated as 
	 *  hyponym relation.  */
	public void testHtmlCommentsRemoval() throws Exception {
		IWiktionaryPage page = parse("Thulium.txt");
		List<IWiktionaryRelation> relations = page.getEntry(0).getSense(0)
				.getRelations(RelationType.HYPONYM);
		assertEquals(0, relations.size());		
	}
	
	/*protected static void assertRelation(final RelationType relationType, 
			final String target, final IWiktionaryRelation actual) {
		assertEquals(relationType, actual.getRelationType());
		assertEquals(target, actual.getTarget());
	}*/
	
}
