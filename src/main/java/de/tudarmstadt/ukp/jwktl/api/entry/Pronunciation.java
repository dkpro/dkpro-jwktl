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
package de.tudarmstadt.ukp.jwktl.api.entry;

import com.sleepycat.persist.model.Persistent;

import de.tudarmstadt.ukp.jwktl.api.IPronunciation;

/**
 * Default implementation of the {@link IPronunciation} interface.
 * See there for details.
 * @author Christian M. Meyer
 */
@Persistent
public class Pronunciation implements IPronunciation {
		
	protected PronunciationType type;
	protected String text;
	protected String note;
	
	/** Creates a new, empty pronunciation. */ 
	public Pronunciation() {}
	
	/** Creates a new pronunciation for the given representation text, 
	 *  notation type and addition information. For audio files, the 
	 *  representation text refers to an audio file name. */
	public Pronunciation(final PronunciationType type,
			final String text, final String note) {
		this.type = type;
		this.text = text;
		this.note = note;
	}
	
	public PronunciationType getType() {
		return type;
	}
	
	public String getText() {
		return text;
	}
	
	public String getNote() {
		return note;
	}
	
}
