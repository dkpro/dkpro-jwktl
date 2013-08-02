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
package de.tudarmstadt.ukp.jwktl.parser.wikisaurus;

import java.util.HashSet;
import java.util.Set;

import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation.LinkType;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;

/**
 * Intermediate representation of Wikisaurus articles used by the
 * {@link WikisaurusArticleParser}. The information stored in this
 * class will be transfered to the main article structure (i.e., the
 * {@link IWiktionarySense}s) if requested.
 * @author Christian M. Meyer 
 */
public class WikisaurusEntry {
	
	private String title;
	private PartOfSpeech partOfSpeech;
	private ILanguage language;
	private String senseDefinition;
	private Set<WiktionaryRelation> relations;
		
	/** Instanciates a new entry with the given title, part of speech,
	 *  language, and sense definition (optional)*/
	public WikisaurusEntry(final String title, final PartOfSpeech partOfSpeech,
			final ILanguage language, final String senseDefinition) {
		this.title = title;
		this.partOfSpeech = partOfSpeech;
		this.language = language;
		this.senseDefinition = senseDefinition;
		this.relations = new HashSet<WiktionaryRelation>();
	}

	/** Adds a new {@link WiktionaryRelation} to this entry based on the 
	 *  given parameters. */
	public void addRelation(final String target, final String targetSense, 
			final RelationType type){
		WiktionaryRelation newRelation = new WiktionaryRelation(target, type);
		if (targetSense != null)
			newRelation.setTargetSense(targetSense);
		newRelation.setLinkType(LinkType.WIKISAURUS);
		relations.add(newRelation);
	}
	
	/** Returns the page title. */
	public String getTitle() {
		return title;
	}

	/** Returns the part of speech tag. */
	public PartOfSpeech getPartOfSpeech() {
		return partOfSpeech;
	}
	
	/** Returns the word language. */
	public ILanguage getLanguage() {
		return language;
	}
	
	/** Returns the sense definition. */
	public String getSenseDefinition() {
		return senseDefinition;
	}

	/** Returns the semantic relations. */
	public Set<WiktionaryRelation> getRelations() {
		return relations;
	}
	
	@Override
	public String toString(){
		return title + "/" + partOfSpeech + "/" + language + "<" + senseDefinition + ">";
	}
	
}
