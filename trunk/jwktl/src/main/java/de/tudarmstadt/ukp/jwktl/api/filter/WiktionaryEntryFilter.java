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
package de.tudarmstadt.ukp.jwktl.api.filter;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;

/**
 * Default implementation of the {@link IWiktionaryEntryFilter} interface
 * which inherits all filter options of the {@link WiktionaryPageFilter}
 * and additionally provides the possibility of filtering entries by word
 * language and part of speech.
 * @author Christian M. Meyer
 */
public class WiktionaryEntryFilter extends WiktionaryPageFilter 
		implements IWiktionaryEntryFilter {

	protected Set<ILanguage> allowedWordLanguages;
	protected Set<PartOfSpeech> allowedPartsOfSpeech;
	
	/** Initializes a page filter without any filter restrictions. */
	public WiktionaryEntryFilter() {
		super();
		allowedWordLanguages = new TreeSet<ILanguage>();
		allowedPartsOfSpeech = new TreeSet<PartOfSpeech>();		
	}
	
	/** Shorthand for setting the allowed entry language, word language,
	 *  and parts of speech. Invoking this constructor is equivalent to 
	 *  using the default constructor and calling the corresponding 
	 *  setter methods. */
	public WiktionaryEntryFilter(final Set<ILanguage> allowedEntryLanguages,
			final Set<ILanguage> allowedWordLanguages,
			final Set<PartOfSpeech> allowedPartsOfSpeech) {
		this();
		setAllowedEntryLanguages(allowedEntryLanguages);
		setAllowedWordLanguages(allowedWordLanguages);
		setAllowedPartsOfSpeech(allowedPartsOfSpeech);
	}
	
	@Override
	public void clear() {
		super.clear();
		clearAllowedWordLanguages();
		clearAllowedPartsOfSpeech();
	}
	
	// -- Word languages --

	/** Clears the list of allowed word languages. */
	public void clearAllowedWordLanguages() {
		allowedWordLanguages.clear();
	}
	
	/** Return an iterable of the list of all allowed word languages. */
	public Iterable<ILanguage> getAllowedWordLanguages() {
		return allowedWordLanguages;
	}
	
	/** Define the set of word languages which are allowed for a 
	 * {@link IWiktionaryEntry}. If no language is specified, the restriction
	 * on the word language will be cleared. */
	public void setAllowedWordLanguages(final ILanguage... allowedWordLanguages) {
		clearAllowedWordLanguages();
		for (ILanguage language : allowedWordLanguages)
			this.allowedWordLanguages.add(language);
	}
	
	/** Define the set of word languages which are allowed for a 
	 * {@link IWiktionaryEntry}. If no language is specified, the restriction
	 * on the word language will be cleared. */
	public void setAllowedWordLanguages(final Collection<ILanguage> allowedWordLanguages) {
		clearAllowedWordLanguages();
		if (allowedWordLanguages != null)
			for (ILanguage language : allowedWordLanguages)
				this.allowedWordLanguages.add(language);
	}

	protected boolean acceptWordLanguage(final IWiktionaryEntry entry) {
		if (allowedWordLanguages.size() == 0)
			return true;
		
		return (allowedWordLanguages.contains(entry.getWordLanguage()));
	}
	
	// -- Parts of speech --
	
	/** Clears the list of allowed parts of speech. */
	public void clearAllowedPartsOfSpeech() {
		allowedPartsOfSpeech.clear();
	}
	
	/** Return an iterable of the list of all allowed parts of speech. */
	public Iterable<PartOfSpeech> getAllowedPartsOfSpeech() {
		return allowedPartsOfSpeech;
	}
	
	/** Define the set of parts of speech which are allowed for a 
	 * {@link IWiktionaryEntry}. If no tag is specified, the restriction
	 * on the parts of speech will be cleared. */
	public void setAllowedPartsOfSpeech(final PartOfSpeech... allowedPartsOfSpeech) {
		clearAllowedPartsOfSpeech();
		for (PartOfSpeech pos : allowedPartsOfSpeech)
			this.allowedPartsOfSpeech.add(pos);
	}

	/** Define the set of parts of speech which are allowed for a 
	 * {@link IWiktionaryEntry}. If no tag is specified, the restriction
	 * on the parts of speech will be cleared. */
	public void setAllowedPartsOfSpeech(final Collection<PartOfSpeech> allowedPartsOfSpeech) {
		clearAllowedPartsOfSpeech();
		if (allowedPartsOfSpeech != null)
			for (PartOfSpeech pos : allowedPartsOfSpeech)
				this.allowedPartsOfSpeech.add(pos);
	}
	
	protected boolean acceptPartOfSpeech(final IWiktionaryEntry entry) {
		if (allowedPartsOfSpeech.size() == 0)
			return true;
		
		return (allowedPartsOfSpeech.contains(entry.getPartOfSpeech()));
		//TODO: multiple POS?
	}
	
	public boolean accept(final IWiktionaryEntry entry) {
		if (!accept(entry.getPage()))
			return false;
		
		if (!acceptWordLanguage(entry))
			return false;
		
		if (!acceptPartOfSpeech(entry))
			return false;
		
		return true;
	}
	
}
