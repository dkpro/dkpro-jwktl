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
package de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.word;

import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikipedia.language.LanguageType;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.multi.en.WRedirectEn;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.multi.ru.WRedirectRu;

/** Redirect related functions in wiki and Wiktionary.
 */
public class WRedirect {

    /** Checks whether this is a redirect page. If this is true then 
     * the title of the target (redirected) page will be returned.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article
     * @param text          defines source wiki text
     * @return if this is not a redirect then return null
     */
    public static String getRedirect(LanguageType wikt_lang,
                                      String page_title,
                                      StringBuffer text) {

        // #ПЕРЕНАПРАВЛЕНИЕ [[нелётный]]
        // #REDIRECT [[burn one's fingers]]

        LanguageType l = wikt_lang;
        String redirect_dest = null;

        if(l  == LanguageType.ru) {
            redirect_dest = WRedirectRu.getRedirect(page_title, text);
        } else if(l == LanguageType.en) {
            redirect_dest = WRedirectEn.getRedirect(page_title, text);
        //} else if(code.equalsIgnoreCase( "simple" )) {
          //  return WordSimple;

            // todo
            // ...

        } else {
            throw new NullPointerException("Null LanguageType");
        }


        return redirect_dest;
    }
    
}
