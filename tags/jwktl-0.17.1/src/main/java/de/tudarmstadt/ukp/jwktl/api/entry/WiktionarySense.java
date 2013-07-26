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
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import de.tudarmstadt.ukp.jwktl.api.IQuotation;
import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;

/**
 * Default implementation of the {@link IWiktionarySense} interface.
 * See there for details.
 * @author Christian M. Meyer
 */
@Persistent
public class WiktionarySense implements IWiktionarySense {

	protected int index;
	protected String marker;

	protected transient WiktionaryEntry entry;
	protected long entryId;
		
	protected IWikiString gloss;
	protected List<IWikiString> examples;
	protected List<IQuotation> quotations;
	protected List<IWikiString> references;
	protected List<IWiktionaryRelation> relations;
	protected List<IWiktionaryTranslation> translations;

	/** Initialize the sense using the given Wiktionary entry. This is necessary
	 *  to initialize the back references to the parent entry, which are not 
	 *  explicitly stored in the database. */
	public void init(final WiktionaryEntry entry) {
		this.entry = entry;
		entryId = entry.getId();
	}

	
	// -- Identifier --
	
	public String getKey() {
		return getEntry().getKey() + ":" + getId();
	}
	
	public String getId() {
		return Integer.toString(index);
	}
	
	/** Shorthand for <code>getEntry().getId()</code>. */
	public long getEntryId() {
		return entryId;
	}
	
	public int getIndex() {
		return index;
	}
	
	/** Assigns the given index to this sense. */
	protected void setIndex(int index) {
		this.index = index;
	}

	public String getMarker() {
		return marker;
	}
	
	/** Assigns the given sense marker to this sense. */
	public void setMarker(final String marker) {
		this.marker = marker;
	}

	
	// -- Parent --
	
	public IWiktionaryEntry getEntry() {
		return entry;
	}

	public IWiktionaryPage getPage() {
		return entry.getPage();
	}
	
	
	// -- Sense --
	
	public IWikiString getGloss() {
		return gloss;
	}
	
	/** Add the given sense definition to this sense. */
	public void setGloss(final IWikiString gloss) {
		this.gloss = gloss;
	}

	/** Add the given example to this sense. */
	public void addExample(final IWikiString example) {
		if (examples == null)
			examples = new ArrayList<IWikiString>();
		examples.add(example);
	}

	public List<IWikiString> getExamples() {
		return examples;
	}
	
	/** Add the given quotation to this sense. */
	public void addQuotation(final IQuotation quotation) {
		if (quotations == null)
			quotations = new ArrayList<IQuotation>();
		quotations.add(quotation);
	}

	public List<IQuotation> getQuotations() {
		return quotations;
	}
	
	/** Add the given reference to this sense. */
	public void addReference(final IWikiString reference) {
		if (references == null)
			references = new ArrayList<IWikiString>();
		references.add(reference);
	}
	
	public List<IWikiString> getReferences() {
		return references;
	}
	
	/** Add the given relation to this sense. */
	public void addRelation(final IWiktionaryRelation relation) {
		if (relations == null)
			relations = new ArrayList<IWiktionaryRelation>();
		relations.add(relation);
	}

	public List<IWiktionaryRelation> getRelations() {
		return relations;
	}
	
	public List<IWiktionaryRelation> getRelations(final RelationType relationType) {
		List<IWiktionaryRelation> result = new ArrayList<IWiktionaryRelation>();
		if (relations != null)
			for (IWiktionaryRelation relation : relations)
				if (relation.getRelationType() == relationType)
					result.add(relation);
		return result;
	}
	
	/** Add the given translation to this sense. */
	public void addTranslation(final IWiktionaryTranslation translation) {
		if (translations == null)
			translations = new ArrayList<IWiktionaryTranslation>();
		translations.add(translation);
	}
	
	public List<IWiktionaryTranslation> getTranslations(final ILanguage language) {		
		List<IWiktionaryTranslation> result = new ArrayList<IWiktionaryTranslation>();
		if (translations != null)
			for (IWiktionaryTranslation trans : translations)
				if (language == trans.getLanguage() 
				|| (language != null && language.equals(trans.getLanguage())))
					result.add(trans);
		return result;
	}
	
	public List<IWiktionaryTranslation> getTranslations() {
		return translations;
	}

	
	// -- Subsenses --
	
	/*public List<IWiktionarySense> getSenses() {
		return null;
	}*/


	@Override
	public String toString() {
		return getClass().getName() + ":" + index + ":" + gloss;
	}
	
}
