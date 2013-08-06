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
package de.tudarmstadt.ukp.jwktl.api;

import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalAspect;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalDegree;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalMood;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalPerson;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalTense;
import de.tudarmstadt.ukp.jwktl.api.util.NonFiniteForm;

/**
 * Represents an (inflected) word form of a {@link IWiktionaryEntry}. The 
 * class can be used to model any different type of modification of a word
 * including verb conjugation and noun/adjective declension. For the former,
 * both finite and non-finite verb forms may be represented. Certain inflected
 * word forms usually found in a dictionary are represented by a combination 
 * of the grammatical properties.
 * <p>Common English word forms</p><ul>
 * <li>Noun: 
 *   number = {SINGULAR, PLURAL}</li>
 * <li>Verb, third person singular, present tense: 
 *   person = THIRD, number = SINGULAR, tense = PRESENT</li>
 * <li>Verb, present participle:
 *   tense = PRESENT, nonFiniteForm = PARTICIPLE</li>
 * <li>Verb, simple past:
 *   tense = PAST</li>
 * <li>Verb, past participle:
 *   tense = PAST, nonFiniteForm = PARTICIPLE</li>
 * <li>Adjective/Adverb:
 *   degree = {POSITIVE, COMPARABLE, SUPERLATIVE}</li></ul>
 * <p>Common German word forms</p><ul>
 * <li>Noun: 
 *   number = {SINGULAR, PLURAL}, case = {NOMINATIVE, GENITIVE, DATIVE, ACCUSATIVE}</li>
 * <li>Verb, present tense: 
 *   mood = INDICATIVE, person = {FIRST, SECOND, THIRD}, number = {SINGULAR, PLURAL}, tense = PRESENT</li>
 * <li>Verb, past tense:
 *   mood = INDICATIVE, person = {FIRST, SECOND, THIRD}, number = {SINGULAR, PLURAL}, tense = PAST</li>
 * <li>Verb, imperative:
 *   mood = IMPERATIVE, person = {FIRST, SECOND, THIRD}, number = {SINGULAR, PLURAL}</li>
 * <li>Verb, conjunctive present (Konjunktiv I):
 *   mood = CONJUNCTIVE, tense = PRESENT</li>
 * <li>Verb, conjunctive past (Konjunktiv II):
 *   mood = CONJUNCTIVE, tense = PAST</li>
 * <li>Verb, present participle (Partizip I):
 *   aspect = IMPERFECT, nonFiniteForm = PARTICIPLE</li>
 * <li>Verb, perfect participle (Partizip II):
 *   aspect = PERFECT, nonFiniteForm = PARTICIPLE</li>
 * <li>Adjective/Adverb:
 *   degree = {POSITIVE, COMPARABLE, SUPERLATIVE}</li></ul>
 * @author Christian M. Meyer
 */
public interface IWiktionaryWordForm {

	/** Returns the word form or <code>null</code> if no such word form
	 *  exists (but specified). An example for the latter is the English noun
	 *  "information", which does not have a plural form. */
	public String getWordForm();
	
	/** Returns the {@link GrammaticalNumber} of this word form or 
	 *  <code>null</code> if no number is specified or applicable. */
	public GrammaticalNumber getNumber();
	
	/** Returns the {@link GrammaticalCase} of this word form or 
	 *  <code>null</code> if no case is specified or applicable. */
	public GrammaticalCase getCase();
	
	/** Returns the {@link GrammaticalPerson} of this word form or 
	 *  <code>null</code> if no person is specified or applicable. */
	public GrammaticalPerson getPerson();
	
	/** Returns the {@link GrammaticalTense} of this word form or 
	 *  <code>null</code> if no tense is specified or applicable. */
	public GrammaticalTense getTense();
	
	/** Returns the {@link GrammaticalMood} of this word form or 
	 *  <code>null</code> if no mood is specified or applicable. */
	public GrammaticalMood getMood();
	
	/** Returns the {@link GrammaticalDegree} of this word form or 
	 *  <code>null</code> if no degree is specified or applicable. */
	public GrammaticalDegree getDegree();
	
	/** Returns the {@link GrammaticalAspect} of this word form or 
	 *  <code>null</code> if no aspect is specified or applicable. */
	public GrammaticalAspect getAspect();
	
	/** Returns the {@link NonFiniteForm} type of this word form or 
	 *  <code>null</code> if no type is specified or applicable. */
	public NonFiniteForm getNonFiniteForm();
	
}
