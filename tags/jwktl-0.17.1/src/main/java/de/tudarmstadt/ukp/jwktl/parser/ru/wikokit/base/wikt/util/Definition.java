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
package de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** A library of functions for definition parsing. Functions are common
 * to many languages, else they should be added to DefinitionLanguageCode.java.
 * Example of definition (3 lines):
 * # {{archaic}} Seeing that, [[since]].
 * #*'''1603''', John Florio, translating Michel de Montaigne, ''Essays'', Folio Society 2006, vol. 1 p. 186-7:
 * #*:'''Sithence''' it must continue so short a time, and begun so late [...], there was no time to be lost.
 */
public class Definition {
    private static final boolean DEBUG = false;
    
    /** Gets position after /^\s+#\s+/ */
    private final static Pattern ptrn_definition_number_sign = Pattern.compile(
            "\\A\\s*#\\s*");           // vim: ^\s*#\s*

    /* Strip number sign '#' and spaces (trim). */
    public static String stripNumberSign (String page_title, String text) {
        
        // gets position in text after "# "
        Matcher m = ptrn_definition_number_sign.matcher(text.toString());
        boolean b_next = m.find();

        if(!b_next) {   // there is no definition section!
            if(DEBUG)
                System.out.println("Warning in Definition.stripNumberSign(): The article '"+
                                    page_title + "' has no number sign '#' in a definition.");
            return text;
        }

        return text.substring(m.end()).trim();
    }
    
    /* Gets first line from the text. */
    public static String getFirstLine (String page_title, String text) {

        int pos = 0;

        if((pos = text.indexOf("\n")) == -1)
            return text;

        return text.substring(0, pos);
    }

}
