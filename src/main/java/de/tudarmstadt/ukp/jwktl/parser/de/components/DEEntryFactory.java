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
package de.tudarmstadt.ukp.jwktl.parser.de.components;

import java.util.Map;
import java.util.TreeMap;

import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.parser.entry.EntryFactory;

/**
 * <p>Extract POS and inflections.
 * 
 *
 */
public class DEEntryFactory extends EntryFactory {
		
	/**
	 * Default constructor. Init embedded language factory and extend it to allow mapping between German name 
	 * and English language name. Two approaches of mapping is utilized. One is to load a mapping file located in lang/language_de.properties.
	 * Another is based on the consideration that two languages should have the same language codes. So the two language code file langugage_code.en and language_code.de are used to
	 * map two language names with identical code together.
	 */
	public DEEntryFactory() {
		super();
	}
	
	public PartOfSpeech findPartOfSpeech(final String name) {
		return PartOfSpeech.findByName(name, posMap);
	}
	
	protected static final Map<String, PartOfSpeech> posMap;
	
	static {
		posMap = new TreeMap<String, PartOfSpeech>();
		posMap.put("ABKÜRZUNG", PartOfSpeech.ABBREVIATION);
		posMap.put("ABTRENNBARE_VERBPARTIKEL", PartOfSpeech.PARTICLE);
		posMap.put("ADJEKTIV", PartOfSpeech.ADJECTIVE);
		posMap.put("ADVERB", PartOfSpeech.ADVERB);
		posMap.put("AFFIX", PartOfSpeech.AFFIX);
		posMap.put("ANTWORTPARTIKEL", PartOfSpeech.ANSWERING_PARTICLE);
		posMap.put("ARTIKEL", PartOfSpeech.ARTICLE);
		posMap.put("BUCHSTABE", PartOfSpeech.LETTER);
		posMap.put("COGNOMEN", PartOfSpeech.LAST_NAME);
		posMap.put("DEKLINIERTE_FORM", PartOfSpeech.WORD_FORM);
		posMap.put("DEKLINIERTE_FORM/KONJUGIERTE_FORM", PartOfSpeech.WORD_FORM);
		posMap.put("DEMONSTRATIVPRONOMEN", PartOfSpeech.DEMONSTRATIVE_PRONOUN);
		posMap.put("EIGENNAME", PartOfSpeech.PROPER_NOUN);
		posMap.put("ERWEITERTER_INFINITIV", PartOfSpeech.WORD_FORM);
		posMap.put("FOKUSPARTIKEL", PartOfSpeech.FOCUS_PARTICLE);
		posMap.put("FRAGEPRONOMEN", PartOfSpeech.INTERROGATIVE_PRONOUN);
		posMap.put("GEBUNDENES_LEXEM", PartOfSpeech.LEXEME);
		posMap.put("GEFLÜGELTES_WORT", PartOfSpeech.IDIOM);
		posMap.put("GERUNDIUM", PartOfSpeech.WORD_FORM);
		posMap.put("GRADPARTIKEL", PartOfSpeech.INTENSIFYING_PARTICLE);
		posMap.put("GRUßFORMEL", PartOfSpeech.SALUTATION);
		posMap.put("HALBPRÄFIX", PartOfSpeech.LEXEME);		
		posMap.put("HANZI", PartOfSpeech.HANZI);
		posMap.put("HILFSVERB", PartOfSpeech.AUXILIARY_VERB);
		posMap.put("HIRAGANA", PartOfSpeech.HIRAGANA);
		posMap.put("INDEFINITARTIKEL", PartOfSpeech.ARTICLE);
		posMap.put("INDEFINITPRONOMEN", PartOfSpeech.INDEFINITE_PRONOUN);
		posMap.put("INTERJEKTION", PartOfSpeech.INTERJECTION);
		posMap.put("INTERROGATIVADVERB", PartOfSpeech.INTERROGATIVE_ADVERB);
		posMap.put("INTERROGATIVPRONOMEN", PartOfSpeech.INTERROGATIVE_PRONOUN);
		posMap.put("KANJI", PartOfSpeech.KANJI);
		posMap.put("KATAKANA", PartOfSpeech.KATAKANA);
		posMap.put("KONJUGIERTE_FORM", PartOfSpeech.WORD_FORM);
		posMap.put("KONJUKTION", PartOfSpeech.CONJUNCTION);
		posMap.put("KONJUNKTION", PartOfSpeech.CONJUNCTION);
		posMap.put("KONJUNKTIONALADVERB", PartOfSpeech.ADVERB);
		posMap.put("KOMPARATIV", PartOfSpeech.WORD_FORM);
		posMap.put("LATINISIERTES_PATRONYMIKON", PartOfSpeech.LAST_NAME);
		posMap.put("LEXEM", PartOfSpeech.LEXEME);
		posMap.put("MERKSPRUCH", PartOfSpeech.MNEMONIC);
		posMap.put("MODALPARTIKEL", PartOfSpeech.MODAL_PARTICLE);
		posMap.put("NACHNAME", PartOfSpeech.LAST_NAME);
		posMap.put("NEGATIONSPARTIKEL", PartOfSpeech.NEGATIVE_PARTICLE);
		posMap.put("NOMEN", PartOfSpeech.NOUN);
		posMap.put("NUMERALE", PartOfSpeech.NUMERAL);
		posMap.put("NUMERUS", PartOfSpeech.NUMERAL);
		posMap.put("ONOMATOPOETIKUM", PartOfSpeech.ONOMATOPOEIA);
		posMap.put("ORTSNAMEN-GRUNDWORT", PartOfSpeech.PLACE_NAME_ENDING);
		posMap.put("PARTIZIP", PartOfSpeech.PARTICIPLE);
		posMap.put("PARTIZIP_I", PartOfSpeech.PARTICIPLE);
		posMap.put("PARTIZIP_II", PartOfSpeech.PARTICIPLE);
		posMap.put("PARTIKEL", PartOfSpeech.PARTICLE);
		posMap.put("PERSONALPRONOMEN", PartOfSpeech.PERSONAL_PRONOUN);
		posMap.put("PLURALETANTUM", PartOfSpeech.PLURALE_TANTUM);
		posMap.put("POSSESSIVPRONOMEN", PartOfSpeech.POSSESSIVE_PRONOUN);
		posMap.put("POSTPOSITION", PartOfSpeech.POSTPOSITION);
		posMap.put("PRÄFIX", PartOfSpeech.PREFIX);
		posMap.put("PRÄFIXOID", PartOfSpeech.PREFIX);
		posMap.put("PRÄPOSITION", PartOfSpeech.PREPOSITION);
		posMap.put("PRONOMEN", PartOfSpeech.PRONOUN);
		posMap.put("PRONOMINALADVERB", PartOfSpeech.ADVERB);
		posMap.put("REDEWENDUNG", PartOfSpeech.IDIOM);		
		posMap.put("REFLEXIVES_PERSONALPRONOMEN", PartOfSpeech.REFLEXIVE_PRONOUN);
		posMap.put("REFLEXIVES_POSSESSIVPRONOMEN", PartOfSpeech.REFLEXIVE_PRONOUN);
		posMap.put("REFLEXIVPRONOMEN", PartOfSpeech.REFLEXIVE_PRONOUN);
		posMap.put("RELATIVPRONOMEN", PartOfSpeech.RELATIVE_PRONOUN);
		posMap.put("REZIPROKPRONOMEN", PartOfSpeech.PRONOUN);
		posMap.put("SATZZEICHEN", PartOfSpeech.PUNCTUATION_MARK);
		posMap.put("SINGULARETANTUM", PartOfSpeech.SINGULARE_TANTUM);
		posMap.put("SPRICHWORT", PartOfSpeech.PROVERB);
		posMap.put("SUBJUNKTION", PartOfSpeech.SUBORDINATOR);
		posMap.put("SUBSTANTIV", PartOfSpeech.NOUN);
		posMap.put("SUBSTATIV", PartOfSpeech.NOUN);
		posMap.put("SUBSTANTIVIERTE_FORM", PartOfSpeech.WORD_FORM);
		posMap.put("SUBSTANTIVIERTER_INFINITIV", PartOfSpeech.WORD_FORM);
		posMap.put("SUFFIX", PartOfSpeech.SUFFIX);
		posMap.put("SUFFIXOID", PartOfSpeech.SUFFIX);
		posMap.put("SUPERLATIV", PartOfSpeech.WORD_FORM);
		posMap.put("SYMBOL", PartOfSpeech.SYMBOL);
		posMap.put("TOPONYM", PartOfSpeech.TOPONYM);		
		posMap.put("UMSCHRIFT", PartOfSpeech.TRANSLITERATION);
		posMap.put("VERB", PartOfSpeech.VERB);
		posMap.put("VERGLEICHSPARTIKEL", PartOfSpeech.COMPARATIVE_PARTICLE);
		posMap.put("VORNAME", PartOfSpeech.FIRST_NAME);
		posMap.put("WORTVERBINDUNG", PartOfSpeech.COLLOCATION);
		posMap.put("WORTKOMBINATION", PartOfSpeech.COMBINING_FORM);
		posMap.put("ZAHLKLASSIFIKATOR", PartOfSpeech.MEASURE_WORD);
		posMap.put("ZAHL", PartOfSpeech.NUMBER);
		posMap.put("ZAHLZEICHEN", PartOfSpeech.NUMBER);
		posMap.put("KONTRAKTION", PartOfSpeech.CONTRACTION);
		posMap.put("KOGNOMEN", PartOfSpeech.LAST_NAME);
		posMap.put("SCHRIFTZEICHEN", PartOfSpeech.CHARACTER);

//		DEUTSCH=UNKNOWN
//		ENGLISCH=UNKNOWN
//		EXZESSIV=UNKNOWN
//		GASKOGNISCH=UNKNOWN
//		HOMONYM=UNKNOWN
//		HOMOGRAPH=UNKNOWN
//		IN WORTVERBINDUNGEN=UNKNOWN
//		PROVENZALISCH=UNKNOWN
//		SILBE=UNKNOWN
//		…=UNKNOWN
	}

}
