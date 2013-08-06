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

import java.util.Date;
import java.util.List;
import java.util.Set;

import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;

/**
 * A {@link IWiktionaryPage} corresponds to a unique URL of Wiktionary. That 
 * is, a distinct web page, such as http://en.wiktionary.org/wiki/plant. The
 * notion is mostly equivalent to the lexicographic definition of a lexical 
 * item. Since non-linguistically pages are filtered out, an 
 * {@link IWiktionaryPage} encodes linguistic information on a certain word 
 * form (e.g., plant) using multiple {@link IWiktionaryEntry}s (distinguishing 
 * the English noun, the English verb, the Dutch noun, etc. Each 
 * {@link IWiktionaryPage} belongs to exactly one {@link IWiktionaryEdition}.
 * @author Christian M. Meyer
 */
public interface IWiktionaryPage {

	// -- Identifier --
	
	/** Returns a unique ID for this page. The ID is unique for all 
	 *  {@link IWiktionaryPage}s of the {@link IWiktionaryEdition} and 
	 *  remains persistent regardless of the JWKTL software version or
	 *  the date of the XML data dump of Wiktionary. */
	public String getKey();
	
	/** Returns the unique ID for this page. This method is equivalent
	 *  to {@link #getKey()}, but returns the ID as a numerical value. */
	public long getId();
	
	
	// -- Page --
	
	/** Returns the title of this Wiktionary page which usually corresponds
	 *  to the lemma of all lexical entries described on this page. */
	public String getTitle();
	
	/** Returns the timestamp of this revision - i.e., the date of the
	 *  last change of the page. */
	public Date getTimestamp();
	
	/** Returns the ID of this revision - i.e., a unique number of the 
	 *  last change made to the page. */
	public long getRevision();
	
	/** Returns the author of this revision - i.e., the name of the user
	 *  that made the last change to the page. */
	public String getAuthor();
	
	/** Returns the language that this page is written in. This is always 
	 *  the language of the whole Wiktionary language edition - i.e. equivalent
	 *  to {@link IWiktionaryEdition#getLanguage()}. As opposed to that,
	 *  the language of a word can be retrieved by 
	 *  {@link IWiktionaryEntry#getWordLanguage()}. There is, for example,
	 *  a Wiktionary page "plant" in the German Wiktionary language edition
	 *  that encodes a lexical entry on the word "plant" of the English 
	 *  language. The entry language would be German and the word language 
	 *  would be English in this case. */
	public ILanguage getEntryLanguage();
	
	/** Returns all categories of the Wiktionary page that are manually 
	 *  defined. Categories being derived automatically by using templates
	 *  are not returned. The returned list is never <code>null</code>. */
	public List<String> getCategories();
	
	/** Returns a list of inter-wiki links of this Wiktionary page. Inter-wiki
	 *  links are links to other language editions of Wiktionary - e.g., from
	 *  the English page "plant" to the German page "plant". Note that this 
	 *  is not a translation, but always the same word form. Use 
	 *  {@link IWiktionarySense#getTranslations()} for word translations. 
	 *  The returned list is never <code>null</code>. */	
	public Set<String> getInterWikiLinks();

	/** Returns the page title that a redirect page targets at. The method
	 *  returns <code>null</code> if the page is not a redirection page. */
	public String getRedirectTarget();
	
	
	// -- Entries --
	
	/** Returns the {@link IWiktionaryEntry} with the given index. The index
	 *  is a running number starting at zero.
	 *  @throws ArrayIndexOutOfBoundsException if there is no entry with
	 *    the given index. */
	public IWiktionaryEntry getEntry(int index);
	
	/** Returns the number of {@link IWiktionaryEntry}s encoded on this
	 *  page. */
	public int getEntryCount();
	
	/** Returns the list of all {@link IWiktionaryEntry}s. The list is
	 *  never <code>null</code> and yields equivalent results to using 
	 *  {@link #getEntry(int)} for all indices from zero to 
	 *  {@link #getEntryCount()}. */
	public List<IWiktionaryEntry> getEntries();
	
}
