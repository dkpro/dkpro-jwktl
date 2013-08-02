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

import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikipedia.util.StringUtilRegular;

/** (Wikified) author name in quote phrase / sentence.
 */
public class AuthorAndWikilink {
    public AuthorAndWikilink() {
        author = "";
        author_wikilink = "";
    }

    /** Author's name of the quotation. */
    public String  author;

    /** Author's name in Wikipedia (format: [[w:name|]] or [[:w:name|]]). */
    public String  author_wikilink;


    /** Parses text (e.g. "[[:s:У окна (Андреев)|У окна]]") into
        * title_wikilink "У окна (Андреев)" and title "У окна".
        */
    public void parseAuthorName(String text) {

        text = StringUtilRegular.replaceComplexSpacesByTrivialSpaces(text);

        // replace "&nbsp;" by " "
        if(text.contains("&nbsp;"))
            text = text.replace("&nbsp;", " ");

        author = text; // first version
        if(!(text.startsWith("[[:w:") ||
                text.startsWith("[[w:")) ||
            !text.endsWith("]]") ||
            !text.contains("|"))
            return;

        if(text.startsWith("[[:w:"))
            text = text.substring(5, text.length() - 2); // "[[:w:" . text . "]]"
        else
            text = text.substring(4, text.length() - 2); // "[[w:" . text . "]]"

        // split by |
        // [[:w:The title|The title]]
        int pos = text.indexOf("|");
        if(-1 == pos)
            return;

        author_wikilink = text.substring(0, pos);
        author = text.substring(pos + 1);
    }
}
