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

import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;

/**
 * A {@link IWiktionaryEntry} corresponds to a lexical entry. That is, a 
 * word defined by the language it is used in, its part of speech, its
 * etymology and all information encoded for this word. The word "plant" has,
 * for instance, separate entries for the English noun, the English verb, and 
 * the Dutch noun. The meaning of an entry is expressed by multiple 
 * {@link IWiktionarySense}s. Each {@link IWiktionaryEntry} belongs to exactly 
 * one {@link IWiktionaryPage}.
 * @author Christian M. Meyer
 */
public interface IWiktionaryEntry {

	// -- Identifier --
	
	/** Returns a unique ID for this lexical entry. The ID is unique for all 
	 *  {@link IWiktionaryEntry}s of the {@link IWiktionaryEdition}. Note 
	 *  however that the ID of an entry may differ between different software 
	 *  versions or dump dates. */
	public String getKey();
	
	/** Returns an ID of this entry that is unique for all entries
	 *  of the containing {@link IWiktionaryPage}. Depending on the parsing
	 *  mode, the ID may be a running number of entries or a sense index.
	 *  Use {@link #getKey()}  for a globally unique identifier. Note 
	 *  however that the ID of an entry may differ between different software 
	 *  versions or dump dates.
	 *  IMPORTANT: The entry ID is unstable w.r.t. to different API
	 *    versions and dump files. Better rely on page IDs and eventually
	 *    local indices. */
	public long getId();
	
	/** Returns the index of this entry. That is, the running number of 
	 *  the entry in accordance to the list of entries of 
	 *  the {@link IWiktionaryPage} (starting at 0). */
	public int getIndex();
	
	
	// -- Parent --
	
	/** Returns a reference to the {@link IWiktionaryPage} that contains
	 *  this entry. */
	public IWiktionaryPage getPage();
	
	/** Returns the ID of the {@link IWiktionaryPage} that contains
	 *  this entry. */
	public long getPageId();
	
	
	// -- Entry --
	
	/** Returns the lemma of this lexical entry denoted by the page title. 
	 *  This method is equivalent to <code>getPage().getTitle()</code>. */
	public String getWord();
	
	/** Returns the header of the lexical entry. This is usually the lemma
	 *  of the entry (i.e. equivalent to {@link #getWord()}, but there are
	 *  some exceptions where the Wiktionary community uses a slightly
	 *  different header. */
	public String getHeader();
	
	/** Returns the language of this lexical entry. This does not necessarily 
	 *  correspond to the language of the {@link IWiktionaryEdition}; e.g.,
	 *  for French entries within the English Wiktionary edition. */
	public ILanguage getWordLanguage();
	
	/** Returns the first part of speech tag encoded for this lexical entry.
	 *  The first tag is usually the most important one, although there can
	 *  be multiple tags. Use {link #getPartsOfSpeech()} to access all 
	 *  part of speech tags encoded. If the part of speech is unknown
	 *  or not specified, <code>null</code> will be returned. */
	public PartOfSpeech getPartOfSpeech();
	
	/** Returns all part of speech tags encoded for this lexical entry. 
	 *  Most of the time, only one part of speech tag is used. However, 
	 *  there are some cases, where multiple tags are used - e.g., for 
	 *  German adjectives/adverbs having the same form or for syntactic 
	 *  additions, such as "plurale tantum" (only taking the plural form). 
	 *  The ordering of tags used in Wiktionary is preserved. The resulted
	 *  list is never null and includes at least one element. */
	public List<PartOfSpeech> getPartsOfSpeech();
	
	/** Returns the grammatical gender of this lexical entry, which can
	 *  be one of masculine, feminine, neuter. If no gender is specified,
	 *  null is returned. */
	public GrammaticalGender getGender();
	
	/** Returns the etymology of this lexical entry as a {@link IWikiString}.
	 *  The result might be <code>null</code> if no etymology has been 
	 *  encoded. */
	public IWikiString getWordEtymology();
	
	/** Some lexical entries refer to other pages rather than encoding 
	 *  all information on the entry again. This is similar to a redirect
	 *  ({@link IWiktionaryPage#getRedirectTarget()}), but limited to the
	 *  entry level. In addition to that, there might be further information
	 *  provided. */
	public String getEntryLink();
	
	/** Returns the type of the {@link #getEntryLink()}, for example, denoting
	 *  that the entry is an old spelling variant of the linked entry. */
	public String getEntryLinkType();
	
	/** Returns a list of pronunciations for this lexical entry. The
	 *  list might be <code>null</code> if not pronunciations are
	 *  encoded. */
	public List<IPronunciation> getPronunciations();
	
	/** Returns a list of word forms for this lexical entry. The
	 *  list might be <code>null</code> if not word forms are
	 *  encoded. */
	public List<IWiktionaryWordForm> getWordForms();
	
	// -- Senses --
	
	/** Returns a dummy {@link IWiktionarySense} that contains all 
	 *  information that has not been assigned to a particular sense. This
	 *  is usually the case if the sense marker of an information does not
	 *  match with any {@link IWiktionarySense#getMarker()} - e.g., containing
	 *  question marks. The unassigned sense is never <code>null</code>.
	 *  This method is equivalent to <code>getSense(0)</code>. */
	public IWiktionarySense getUnassignedSense();

	/** Returns the {@link IWiktionarySense} with the given index. 
	 *  IMPORTANT: The index is a running number starting at 1. Providing
	 *  parameter 0 yields the unassigned sense (equivalent to 
	 *  {@link #getUnassignedSense()}. The maximum index is equivalent to 
	 *  {@link #getSenseCount()} (rather than {@link #getSenseCount()} - 1).
	 *  @throws ArrayIndexOutOfBoundsException if there is no sense with
	 *    the given index. */
	public IWiktionarySense getSense(int index);
	
	/** Returns the number of {@link IWiktionarySense}s encoded for this 
	 *  lexical entry. */
	public int getSenseCount();
	
	/** Returns the list of all {@link IWiktionarySense}s. The list is
	 *  never null nor empty. The elements of the list are all senses of 
	 *  this entry, i.e. all senses with index 1 to {@link #getSenseCount()}. 
	 *  This method is equivalent to {@link #getSenses(boolean)} with parameter 
	 *  <code>false</code>. */
	public Iterable<IWiktionarySense> getSenses();

	/** Returns the list of all {@link IWiktionarySense}s. The list is
	 *  never null nor empty. If the parameter is set to <code>true</code>,
	 *  the first element (list index 0) is the unassigned sense (equivalent 
	 *  to {@link #getUnassignedSense()}. The following elements are all
	 *  senses of this entry, i.e. all senses with index 1 to 
	 *  {@link #getSenseCount()}. */
	public Iterable<IWiktionarySense> getSenses(boolean includeUnassignedSense);

	
	// -- Combination --
	
	/** Returns a list containing all sense definitions of the entry's senses 
	 *  (including the unassigned sense). Hence, the method is a shorthand
	 *  for invoking {@link IWiktionarySense#getGloss()} for each sense. 
	 *  The list is never <code>null</code> but might be empty. */
	public List<IWikiString> getGlosses();
	
	/** Returns a list containing all sense definitions of the entry's senses 
	 *  (including the unassigned sense). Hence, the method is a shorthand
	 *  for invoking {@link IWiktionarySense#getExamples()} for each sense. 
	 *  The list is never <code>null</code> but might be empty. */
	public List<IWikiString> getExamples();
	
	/** Returns a list containing all quotations of the entry's senses 
	 *  (including the unassigned sense). Hence, the method is a shorthand
	 *  for invoking {@link IWiktionarySense#getQuotations()} for each sense. 
	 *  The list is never <code>null</code> but might be empty. */
	public List<IQuotation> getQuotations();
	
	/** Returns a list containing all semantic relations of the entry's senses 
	 *  (including the unassigned sense). Hence, the method is a shorthand
	 *  for invoking {@link IWiktionarySense#getRelations()} for each sense. 
	 *  The list is never <code>null</code> but might be empty. */
	public List<IWiktionaryRelation> getRelations();

	/** Returns a list containing all semantic relations of the entry's senses 
	 *  (including the unassigned sense) of the given type. Hence, the 
	 *  method is a shorthand for invoking 
	 *  {@link IWiktionarySense#getRelations(RelationType)} for each sense. 
	 *  The list is never <code>null</code> but might be empty. */
	public List<IWiktionaryRelation> getRelations(final RelationType relationType);
	
	/** Returns a list containing all references of the entry's senses 
	 *  (including the unassigned sense). Hence, the method is a shorthand
	 *  for invoking {@link IWiktionarySense#getReferences()} for each sense. 
	 *  The list is never <code>null</code> but might be empty. */
	public List<IWikiString> getReferences();
	
	/** Returns a list containing all translations of the entry's senses 
	 *  (including the unassigned sense). Hence, the method is a shorthand 
	 *  for invoking {@link IWiktionarySense#getTranslations()} for each sense. 
	 *  The list is never <code>null</code> but might be empty. */
	public List<IWiktionaryTranslation> getTranslations();
	
	/** Returns a list containing all translations of the entry's senses 
	 *  (including the unassigned sense) to the given language. Hence, the 
	 *  method is a shorthand for invoking 
	 *  {@link IWiktionarySense#getTranslations(ILanguage)} for each sense. 
	 *  The list is never <code>null</code> but might be empty. */
	public List<IWiktionaryTranslation> getTranslations(final ILanguage language);
	
}
