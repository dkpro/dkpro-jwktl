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

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;


/**
 * Generic representation of the parts of speech used in Wiktionary. The 
 * part of speech is defined for a certain {@link IWiktionaryEntry}.
 * @see IWiktionaryEntry#getPartOfSpeech() 
 * @author Christian M. Meyer
 * @author Christof Müller
 * @author Lizhen Qu
 */
public enum PartOfSpeech {

	/** Noun. */
	NOUN,
	/** Proper noun (names, locations, organizations) */
	PROPER_NOUN,
	/** First/given name (e.g. Nadine). */
	FIRST_NAME,
	/** Last/family name (e.g. Miller). */
	LAST_NAME,
	/** Toponym (i.e., a place name). */
	TOPONYM,

	/** Only takes the singular form. */
	SINGULARE_TANTUM,
	/** Only takes the plural form. */
	PLURALE_TANTUM,
	
	/** Measure words (e.g., litre). */
	MEASURE_WORD,
	
	/** Verb. */
	VERB,
	/** Auxiliary verb (can, might, must, etc.). */
	AUXILIARY_VERB,
	
	/** Adjective. */
	ADJECTIVE,
	/** Adverb. */
	ADVERB,
	
	/** Interjection. */
	INTERJECTION,
	/** Salutation (e.g., good afternoon). */
	SALUTATION,
	/** Onomatopoeia (e.g., peng, tic-tac). */
	ONOMATOPOEIA,
	
	/** Phrase. */
	PHRASE,
	/** Idiom (e.g., rock 'n' roll). */
	IDIOM,
	/** Collocation (e.g., strong tea). */
	COLLOCATION,
	/** Proverb (e.g., that's the way life is). */
	PROVERB,
	/** Mnemonic (e.g., "My Very Educated Mother Just Served Us Nachos"
	 *  for planet names). */
	MNEMONIC,
	
	/** Pronoun. */
	PRONOUN,
	/** (Irreflexive) personal pronoun (I, you, he, she, we, etc.). */
	PERSONAL_PRONOUN,
	/** Reflexive personal pronoun (myself, herself, ourselves, etc.). */
	REFLEXIVE_PRONOUN,
	/** Possessive pronoun (mine, your, our, etc.). */
	POSSESSIVE_PRONOUN,	
	/** Demonstrative pronoun (_This_ is fast). */
	DEMONSTRATIVE_PRONOUN,
	/** Relative pronoun (She sold the car, _which_ was very old ). */
	RELATIVE_PRONOUN,
	/** Indefinite pronoun (_Nobody_ bought the car ). */
	INDEFINITE_PRONOUN,

	/** Interrogative pronoun (who, what, etc.). */ 
	INTERROGATIVE_PRONOUN,	
	/** Interrogative adverb (how, when, etc.). */ 
	INTERROGATIVE_ADVERB,
	
	/** Particle. */
	PARTICLE,
	/** Answer particle (yes, no, etc.). */
	ANSWERING_PARTICLE,
	/** Negative particle (neither...nor, etc.). */
	NEGATIVE_PARTICLE,
	/** Comparative particle (She is taller _than_ me). */
	COMPARATIVE_PARTICLE,
	/** Focus particle (also, only, even, etc.). */
	FOCUS_PARTICLE,
	/** Intensifying particle (very, low, etc.). */
	INTENSIFYING_PARTICLE,
	/** Modal particle (express attitude, e.g., German: Sprich _doch mal_ mit ihr ). */
	MODAL_PARTICLE,
	
	/** Article (a, the, etc.). */
	ARTICLE,
	/** Determiner (few, most, etc.). */
	DETERMINER,

	/** Abbreviation. */
	ABBREVIATION,
	/** Acronym (pronounced as a word, e.g., "ROM", "NATO", "sonar") */
	ACRONYM,
	/** Initialism (pronounced as letter by letter, e.g., "CD", "URL") */
	INITIALISM,
	/** Contraction (e.g., it's). */ 
	CONTRACTION,
	
	/** Conjunction (and, or, etc.). */
	CONJUNCTION,
	/** Subordinating conjunction (as soon as, after, etc.). */
	SUBORDINATOR,	

	/** Preposition (e.g., underneath). */
	PREPOSITION,
	/** Postposition (e.g., ago). */
	POSTPOSITION,
	
	/** Affix. */
	AFFIX,
	/** Prefix. */
	PREFIX,
	/** Suffix. */
	SUFFIX,
	/** Place name suffix (e.g., -burg). */
	PLACE_NAME_ENDING,
	/** Bound lexeme. */
	LEXEME,

	/** Character. */
	CHARACTER,
	/** Letter of the alphabet (A, B, C, etc.). */
	LETTER,
	/** Number and numeral (e.g., two, fifteen, etc.). */
	NUMBER,
	/** Number and numeral (e.g., two, fifteen, etc.). */
	NUMERAL,
	/** Punctuation mark (., ?, ;, etc.). */
	PUNCTUATION_MARK,
	/** Symbol (+, §, $, etc.). */
	SYMBOL,
	/** Chinese Hanzi character. */
	HANZI,
	/** Japanese Kanji character. */
	KANJI,
	/** Japanese Katakana character. */
	KATAKANA,
	/** Japanese Hiragana character. */
	HIRAGANA,
	
	/** Gismu (a root word in Lojban). */
	GISMU,
	
	/** Inflected word form. */
	WORD_FORM,
	/** Participle. */
	PARTICIPLE,
	/** Transliterated word form. */
	TRANSLITERATION,
	
	/** @deprecated No longer used. */
	@Deprecated
	COMBINING_FORM,
	/** @deprecated No longer used. */
	@Deprecated
	EXPRESSION,
	/** @deprecated No longer used. */
	@Deprecated
	NOUN_PHRASE;
	

	private static final Logger logger = Logger.getLogger(PartOfSpeech.class.getName());
	
	protected static Set<String> unknownPos;
		
	/** Find the part of speech with the given name. The method only for its
	 *  canonical English name. Use {@link #findByName(String, Map)} for 
	 *  language-specific lookup. If no part of speech could be found,
	 *  <code>null</code> is returned. */
	public static PartOfSpeech findByName(final String name) {
		return findByName(name, null);
	}

	/** Find the part of speech with the given name. The method checks both 
	 *  for the canonical English name as well as alternative names in 
	 *  other languages, which can be specified by passing a custom 
	 *  additional map. If no part of speech could be found,
	 *  <code>null</code> is returned. */
	public static PartOfSpeech findByName(final String name, 
			final Map<String, PartOfSpeech> additionalMap) {
		if (name == null || name.isEmpty())
			return null;
		
		StringBuilder label = new StringBuilder();
		for (char p : name.trim().toCharArray())
			if (p == ' ' || p == '\n' || p == '\r' || p == '\t')
				label.append('_');
			else
				label.append(Character.toUpperCase(p));
		
		PartOfSpeech result = null;
		if (additionalMap != null)
			result = additionalMap.get(label.toString());
		
		if (result == null)
			try {
				result = PartOfSpeech.valueOf(label.toString());
			} catch (IllegalArgumentException e) {
				logger.finer("Unknown part of speech: " + label.toString());
			}
		
		/*if (result == null) {
			if (unknownPos == null)
				unknownPos = new TreeSet<String>();
			if (!unknownPos.contains(label.toString())) {
				unknownPos.add(label.toString());
				System.err.println("Unknown part of speech: " + label.toString());
			}
		}*/
		return result;
	}
	
	/** Tests if the specified parts of speech are equal. The method returns 
	 * <code>true</code> if both parts of speech are <code>null</code>, but 
	 * <code>false</code> if only one of them is <code>null</code>. */
	public static boolean equals(final PartOfSpeech partOfSpeech1, 
			final PartOfSpeech partOfSpeech2) {
		if (partOfSpeech1 == partOfSpeech2)
			return true;
		else
		if (partOfSpeech1 == null || partOfSpeech2 == null)
			return false;
		else
			return partOfSpeech1.equals(partOfSpeech2);
	}
	
}
