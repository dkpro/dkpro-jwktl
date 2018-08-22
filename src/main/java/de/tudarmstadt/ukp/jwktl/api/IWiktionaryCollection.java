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

import de.tudarmstadt.ukp.jwktl.api.filter.IWiktionaryPageFilter;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;

/**
 * Models a list of multiple Wiktionary language editions (e.g., the 
 * English and the German Wiktionary editions). Using the querying and 
 * iteration methods provided by this interface, the information from
 * all attached language editions may be processed at once and their 
 * information can be combined.
 * @author Christian M. Meyer
 */
public interface IWiktionaryCollection extends IWiktionary {

	 /** Add the specified Wiktionary language edition to the current 
	  *  collection. Querying the collection will then aggregate 
	  *  information from all added editions.
	  *  @param edition Wiktionary language edition to be added.
	  */
	 void addEdition(final IWiktionaryEdition edition);
	
	/** @return A list of all Wiktionary langauge editions in
	 *  this collection. */
	Iterable<IWiktionaryEdition> getEditions();
	
	/** Removes the specified edition from the collection. Note that
	 *  the removed edition is still open. If you do not need it anymore,
	 *  call additionally the {@link IWiktionaryEdition#close()} method.
	 *  @param edition Wiktionary language edition to be removed.
	 */
	void removeEdition(final IWiktionaryEdition edition);

	
	// -- Pages --
	
	/** @return The pages with the given (edition-specific) unique id.
	 *  @param id numeric edition-specific id of the pages.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	List<IWiktionaryPage> getPagesForId(long id);
	
	/** @return The page of the specified Wiktionary language edition with 
	 *  the given unique id.
	 *  @param id numeric edition-specific id of the page.
	 *  @param entryLanguage language of the edition.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionaryPage getPageForId(long id, final ILanguage entryLanguage);
	
	/** Returns all pages with the given title. The method only returns the
	 *  page if its title matches exactly. Use 
	 *  {@link #getPagesForWord(String, boolean)} for case insensitive and
	 *  string-normalized matching.
	 *  @param word word or title of the pages.
	 *  @return Returns all pages with the given title.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	List<IWiktionaryPage> getPagesForWord(final String word);

	/** Returns all pages with the given title. The method only returns the
	 *  page if its title matches exactly. Use 
	 *  {@link #getPagesForWord(String, boolean)} for case insensitive and
	 *  string-normalized matching. Using the given
	 *  {@link IWiktionaryPageFilter}, unwanted pages can be ignored.
	 *  @param word word or title of the pages.
	 *  @param filter filter for pages, may be <code>null</code> for no filtering.
	 *  @return Returns all pages with the given title.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	List<IWiktionaryPage> getPagesForWord(final String word,
										  final IWiktionaryPageFilter filter);

	
	// -- Entries --

//	public IWiktionaryEntry getEntryForId(long entryId); --
//	public List<IWiktionaryEntry> getEntriesForId(long entryId);
//	public IWiktionaryEntry getEntryForId(long entryId, final ILanguage entryLanguage);
	
//	public IWiktionaryEntry getEntryForId(long pageId, int entryIdx); --

//	public IWiktionaryEntry getEntryForWord(final String word,
//			int entryIdx); --
		
	
//	public void getEntriesForWord(final String word);
//	public void getEntriesForWord(final String word, final IWiktionaryEntryFilter filter);

//	public Iterable<IWiktionaryEntry> getAllEntries(boolean sortByTitle, boolean normalize, final IWiktionaryEntryFilter filter);
	
	
	// -- Senses --

	/** Returns the word sense with the given (edition-specific) unique id. 
	 *  Note that this id is only stable over the same XML dump and JWKTL 
	 *  version.
	 *  @param key edition-specific unique id of the sense.
	 *  @param language language of the sense.
	 *  @return The word sense with the given (edition-specific) unique id.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionarySense getSenseForKey(final String key, final ILanguage language);

//	public List<IWiktionarySense> getSensesForKey(final String id);

//	public IWiktionarySense getSenseForId(long entryId, int senseIdx); --
	
//	public IWiktionarySense getSenseForId(long pageId, int entryIdx, int senseIdx); --

//	public IWiktionarySense getSensesForWord(final String word,
//			int entryIdx, int senseIdx); --
	
}
