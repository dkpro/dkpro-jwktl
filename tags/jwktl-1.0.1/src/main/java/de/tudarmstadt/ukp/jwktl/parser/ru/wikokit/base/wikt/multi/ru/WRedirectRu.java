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

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** Redirect related functions in wiki and Russian Wiktionary.
 *
 * @see http://ru.wiktionary.org/wiki/Викисловарь:Перенаправления
 */
public class WRedirectRu {
    
    /** Gets target page of the redirect, extracts [[pagename]] from double brackets. */
    private final static Pattern ptrn_redirect = Pattern.compile(
            "#(REDIRECT|ПЕРЕНАПРАВЛЕНИЕ) \\[\\[(.+?)\\]\\]",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    
    /** Checks whether this is a redirect page. If this is true then
     * the title of the target (redirected) page will be returned.
     *
     * @param wikt_lang     language of Wiktionary
     * @param page_title    word which are described in this article
     * @param text          defines source wiki text
     * @return if this is not a redirect then return null
     */
    public static String getRedirect(String page_title,
                                      StringBuffer text) {

        // #REDIRECT [[pagename]] (or #redirect [[pagename]]
        // or #ПЕРЕНАПРАВЛЕНИЕ [[pagename]]
        
        //int len = "#REDIRECT [[".length(); // == 12
        if(text.length() < 12 || text.charAt(0) != '#')
            return null;
        
        Matcher m = ptrn_redirect.matcher(text);
        if (m.find()){
            return m.group(2);
        }
        
        return null;
    }

}
