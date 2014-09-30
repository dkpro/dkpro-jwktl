/*******************************************************************************
 * Copyright 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
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
package de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.multi.ru;

import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikipedia.language.LanguageType;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.util.WikiText;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word.WTranslationEntry;

/** One line in the Translation section, i.e. a translation to one language,
 * e.g. "|en=[[airplane]], [[plane]], [[aircraft]]".
 */
public class WTranslationEntryRu {
    
    /** Parses one entry (one line) of a translation box,
     * extracts a language and a list of translations (wikified words) for this language,
     * creates and fills WTranslationEntry.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article 'text'
     * @param text          translaton box text
     * @return WTranslationEntry or null if the translation language or translation text are absent.
     */
    public static WTranslationEntry parse(
                    String page_title,
                    String text)
    {
        // split "en=[[little]] [[bell]], [[handbell]], [[doorbell]]" into "en" and remain
        int pos_equal_sign = text.indexOf('=');
        if(-1 == pos_equal_sign)
            return null;

        // does exist any translation after "="
        if(pos_equal_sign + 1 > text.length()) // Warnings and error messages are interesting
            return null;                       // only when there are any translations

        // 1. language code
        String lang_code = text.substring(0, pos_equal_sign).trim();
        if(!LanguageType.has(lang_code)) { 
            // concise logging: only one message for one uknown language code
            if(!LanguageType.hasUnknownLangCode(lang_code)) {
                LanguageType.addUnknownLangCode(lang_code);
                System.out.println("Warning in WTranslationEntryRu.parse(): The article '"+
                        page_title + "' has translation into unknown language with code: " + lang_code + ".");
            }
            if(lang_code.length() > 7)
                System.out.println("Error in WTranslationEntryRu.parse(): The article '"+
                        page_title + "' has too long unknown language code: " + lang_code + ".");
            return null;
        }
        LanguageType lang = LanguageType.get(lang_code);
        
        // 2. translation wikified text
        String trans_text = text.substring(pos_equal_sign+1);
        if(0 == trans_text.length() ||
                trans_text.equalsIgnoreCase("[[]]"))
            return null;

        WikiText[] wt = WikiText.create(page_title, trans_text);
        if(0 == wt.length)
            return null;
        
        return new WTranslationEntry(lang, wt);
    }

}
