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

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;

/**
 * Default implementation of the {@link IWiktionaryTranslation} interface.
 * See there for details.
 * @author Christian M. Meyer
 */
@Persistent
public class WiktionaryTranslation implements IWiktionaryTranslation {

	protected transient ILanguage language;
	protected String languageStr;
	protected String translation;
	protected String transliteration;
	protected String additionalInformation;
	
	/** Creates a new, empty translation. */ 
	public WiktionaryTranslation() {}
	
	/** Creates a new translation for the given language and translation 
	 *  string. */
	public WiktionaryTranslation(final ILanguage language, final String translation) {
		this.language = language;
		if (language != null)
			languageStr = language.getCode();
		this.translation = translation;
	}
	
	public ILanguage getLanguage() {
		if (language == null && languageStr != null)
			language = Language.get(languageStr);
		return language;
	}

	public String getTranslation() {
		return translation;
	}

	public String getTransliteration() {
		return transliteration;
	}
	
	/** Assigns the given transliteration to this translation. */
	public void setTransliteration(final String transliteration) {
		this.transliteration = transliteration;
	}
	
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	
	/** Assigns the given additional information to this translation. */
	public void setAdditionalInformation(final String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	@Override
	public String toString() {
		return languageStr + ":" + translation;
	}
	
}
