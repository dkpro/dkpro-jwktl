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
package de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.multi.en;

/** Phrase or sentence that illustrates a meaning of a word in Russian Wiktionary.
 */
public class WQuoteEn {


    /** Removes highlighted marks from a sentence.
     * Sentence with '''words'''. -> Sentence with words.
     */
    public static String removeHighlightedMarksFromSentence(String str)
    {
        if(str.contains("'''"))
            return str.replace("'''", "");

        return str;
    }
    
}
