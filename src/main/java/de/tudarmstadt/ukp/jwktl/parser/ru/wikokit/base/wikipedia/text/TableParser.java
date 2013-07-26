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
package de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikipedia.text;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** Parser of wiki tables {| .. |}.
 */
public class TableParser {
    
    private final static StringBuffer   NULL_STRINGBUFFER = new StringBuffer("");
    
    /*private final static Pattern ptrn_table_boundary        = Pattern.compile("{|(.+?)|}", Pattern.DOTALL);
    private final static Pattern ptrn_left_table_boundary   = Pattern.compile("{|");
    private final static Pattern ptrn_right_table_boundary  = Pattern.compile("|}");*/
    
    //private final static Pattern ptrn_table_boundaries = Pattern.compile("{\\||\\|}");
    private final static Pattern ptrn_table_boundaries = Pattern.compile("\\{\\||\\|\\}");
    
    /** Removes tables, and embedded tables also, e.g. 
     * "{| table 1 \n {| A table in the table 1 \n|}|}".
     * 
     * Remark: if this func is before CurlyBrackets () then it generates
     * warnings, since end of infobox (template) {{ |}} looks like end of table.
     */
    public static StringBuffer removeWikiTables(StringBuffer text)
    {
        final String w_closed_too_many = "Warning (wikipedia.text.TableParser.removeWikiTables()): number of opened brackets '{|' < than closed brackets '|}'";
        final String w_opened_too_many = "Warning (wikipedia.text.TableParser.removeWikiTables()): number of opened brackets '{|' > than closed brackets '|}'";
        
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        Matcher m = ptrn_table_boundaries.matcher(text.toString());
        boolean result = m.find();
        if(result) {
            StringBuffer sb = new StringBuffer();
            int n_nested = 0;
            while(result) {
                String g0 = m.group(0);
                if('{' == g0.charAt(0)) {
                    if(0 == n_nested) { // first opened bracket
                        m.appendReplacement(sb, "");
                    }
                    n_nested ++;
                } else {
                    n_nested --;
                    if(n_nested == 0) {
                        StringBuffer temp = new StringBuffer();
                        m.appendReplacement(temp, ""); // I don't know why this line is important!
                    }
                    if(n_nested < 0) {
                        System.out.println(w_closed_too_many);
                    }
                }
                result = m.find();
            }
            m.appendTail(sb);
            
            if(n_nested < 0) {
                System.out.println(w_closed_too_many);
            } else {
                if(n_nested > 0) {
                    System.out.println(w_opened_too_many);
                }
            }    
            return sb;
        }
        
        return text;
    }
    
    
}
