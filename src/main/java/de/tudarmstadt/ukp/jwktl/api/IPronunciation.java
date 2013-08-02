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

/**
 * Pronunciation information for {@link IWiktionaryEntry}s. There can be 
 * different types of pronunciation information including standardized
 * written representations using the IPA or SAMPA notation, audio files
 * of people reading a word aloud, and information on the rhyming suffix
 * of a lexical entry. 
 * @author Christiam M. Meyer
 */
public interface IPronunciation {

	/** Types of different pronunciation information used by 
	 *  {@link IPronunciation#getType()}. */
	public enum PronunciationType {
		
		/** International Phonetic Alphabet */
		IPA,
		/** Speech Assessment Methods Phonetic Alphabet */
		SAMPA,
		/** Audio file of this pronunciation. */
		AUDIO,
		/** Suffix used to identify rhymes. */
		RHYME;
		
	}
	
	/** Returns the type of this pronunciation, which can be audio files 
	 *  or a specific notation schema used to represent pronunciation 
	 *  information. */
	public PronunciationType getType();
	
	/** The representation of the pronunciation using a standardized
	 *  notation such as IPA. In case of audio files, the file name of
	 *  the sound file is returned. The corresponding URL of this sound file
	 *  needs to be obtained by querying 
	 *  http://[LANGUAGE].wiktionary.org/wiki/File:[FILENAME]. */
	public String getText();
	
	/** Returns additional information for this pronunciation, such as 
	 *  a geographical reference. */
	public String getNote();
	
}
