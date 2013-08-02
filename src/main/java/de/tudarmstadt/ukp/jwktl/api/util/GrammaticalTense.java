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
 * Enumeration of the grammatical tense of a {@link IWiktionaryWordForm}.
 * Note that tense is often combined with verb aspects (e.g., present 
 * perfect). Such combinations can be modeled in combination with 
 * enumeration values from {@link GrammaticalAspect}. 
 * @author Christian M. Meyer
 */
public enum GrammaticalTense {

	/** The past; an utterance refers to the time before a reference time. */
	PAST,
	
	/** The present; an utterance refers to the reference time. */
	PRESENT,
	
	/** The future; an utterance refers to the time after a reference time. */
	FUTURE;
	
}
