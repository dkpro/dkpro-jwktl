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
import java.util.Iterator;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryEntryFilter;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionarySenseFilter;
import de.tudarmstadt.ukp.jwktl.api.util.IWiktionaryIterator;
import de.tudarmstadt.ukp.jwktl.api.util.WiktionaryIterator;

/**
 * Abstract base class for implementations of {@link IWiktionaryEdition}s.
 * @author Christian M. Meyer
 */
public abstract class WiktionaryEdition extends AbstractWiktionary
		implements IWiktionaryEdition {

	protected boolean isClosed;
	
	/** Initializes the Wiktionary edition. */
	public WiktionaryEdition() {
		isClosed = false;
	}
	
	// -- Entries --

	public IWiktionaryEntry getEntryForId(long pageId, int entryIdx) {
		ensureOpen();
		IWiktionaryPage page = getPageForId(pageId); 
		return (page == null ? null : page.getEntries().get(entryIdx));
	}

	public IWiktionaryEntry getEntryForWord(final String word,
			int entryIdx) {
		IWiktionaryPage page = getPageForWord(word); 
		return (page == null ? null : page.getEntries().get(entryIdx));
	}
	
	public List<IWiktionaryEntry> getEntriesForWord(final String word,
			final IWiktionaryEntryFilter filter, boolean normalize) {
		ensureOpen();
		List<IWiktionaryEntry> result = new ArrayList<IWiktionaryEntry>();
		if (word == null || word.isEmpty())
			return result;
		
		List<IWiktionaryPage> pages = getPagesForWord(word, normalize);
		for (IWiktionaryPage page : pages)
			for (IWiktionaryEntry entry : page.getEntries())
				if (filter == null || filter.accept(entry))
					result.add(entry);
		
		return result;
	}

	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			final IWiktionaryEntryFilter filter, 
			final boolean sortByTitle, final boolean normalize) {
		ensureOpen();
		return new WiktionaryIterator<IWiktionaryEntry>() {
			
			protected IWiktionaryIterator<IWiktionaryPage> allPages
					= getAllPages(null, sortByTitle, normalize);
			protected Iterator<IWiktionaryEntry> entries;
			
			protected IWiktionaryEntry fetchNext() {
				do {
					// If there are no entries left, try to fetch the next page.
					if (entries == null || !entries.hasNext())
						if (fetchNextPage() == null)
							return null; // No page left.
					
					// As long as entries are left, return them!
					if (entries != null && entries.hasNext()) {
						IWiktionaryEntry result = entries.next();
						if (filter == null || filter.accept(result))
							return result;
					}
				} while (true);
			}
			
			protected IWiktionaryPage fetchNextPage() {
				if (!allPages.hasNext())
					return null;

				IWiktionaryPage result = allPages.next();
				entries = result.getEntries().iterator();
				return result;
			}
			
			protected void doClose() {
				allPages.close();
			}
			
		};
	}

	
	// -- Senses --

	public IWiktionarySense getSenseForId(long entryId, int senseIdx) {
		ensureOpen();
		IWiktionaryEntry entry = getEntryForId(entryId);
		return (entry == null ? null : entry.getSense(senseIdx));
	}
	
	public IWiktionarySense getSenseForId(long pageId, int entryIdx, int senseIdx) {
		ensureOpen();
		IWiktionaryEntry entry = getEntryForId(pageId, entryIdx);
		return (entry == null ? null : entry.getSense(senseIdx));
	}

	public List<IWiktionarySense> getSensesForWord(final String word,
			int entryIdx) {
		ensureOpen();
		IWiktionaryEntry entry = getEntryForWord(word, entryIdx);
		List<IWiktionarySense> result = new ArrayList<IWiktionarySense>();
		for (IWiktionarySense sense : entry.getSenses())
			result.add(sense);
		return result;
//		return (entry == null ? null : entry.getSenses());
	}
	
	public IWiktionarySense getSensesForWord(final String word,
			int entryIdx, int senseIdx) {
		ensureOpen();
		IWiktionaryEntry entry = getEntryForWord(word, entryIdx);
		return (entry == null ? null : entry.getSense(senseIdx));
	} 
	
	public List<IWiktionarySense> getSensesForWord(final String word,
			final IWiktionarySenseFilter filter, boolean normalize) {
		ensureOpen();
		List<IWiktionarySense> result = new ArrayList<IWiktionarySense>();
		if (word == null || word.isEmpty())
			return result;
		
		List<IWiktionaryPage> pages = getPagesForWord(word, normalize);
		for (IWiktionaryPage page : pages)
			for (IWiktionaryEntry entry : page.getEntries())
				for (IWiktionarySense sense : entry.getSenses())
					if (filter == null || filter.accept(sense))
						result.add(sense);
		
		return result;
	}
		
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			final IWiktionarySenseFilter filter, 
			final boolean sortByTitle, final boolean normalize) {
		ensureOpen();
		return new WiktionaryIterator<IWiktionarySense>() {
			
			protected IWiktionaryIterator<IWiktionaryEntry> allEntries
					= getAllEntries(sortByTitle, normalize);
			protected Iterator<IWiktionarySense> senses;
			
			protected IWiktionarySense fetchNext() {
				do {
					if (senses == null || !senses.hasNext())
						if (fetchNextEntry() == null)
							return null;
					
					if (senses != null && senses.hasNext()) {
						IWiktionarySense result = senses.next();
						if (filter == null || filter.accept(result))
							return result;
					}
				} while (true);
			}

			protected IWiktionaryEntry fetchNextEntry() {
				if (!allEntries.hasNext())
					return null;

				IWiktionaryEntry result = allEntries.next();
				senses = result.getSenses().iterator();
				return result;
			}
			
			protected void doClose() {
				allEntries.close();
			}
			
		};
	}
	
	
	// -- Close --

	/** Disconnects from the database. This is necessary to ensure the 
	 *  consistency of the data. No retrieval methods can be called after
	 *  closing the connection, so this should be done prior to terminating
	 *  the application. Nothing happens on calling the method more than 
	 *  once. */
	public void close() {
		if (isClosed) 
			return;
		
		doClose();
		isClosed = true;
	}

	/** Returns if the connection has already been closed. If so, no
	 *  retrieval method can be called anymore. To close an open
	 *  connection, use the {@link #close()} method. */
	public boolean isClosed() {
		return isClosed;
	}
	
	protected abstract void doClose();

	/** @throws IllegalStateException if the connection has already been 
	 * 		closed. This method is to be called from each retrieval method.  */
	protected void ensureOpen() {
		if (isClosed)
			throw new IllegalStateException("Wiktionary was already closed.");
	}
	
}
