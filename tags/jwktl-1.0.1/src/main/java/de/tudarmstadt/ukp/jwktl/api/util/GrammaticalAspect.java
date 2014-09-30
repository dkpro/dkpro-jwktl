/*******************************************************************************
 * Copyright 2013
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
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
package de.tudarmstadt.ukp.jwktl.api.util;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;

/**
 * Enumeration of the grammatical aspect of a {@link IWiktionaryWordForm}. 
 * Note that tense is often combined with verb aspects (e.g., present 
 * perfect). Such combinations can be modeled in combination with 
 * enumeration values from {@link GrammaticalTense}.
 * @author Christian M. Meyer
 */
public enum GrammaticalAspect {

	/** An ongoing, habitual, repeated situation. Used to express the 
	 *  English simple forms (e.g., "I paint the house") and 
	 *  progressive forms ("I am painting the house"). The imperfect 
	 *  aspect is also used for the German "Partizip I" form 
	 *  (e.g. "die liebende Mutter").  */
	IMPERFECT,
	
	/** A completed situation. Used to express the English perfect forms 
	 *  (e.g., "I have painted the house"). The perfect aspect is also 
	 *  used for the German "Partizip II" form 
	 *  (e.g., "die geliebte Mutter"). */
	PERFECT;

//    Perfective
//        Aorist
//        Momentane
//        Semelfactive
//    Imperfective
//        Continuous and progressive
//        Durative
//        Imperfect
//        Iterative/distributive/frequentative

}
