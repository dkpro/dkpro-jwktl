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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryCollection;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryEntryFilter;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryPageFilter;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionarySenseFilter;
import de.tudarmstadt.ukp.jwktl.api.util.HierarchicalWiktionaryIterator;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.IWiktionaryIterator;
import de.tudarmstadt.ukp.jwktl.api.util.Language;

/**
 * Default implementation of the {@link IWiktionaryCollection} interface. The 
 * implementation can be initialized with multiple {@link IWiktionaryEdition}s
 * in order to access parsed Wiktionary information of multiple language 
 * editions.
 * @author Christian M. Meyer
 */
public class WiktionaryCollection extends AbstractWiktionary 
		implements IWiktionaryCollection {
	
	protected List<IWiktionaryEdition> editions;

	/** Initializes the Wiktionary collection. */
	public WiktionaryCollection() {
		editions = new LinkedList<IWiktionaryEdition>();
	}

	public void addEdition(final IWiktionaryEdition edition) {
		if (!editions.isEmpty() && isClosed())
			throw new IllegalStateException("WiktionaryCollection was already closed.");
		editions.add(edition);
	}

	public Iterable<IWiktionaryEdition> getEditions() {
		return editions;
	}

	public void removeEdition(final IWiktionaryEdition edition) {
		editions.remove(edition);
	}

	
	// -- Pages --
	
	public List<IWiktionaryPage> getPagesForId(long id) {
		List<IWiktionaryPage> result = new ArrayList<IWiktionaryPage>();
		for (IWiktionaryEdition edition : editions) {
			IWiktionaryPage page = edition.getPageForId(id);
			if (page != null)
				result.add(page);
		}
		return result;
	}
	
	public IWiktionaryPage getPageForId(long id, final ILanguage entryLanguage) {
		for (IWiktionaryEdition edition : editions)
			if (entryLanguage.equals(edition.getLanguage()))
				return edition.getPageForId(id);
					
		return null;
	}
	
	public List<IWiktionaryPage> getPagesForWord(final String word) {
		return getPagesForWord(word, null);
	}
	
	public List<IWiktionaryPage> getPagesForWord(final String word,
			final IWiktionaryPageFilter filter) {
		List<IWiktionaryPage> result = new ArrayList<IWiktionaryPage>();
		for (IWiktionaryEdition edition : editions) {
			IWiktionaryPage page = edition.getPageForWord(word);
			if (page != null && (filter == null || filter.accept(page)))
				result.add(page);
		}
		return result;
	}
	
	public List<IWiktionaryPage> getPagesForWord(final String word,
			final IWiktionaryPageFilter filter, boolean normalize) {
		List<IWiktionaryPage> result = new ArrayList<IWiktionaryPage>();
		for (IWiktionaryEdition edition : editions)
			result.addAll(edition.getPagesForWord(word, filter, normalize));
		return result;
	}
	
	public IWiktionaryIterator<IWiktionaryPage> getAllPages(
			final IWiktionaryPageFilter filter, final boolean sortByTitle, 
			final boolean normalize) {
		return new HierarchicalWiktionaryIterator<IWiktionaryPage, IWiktionaryEdition>(editions.iterator()){
			protected IWiktionaryIterator<IWiktionaryPage> getInnerIterator(
					final IWiktionaryEdition edition) {
				return edition.getAllPages(filter, sortByTitle, normalize);
			}
		};
	}

	
	// -- Entries --

	public List<IWiktionaryEntry> getEntriesForWord(final String word,
			final IWiktionaryEntryFilter filter, boolean normalize) {
		List<IWiktionaryEntry> result = new ArrayList<IWiktionaryEntry>();
		for (IWiktionaryEdition edition : editions)
			result.addAll(edition.getEntriesForWord(word, filter, normalize));
		return result;
	}
	
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			final IWiktionaryEntryFilter filter, 
			final boolean sortByTitle, final boolean normalize) {
		return new HierarchicalWiktionaryIterator<IWiktionaryEntry, IWiktionaryEdition>(editions.iterator()){
			protected IWiktionaryIterator<IWiktionaryEntry> getInnerIterator(
					final IWiktionaryEdition edition) {
				return edition.getAllEntries(filter, sortByTitle, normalize);
			}
		};
	}

	
	// -- Senses --

	public IWiktionarySense getSenseForKey(final String key, 
			final ILanguage language) {
		for (IWiktionaryEdition edition : editions)
			if (Language.equals(edition.getLanguage(), language))
				return edition.getSenseForKey(key);
		return null;
	}

	public List<IWiktionarySense> getSensesForWord(final String word,
			final IWiktionarySenseFilter filter, boolean normalize) {
		List<IWiktionarySense> result = new ArrayList<IWiktionarySense>();
		for (IWiktionaryEdition edition : editions)
			result.addAll(edition.getSensesForWord(word, filter, normalize));
		return result;
	}
	
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			final IWiktionarySenseFilter filter, final boolean sortByTitle, 
			final boolean normalize) {
		return new HierarchicalWiktionaryIterator<IWiktionarySense, IWiktionaryEdition>(editions.iterator()){
			protected IWiktionaryIterator<IWiktionarySense> getInnerIterator(
					final IWiktionaryEdition edition) {
				return edition.getAllSenses(filter, sortByTitle, normalize);
			}
		};
	}


	// -- Close --
	
	public void close() {
		for (IWiktionaryEdition edition : editions)
			edition.close();
	}

	public boolean isClosed() {
		if (editions.isEmpty())
			return true;
		
		for (IWiktionaryEdition edition : editions)
			if (edition.isClosed())
				return true;

		return false;
	}

}
