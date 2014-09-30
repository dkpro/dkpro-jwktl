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

//import wikt.constant.POSType;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.constant.POS;

/** Data structure consists of a POS code and the corresponding text.  */
public class POSText {
    
    /** Part of speech code of the text. */
    private POS pos;
    
    /** POS name found in text, e.g. explicitly: "Verb", or implicitly "stitch I". */
    //private String pos_name;
    
    /** Text */
    private StringBuffer text;
    
    public POSText() {}
    
    /*public POSText(POSType _pos) { //, StringBuffer _text) {
        pos = _pos;
        text = new StringBuffer();
        //text = _text;
    }*/
    
    //public POSText(POSType _pos, StringBuffer _text) {
    public POSText(POS _pos, String _text) {
        pos = _pos;
        text = new StringBuffer(_text);
    }

    public POSText(POS _pos, StringBuffer _text) {
        pos = _pos;
        text = _text;
    }
    
    public POS getPOSType() {
        return pos;
    }

    public StringBuffer getText() {
        return text;
    }

}
