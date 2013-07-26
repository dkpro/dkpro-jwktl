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

import java.util.Map;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.IWiktionaryEntryParser;
import de.tudarmstadt.ukp.jwktl.parser.WiktionaryEntryParser;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikipedia.language.LanguageType;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.constant.Relation;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WLanguage;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WMeaning;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WPOS;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WRelation;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WTranslation;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WordBase;
import de.tudarmstadt.ukp.jwktl.parser.util.IBlockHandler;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * An implementation of the {@link IWiktionaryEntryParser} interface for 
 * parsing the contents of article pages from the Russian Wiktionary.
 * The entry parser is delegate its calls to the Wikokit API. 
 * @author Yevgen Chebotar
 * @author Christian M. Meyer
 */
public class RUWiktionaryEntryParser extends WiktionaryEntryParser {
	
	/** Initializes the Russian entry parser. That is, the language and the
	 *  redirection pattern is defined. */
	public RUWiktionaryEntryParser() {
		super(Language.RUSSIAN, "REDIRECT");
	}

	@Override
	public void parse(WiktionaryPage page, String text) {
		//if (!page.getTitle().equals("лечу")) return;
		
		// Handle redirects.
		if (checkForRedirect(page, text))
			return;
				
		// RUSSIAN PARSER.
		try {
		WordBase word = new WordBase(page.getTitle(), LanguageType.get("ru"), 
				new StringBuffer(text));
		
		// Convert to WordBase to ParsedWordEntry.
		WikokitToJWKTL jwktlConverter = new WikokitToJWKTL();   
		String page_title = word.getPageTitle();
//    	ParsedWordEntry jwktlParsedWordEntry = new ParsedWordEntry();      
        //Lemma
//    	jwktlParsedWordEntry.setLemma(page_title);

        //Category
    	/*String[] categories = Categorylinks.GetCategoryTitleByArticleID(dumpConn, 
        		PageTableBase.getIDByTitle(dumpConn, page_title));
        if (categories != null){
        	System.out.println("FOUND CATEGORY "+page_title);
        	for(String category : categories)
        		jwktlParsedWordEntry.addCategory(category);        
        }*/
        if (word.isRedirect())
            return;

        WLanguage[] w_languages = word.getAllLanguages();
        for (WLanguage w_lang : w_languages) {
            LanguageType lang_type = w_lang.getLanguage();           
            WPOS[] w_pos_all = w_lang.getAllPOS();
//            int etymology_n = 0;
            for (WPOS w_pos : w_pos_all) {
//            	WiktionaryEntry jwktlPosEntry = new WiktionaryEntry();
            	WiktionaryEntry jwktlPosEntry = page.createEntry();
                jwktlPosEntry.addPartOfSpeech(jwktlConverter.convertPOS(w_pos.getPOS()));
                jwktlPosEntry.setWordLanguage(jwktlConverter.convertLang(lang_type));                
//                etymology_n ++;          
               
                Map<Relation, WRelation[]> m_relations = w_pos.getAllRelations();
                WTranslation[] translations = w_pos.getAllTranslation();
                WMeaning[] w_meaning_all = w_pos.getAllMeanings();
                //Glosses
                for(int i=0; i<w_meaning_all.length; i++) {
                    WMeaning w_meaning = w_meaning_all[i];
                    /*if (w_meaning != null && w_meaning.getWikiText() != null 
                    		&& w_meaning.getWikiWords() != null)
                    	for (WikiWord wikiWord : w_meaning.getWikiWords())
                    		if (0 != wikiWord.getWordLink().compareTo(wikiWord.getWordVisible())) {
                    			System.err.println("INFLECTION: " + page.getTitle() + " " + w_meaning.getWikifiedText());
                    			jwktlPosEntry.addPos(PartOfSpeech.WORD_FORM);
                    		}*/ // these are not really inflections!!
                    jwktlPosEntry.addSense(jwktlConverter.convertMeaningToSenseEntry(jwktlPosEntry, page_title, w_meaning, m_relations, translations, i));                    
               
                }
                // some stubs don't have definition, but they have translations
                if(w_meaning_all.length == 0 && translations.length > 0) {                	
                	jwktlConverter.addTranslationsToPosEntry(translations, jwktlPosEntry);                	
                }
//                jwktlParsedWordEntry.addPosEntry(jwktlPosEntry);
                page.addEntry(jwktlPosEntry);
            }
        }
        
        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected ParsingContext createParsingContext(final WiktionaryPage page) {
		return new ParsingContext(page);
	}

	@Override
	public IBlockHandler selectHandler(String line) {
		return null;
	}

	@Override
	public boolean isStartOfBlock(String line) {
		return false;
	}

}
