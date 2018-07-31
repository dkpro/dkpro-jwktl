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

import java.io.File;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;

/**
 * Models a single Wiktionary language edition (e.g., the English Wiktionary)
 * for which this interface provides multiple querying and iteration methods.
 * @author Christian M. Meyer
 */
public interface IWiktionaryEdition extends IWiktionary {

	/** @return The language of the Wiktionary edition, which is equivalent
	 *  to the entry language of the contained entries. */
	ILanguage getLanguage();

	/** @return The file path of the parsed database. */
	File getDBPath();

	
	// -- Pages --
	
	/** @return The page with the given unique id.
	 *  @param id numeric id of the page.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionaryPage getPageForId(long id);
	
	/** @return The page with the given title. The method only returns the
	 *  page if its title matches exactly. Use 
	 *  {@link #getPagesForWord(String, boolean)} for case insensitive and
	 *  string-normalized matching.
	 *  @param word word or title of the page.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionaryPage getPageForWord(final String word);

	
	// -- Entries --

	/** @return The {@link IWiktionaryEntry} with the given entry id. Note 
	 *  that this id is only stable over the same XML dump and JWKTL version.
	 *  @param entryId numeric id of the entry.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionaryEntry getEntryForId(long entryId);
	
	/** @return The {@link IWiktionaryEntry} with the given page id and 
	 *  entry index. Note that this id combination is only stable over the 
	 *  same XML dump and JWKTL version.
	 *  @param pageId numeric id of the entry page.
	 *  @param entryIdx entry index.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionaryEntry getEntryForId(long pageId, int entryIdx);

	/** @return The {@link IWiktionaryEntry} encoded on a page with 
	 *  the given title and being part of an entry with the specified entry
	 *  index. The method only returns the entries if the page title 
	 *  matches exactly.
	 *  @param word word or title of the entry page.
	 *  @param entryIdx entry index.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionaryEntry getEntryForWord(final String word,
									 int entryIdx);
	
	
	// -- Senses --

	/** @return The word sense with the given unique id. Note that this id
	 *  is only stable over the same XML dump and JWKTL version.
	 *  @param id id of the word sense.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionarySense getSenseForKey(final String id);

	/** @return The word sense with the given entry id and sense index. Note 
	 *  that this id combination is only stable over the same XML dump and 
	 *  JWKTL version.
	 *  @param entryId id of the entry.
	 *  @param senseIdx sense index.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionarySense getSenseForId(long entryId, int senseIdx);
	
	/** @return The word sense with the given page id, entry index, and sense 
	 *  index. Note that this id combination is only stable over the same XML 
	 *  dump and JWKTL version.
	 *  @param pageId numeric id of the entry page.
	 *  @param entryIdx entry index.
	 *  @param senseIdx sense index.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionarySense getSenseForId(long pageId, int entryIdx, int senseIdx);

	/** @return A list of {@link IWiktionarySense}s encoded on a page with 
	 *  the given title and being part of an entry with the specified entry
	 *  index. The method only returns the senses if the page title 
	 *  matches exactly.
	 *  @param word word or title of the page.
	 *  @param entryIdx entry index.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	List<IWiktionarySense> getSensesForWord(final String word,
											int entryIdx);

	/** @return The {@link IWiktionarySense} encoded on the page with 
	 *  the given title and being part of the entry with the specified entry 
	 *  index at the specified sense index. The method only returns the 
	 *  sense if the page title matches exactly.
	 *  @param word word or title of the page.
	 *  @param entryIdx entry index.
	 *  @param senseIdx sense index.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	IWiktionarySense getSensesForWord(final String word,
									  int entryIdx, int senseIdx);

}
