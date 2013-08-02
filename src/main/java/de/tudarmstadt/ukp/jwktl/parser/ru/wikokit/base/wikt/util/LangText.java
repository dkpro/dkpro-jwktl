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

import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikipedia.language.LanguageType;

/** Data structure consists of a language code and the corresponding text.
 */
public class LangText {
    
    /** Language of the text, e.g. the article about one word can contain "en" block for English word, "de", "fr", etc. */
    private LanguageType lang;
    
    /** Text */
    public StringBuffer text;
    
    public LangText() {}
    
    public LangText(LanguageType _lang) { //, StringBuffer _text) {
        lang = _lang;
        text = new StringBuffer();
        //text = _text;
    }

    /** Gets language of the text, e.g. "en" for English word, "de", "fr", etc. */
    public LanguageType getLanguage() {
        return lang;
    }
}
