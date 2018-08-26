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

import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase.ACCUSATIVE;
import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase.DATIVE;
import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase.GENITIVE;
import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase.NOMINATIVE;
import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender.FEMININE;
import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender.MASCULINE;
import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender.NEUTER;
import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber.PLURAL;
import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber.SINGULAR;

import java.util.Iterator;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalAspect;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalDegree;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalMood;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalPerson;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalTense;
import de.tudarmstadt.ukp.jwktl.api.util.NonFiniteForm;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEWordFormHandler;

/**
 * Test case for {@link DEWordFormHandler}.
 * 
 * @author Christian M. Meyer
 */
public class DEWordFormHandlerTest extends DEWiktionaryEntryParserTest {

	/***/
	public void testAberration() throws Exception {
		IWiktionaryPage page = parse("Aberration.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Aberration", null, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("Aberrationen", null, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testHallo() throws Exception {
		IWiktionaryPage page = parse("Hallo.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("das Hallo", NOMINATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("die Hallos", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("des Hallos", GENITIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("der Hallos", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("dem Hallo", DATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("den Hallos", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("das Hallo", ACCUSATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("die Hallos", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testKiefer() throws Exception {
		IWiktionaryPage page = parse("Kiefer.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("die Kiefer", NOMINATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("die Kiefern", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("der Kiefer", GENITIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("der Kiefern", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("der Kiefer", DATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("den Kiefern", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("die Kiefer", ACCUSATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("die Kiefern", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
		entry = page.getEntry(1);
		actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Kiefer", null, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("Kiefer", null, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testStaat() throws Exception {
		IWiktionaryPage page = parse("Staat.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("der Staat", NOMINATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("die Staaten", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("die Stäte", NOMINATIVE, PLURAL, null, 2, actualIter.next());
		assertWordFormNoun("die Staat", NOMINATIVE, PLURAL, null, 3, actualIter.next());
		assertWordFormNoun("die Staate", NOMINATIVE, PLURAL, null, 4, actualIter.next());
		assertWordFormNoun("des Staates", GENITIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("des Staats", GENITIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("der Staaten", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("der Stäte", GENITIVE, PLURAL, null, 2, actualIter.next());
		assertWordFormNoun("der Staat", GENITIVE, PLURAL, null, 3, actualIter.next());
		assertWordFormNoun("der Staate", GENITIVE, PLURAL, null, 4, actualIter.next());
		assertWordFormNoun("dem Staate", DATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("dem Staat", DATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("den Staaten", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("den Stäten", DATIVE, PLURAL, null, 2, actualIter.next());
		assertWordFormNoun("den Staaten", DATIVE, PLURAL, null, 3, actualIter.next());
		assertWordFormNoun("den Staaten", DATIVE, PLURAL, null, 4, actualIter.next());
		assertWordFormNoun("den Staat", ACCUSATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("die Staaten", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("die Stäte", ACCUSATIVE, PLURAL, null, 2, actualIter.next());
		assertWordFormNoun("die Staat", ACCUSATIVE, PLURAL, null, 3, actualIter.next());
		assertWordFormNoun("die Staate", ACCUSATIVE, PLURAL, null, 4, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testThulium() throws Exception {
		IWiktionaryPage page = parse("Thulium.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("das Thulium", NOMINATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun(null, NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("des Thuliums", GENITIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun(null, GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("dem Thulium", DATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun(null, DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("das Thulium", ACCUSATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun(null, ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testTier() throws Exception {
		IWiktionaryPage page = parse("Tier.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("das Tier", NOMINATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("die Tiere", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("des Tier(e)s", GENITIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("der Tiere", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("dem Tier(e)", DATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("den Tieren", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("das Tier", ACCUSATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("die Tiere", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testTun() throws Exception {
		IWiktionaryPage page = parse("Tun.txt");
		IWiktionaryEntry entry = page.getEntry(1);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("das Tun", NOMINATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun(null, NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("des Tuns", GENITIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun(null, GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("dem Tun", DATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun(null, DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("das Tun", ACCUSATIVE, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun(null, ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testAngestellte() throws Exception {
		/*
		 * IWiktionaryPage page = parse("Angestellte.txt"); IWiktionaryEntry entry =
		 * page.getEntry(0); Iterator<IWiktionaryWordForm> actualIter =
		 * entry.getWordForms().iterator(); // Starke Deklination
		 * assertWordFormNoun("Angestellte", NOMINATIVE, SINGULAR, actualIter.next());
		 * assertWordFormNoun("Angestellte", NOMINATIVE, PLURAL, actualIter.next());
		 * assertWordFormNoun("Angestellter", GENITIVE, SINGULAR, actualIter.next());
		 * assertWordFormNoun("Angestellter", GENITIVE, PLURAL, actualIter.next());
		 * assertWordFormNoun("Angestellter", DATIVE, SINGULAR, actualIter.next());
		 * assertWordFormNoun("Angestellten", DATIVE, PLURAL, actualIter.next());
		 * assertWordFormNoun("Angestellte", ACCUSATIVE, SINGULAR, actualIter.next());
		 * assertWordFormNoun("Angestellte", ACCUSATIVE, PLURAL, actualIter.next()); //
		 * Schwache Deklination. assertWordFormNoun("die Angestellte", NOMINATIVE,
		 * SINGULAR, actualIter.next()); assertWordFormNoun("die Angestellten",
		 * NOMINATIVE, PLURAL, actualIter.next());
		 * assertWordFormNoun("der Angestellten", GENITIVE, SINGULAR,
		 * actualIter.next()); assertWordFormNoun("der Angestellten", GENITIVE, PLURAL,
		 * actualIter.next()); assertWordFormNoun("der Angestellten", DATIVE, SINGULAR,
		 * actualIter.next()); assertWordFormNoun("den Angestellten", DATIVE, PLURAL,
		 * actualIter.next()); assertWordFormNoun("die Angestellte", ACCUSATIVE,
		 * SINGULAR, actualIter.next()); assertWordFormNoun("die Angestellten",
		 * ACCUSATIVE, PLURAL, actualIter.next()); // Gemischte Deklination
		 * assertWordFormNoun("eine Angestellte", NOMINATIVE, SINGULAR,
		 * actualIter.next()); assertWordFormNoun("keine Angestellten", NOMINATIVE,
		 * PLURAL, actualIter.next()); assertWordFormNoun("einer Angestellten",
		 * GENITIVE, SINGULAR, actualIter.next());
		 * assertWordFormNoun("keiner Angestellten", GENITIVE, PLURAL,
		 * actualIter.next()); assertWordFormNoun("einer Angestellten", DATIVE,
		 * SINGULAR, actualIter.next()); assertWordFormNoun("keinen Angestellten",
		 * DATIVE, PLURAL, actualIter.next()); assertWordFormNoun("eine Angestellte",
		 * ACCUSATIVE, SINGULAR, actualIter.next());
		 * assertWordFormNoun("keine Angestellten", ACCUSATIVE, PLURAL,
		 * actualIter.next()); assertFalse(actualIter.hasNext());
		 */
		// TODO: weak and strong declination.
		// http://de.wiktionary.org/wiki/Kategorie:Wiktionary:Flexionstabelle_%28Deutsch%29
	}

	/***/
	public void testMitreissen() throws Exception {
		IWiktionaryPage page = parse("mitreissen.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormVerb("reiße mit", GrammaticalPerson.FIRST, GrammaticalTense.PRESENT, GrammaticalMood.INDICATIVE,
				SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("reißt mit", GrammaticalPerson.SECOND, GrammaticalTense.PRESENT, GrammaticalMood.INDICATIVE,
				SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("reißt mit", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, GrammaticalMood.INDICATIVE,
				SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("riss mit", GrammaticalPerson.FIRST, GrammaticalTense.PAST, GrammaticalMood.INDICATIVE,
				SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("mitgerissen", null, null, null, null, GrammaticalAspect.PERFECT, NonFiniteForm.PARTICIPLE,
				actualIter.next());
		assertWordFormVerb("risse mit", GrammaticalPerson.FIRST, GrammaticalTense.PAST, GrammaticalMood.CONJUNCTIVE,
				SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("reiß mit!", GrammaticalPerson.SECOND, null, GrammaticalMood.IMPERATIVE, SINGULAR, null,
				null, actualIter.next());
		assertWordFormVerb("reißt mit!", GrammaticalPerson.SECOND, null, GrammaticalMood.IMPERATIVE, PLURAL, null, null,
				actualIter.next());
		// Hilfsverb=haben
		// Weitere_Konjugationen= mitreißen (Konjugation)
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testReden() throws Exception {
		IWiktionaryPage page = parse("reden.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormVerb("rede", GrammaticalPerson.FIRST, GrammaticalTense.PRESENT, GrammaticalMood.INDICATIVE,
				SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("redest", GrammaticalPerson.SECOND, GrammaticalTense.PRESENT, GrammaticalMood.INDICATIVE,
				SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("redet", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, GrammaticalMood.INDICATIVE,
				SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("redete", GrammaticalPerson.FIRST, GrammaticalTense.PAST, GrammaticalMood.INDICATIVE,
				SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("geredet", null, null, null, null, GrammaticalAspect.PERFECT, NonFiniteForm.PARTICIPLE,
				actualIter.next());
		assertWordFormVerb("redete", GrammaticalPerson.FIRST, GrammaticalTense.PAST, GrammaticalMood.CONJUNCTIVE,
				SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("red", null, null, GrammaticalMood.IMPERATIVE, SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("rede", null, null, GrammaticalMood.IMPERATIVE, SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("redet", null, null, GrammaticalMood.IMPERATIVE, PLURAL, null, null, actualIter.next());
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

	/***/
	public void testGelb() throws Exception {
		IWiktionaryPage page = parse("Gelb.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Gelb", NOMINATIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Gelbs", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gelbtöne", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gelbs", GENITIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Gelbs", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gelbtöne", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gelb", DATIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Gelbs", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gelbtönen", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gelb", ACCUSATIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Gelbs", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gelbtöne", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testMaerz() throws Exception {
		IWiktionaryPage page = parse("Maerz.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("März", NOMINATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Märze", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("März", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Märzes", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Märzen", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Märze", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("März", DATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Märzen", DATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Märzen", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("März", ACCUSATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Märze", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testMockumentary() throws Exception {
		IWiktionaryPage page = parse("Mockumentary.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Mockumentary", NOMINATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Mockumentary", NOMINATIVE, SINGULAR, FEMININE, 2, actualIter.next());
		assertWordFormNoun("Mockumentary", NOMINATIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Mockumentarys", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Mockumentary", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Mockumentary", GENITIVE, SINGULAR, FEMININE, 2, actualIter.next());
		assertWordFormNoun("Mockumentarys", GENITIVE, SINGULAR, FEMININE, 2, actualIter.next());
		assertWordFormNoun("Mockumentary", GENITIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Mockumentarys", GENITIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Mockumentarys", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Mockumentary", DATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Mockumentary", DATIVE, SINGULAR, FEMININE, 2, actualIter.next());
		assertWordFormNoun("Mockumentary", DATIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Mockumentarys", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Mockumentary", ACCUSATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Mockumentary", ACCUSATIVE, SINGULAR, FEMININE, 2, actualIter.next());
		assertWordFormNoun("Mockumentary", ACCUSATIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Mockumentarys", ACCUSATIVE, PLURAL, null, 1, actualIter.next());

		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testKeks() throws Exception {
		IWiktionaryPage page = parse("Keks.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Keks", NOMINATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Keks", NOMINATIVE, SINGULAR, NEUTER, 2, actualIter.next());
		assertWordFormNoun("Kekse", NOMINATIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Kekses", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Kekses", GENITIVE, SINGULAR, NEUTER, 2, actualIter.next());
		assertWordFormNoun("Kekse", GENITIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Keks", DATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Kekse", DATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Keks", DATIVE, SINGULAR, NEUTER, 2, actualIter.next());
		assertWordFormNoun("Kekse", DATIVE, SINGULAR, NEUTER, 2, actualIter.next());
		assertWordFormNoun("Keksen", DATIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Keks", ACCUSATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Keks", ACCUSATIVE, SINGULAR, NEUTER, 2, actualIter.next());
		assertWordFormNoun("Kekse", ACCUSATIVE, PLURAL, null, 1, actualIter.next());

		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testFreischurf() throws Exception {
		IWiktionaryPage page = parse("Freischurf.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Freischurf", NOMINATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Freischürfe", NOMINATIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Freischurfes", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Freischurfs", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Freischürfe", GENITIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Freischurf", DATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Freischürfen", DATIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Freischurf", ACCUSATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Freischürfe", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	// Here we have a problem. We can't group declination by gender
	/***/
	public void testFels() throws Exception {
		IWiktionaryPage page = parse("Fels.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Fels", NOMINATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Fels", NOMINATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Felsen", NOMINATIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Fels", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Felses", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Felsens", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Felsen", GENITIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Felsen", GENITIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Fels", DATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Felsen", DATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Felsen", DATIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Fels", ACCUSATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Felsen", ACCUSATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Felsen", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testUnschlitt() throws Exception {
		IWiktionaryPage page = parse("Unschlitt.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Unschlitt", NOMINATIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Unschlitt", NOMINATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Unschlitte", NOMINATIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Unschlitts", GENITIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Unschlittes", GENITIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Unschlitts", GENITIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Unschlittes", GENITIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Unschlitte", GENITIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Unschlitt", DATIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Unschlitt", DATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Unschlitten", DATIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Unschlitt", ACCUSATIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Unschlitt", ACCUSATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Unschlitte", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testFote() throws Exception {
		IWiktionaryPage page = parse("Fote.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("de Fote", null, SINGULAR, null, 1, actualIter.next());
		assertWordFormNoun("de Foten", null, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testGeneraladmiral() throws Exception {
		IWiktionaryPage page = parse("Generaladmiral.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Generaladmiral", NOMINATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Generaladmirale", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Generaladmiräle", NOMINATIVE, PLURAL, null, 2, actualIter.next());

		assertWordFormNoun("Generaladmirals", GENITIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Generaladmirale", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Generaladmiräle", GENITIVE, PLURAL, null, 2, actualIter.next());

		assertWordFormNoun("Generaladmiral", DATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Generaladmirale", DATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Generaladmiralen", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Generaladmirälen", DATIVE, PLURAL, null, 2, actualIter.next());

		assertWordFormNoun("Generaladmiral", ACCUSATIVE, SINGULAR, MASCULINE, 1, actualIter.next());
		assertWordFormNoun("Generaladmirale", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Generaladmiräle", ACCUSATIVE, PLURAL, null, 2, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testGams() throws Exception {
		IWiktionaryPage page = parse("Gams.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Gams", NOMINATIVE, SINGULAR, FEMININE, 1, actualIter.next());
		assertWordFormNoun("Gams", NOMINATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Gams", NOMINATIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Gams", GENITIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Gams", GENITIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Gamsen", GENITIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Gamsen", DATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Gamsen", ACCUSATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Gamsen", GENITIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Gamsen", DATIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Gamsen", ACCUSATIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Gams", DATIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Gams", DATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Gams", ACCUSATIVE, SINGULAR, NEUTER, 3, actualIter.next());
		assertWordFormNoun("Gams", ACCUSATIVE, SINGULAR, MASCULINE, 2, actualIter.next());
		assertWordFormNoun("Gams", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gamsen", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gams", GENITIVE, SINGULAR, FEMININE, 1, actualIter.next());
		assertWordFormNoun("Gams", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gamsen", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gams", DATIVE, SINGULAR, FEMININE, 1, actualIter.next());
		assertWordFormNoun("Gams", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gamsen", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gams", ACCUSATIVE, SINGULAR, FEMININE, 1, actualIter.next());
		assertWordFormNoun("Gams", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Gamsen", ACCUSATIVE, PLURAL, null, 1, actualIter.next());
		assertFalse(actualIter.hasNext());
	}
	
	/***/
	public void testApfelschorle() throws Exception {
		IWiktionaryPage page = parse("Apfelschorle.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("Apfelschorle", NOMINATIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Apfelschorles", NOMINATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Apfelschorles", GENITIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Apfelschorles", GENITIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Apfelschorle", DATIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Apfelschorles", DATIVE, PLURAL, null, 1, actualIter.next());
		assertWordFormNoun("Apfelschorle", ACCUSATIVE, SINGULAR, NEUTER, 1, actualIter.next());
		assertWordFormNoun("Apfelschorles", ACCUSATIVE, PLURAL, null, 1, actualIter.next());

		assertWordFormNoun("Apfelschorle", NOMINATIVE, SINGULAR, FEMININE, 5, actualIter.next());
		assertWordFormNoun("Apfelschorlen", NOMINATIVE, PLURAL, null, 5, actualIter.next());
		assertWordFormNoun("Apfelschorle", GENITIVE, SINGULAR, FEMININE, 5, actualIter.next());
		assertWordFormNoun("Apfelschorlen", GENITIVE, PLURAL, null, 5, actualIter.next());
		assertWordFormNoun("Apfelschorle", DATIVE, SINGULAR, FEMININE, 5, actualIter.next());
		assertWordFormNoun("Apfelschorlen", DATIVE, PLURAL, null, 5, actualIter.next());
		assertWordFormNoun("Apfelschorle", ACCUSATIVE, SINGULAR, FEMININE, 5, actualIter.next());
		assertWordFormNoun("Apfelschorlen", ACCUSATIVE, PLURAL, null, 5, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	protected void assertWordFormNoun(final String expectedForm, final GrammaticalCase expectedCase,
			final GrammaticalNumber expectedNumber, GrammaticalGender expectedGender,
			int expectedInflectionGroup, final IWiktionaryWordForm actual) {
		assertEquals(expectedForm, actual.getWordForm());
		assertEquals(expectedCase, actual.getCase());
		assertEquals(expectedNumber, actual.getNumber());
		assertEquals(expectedGender, actual.getGender());
		assertEquals(expectedInflectionGroup, actual.getInflectionGroup());
	}

	protected void assertWordFormVerb(final String expectedForm, final GrammaticalPerson expectedPerson,
			final GrammaticalTense expectedTense, final GrammaticalMood expectedMood,
			final GrammaticalNumber expectedNumber, final GrammaticalAspect expectedAspect,
			final NonFiniteForm expectedNonFiniteForm, final IWiktionaryWordForm actual) {
		assertEquals(expectedForm, actual.getWordForm());
		assertEquals(expectedPerson, actual.getPerson());
		assertEquals(expectedTense, actual.getTense());
		assertEquals(expectedMood, actual.getMood());
		assertEquals(expectedNumber, actual.getNumber());
		assertEquals(expectedAspect, actual.getAspect());
		assertEquals(expectedNonFiniteForm, actual.getNonFiniteForm());
	}

	protected void assertWordFormAdjective(final String expectedForm, final GrammaticalDegree expectedDegree,
			final IWiktionaryWordForm actual) {
		assertEquals(expectedForm, actual.getWordForm());
		assertEquals(expectedDegree, actual.getDegree());
	}

}
