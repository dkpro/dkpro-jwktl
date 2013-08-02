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

import java.util.Map;
import java.util.TreeMap;

import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.parser.entry.EntryFactory;

/**
 * <p>A factory creates PosEntry objects
 *
 */
public class ENEntryFactory extends EntryFactory {
			
	public PartOfSpeech findPartOfSpeech(final String name) {
		return PartOfSpeech.findByName(name, posMap);
	}
	
	protected static final Map<String, PartOfSpeech> posMap;
	
	static {
		posMap = new TreeMap<String, PartOfSpeech>();
		posMap.put("ABBREVIATION", PartOfSpeech.ABBREVIATION);
		posMap.put("ACRONYM", PartOfSpeech.ACRONYM);
		posMap.put("ADJECTIVE", PartOfSpeech.ADJECTIVE);
		posMap.put("ADVERB", PartOfSpeech.ADVERB);
		posMap.put("ARTICLE", PartOfSpeech.ARTICLE);
		posMap.put("COMBINING_FORM", PartOfSpeech.COMBINING_FORM);
		posMap.put("CONJUNCTION", PartOfSpeech.CONJUNCTION);
		posMap.put("CONTRACTION", PartOfSpeech.CONTRACTION);
		posMap.put("DETERMINER", PartOfSpeech.DETERMINER);
		posMap.put("GISMU", PartOfSpeech.GISMU);
		posMap.put("IDIOM", PartOfSpeech.IDIOM);
		posMap.put("INITIALISM", PartOfSpeech.INITIALISM);
		posMap.put("INTERJECTION", PartOfSpeech.INTERJECTION);		
		posMap.put("LETTER", PartOfSpeech.LETTER);
		posMap.put("NOUN", PartOfSpeech.NOUN);
		posMap.put("NOUN_PHRASE", PartOfSpeech.NOUN_PHRASE);
		posMap.put("NUMBER", PartOfSpeech.NUMBER);
		posMap.put("PARTICIPLE", PartOfSpeech.PARTICIPLE);
		posMap.put("PARTICLE", PartOfSpeech.PARTICLE);
		posMap.put("POSTPOSITION", PartOfSpeech.POSTPOSITION);
		posMap.put("PREFIX", PartOfSpeech.PREFIX);
		posMap.put("PREPOSITION", PartOfSpeech.PREPOSITION);
		posMap.put("PRONOUN", PartOfSpeech.PRONOUN);
		posMap.put("PROPER_NOUN", PartOfSpeech.PROPER_NOUN);
		posMap.put("PROVERB", PartOfSpeech.PROVERB);
		posMap.put("SUFFIX", PartOfSpeech.SUFFIX);
		posMap.put("SYMBOL", PartOfSpeech.SYMBOL);
		posMap.put("VERB", PartOfSpeech.VERB);
		
		posMap.put("HAN_CHARACTER", PartOfSpeech.CHARACTER);
		posMap.put("HANJI", PartOfSpeech.CHARACTER);
		posMap.put("KANJI", PartOfSpeech.KANJI);
		posMap.put("HIRAGANA_CHARACTER", PartOfSpeech.HIRAGANA);
		posMap.put("KATAKANA_CHARACTER", PartOfSpeech.KATAKANA);
		posMap.put("ROMAJI", PartOfSpeech.CHARACTER);
		posMap.put("HANJA", PartOfSpeech.CHARACTER);		
		
		posMap.put("NUMERAL", PartOfSpeech.NUMERAL);
		posMap.put("CARDINAL_NUMBER", PartOfSpeech.NUMBER);
		posMap.put("CARDINAL_NUMERAL", PartOfSpeech.NUMERAL);
		posMap.put("ORDINAL_NUMBER", PartOfSpeech.NUMBER);
		posMap.put("ORDINAL_NUMERAL", PartOfSpeech.NUMERAL);		
		posMap.put("PROPERR_NOUN", PartOfSpeech.PROPER_NOUN);
		posMap.put("PROPER_OUN", PartOfSpeech.PROPER_NOUN);
		
		posMap.put("EXPRESSION", PartOfSpeech.EXPRESSION);
		posMap.put("PHRASE", PartOfSpeech.PHRASE);
		posMap.put("PREPOSITIONAL_PHRASE", PartOfSpeech.PHRASE);
		posMap.put("CMAVO", PartOfSpeech.EXPRESSION);
		posMap.put("NOUN_FORM", PartOfSpeech.WORD_FORM);
		posMap.put("VERB_FORM", PartOfSpeech.WORD_FORM);
		posMap.put("INFIX", PartOfSpeech.AFFIX);
	}
		
}
