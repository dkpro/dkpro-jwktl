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
 * Enumeration of the grammatical number of a {@link IWiktionaryWordForm}. 
 * @author Christian M. Meyer
 */
public enum GrammaticalPerson {

	/** The first person; the speaker; referred to by "I", "we". */
	FIRST,
	
	/** The second person; the addressee; referred to by "you". */
	SECOND,
	
	/** The third person; the other; referred to by "he", "she", 
	 *  "it", "they". */
	THIRD;
	
}
