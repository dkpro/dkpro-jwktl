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
 * Enumeration of the grammatical degree of a {@link IWiktionaryWordForm}. 
 * @author Christian M. Meyer
 */
public enum GrammaticalDegree {

	/** Denotes an a property (e.g., "Your flowers are _pretty_"). */
	POSITIVE,
	
	/** Indicates a greater degree (e.g., "Your flowers are 
	 *  _prettier_ than mine"). */
	COMPARATIVE,
	
	/** Indicates the greatest degree (e.g., "Your flowers are 
	 *  _prettiest_"). */
	SUPERLATIVE;
	
//	ELATIVE,
	
}
