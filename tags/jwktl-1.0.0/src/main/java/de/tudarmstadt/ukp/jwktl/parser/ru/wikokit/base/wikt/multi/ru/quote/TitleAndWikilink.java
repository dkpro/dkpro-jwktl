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
package de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.multi.ru.quote;

/** (Wikified) title of quote phrase / sentence.
 */
public class TitleAndWikilink {
    public TitleAndWikilink() {
        title = "";
        title_wikilink = "";
    }

    /** Title of the work. */
    public String  title;

    /** Link to a book in Wikipedia (format: [[s:title|]] or [[:s:title|]]). */
    public String  title_wikilink;


    /** Parses text (e.g. "[[:s:У окна (Андреев)|У окна]]") into
        * title_wikilink "У окна (Андреев)" and title "У окна".
        */
    public void parseTitle(String text) {

        // replace "&nbsp;" by " "
        if(text.contains("&nbsp;"))
            text = text.replace("&nbsp;", " ");

        title = text; // first version
        if(!(text.startsWith("[[:s:") ||
                text.startsWith("[[s:")) ||
            !text.endsWith("]]") ||
            !text.contains("|"))
            return;

        if(text.startsWith("[[:s:"))
            text = text.substring(5, text.length() - 2); // "[[:s:" . text . "]]"
        else
            text = text.substring(4, text.length() - 2); // "[[s:" . text . "]]"

        // split by |
        // [[:s:The title|The title]]
        int pos = text.indexOf("|");
        if(-1 == pos)
            return;

        title_wikilink = text.substring(0, pos);
        title = text.substring(pos + 1);
    }
}
