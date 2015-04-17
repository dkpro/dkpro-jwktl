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

/**
 * Represents a paradigmatic relation between lexical entries or word senses.
 * A relations consists of a source and a target as well as additional
 * information on this relationship, such as a type identifier (e.g.,
 * synonymy, hyponymy, antonymy). The source of the relation is always a
 * {@link IWiktionarySense}. For relations that have not yet been attached
 * to a certain word sense or that are not depending on a certain word sense
 * (e.g., for derived terms that hold for an entire lexical entry), the
 * unassigned sense ({@link IWiktionaryEntry#getUnassignedSense()}) is
 * used. The target of a relation is given as a word form, sometimes 
 * including additional hints on the word sense, such as sense indices
 * or grammatical information.
 * @author Christian M. Meyer
 */
public interface IWiktionaryRelation {

	/** Types of a link used by {@link IWiktionaryRelation#getLinkType()}. */
	enum LinkType {
		/** The link was found on the original article page. */
		ARTICLE, 
		/** The link was found in the Wikisaurus (thesaurus) extension. */
		WIKISAURUS;
		//DANGLING, NOT_LINKED, 		
	}
	
		
	/** Returns the target word form of this relation. */
	String getTarget();
	
	/** Returns additional information on the word sense of the relation's 
	 *  target. This is not yet structured information, but the bare 
	 *  piece of text encoded in Wiktionary. */
	String getTargetSense();
	
	/** Returns the type of this relation (never <code>null</code>). */
	RelationType getRelationType();
	
	/** Returns the {@link LinkType} that is used to encode the relation's
	 *  target. */
	LinkType getLinkType();
	
}
