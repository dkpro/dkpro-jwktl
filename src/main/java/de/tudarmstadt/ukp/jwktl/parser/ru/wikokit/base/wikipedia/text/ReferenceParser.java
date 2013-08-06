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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikipedia.util.StringUtil;

/** Parser of wiki references &lt;ref>...&lt;/ref>
 */
public class ReferenceParser {
    
    //private final static Pattern ptrn_ref             = Pattern.compile("<ref>");
    private final static Pattern ptrn_ref_boundaries    = Pattern.compile("<ref>(.+?)</ref>");
    
    private final static Pattern ptrn_http_url  = Pattern.compile("\\bhttp://.+?(\\s|$)");
    
    private final static StringBuffer   NULL_STRINGBUFFER = new StringBuffer("");
    
    
    //sb = removeHTTPURL(sb);
    
    /** Removes URL like http://... fro the text.
     */ 
    //expandReferenceToEndOfText() {
    private static StringBuffer removeHTTPURL(StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        
        Matcher m = ptrn_http_url.matcher(text.toString());
        return new StringBuffer(m.replaceAll(""));
    }
    
    /** Expands texts of the refence, and adds it to the end of text.
     * 
     * If the reference contains a template, e.g. &lt;ref>{{cite book |..&lt;/ref>
     * then the whole reference will be deleted.
     */ 
    public static StringBuffer expandMoveToEndOfText(StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        
        //Matcher m = ptrn_ref_boundaries.matcher(StringUtil.escapeCharDollarAndBackslash(text.toString()));
        Matcher m = ptrn_ref_boundaries.matcher(text.toString());
        
        boolean bfound = m.find();
        if(bfound) {
            StringBuffer result = new StringBuffer();
            StringBuffer eo_text = new StringBuffer();  // end of text
            while(bfound) {
                                                                   // group(1) := text within <ref>reference boundaries</ref>
                StringBuffer sb = WikiParser.parseCurlyBrackets(
                            StringUtil.escapeCharDollarAndBackslash(m.group(1) ));
                sb = removeHTTPURL(sb);
                            
                eo_text.append( sb );
                m.appendReplacement(result, "");
                bfound = m.find();
            }
            m.appendTail(result);
            if(eo_text.length() > 0) {
                result.append("\n\n");
                result.append(eo_text);
            }
            return result;
        }
        
        return text;
    }

    /** Removes refences from the text.
     */
    public static StringBuffer removeReferences(StringBuffer text)
    {
        if(null == text || 0 == text.length()) {
            return NULL_STRINGBUFFER;
        }
        
        //Matcher m = ptrn_ref_boundaries.matcher(StringUtil.escapeCharDollarAndBackslash(text.toString()));
        Matcher m = ptrn_ref_boundaries.matcher(text.toString());
        return new StringBuffer(m.replaceAll(""));
    }
}
