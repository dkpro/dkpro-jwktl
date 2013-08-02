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

import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionary;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryEntryFilter;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryPageFilter;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionarySenseFilter;
import de.tudarmstadt.ukp.jwktl.api.util.IWiktionaryIterator;

/**
 * Default implementation of the {@link IWiktionary} interface.
 * @author Christian M. Meyer
 */
public abstract class AbstractWiktionary
		implements IWiktionary {

	// -- Pages --

	public List<IWiktionaryPage> getPagesForWord(final String word, 
			boolean normalize) {
		return getPagesForWord(word, null, normalize);
	}

	public abstract List<IWiktionaryPage> getPagesForWord(final String word,
			final IWiktionaryPageFilter filter, boolean normalize);
	
	public IWiktionaryIterator<IWiktionaryPage> getAllPages() {
		return getAllPages(null, false, false);
	}
	
	public IWiktionaryIterator<IWiktionaryPage> getAllPages(boolean sortByTitle) {
		return getAllPages(null, sortByTitle, false);
	}
	
	public IWiktionaryIterator<IWiktionaryPage> getAllPages(boolean sortByTitle,
			boolean normalize) {
		return getAllPages(null, sortByTitle, normalize);
	}
	
	public IWiktionaryIterator<IWiktionaryPage> getAllPages(
			final IWiktionaryPageFilter filter) {
		return getAllPages(filter, false, false);
	}
	
	public IWiktionaryIterator<IWiktionaryPage> getAllPages(
			final IWiktionaryPageFilter filter, boolean sortByTitle) {
		return getAllPages(filter, sortByTitle, false);
	}

	public abstract IWiktionaryIterator<IWiktionaryPage> getAllPages(
			final IWiktionaryPageFilter filter, boolean sortByTitle, 
			boolean normalize);
	

	// -- Entries --

	public List<IWiktionaryEntry> getEntriesForWord(final String word) {
		return getEntriesForWord(word, null, false);
	}

	public List<IWiktionaryEntry> getEntriesForWord(final String word,
			boolean normalize) {
		return getEntriesForWord(word, null, normalize);
	}

	public List<IWiktionaryEntry> getEntriesForWord(final String word,
			final IWiktionaryEntryFilter filter) {
		return getEntriesForWord(word, filter, false);
	}

	public abstract List<IWiktionaryEntry> getEntriesForWord(final String word,
			final IWiktionaryEntryFilter filter, boolean normalize);

	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries() {
		return getAllEntries(null, false, false);
	}
	
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			boolean sortByTitle) {
		return getAllEntries(null, sortByTitle, false);
	}
	
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			boolean sortByTitle, boolean normalize) {
		return getAllEntries(null, sortByTitle, normalize);
	}
	
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			final IWiktionaryEntryFilter filter) {
		return getAllEntries(filter, false, false);
	}
	
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			final IWiktionaryEntryFilter filter, boolean sortByTitle) {
		return getAllEntries(filter, sortByTitle, false);
	}

	public abstract IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			final IWiktionaryEntryFilter filter, 
			boolean sortByTitle, boolean normalize);


	// -- Senses --

	public List<IWiktionarySense> getSensesForWord(final String word) {
		return getSensesForWord(word, null, false);
	}

	public List<IWiktionarySense> getSensesForWord(final String word, 
			boolean normalize) {
		return getSensesForWord(word, null, normalize);
	}

	public List<IWiktionarySense> getSensesForWord(final String word,
			final IWiktionarySenseFilter filter) {
		return getSensesForWord(word, filter, false);
	}

	public abstract List<IWiktionarySense> getSensesForWord(final String word,
			final IWiktionarySenseFilter filter, boolean normalize);

	public IWiktionaryIterator<IWiktionarySense> getAllSenses() {
		return getAllSenses(null, false, false);
	}
	
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			boolean sortByTitle) {
		return getAllSenses(null, sortByTitle, false);
	}
	
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			boolean sortByTitle, boolean normalize) {
		return getAllSenses(null, sortByTitle, normalize);
	}
	
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			final IWiktionarySenseFilter filter) {
		return getAllSenses(filter, false, false);
	}
	
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			final IWiktionarySenseFilter filter, boolean sortByTitle) {
		return getAllSenses(filter, sortByTitle, false);
	}

	public abstract IWiktionaryIterator<IWiktionarySense> getAllSenses(
			final IWiktionarySenseFilter filter, boolean sortByTitle, 
			boolean normalize);

}
