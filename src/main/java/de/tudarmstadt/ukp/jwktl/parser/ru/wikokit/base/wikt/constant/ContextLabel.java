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
package de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.constant;

import java.util.Map;
import java.util.HashMap;

/** Contexual information for definitions, such as archaic, by analogy, 
 * chemistry, etc.
 *
 * See http://en.wiktionary.org/wiki/Template_talk:context
 */
public abstract class ContextLabel {
       
    /** Two (or more) letter label code, e.g. 'устар.', 'п.'. */
//    private final String label;
    
    /** Label name, e.g. 'устарелое', 'переносное значение'. */
//    private final String name;
    
    /** Category associated with this label. */
//    private final String category;
    
    private static Map<String, String> label2name     = new HashMap<String, String>();
    private static Map<String, String> label2category = new HashMap<String, String>();
    
    protected ContextLabel(String label,String name,String category) { 
//        this.label      = label; 
//        this.name       = name; 
//        this.category   = category; 
        label2name.     put(label, name);
        label2category. put(label, category);
    }
}
