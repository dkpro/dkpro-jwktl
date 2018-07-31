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

import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;

/**
 * A {@link IWiktionarySense} represents one word sense of a lexical entry 
 * (also called a lexical unit). Each {@link IWiktionarySense} belongs to 
 * exactly one {@link IWiktionaryEntry}. Note that 
 * {@link IWiktionaryEntry#getUnassignedSense()} returns a dummy word sense
 * carrying all information that has not been assigned to a particular 
 * sense - for this case, the {@link IWiktionarySense} does not model an
 * actual word sense,  
 * @author Christian M. Meyer
 */
public interface IWiktionarySense {
	
	// -- Identifier --
	
	/** Returns a unique ID for this word sense. The ID is unique for all 
	 *  {@link IWiktionarySense}s of the {@link IWiktionaryEdition}. Note 
	 *  however that the ID of a sense may differ between different software 
	 *  versions or dump dates.
	 *  @return A globally unique ID for this word sense.
	 */
	String getKey();
	
	/** Returns an ID of this sense that is unique for all senses 
	 *  of the containing {@link IWiktionaryEntry}. Use {@link #getKey()} 
	 *  for a globally unique identifier.
	 *  @return A entry-unique ID for this word sense.
	 */
	String getId();
	
	/** Returns the index of this sense. That is, the running number of 
	 *  the sense in accordance to the list of senses of the 
	 *  {@link IWiktionaryEntry}. The first sense has index 1. 
	 *  Note that this is not generally the index marker used in Wiktionary: 
	 *  the first sense has for example always index 1, although it might
	 *  be marked with "[2]" or "[1a]". See {@link #getMarker()} for details.
	 *  @return The index of this sense, starting with 1.
	 */
	int getIndex();
	
	/** Returns the marker for this sense as defined in Wiktionary. This 
	 *  can be different for each type of Wiktionary edition; for example,
	 *  running numbers, short textual labels,...
	 *  @return The marker for this sense as defined in Wiktionary.
	 */
	String getMarker();
	
	
	// -- Parent --
	
	/** @return A reference to the {@link IWiktionaryEntry} that contains
	 *  this sense. */
	IWiktionaryEntry getEntry();
	
	/** @return A reference to the {@link IWiktionaryPage} that contains
	 *  this sense. */
	IWiktionaryPage getPage();
	
	
	// -- Sense --
	
	/** @return The sense description as a {@link IWikiString}. */
	IWikiString getGloss();
	
	/** @return A list of sense examples or <code>null</code> if no sense 
	 *  examples are encoded. */
	List<IWiktionaryExample> getExamples();
	
	/** @return A list of quotations or <code>null</code> if no 
	 *  quotations are encoded. */
	List<IQuotation> getQuotations();
	
	/** @return The list of all sense relations (or <code>null</code> 
	 *  if no relations are encoded). */
	List<IWiktionaryRelation> getRelations();
	
	/** @return The list of sense relations of the given relation type. 
	 *  If there are no relations of this type, an empty list will be 
	 *  returned.
	 *  @param relationType type of the relation.
	 */
	List<IWiktionaryRelation> getRelations(final RelationType relationType);
	
	/** @return A list of references for this sense or 
	 *  <code>null</code> if no references are encoded. */
	List<IWikiString> getReferences();
	
	/** @return The list of all translations (or <code>null</code> 
	 *  if no translations are encoded). */
	List<IWiktionaryTranslation> getTranslations();

	/** @return The list of translations into the given language. 
	 *  If there are no translations of this type, an empty list will be 
	 *  returned.
	 *  @param language translation language.
	 */
	List<IWiktionaryTranslation> getTranslations(final ILanguage language);
	
	
	// -- Subsenses --

	//public List<IWiktionarySense> getSenses();

}
