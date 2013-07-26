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
//import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;

/** WikiText is a text, where [[some]] [[word]]s [[be|are]] [[wikify|wikified]],
 * e.g. "[[little]] [[bell]]".
 */
public class WikiText {

    /** Visible text, e.g. "bullets m." for "[[bullet]]s {{m}}" */
    private String text;
    
     /** Wiki internal links, e.g. "bullet" and "bullets for "[[bullet]]s" {{m}} */
    private WikiWord[] wiki_words;

    private final static WikiText[] NULL_WIKITEXT_ARRAY = new WikiText[0];

    /** Split by comma and semicolon */
    private final static Pattern ptrn_comma_semicolon = Pattern.compile(
            "[,;]+");

    /** Context label (reserved for future use).
     *
     * Comment: is used in Russian Wiktionary.
     * It is not used in English Wiktionary.
     */
    //private int label_id;

    public WikiText(String _text, WikiWord[] _wiki_words) {
        text        = _text;
        wiki_words  = _wiki_words;
    }

    /** Gets visible text, e.g. "bullets m." for "[[bullet]]s {{m}}" */
    public String getVisibleText() {
        return text;
    }

    // start position of every wiki_words in text
    // todo
    // ...

    /** Gets array of internal links (wiki words, i.e. words with hyperlinks). */
    public WikiWord[] getWikiWords() {
        return wiki_words;
    }

    /** Frees memory recursively. */
    public void free ()
    {
        if(null != wiki_words) {
            for(int i=0; i<wiki_words.length; i++)
                wiki_words[i] = null;
            wiki_words = null;
        }
    }



    /** Parses text, creates array of wiki words (words with hyperlinks),
     * e.g. text is "[[little]] [[bell]]", wiki_words[]="little", "bell"
     */
    public static WikiText createOnePhrase(String page_title, String text)
    {
        if(0 == text.trim().length()) {
            return null;
        }
        StringBuffer sb = new StringBuffer(text);
        
        String      s = WikiWord.parseDoubleBrackets(page_title, sb).toString();
        WikiWord[] ww = WikiWord.getWikiWords(page_title, sb);
        return new WikiText(s, ww);
    }

    /** Parses text, creates array of wiki words (words with hyperlinks),
     * e.g. text is "[[little]] [[bell]], [[handbell]], [[doorbell]]".
     * @return empty array if there is no text.
     */
    public static WikiText[] create(String page_title, String text)
    {
        if(0 == text.trim().length()) {
            return NULL_WIKITEXT_ARRAY;
        }
        
        String[] ww = ptrn_comma_semicolon.split(text);   // split by comma and semicolon

        // split should take into account brackets, e.g. "bread (new, old), butter" -> "bread (new, old)", "butter"
        // todo
        // ...
        
        List<WikiText> wt_list = new ArrayList<WikiText>();
        for(String w : ww) {
            WikiText wt = WikiText.createOnePhrase(page_title, w.trim());
            if(null != wt) {
                wt_list.add(wt);
            }
        }
        
        if(0 == wt_list.size()) {
            return NULL_WIKITEXT_ARRAY;
        }
        
        return (WikiText[])wt_list.toArray(NULL_WIKITEXT_ARRAY);
    }


    /** Creates array of wiki words (words with hyperlinks) without any parsing.
     *
     * @param wikified_words words which are already without [[wikification]],
     *              e.g. translation extracted from {{t|lang_code|wiki_word}}
     * @return empty array if there is no text.
     */
    public static WikiText[] createWithoutParsing(String page_title,
                                                    List<String> wikified_words)
    {
        int size = wikified_words.size();
        if(0 == size)
            return NULL_WIKITEXT_ARRAY;

        WikiText[] wt = new WikiText[size];
        int i=0;
        for(String w : wikified_words) {

            WikiWord[] ww_array1 = new WikiWord[1];
            ww_array1[0] = new WikiWord(w, w, null);

            wt[i++] = new WikiText(w, ww_array1);
        }

        return wt;
    }
}


    // return true, if wiki text corresponds only to one word, it's important for translation
    // e.g. "[[little]] [[bell]]" == false;
    //      "[[doorbell]]" == true
    //
    // boolean isOneWord (translation)
    // todo
    // ...
