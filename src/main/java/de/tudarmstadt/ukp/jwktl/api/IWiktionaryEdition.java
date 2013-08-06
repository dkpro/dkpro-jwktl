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

	/** Returns the language of the Wiktionary edition, which is equivalent
	 *  to the entry language of the contained entries. */
	public ILanguage getLanguage();

	/** Returns the file path of the parsed database. */
	public File getDBPath();

	
	// -- Pages --
	
	/** Returns the page with the given unique id.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public IWiktionaryPage getPageForId(long id);
	
	/** Returns the page with the given title. The method only returns the
	 *  page if its title matches exactly. Use 
	 *  {@link #getPagesForWord(String, boolean)} for case insensitive and
	 *  string-normalized matching.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public IWiktionaryPage getPageForWord(final String word);

	
	// -- Entries --

	/** Returns the {@link IWiktionaryEntry} with the given entry id. Note 
	 *  that this id is only stable over the same XML dump and JWKTL version.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public IWiktionaryEntry getEntryForId(long entryId);
	
	/** Returns the {@link IWiktionaryEntry} with the given page id and 
	 *  entry index. Note that this id combination is only stable over the 
	 *  same XML dump and JWKTL version.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public IWiktionaryEntry getEntryForId(long pageId, int entryIdx);

	/** Returns the {@link IWiktionaryEntry} encoded on a page with 
	 *  the given title and being part of an entry with the specified entry
	 *  index. The method only returns the entries if the page title 
	 *  matches exactly.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public IWiktionaryEntry getEntryForWord(final String word,
			int entryIdx);
	
	
	// -- Senses --

	/** Returns the word sense with the given unique id. Note that this id
	 *  is only stable over the same XML dump and JWKTL version.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public IWiktionarySense getSenseForKey(final String id);

	/** Returns the word sense with the given entry id and sense index. Note 
	 *  that this id combination is only stable over the same XML dump and 
	 *  JWKTL version.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public IWiktionarySense getSenseForId(long entryId, int senseIdx);
	
	/** Returns the word sense with the given page id, entry index, and sense 
	 *  index. Note that this id combination is only stable over the same XML 
	 *  dump and JWKTL version.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public IWiktionarySense getSenseForId(long pageId, int entryIdx, int senseIdx);

	/** Returns a list of {@link IWiktionarySense}s encoded on a page with 
	 *  the given title and being part of an entry with the specified entry
	 *  index. The method only returns the senses if the page title 
	 *  matches exactly.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public List<IWiktionarySense> getSensesForWord(final String word,
			int entryIdx);

	/** Returns the {@link IWiktionarySense} encoded on the page with 
	 *  the given title and being part of the entry with the specified entry 
	 *  index at the specified sense index. The method only returns the 
	 *  sense if the page title matches exactly.
	 *  @throws IllegalStateException if the connection has already been closed.
	 *  @throws WiktionaryException upon database errors. */
	public IWiktionarySense getSensesForWord(final String word,
			int entryIdx, int senseIdx);

}
