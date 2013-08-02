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

/** Names of types of soft redirects used in all wiktionaries.
 *
 * @see Wiktionary:Redirections and Help:Redirect in English Wiktionary
 * @see TPage.is_redirect - a hard redirect.
 */
public class SoftRedirectType {

    /** Name of a redirect type, e.g. SpellingError */
    private final String name;

    @Override
    public String  toString() { return name; }

    /* Set helps to check the presence of elements */
    private static Map<String, SoftRedirectType> name2type = new HashMap<String, SoftRedirectType>();

    private SoftRedirectType (String _name) {
        name = _name;
        name2type.put(_name, this);
    }

    /** Checks weather exists the type by its name. */
    public static boolean has(String _name) {
        return name2type.containsKey(_name);
    }

    /** Gets a type by its name */
    public static SoftRedirectType get(String _name) {
        return name2type.get(_name);
    }

    
    /** The types of soft redirects are: */
    /*************************************/

    /** It's not a redirect, it is the usual Wiktionary entry */
    public static final SoftRedirectType None       = new SoftRedirectType("None");

    /** Wordform - soft redirect to correct spelling. */
    public static final SoftRedirectType Wordform   = new SoftRedirectType("Wordform");

    /** Misspelling - soft redirect to correct spelling {{misspelling of|}} or {{wrongname|}}. */
    public static final SoftRedirectType Misspelling  = new SoftRedirectType("Misspelling");
}
