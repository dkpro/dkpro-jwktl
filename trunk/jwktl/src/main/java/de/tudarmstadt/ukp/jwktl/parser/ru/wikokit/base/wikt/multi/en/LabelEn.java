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

import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.constant.ContextLabel;

/** Contexual information for definitions, or Synonyms, or Translations 
 * in English Wiktionary.
 * 
 * See http://en.wiktionary.org/wiki/Template_talk:context
 *     http://en.wiktionary.org/wiki/Wiktionary:Entry_layout_explained
 */
public class LabelEn extends ContextLabel {
    
    private LabelEn(String label,String name,String category) {
        super(label, name, category);
    }
    
    public static final ContextLabel AU     = new LabelEn("AU",     "Australia",    "");
    public static final ContextLabel slang  = new LabelEn("slang",  "slang",        "");
    
    public static final ContextLabel astronomy = new LabelEn("astronomy","astronomy",   "Astronomy");
}
