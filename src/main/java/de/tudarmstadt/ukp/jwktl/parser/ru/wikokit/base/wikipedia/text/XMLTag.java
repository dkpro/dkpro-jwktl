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

import java.util.Map;
import java.util.HashMap;


/** XML (HTML) tags, e.g. &nbsp; &quot; &lt;br />, etc.
 */
public class XMLTag {
    
    /** Glyph character, e.g. '>', ' ', etc. */
    private final Character glyph;
    
    /** HTML tag, e.g. '&gt;', '&nbsp;', etc. */
    private final String html;
    
    /** Map from glyph to HTML tag, e.g. '"' -> &quot; */
    private static Map<Character, String> glyph_to_html = new HashMap<Character, String>();
    
    /** vice versa: map from HTML tag to glyph, e.g. &quot; -> " */
    private static Map<String, Character> html_to_glyph = new HashMap<String, Character>();
    
    // list of tags sorted by length: STR_AMP_LT STR_AMP_GT STR_AMP_AMP STR_AMP_QUOT STR_AMP_39
    public static final XMLTag LT           = new XMLTag("&lt;",    '<' );
    public static final XMLTag GT           = new XMLTag("&gt;",    '>' );
    
    public static final XMLTag AMP          = new XMLTag("&amp;",   '&' );
    
    public static final XMLTag QUOT         = new XMLTag("&quot;",  '"' );
    public static final XMLTag APOSTROPHE   = new XMLTag("&#039;",  '\'');
    public static final XMLTag NBSP         = new XMLTag("&nbsp;",  ' ' );   // usual space
    
    public static final XMLTag NDASH        = new XMLTag("&ndash;", '–' );
    public static final XMLTag MDASH        = new XMLTag("&mdash;", '—' );
    
    
    private XMLTag(String html,char glyph) { 
        this.html = html;
        this.glyph = Character.valueOf(glyph);
        
        glyph_to_html.put(this.glyph,   html);
        html_to_glyph.put(html,         this.glyph);
    }
    
    /** Checks weather exists the glyph character. */
    public static boolean existsGlyph(char glyph) {
        return glyph_to_html.containsKey(Character.valueOf(glyph));
    }
    
    /** Checks weather exists the XML (HTML) tag. */
    public static boolean existsTag(String tag) {
        return html_to_glyph.containsKey(tag);
    }
    
    public Character getGlyph() { return glyph; }
    public String    getHTML()  { return html;  }
    
    /** Gets HTML tag by glyph character */
    public static String getHTML(char glyph) throws NullPointerException
    {   
        Character g = Character.valueOf(glyph);
        if( glyph_to_html.containsKey(g) ) {
            return glyph_to_html.get(g);
        } else {
            throw new NullPointerException("Null XMLTag");
        }
    }
    
    /** Gets glyph character by HTML tag */
    public static char getGlyphCharacter(String tag) throws NullPointerException
    {   
        if( html_to_glyph.containsKey(tag) ) {
            return html_to_glyph.get(tag);
        } else {
            throw new NullPointerException("Null XMLTag");
        }
    }
    
    
}
