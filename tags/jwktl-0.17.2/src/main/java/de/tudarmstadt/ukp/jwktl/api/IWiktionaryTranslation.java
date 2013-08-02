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
package de.tudarmstadt.ukp.jwktl.api;

import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;

/**
 * Represents a lexical translation of a {@link IWiktionarySense}.
 * A translation is defined by the translated word form and the target language
 * the translated word form belongs to. In addition to that, there can be
 * a transliteration (if the target language uses a different script) or 
 * grammatical information given to clarify the usage of a certain translation.
 * Although each translation belongs to a certain word sense (e.g., retrieved by 
 * {@link IWiktionarySense#getTranslations()}) there can be translations that
 * have not (yet) been assigned to a word sense. Use the unassigned sense 
 * ({@link IWiktionaryEntry#getUnassignedSense()}) to retrieve them.
 * @author Christian M. Meyer
 */
public interface IWiktionaryTranslation {

	/** Returns the target language of the translation. */
	public ILanguage getLanguage();
	
	/** Returns the translated word form (belonging to target language). */
	public String getTranslation();
	
	/** Returns a transliteration to the script of the encoding Wiktionary.
	 *  A Russian translation within the English Wiktionary could, for 
	 *  example, be transliterated from the Cyrillic to the Latin script. 
	 *  Returns <code>null</code> if no transliteration is specified. */
	public String getTransliteration();
	
	/** Returns additional information on the translation, including usage
	 *  notes, meaning-related information, and grammatical properties. */
	public String getAdditionalInformation();
	
	//public String getGender();	
	//public Boolean isSingular();	
	//public String getSenseMarker();	
	//exists in target Wiktionary?	
	
}
