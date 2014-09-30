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
package de.tudarmstadt.ukp.jwktl.parser.ru;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.entry.Quotation;
import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikipedia.language.LanguageType;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.constant.POS;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.constant.Relation;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.util.WikiText;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WMeaning;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WQuote;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WRelation;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WTranslation;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WTranslationEntry;

/**
 * Helper class to convert parsed Wikokit entries into JWKTL data objects.
 * @author Yevgen Chebotar
 * @author Christian M. Meyer
 */
public class WikokitToJWKTL {
	
	private Map<String, PartOfSpeech> posMap;
	private Map<Relation, RelationType> relationMap;
	
	/** Initializes the converter and its part of speech and relation type
	 *  mappings. */
	public WikokitToJWKTL() {
		posMap = new HashMap<String, PartOfSpeech>();    
//		posMap.put("unknown", PartOfSpeech.UNKNOWN);
		posMap.put("noun", PartOfSpeech.NOUN);
		posMap.put("verb", PartOfSpeech.VERB);
		posMap.put("adverb", PartOfSpeech.ADVERB);
		posMap.put("adjective", PartOfSpeech.ADJECTIVE);
		posMap.put("pronoun", PartOfSpeech.PRONOUN);
		posMap.put("conjunction", PartOfSpeech.CONJUNCTION);
		posMap.put("interjection", PartOfSpeech.INTERJECTION);
		posMap.put("preposition", PartOfSpeech.PREPOSITION);
		posMap.put("proper noun", PartOfSpeech.PROPER_NOUN);
		posMap.put("article", PartOfSpeech.ARTICLE);
		posMap.put("prefix", PartOfSpeech.PREFIX);
		posMap.put("suffix", PartOfSpeech.SUFFIX);
		posMap.put("phrase", PartOfSpeech.PHRASE);
		posMap.put("idiom", PartOfSpeech.IDIOM);
		posMap.put("prepositional phrase", PartOfSpeech.PHRASE);
		posMap.put("numeral", PartOfSpeech.NUMERAL);
		posMap.put("acronym", PartOfSpeech.ACRONYM);
		posMap.put("abbreviation", PartOfSpeech.ABBREVIATION);
		posMap.put("initialism", PartOfSpeech.INITIALISM);
		posMap.put("symbol", PartOfSpeech.SYMBOL);
		posMap.put("letter", PartOfSpeech.LETTER);
		posMap.put("particle", PartOfSpeech.PARTICLE);
		posMap.put("participle", PartOfSpeech.PARTICIPLE);

		posMap.put("determiner", PartOfSpeech.DETERMINER);
		posMap.put("infix", PartOfSpeech.AFFIX);
		posMap.put("interfix", PartOfSpeech.AFFIX);
		posMap.put("affix", PartOfSpeech.AFFIX);
		posMap.put("circumfix", PartOfSpeech.AFFIX);
		posMap.put("counter", PartOfSpeech.NUMERAL);
		posMap.put("kanji", PartOfSpeech.KANJI);
		posMap.put("kanji reading", PartOfSpeech.KANJI);
		posMap.put("hanja reading", PartOfSpeech.KANJI);
		posMap.put("hiragana letter", PartOfSpeech.HIRAGANA);
		posMap.put("katakana letter", PartOfSpeech.KATAKANA);
		//posMap.put("pinyin", PartOfSpeech.UNKNOWN);
		//posMap.put("han character", PartOfSpeech.UNKNOWN);
		posMap.put("hanzi", PartOfSpeech.HANZI);
		//posMap.put("hanja", PartOfSpeech.UNKNOWN);
		posMap.put("proverb", PartOfSpeech.PROVERB);
		posMap.put("expression", PartOfSpeech.EXPRESSION);
		posMap.put("possessive_adjective", PartOfSpeech.POSSESSIVE_PRONOUN);
		posMap.put("postposition", PartOfSpeech.POSTPOSITION);
		posMap.put("gerund", PartOfSpeech.WORD_FORM);
		posMap.put("pronominal_adverb", PartOfSpeech.ADVERB);
		posMap.put("adnominal", PartOfSpeech.ADJECTIVE);
		//posMap.put("root", PartOfSpeech.UNKNOWN);
		//posMap.put("pinyin syllable", PartOfSpeech.UNKNOWN);
		//posMap.put("syllable", PartOfSpeech.UNKNOWN);
		posMap.put("hiragana character", PartOfSpeech.HIRAGANA);
		posMap.put("katakana character", PartOfSpeech.KATAKANA);
		//posMap.put("jyutping syllable", PartOfSpeech.UNKNOWN);
		posMap.put("gismu", PartOfSpeech.GISMU);
		//posMap.put("lujvo", PartOfSpeech.UNKNOWN);
		//posMap.put("brivla", PartOfSpeech.UNKNOWN);
		//posMap.put("classifier", PartOfSpeech.UNKNOWN);
		//posMap.put("predicative", PartOfSpeech.UNKNOWN);
		posMap.put("measure word", PartOfSpeech.MEASURE_WORD);
		//posMap.put("correlative", PartOfSpeech.UNKNOWN);
		//posMap.put("preverb", PartOfSpeech.UNKNOWN);
		//posMap.put("prenoun", PartOfSpeech.UNKNOWN);
		//posMap.put("noun stem", PartOfSpeech.UNKNOWN);
		//posMap.put("noun class", PartOfSpeech.UNKNOWN);
		//posMap.put("combined-kana character", PartOfSpeech.UNKNOWN);
	    
		posMap.put("verb-interjection", PartOfSpeech.INTERJECTION);
	    posMap.put("parenthesis", PartOfSpeech.SYMBOL);
		posMap.put("prefix of compound words", PartOfSpeech.PREFIX);
	    
		
		relationMap = new HashMap<Relation, RelationType>();
		relationMap.put(Relation.synonymy, RelationType.SYNONYM);
		relationMap.put(Relation.antonymy, RelationType.ANTONYM);
		relationMap.put(Relation.hypernymy, RelationType.HYPERNYM);
		relationMap.put(Relation.hyponymy, RelationType.HYPONYM);
		relationMap.put(Relation.holonymy, RelationType.HOLONYM);
		relationMap.put(Relation.meronymy, RelationType.MERONYM);
		relationMap.put(Relation.troponymy, RelationType.TROPONYM);
		relationMap.put(Relation.coordinate_term, RelationType.COORDINATE_TERM);
		relationMap.put(Relation.otherwise_related, RelationType.SEE_ALSO);
	}
	
	/** Converts a Wikokit part of speech to JWKTL. */
	public PartOfSpeech convertPOS(POS wikoPos){	
		PartOfSpeech result = posMap.get(wikoPos.toString());
		return result;
	}
	
	/** Converts a Wikokit language to JWKTL. */
	public ILanguage convertLang(LanguageType wikoLang){
		return Language.findByName(wikoLang.getName());
	}
	
	/** Converts a Wikokit sense to JWKTL. */
	public WiktionarySense convertMeaningToSenseEntry(
			final WiktionaryEntry entry,
			final String pageTitle, 
			final WMeaning wikoMeaning, 
			final Map<Relation, WRelation[]> wikoRelations,
			final WTranslation[] translations, int meaningNr) {
		if (wikoMeaning == null) {
//			System.err.println("wikoMeaning == null");
			return null;
		}
		
//		WiktionarySense result = new WiktionarySense();
		WiktionarySense result = entry.createSense();
		try {
			// Gloss
			if (wikoMeaning.getWikiText() != null) {
				IWikiString gloss = new WikiString(wikoMeaning.getWikifiedText());
				result.setGloss(gloss);
			} 
//			else
//				System.err.println("wikoMeaning.getWikiText() != null");

			// Quotation
			if (wikoMeaning.getQuotes() != null) {
				Quotation jwktlQuote = new Quotation();
				for (WQuote wQuote : wikoMeaning.getQuotes())
					jwktlQuote.addLine(new WikiString(wQuote.getText()));
				result.addQuotation(jwktlQuote);
			}
		
			// Relation Words
			for (Entry<Relation, WRelation[]> rel : wikoRelations.entrySet()) {
				RelationType relType = relationMap.get(rel.getKey());
				WRelation[] wr = rel.getValue();
				if (meaningNr< wr.length && null != wr[meaningNr]) {
					WRelation curRel = wr[meaningNr];                
					WikiText[] relWords = curRel.get();                               
					for(WikiText relWord : relWords) {
						result.addRelation(new WiktionaryRelation(relWord.getVisibleText(), relType));
					}
				}
			}

			// Translations.
			if (meaningNr < translations.length && translations[meaningNr] != null) {
				for (WTranslationEntry transEntry: translations[meaningNr].getTranslations()) {
					ILanguage jwktlLang = convertLang(transEntry.getLanguage());
					for (WikiText word : transEntry.getWikiPhrases())
						result.addTranslation(new WiktionaryTranslation(jwktlLang, word.getVisibleText()));
				}
			}
        
			// ToDo Examples in JWKTL, Categories, ContextLabels
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;	
	}
	
	/** Add Wikokit translations to the Wiktionary entry. */
	public void addTranslationsToPosEntry(final WTranslation[] translations, 
			final WiktionaryEntry posEntry) {
		for (WTranslation translation : translations)
			for (WTranslationEntry transEntry : translation.getTranslations()) {
				ILanguage jwktlLang = convertLang(transEntry.getLanguage());
	    		for (WikiText word : transEntry.getWikiPhrases()) {
	    			posEntry.getUnassignedSense().addTranslation(
	    					new WiktionaryTranslation(jwktlLang, word.getVisibleText()));
	    		}
			}
	}
	
}
