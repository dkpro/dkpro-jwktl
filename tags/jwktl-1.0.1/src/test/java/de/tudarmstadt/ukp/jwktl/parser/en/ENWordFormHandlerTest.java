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
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalAspect;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalDegree;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalMood;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalPerson;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalTense;
import de.tudarmstadt.ukp.jwktl.api.util.NonFiniteForm;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENWordFormHandler;

/**
 * Test case for {@link ENWordFormHandler}.
 * @author Christian M. Meyer
 */
public class ENWordFormHandlerTest extends ENWiktionaryEntryParserTest {

	/***/
	public void testNounExamples() throws Exception {
		// See http://en.wiktionary.org/wiki/Template:en-noun
		ENWordFormHandler handler = new ENWordFormHandler("noun");
		handler.parse("{{en-noun}}");
		Iterator<IWiktionaryWordForm> actualIter = handler.getWordForms().iterator();
		assertWordFormNoun("nouns", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
		
		handler = new ENWordFormHandler("noun");
		handler.parse("{{en-noun|s}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("nouns", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("church");
		handler.parse("{{en-noun|es}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("churches", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
		
		handler = new ENWordFormHandler("belfry");
		handler.parse("{{en-noun|belfr|ies}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("belfries", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("awe");
		handler.parse("{{en-noun|-}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun(null, GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("beer");
		handler.parse("{{en-noun|s|-}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("beers", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("rain");
		handler.parse("{{en-noun|-|s}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("rains", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("greenery");
		handler.parse("{{en-noun|-|greeneries}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("greeneries", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("abliguration");
		handler.parse("{{en-noun|!}}");
		actualIter =  handler.getWordForms().iterator();	
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("abliguration");
		handler.parse("{{en-noun|!|s}}");
		actualIter =  handler.getWordForms().iterator();	
		assertFalse(actualIter.hasNext());
		
		handler = new ENWordFormHandler("tuchus");
		handler.parse("{{en-noun|?}}");
		actualIter =  handler.getWordForms().iterator();
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("tuchus");
		handler.parse("{{en-noun|?|s}}");
		actualIter =  handler.getWordForms().iterator();
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("noun");
		handler.parse("{{en-noun|s|?}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("nouns", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("bass");
		handler.parse("{{en-noun|es|pl2=bass}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("bass", GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("basses", GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("seraph");
		handler.parse("{{en-noun|pl=seraphs|pl2=seraphim}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("seraphs", GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("seraphim", GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("hot dog");
		handler.parse("{{en-noun|sg=[[hot]] [[dog]]}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("hot dogs", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("shoe polish");
		handler.parse("{{en-noun|sg=[[shoe]] [[polish]]|es}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("shoe polishes", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("chainman");
		handler.parse("{{en-noun|sg=[[chain]][[man]]|pl=chainmen}}");
		actualIter =  handler.getWordForms().iterator();
		assertWordFormNoun("chainmen", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());		
	}

	/***/
	public void testProperNounExamples() throws Exception {
		// See http://en.wiktionary.org/wiki/Template:en-proper_noun
		ENWordFormHandler handler = new ENWordFormHandler("Alan");
		handler.parse("{{en-proper noun}}");
		Iterator<IWiktionaryWordForm> actualIter = handler.getWordForms().iterator();
		assertWordFormNoun(null, GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
		
		handler = new ENWordFormHandler("Wiktionarian");
		handler.parse("{{en-proper noun|s}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormNoun("Wiktionarians", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("Ally");
		handler.parse("{{en-proper noun|All|ies}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormNoun("Allies", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());		
		handler = new ENWordFormHandler("Superman");
		handler.parse("{{en-proper noun|Supermen}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormNoun("Supermen", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("America");
		handler.parse("{{en-proper noun|s|-}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormNoun("Americas", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("Snuffaluphogus");
		handler.parse("{{en-proper noun|es|pl2=Snuffaluphogi|pl3=Snuffaluphogae}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormNoun("Snuffaluphoguses", GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("Snuffaluphogi", GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("Snuffaluphogae", GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("Ancient Fuzzy Die");
		handler.parse("{{en-proper noun|head=[[ancient|Ancient]] [[fuzzy|Fuzzy]] [[die|Die]]|Depending on meaning, either '''Ancient Fuzzy Die''' or '''[[Ancient Fuzzy Dice]]'''}}");
		actualIter = handler.getWordForms().iterator();
//		assertWordFormNoun("Depending on meaning, either '''Ancient Fuzzy Die''' or '''[[Ancient Fuzzy Dice]]'''", GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
	}
	
	/***/
	public void testVerbExamples() throws Exception {
		// See http://en.wiktionary.org/wiki/Template:en-verb
		ENWordFormHandler handler = new ENWordFormHandler("verb");
		handler.parse("{{en-verb}}");
		Iterator<IWiktionaryWordForm> actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("verbs", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("verbing", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("verbed", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("verbed", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());
		
		handler = new ENWordFormHandler("buzz");
		handler.parse("{{en-verb|buzz|es}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("buzzes", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("buzzing", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("buzzed", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("buzzed", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("dye");
		handler.parse("{{en-verb|dye|d}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("dyes", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("dyeing", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("dyed", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("dyed", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("admire");
		handler.parse("{{en-verb|admir|ing}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("admires", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("admiring", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("admired", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("admired", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("bus");
		handler.parse("{{en-verb|bus|s|es}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("busses", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("bussing", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("bussed", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("bussed", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("cry");
		handler.parse("{{en-verb|cr|i|ed}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("cries", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("crying", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("cried", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("cried", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("tie");
		handler.parse("{{en-verb|t|y|ing}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("ties", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("tying", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("tied", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("tied", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("trek");
		handler.parse("{{en-verb|trek|k|ed}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("treks", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("trekking", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("trekked", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("trekked", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());
		
		handler = new ENWordFormHandler("set");
		handler.parse("{{en-verb|sets|setting|set}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("sets", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("setting", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("set", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("set", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("do");
		handler.parse("{{en-verb|does|doing|did|done}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("does", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("doing", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("did", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("done", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());
		
		handler = new ENWordFormHandler("tie");
		handler.parse("{{en-verb|ties|'''[[tying]]''' or '''[[tieing]]'''|tied}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("ties", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb("'''[[tying]]''' or '''[[tieing]]'''", null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("tied", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb("tied", null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("can");
		handler.parse("{{en-verb| inf= - | can | - | could | - }}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormVerb("can", GrammaticalPerson.THIRD, GrammaticalTense.PRESENT, null, GrammaticalNumber.SINGULAR, null, null, actualIter.next());
		assertWordFormVerb(null, null, GrammaticalTense.PRESENT, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertWordFormVerb("could", null, GrammaticalTense.PAST, null, null, null, null, actualIter.next());
		assertWordFormVerb(null, null, GrammaticalTense.PAST, null, null, null, NonFiniteForm.PARTICIPLE, actualIter.next());
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testAdjectiveExamples() throws Exception {
		// See http://en.wiktionary.org/wiki/Template:en-adjective
		ENWordFormHandler handler = new ENWordFormHandler("beautiful");
		handler.parse("{{en-adj}}");
		Iterator<IWiktionaryWordForm> actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("beautiful", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("more beautiful", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("most beautiful", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());
		
		handler = new ENWordFormHandler("tall");
		handler.parse("{{en-adj|er}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("tall", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("taller", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("tallest", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());
		
		handler = new ENWordFormHandler("pretty");
		handler.parse("{{en-adj|pretti|er}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("pretty", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("prettier", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("prettiest", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("late");
		handler.parse("{{en-adj|lat|er}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("late", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("later", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("latest", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());
		
		handler = new ENWordFormHandler("good");
		handler.parse("{{en-adj|better|best}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("good", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("better", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("best", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("annual");
		handler.parse("{{en-adj|-}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("annual", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective(null, GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective(null, GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("abject");
		handler.parse("{{en-adj|er|more}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("abject", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("abjecter", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("more abject", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("abjectest", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertWordFormAdjective("most abject", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("little known");
		handler.parse("{{en-adj|head=[[little]] [[known]]|depending on meaning either '''[[lesser]] known''' or '''[[littler]] known'''|least known or [[littlest]] known}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("little known", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("depending on meaning either '''[[lesser]] known''' or '''[[littler]] known'''", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("least known or [[littlest]] known", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("funky");
		handler.parse("{{en-adj|funkier}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("funky", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("funkier", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("most funky", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("funky");
		handler.parse("{{en-adj|-|funkier}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("funky", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("funkier", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("most funky", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());
		handler = new ENWordFormHandler("abject");
		handler.parse("{{en-adj|-|er|more}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("abject", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("abjecter", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("more abject", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("abjectest", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertWordFormAdjective("most abject", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());

		handler = new ENWordFormHandler("Afrikaans");
		handler.parse("{{en-adj|?}}");
		actualIter = handler.getWordForms().iterator();
		assertFalse(actualIter.hasNext());
				
		handler = new ENWordFormHandler("adjective");
		handler.parse("{{en-adj|-|-}}");
		actualIter = handler.getWordForms().iterator();
		assertWordFormAdjective("adjective", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective(null, GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective(null, GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext());
	}
	
	/***/
	public void testBass() throws Exception {
		IWiktionaryPage page = parse("bass.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormAdjective("bass", GrammaticalDegree.POSITIVE, actualIter.next());
		assertWordFormAdjective("more bass", GrammaticalDegree.COMPARATIVE, actualIter.next());
		assertWordFormAdjective("most bass", GrammaticalDegree.SUPERLATIVE, actualIter.next());
		assertFalse(actualIter.hasNext()); 
		
		entry = page.getEntry(1);
		actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("basses", GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());

		entry = page.getEntry(2);
		actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("bass", GrammaticalNumber.PLURAL, actualIter.next());
		assertWordFormNoun("basses", GrammaticalNumber.PLURAL, actualIter.next());
		assertFalse(actualIter.hasNext());
	}
	
	/***/
	public void testBoat() throws Exception {
		IWiktionaryPage page = parse("boat.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("boats", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
	}

	/***/
	public void testCow() throws Exception {
		IWiktionaryPage page = parse("cow.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();		
		assertWordFormNoun("'''[[cows]]''', '''[[cattle]]''' ''or'' '''[[kine]]''' (''archaic'')", GrammaticalNumber.PLURAL, actualIter.next());
//		assertWordFormNoun("cows", GrammaticalNumber.PLURAL, actualIter.next());
//		assertWordFormNoun("cattle", GrammaticalNumber.PLURAL, actualIter.next());
//		assertWordFormNoun("kine", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
	}
	
	/***/
	public void testDictionary() throws Exception {
		IWiktionaryPage page = parse("dictionary.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryWordForm> actualIter = entry.getWordForms().iterator();
		assertWordFormNoun("dictionaries", GrammaticalNumber.PLURAL, actualIter.next());		
		assertFalse(actualIter.hasNext());
	}
	
	
	protected void assertWordFormNoun(final String expectedForm, 
			final GrammaticalNumber expectedNumber,
			final IWiktionaryWordForm actual) {
		assertEquals(expectedForm, actual.getWordForm());
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
