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

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalAspect;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalDegree;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalMood;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalPerson;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalTense;
import de.tudarmstadt.ukp.jwktl.api.util.NonFiniteForm;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEWordFormHandler;

/**
 * Test case for {@link DEWordFormHandler}.
 * @author Christian M. Meyer
 */
public class DEWordFormHandlerTest extends DEWiktionaryEntryParserTest {
	
	/***/
	public void testAberration() throws Exception {
		IWiktionaryPage page = parse("Aberration.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Aberration", null, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("Aberrationen", null, GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testHallo() throws Exception {
		IWiktionaryPage page = parse("Hallo.txt");		
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("das Hallo", GrammaticalCase.NOMINATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("die Hallos", GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("des Hallos", GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("der Hallos", GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("dem Hallo", GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("den Hallos", GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("das Hallo", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("die Hallos", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
	}
	
	/***/
	public void testKiefer() throws Exception {
		IWiktionaryPage page = parse("Kiefer.txt");		
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("die Kiefer", GrammaticalCase.NOMINATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("die Kiefern", GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("der Kiefer", GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("der Kiefern", GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("der Kiefer", GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("den Kiefern", GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("die Kiefer", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("die Kiefern", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
		entry = page.getEntry(1);
		actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Kiefer", null, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("Kiefer", null, GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testStaat() throws Exception {
		IWiktionaryPage page = parse("Staat.txt");		
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("der Staat", GrammaticalCase.NOMINATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("die Staaten", GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("die Stäte", GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("die Staat", GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("die Staate", GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("des Staates", GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("des Staats", GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("der Staaten", GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("der Stäte", GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("der Staat", GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("der Staate", GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("dem Staate", GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("dem Staat", GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("den Staaten", GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("den Stäten", GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("den Staaten", GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("den Staaten", GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("den Staat", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("die Staaten", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("die Stäte", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("die Staat", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("die Staate", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
	}
	
	/***/
	public void testThulium() throws Exception {
		IWiktionaryPage page = parse("Thulium.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("das Thulium", GrammaticalCase.NOMINATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun(null, GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("des Thuliums", GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun(null, GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("dem Thulium", GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun(null, GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("das Thulium", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun(null, GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
	}
	
	/***/
	public void testTier() throws Exception {
		IWiktionaryPage page = parse("Tier.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("das Tier", GrammaticalCase.NOMINATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("die Tiere", GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("des Tier(e)s", GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("der Tiere", GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("dem Tier(e)", GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("den Tieren", GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("das Tier", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("die Tiere", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
	}
	
	/***/
	public void testTun() throws Exception {
		IWiktionaryPage page = parse("Tun.txt");
		IWiktionaryEntry entry = page.getEntry(1);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("das Tun", GrammaticalCase.NOMINATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun(null, GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("des Tuns", GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun(null, GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("dem Tun", GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun(null, GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("das Tun", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun(null, GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testAngestellte() throws Exception {
		/*
		IWiktionaryPage page = parse("Angestellte.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		// Starke Deklination
		assertWordFormNoun("Angestellte", GrammaticalCase.NOMINATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("Angestellte", GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("Angestellter", GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("Angestellter", GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("Angestellter", GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("Angestellten", GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("Angestellte", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("Angestellte", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		// Schwache Deklination.
		assertWordFormNoun("die Angestellte", GrammaticalCase.NOMINATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("die Angestellten", GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("der Angestellten", GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("der Angestellten", GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("der Angestellten", GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("den Angestellten", GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("die Angestellte", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("die Angestellten", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		// Gemischte Deklination
		assertWordFormNoun("eine Angestellte", GrammaticalCase.NOMINATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("keine Angestellten", GrammaticalCase.NOMINATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("einer Angestellten", GrammaticalCase.GENITIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("keiner Angestellten", GrammaticalCase.GENITIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("einer Angestellten", GrammaticalCase.DATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("keinen Angestellten", GrammaticalCase.DATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("eine Angestellte", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.SINGULAR, actualIter.next());
		assertWordFormNoun("keine Angestellten", GrammaticalCase.ACCUSATIVE, GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
		*/
		//TODO: weak and strong declination.
		// http://de.wiktionary.org/wiki/Kategorie:Wiktionary:Flexionstabelle_%28Deutsch%29
	}
	 
	/***/
	public void testMitreissen() throws Exception {
		IWiktionaryPage page = parse("mitreissen.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormVerb("reiße mit", GrammaticalPerson.FIRST, GrammaticalTense.PRESENT, GrammaticalMood.INDICATIVE, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("reißt mit", GrammaticalPerson.SECOND, GrammaticalTense.PRESENT, GrammaticalMood.INDICATIVE, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("reißt mit", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, GrammaticalMood.INDICATIVE, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("riss mit", GrammaticalPerson.FIRST, GrammaticalTense.PAST, GrammaticalMood.INDICATIVE, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("mitgerissen", null, null, null, null, GrammaticalAspect.PERFECT, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("risse mit", GrammaticalPerson.FIRST, GrammaticalTense.PAST, GrammaticalMood.CONJUNCTIVE, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("reiß mit!", GrammaticalPerson.SECOND, null, GrammaticalMood.IMPERATIVE, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("reißt mit!", GrammaticalPerson.SECOND, null, GrammaticalMood.IMPERATIVE, GrammaticalNumber.PLURAL, null, null, actualIter.next());
		//Hilfsverb=haben
		//Weitere_Konjugationen= mitreißen (Konjugation)
		assertFalse(actualIter.hasNext());
	}
	
	/***/
	public void testGut() throws Exception {
		IWiktionaryPage page = parse("gut.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormAdjective("gut", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("besser", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("am besten", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testPittoresk() throws Exception {
		IWiktionaryPage page = parse("pittoresk.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormAdjective("pittoresk", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("pittoresker", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("am pittoreskesten", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());
	}
	
	
	protected void assertWordFormNoun(final String expectedForm, 
			final GrammaticalCase expectedCase, 
			final GrammaticalNumber expectedNumber,
			final IWiktionaryWordForm actual) {
		assertEquals(expectedForm, actual.getWordForm());
		assertEquals(expectedCase, actual.getCase());
		assertEquals(expectedNumber, actual.getNumber());
	}

	protected void assertWordFormVerb(final String expectedForm, 
			final GrammaticalPerson expectedPerson, 
			final GrammaticalTense expectedTense,
			final GrammaticalMood expectedMood,
			final GrammaticalNumber expectedNumber,
			final GrammaticalAspect expectedAspect, 
			final NonFiniteForm expectedNonFiniteForm, 
			final IWiktionaryWordForm actual) {
		assertEquals(expectedForm, actual.getWordForm());
		assertEquals(expectedPerson, actual.getPerson());
		assertEquals(expectedTense, actual.getTense());
		assertEquals(expectedMood, actual.getMood());
		assertEquals(expectedNumber, actual.getNumber());
		assertEquals(expectedAspect, actual.getAspect());
		assertEquals(expectedNonFiniteForm, actual.getNonFiniteForm());
	}

	protected void assertWordFormAdjective(final String expectedForm, 
			final GrammaticalDegree expectedDegree,
			final IWiktionaryWordForm actual) {
		assertEquals(expectedForm, actual.getWordForm());
		assertEquals(expectedDegree, actual.getDegree());
	}

}
