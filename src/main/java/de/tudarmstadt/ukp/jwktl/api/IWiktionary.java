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

import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryEntryFilter;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryPageFilter;
import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionarySenseFilter;
import de.tudarmstadt.ukp.jwktl.api.util.IWiktionaryIterator;

/**
 * Base interface for the Wiktionary language edition and collection modelling
 * common methods for querying and iterating over the encoded entries.
 * @author Christian M. Meyer
 */
public interface IWiktionary {

	// -- Pages --
	
	/** Returns the page with the given title. The method returns also pages,
	 *  whose title matches in a case insensitive or string-normalized manner.
	 *  The latter means that strings are converted to lower case, and 
	 *  umlauts or accents are substituted by their canonical form. The
	 *  word "prêt-à-porter" is, e.g., normalized to "pret-a-porter". Use
	 *  <code>false</code> for exact matches.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionaryPage> getPagesForWord(final String word, 
			boolean normalize);

	/** Returns the page with the given title. The method returns also pages,
	 *  whose title matches in a case insensitive or string-normalized manner.
	 *  The latter means that strings are converted to lower case, and 
	 *  umlauts or accents are substituted by their canonical form. The
	 *  word "prêt-à-porter" is, e.g., normalized to "pret-a-porter". Using the 
	 *  given {@link IWiktionaryPageFilter}, unwanted pages can be ignored.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionaryPage> getPagesForWord(final String word, 
			final IWiktionaryPageFilter filter,
			boolean normalize);
	
	
	/** Returns an iterator over all {@link IWiktionaryPage}s within 
	 *  the Wiktionary edition. The pages are sorted by their page id. */
	public IWiktionaryIterator<IWiktionaryPage> getAllPages();
	
	/** Returns an iterator over all {@link IWiktionaryPage}s within 
	 *  the Wiktionary edition. 
	 *  @param sortByTitle if <code>true</code> sort by page title 
	 *    (case sensitive); otherwise by page id. */
	public IWiktionaryIterator<IWiktionaryPage> getAllPages(
			final boolean sortByTitle);
	
	/** Returns an iterator over all {@link IWiktionaryPage}s within 
	 *  the Wiktionary edition. 
	 *  @param sortByTitle if <code>true</code> sort by page title;
	 *    otherwise by page id.  
	 *  @param normalize if <code>true</code> sort case insensitive;
	 *    otherwise case sensitive (only affects sorting by title). */
	public IWiktionaryIterator<IWiktionaryPage> getAllPages(
			final boolean sortByTitle, final boolean normalize);

	/** Returns an iterator over all {@link IWiktionaryPage}s within 
	 *  the Wiktionary edition. The pages are sorted by their page id. 
	 *  Using the given {@link IWiktionaryPageFilter}, unwanted pages 
	 *  can be ignored. */
	public IWiktionaryIterator<IWiktionaryPage> getAllPages(
			final IWiktionaryPageFilter filter);
	
	/** Returns an iterator over all {@link IWiktionaryPage}s within 
	 *  the Wiktionary edition. Using the given {@link IWiktionaryPageFilter}, 
	 *  unwanted pages can be ignored. 
	 *  @param sortByTitle if <code>true</code> sort by page title 
	 *    (case sensitive); otherwise by page id. */
	public IWiktionaryIterator<IWiktionaryPage> getAllPages(
			final IWiktionaryPageFilter filter,
			final boolean sortByTitle);
	
	/** Returns an iterator over all {@link IWiktionaryPage}s within 
	 *  the Wiktionary edition. Using the given {@link IWiktionaryPageFilter}, 
	 *  unwanted pages can be ignored. 
	 *  @param sortByTitle if <code>true</code> sort by page title;
	 *    otherwise by page id.  
	 *  @param normalize if <code>true</code> sort case insensitive;
	 *    otherwise case sensitive (only affects sorting by title). */
	public IWiktionaryIterator<IWiktionaryPage> getAllPages(
			final IWiktionaryPageFilter filter,
			final boolean sortByTitle, final boolean normalize);
	
	
	// -- Entries --
	
	/** Returns a list of {@link IWiktionaryEntry}s encoded on a page with 
	 *  the given title. The method only returns an entry if the page title 
	 *  matches exactly. Use {@link #getEntriesForWord(String, boolean)} for 
	 *  case insensitive and string-normalized matching.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionaryEntry> getEntriesForWord(final String word);
	
	/** Returns a list of {@link IWiktionaryEntry}s encoded on a page with 
	 *  the given title. 
	 *  @param normalize if <code>true</code>, match the page title in a 
	 *    case insensitive manner.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionaryEntry> getEntriesForWord(final String word,
			boolean normalize);

	/** Returns a list of {@link IWiktionaryEntry}s encoded on a page with 
	 *  the given title. The method only returns an entry if the page title 
	 *  matches exactly. Use {@link #getEntriesForWord(String, boolean)} for 
	 *  case insensitive and string-normalized matching. Using the given 
	 *  {@link IWiktionaryEntryFilter}, unwanted entries can be ignored.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionaryEntry> getEntriesForWord(final String word,
			final IWiktionaryEntryFilter filter);
	
	/** Returns a list of {@link IWiktionaryEntry}s encoded on a page with 
	 *  the given title. Using the given  {@link IWiktionaryEntryFilter}, 
	 *  unwanted entries can be ignored. 
	 *  @param normalize if <code>true</code>, match the page title in a 
	 *    case insensitive manner.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionaryEntry> getEntriesForWord(final String word,
			final IWiktionaryEntryFilter filter,
			boolean normalize);
	
	/** Returns an iterator over all {@link IWiktionaryEntry}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages()} and then
	 *  over the page's entries using {@link IWiktionaryPage#getEntries()}.
	 *  The pages are sorted by their page id; the entries by their
	 *  index. */
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries();
	
	/** Returns an iterator over all {@link IWiktionaryEntry}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages(boolean)} and then
	 *  over the page's entries using {@link IWiktionaryPage#getEntries()}.
	 *  The pages are sorted according to the method's parameters; the 
	 *  entries are sorted by their index.
	 *  @param sortByTitle if <code>true</code> sort by page title 
	 *    (case sensitive); otherwise by page id. */
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			final boolean sortByTitle);
	
	/** Returns an iterator over all {@link IWiktionaryEntry}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages(boolean, boolean)} 
	 *  and then over the page's entries using 
	 *  {@link IWiktionaryPage#getEntries()}.
	 *  The pages are sorted according to the method's parameters; the 
	 *  entries are sorted by their index. 
	 *  @param sortByTitle if <code>true</code> sort by page title;
	 *    otherwise by page id.  
	 *  @param normalize if <code>true</code> sort case insensitive;
	 *    otherwise case sensitive (only affects sorting by title). */
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			final boolean sortByTitle, final boolean normalize);

	/** Returns an iterator over all {@link IWiktionaryEntry}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages()} and then
	 *  over the page's entries using {@link IWiktionaryPage#getEntries()}.
	 *  The pages are sorted by their page id; the entries by their
	 *  index. Using the given  {@link IWiktionaryEntryFilter}, unwanted 
	 *  entries can be ignored. */
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			final IWiktionaryEntryFilter filter);
	
	/** Returns an iterator over all {@link IWiktionaryEntry}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages(boolean)} and then
	 *  over the page's entries using {@link IWiktionaryPage#getEntries()}.
	 *  The pages are sorted according to the method's parameters; the 
	 *  entries are sorted by their index. Using the given 
	 *  {@link IWiktionaryEntryFilter}, unwanted entries can be ignored.
	 *  @param sortByTitle if <code>true</code> sort by page title 
	 *    (case sensitive); otherwise by page id. */
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			final IWiktionaryEntryFilter filter,
			final boolean sortByTitle);
	
	/** Returns an iterator over all {@link IWiktionaryEntry}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages(boolean, boolean)} 
	 *  and then over the page's entries using 
	 *  {@link IWiktionaryPage#getEntries()}.
	 *  The pages are sorted according to the method's parameters; the 
	 *  entries are sorted by their index. Using the given 
	 *  {@link IWiktionaryEntryFilter}, unwanted entries can be ignored. 
	 *  @param sortByTitle if <code>true</code> sort by page title;
	 *    otherwise by page id.  
	 *  @param normalize if <code>true</code> sort case insensitive;
	 *    otherwise case sensitive (only affects sorting by title). */
	public IWiktionaryIterator<IWiktionaryEntry> getAllEntries(
			final IWiktionaryEntryFilter filter,
			final boolean sortByTitle, final boolean normalize);

	
	// -- Senses --

	/** Returns a list of {@link IWiktionarySense}s encoded on a page with 
	 *  the given title. The method only returns the senses if the page title 
	 *  matches exactly. Use {@link #getSensesForWord(String, boolean)} for 
	 *  case insensitive and string-normalized matching.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionarySense> getSensesForWord(final String word);
	
	/** Returns a list of {@link IWiktionarySense}s encoded on a page with 
	 *  the given title. 
	 *  @param normalize if <code>true</code>, match the page title in a 
	 *    case insensitive manner.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionarySense> getSensesForWord(final String word, 
			boolean normalize);

	/** Returns a list of {@link IWiktionarySense}s encoded on a page with 
	 *  the given title. The method only returns the senses if the page title 
	 *  matches exactly. Use {@link #getSensesForWord(String, boolean)} for 
	 *  case insensitive and string-normalized matching. Using the given 
	 *  {@link IWiktionarySenseFilter}, unwanted word senses can be ignored.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionarySense> getSensesForWord(final String word,
			final IWiktionarySenseFilter filter);
	
	/** Returns a list of {@link IWiktionarySense}s encoded on a page with 
	 *  the given title. Using the given  {@link IWiktionarySenseFilter}, 
	 *  unwanted word senses can be ignored. 
	 *  @param normalize if <code>true</code>, match the page title in a 
	 *    case insensitive manner.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionarySense> getSensesForWord(final String word,
			final IWiktionarySenseFilter filter,
			boolean normalize);
	
//	public List<IWiktionarySense> getSensesForWord(final String word,
//			int entryIdx);

	/** Returns an iterator over all {@link IWiktionarySense}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages()} and then
	 *  over the page's entries using {@link IWiktionaryPage#getEntries()}
	 *  and then over the entry's senses using 
	 *  {@link IWiktionaryEntry#getSenses()}.
	 *  The pages are sorted by their page id; the entries and senses by 
	 *  their index. */
	public IWiktionaryIterator<IWiktionarySense> getAllSenses();
	
	/** Returns an iterator over all {@link IWiktionaryEntry}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages(boolean)} and then
	 *  over the page's entries using {@link IWiktionaryPage#getEntries()}
	 *  and then over the entry's senses using 
	 *  {@link IWiktionaryEntry#getSenses()}.
	 *  The pages are sorted according to the method's parameters; the 
	 *  entries and senses are sorted by their index.
	 *  @param sortByTitle if <code>true</code> sort by page title 
	 *    (case sensitive); otherwise by page id. */
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			final boolean sortByTitle);
	
	/** Returns an iterator over all {@link IWiktionaryEntry}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages(boolean, boolean)} 
	 *  and then over the page's entries using 
	 *  {@link IWiktionaryPage#getEntries()} and then over the entry's 
	 *  senses using {@link IWiktionaryEntry#getSenses()}.
	 *  The pages are sorted according to the method's parameters; the 
	 *  entries and senses are sorted by their index. 
	 *  @param sortByTitle if <code>true</code> sort by page title;
	 *    otherwise by page id.  
	 *  @param normalize if <code>true</code> sort case insensitive;
	 *    otherwise case sensitive (only affects sorting by title). */
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			final boolean sortByTitle, final boolean normalize);

	/** Returns an iterator over all {@link IWiktionarySense}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages()} and then
	 *  over the page's entries using {@link IWiktionaryPage#getEntries()}
	 *  and then over the entry's senses using 
	 *  {@link IWiktionaryEntry#getSenses()}.
	 *  The pages are sorted by their page id; the entries and senses by 
	 *  their index. Using the given {@link IWiktionarySenseFilter}, unwanted 
	 *  word senses can be ignored. */
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			final IWiktionarySenseFilter filter);
	
	/** Returns an iterator over all {@link IWiktionaryEntry}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages(boolean)} and then
	 *  over the page's entries using {@link IWiktionaryPage#getEntries()}
	 *  and then over the entry's senses using 
	 *  {@link IWiktionaryEntry#getSenses()}.
	 *  The pages are sorted according to the method's parameters; the 
	 *  entries and senses are sorted by their index. Using the given 
	 *  {@link IWiktionarySenseFilter}, unwanted word senses can be ignored.
	 *  @param sortByTitle if <code>true</code> sort by page title 
	 *    (case sensitive); otherwise by page id. */
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			final IWiktionarySenseFilter filter,
			final boolean sortByTitle);
	
	/** Returns an iterator over all {@link IWiktionaryEntry}s within 
	 *  the Wiktionary edition. This is equivalent to iterating over all 
	 *  pages using {@link IWiktionaryEdition#getAllPages(boolean, boolean)} 
	 *  and then over the page's entries using 
	 *  {@link IWiktionaryPage#getEntries()} and then over the entry's 
	 *  senses using {@link IWiktionaryEntry#getSenses()}.
	 *  The pages are sorted according to the method's parameters; the 
	 *  entries and senses are sorted by their index. Using the given 
	 *  {@link IWiktionarySenseFilter}, unwanted word senses can be ignored. 
	 *  @param sortByTitle if <code>true</code> sort by page title;
	 *    otherwise by page id.  
	 *  @param normalize if <code>true</code> sort case insensitive;
	 *    otherwise case sensitive (only affects sorting by title). */
	public IWiktionaryIterator<IWiktionarySense> getAllSenses(
			final IWiktionarySenseFilter filter,
			final boolean sortByTitle, final boolean normalize);

	// -- Close --
	
	/** Disconnects from the database. This is necessary to ensure the 
	 *  consistency of the data. No retrieval methods can be called after
	 *  closing the connection, so this should be done prior to terminating
	 *  the application. Nothing happens on calling the method more than 
	 *  once. */
	public void close();

	/** Returns <code>true</code> if the database connection has already been 
	 *  closed using the {@link #close()} method. */
	public boolean isClosed();
	
}
