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

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryCollection;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;

/**
 * Default implementation of the {@link IWiktionaryPageFilter} interface
 * which provides the possibility of filtering out pages with certain
 * entry languages. This is useful if a {@link IWiktionaryCollection} is
 * used to access multiple {@link IWiktionaryEdition}s of different
 * entry languages.
 * @author Christian M. Meyer
 */
public class WiktionaryPageFilter implements IWiktionaryPageFilter {

	protected Set<ILanguage> allowedEntryLanguages;
	
	/** Initializes a page filter without any filter restrictions. */
	public WiktionaryPageFilter() {
		this.allowedEntryLanguages = new TreeSet<ILanguage>();
	}

	/** Clears all filter options. */
	public void clear() {
		clearAllowedEntryLanguages();
	}
	
	/** Clears the list of allowed entry languages. */
	public void clearAllowedEntryLanguages() {
		allowedEntryLanguages.clear();
	}
	
	/** Return an iterable of the list of all allowed entry languages. */
	public Iterable<ILanguage> getAllowedEntryLanguages() {
		return allowedEntryLanguages;
	}
	
	/** Define the set of entry languages which are allowed for a 
	 * {@link IWiktionaryPage}. If no language is specified, the restriction
	 * on the entry language will be cleared. */
	public void setAllowedEntryLanguages(final ILanguage... allowedEntryLanguages) {
		clearAllowedEntryLanguages();
		for (ILanguage language : allowedEntryLanguages)
			this.allowedEntryLanguages.add(language);
	}
	
	/** Define the set of entry languages which are allowed for a 
	 * {@link IWiktionaryPage}. If no language is specified, the restriction
	 * on the entry language will be cleared. */
	public void setAllowedEntryLanguages(final Collection<ILanguage> allowedEntryLanguages) {
		clearAllowedEntryLanguages();
		if (allowedEntryLanguages != null)
			for (ILanguage language : allowedEntryLanguages)
				this.allowedEntryLanguages.add(language);
	}

	protected boolean acceptEntryLanguage(final IWiktionaryPage page) {
		if (allowedEntryLanguages.size() == 0)
			return true;
		if (page.getEntryLanguage() == null)
			return false;
		
		return (allowedEntryLanguages.contains(page.getEntryLanguage()));
	}
	
	public boolean accept(final IWiktionaryPage page) {
		return acceptEntryLanguage(page);
	}
	
}
