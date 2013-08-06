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

import de.tudarmstadt.ukp.jwktl.api.IPronunciation;
import de.tudarmstadt.ukp.jwktl.api.IQuotation;
import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;

/**
 * Default implementation of the {@link IWiktionaryEntry} interface.
 * See there for details.
 * @author Christian M. Meyer
 */
@Persistent
public class WiktionaryEntry implements IWiktionaryEntry {

	protected long id;
	protected int index;
	
	protected transient IWiktionaryPage page;
	protected long pageId;
	
	protected String header;
	protected String wordLanguageStr;
	protected transient ILanguage wordLanguage;
	protected List<PartOfSpeech> partsOfSpeech;
	protected GrammaticalGender gender;
	protected IWikiString etymology;
	protected String entryLink;
	protected String entryLinkType;
	protected List<IPronunciation> pronunciations;
	protected List<IWiktionaryWordForm> wordForms;

	protected List<WiktionarySense> senses;
	
	/** Instanciates a new, empty entry. */
	public WiktionaryEntry() {
		partsOfSpeech = new ArrayList<PartOfSpeech>();
		senses = new ArrayList<WiktionarySense>();
		senses.add(new WiktionarySense()); // Dummy sense for all unassigned information.
	}
	
	/** Initialize the entry using the given Wiktionary page. This is necessary
	 *  to initialize the back references to the parent page, which are not 
	 *  explicitly stored in the database. */
	public void init(final WiktionaryPage page) {
		this.page = page;
		this.pageId = page.getId();
		for (WiktionarySense sense : senses)
			sense.init(this);
	}

	/** Factory method for creating a new word sense. */
	public WiktionarySense createSense() {
		WiktionarySense result = new WiktionarySense();
		result.init(this);
		return result;
	}
	
	
	// -- Identifier --
	
	public String getKey() {
		return pageId + ":" + getIndex();
	}
	
	public long getId() {
		return id;
	}

	/** Assign the specified entry ID. */
	public void setId(long id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}
	
	
	// -- Parent --
	
	public IWiktionaryPage getPage() {
		return page;
	}
	
	public long getPageId() {
		return pageId;
	}
	
	// -- Entry --

	public String getWord() {
		return getPage().getTitle();
	}
			
	public String getHeader() {
		return header;
	}

	/** Assigns the given header text (i.e., the first headline of the entry,
	 *  which normally correponds to a word's lemma). */
	public void setHeader(final String header) {
		this.header = header;
	}
	
	public ILanguage getWordLanguage() {
		if (wordLanguage == null && wordLanguageStr != null)
			wordLanguage = Language.get(wordLanguageStr);
		return wordLanguage;
	}
	
	/** Assigns the given word language. */
	public void setWordLanguage(final ILanguage wordLanguage) {
		this.wordLanguage = wordLanguage;
		if (wordLanguage != null)
			wordLanguageStr = wordLanguage.getCode();
	}

	public PartOfSpeech getPartOfSpeech() {
		return (partsOfSpeech.size() > 0 ? partsOfSpeech.get(0) : null);
	}

	public List<PartOfSpeech> getPartsOfSpeech() {
		return partsOfSpeech;
	}
	
	/** Adds the given part of speech to the list of part of speech tags. */
	public void addPartOfSpeech(final PartOfSpeech partOfSpeech) {
		partsOfSpeech.add(partOfSpeech);
	}

	public GrammaticalGender getGender() {
		return gender;
	}
	
	/** Assigns the given grammatical gender. */
	public void setGender(final GrammaticalGender gender) {
		this.gender = gender;
	}

	public IWikiString getWordEtymology() {
		return etymology;
	}
	
	/** Assigns the given etymology text. */
	public void setWordEtymology(final IWikiString etymology) {
		this.etymology = etymology;
	}
	
	public String getEntryLink() {
		return entryLink;
	}
	
	public String getEntryLinkType() {
		return entryLinkType;
	}
	
	/** Assigns the given entry link. */
	public void setEntryLink(final String entryLink, final String entryLinkType) {
		this.entryLink = entryLink;
		this.entryLinkType = entryLinkType;
	}

	/** Adds the given pronunciation. */
	public void addPronunciation(final IPronunciation pronunciation) {
		if (pronunciations == null)
			pronunciations = new ArrayList<IPronunciation>();
		pronunciations.add(pronunciation);
	}

	public List<IPronunciation> getPronunciations() {
		return pronunciations;
	}

	/** Adds the given word form. */
	public void addWordForm(final IWiktionaryWordForm wordForm) {
		if (wordForms == null)
			wordForms = new ArrayList<IWiktionaryWordForm>();
		wordForms.add(wordForm);
	}
	
	public List<IWiktionaryWordForm> getWordForms() {
		return wordForms;
	}

	
	// -- Senses --

	/** Add the given sense to the list of senses. */
	public void addSense(final WiktionarySense sense) {
		sense.setIndex(senses.size());
		senses.add(sense);
	}
	
	public WiktionarySense getUnassignedSense() {
		return senses.get(0);
	}

	/*@Deprecated
	public void setUnassignedSense(final WiktionarySense unassignedSense) {
		senses.set(0, unassignedSense);
	}*/
	
	public WiktionarySense getSense(int index) {
		return senses.get(index);
	}
	
	public int getSenseCount() {
		return senses.size() - 1; // don't count the unassigned sense.
	}

	public List<IWiktionarySense> getSenses() {
		// This cast is used to transform the internal representation using
		// the WiktionarySense type to the interface type IWiktionarySense.
		// Although this might not be the cleanest way to do that, exposing
		// the interface type prevents the user from changing the data without
		// raising a performance bottleneck of returning a newly created list
		// of the senses. It is the user's responsibility to not make any 
		// changes to this exposed internal representation.
//		return (List) senses;
		return getSenses(false);
	}
	
	public List<IWiktionarySense> getSenses(boolean includeUnassignedSense) {
		// This cast is used to transform the internal representation using
		// the WiktionarySense type to the interface type IWiktionarySense.
		// Although this might not be the cleanest way to do that, exposing
		// the interface type prevents the user from changing the data without
		// raising a performance bottleneck of returning a newly created list
		// of the senses. It is the user's responsibility to not make any 
		// changes to this exposed internal representation.
		if (includeUnassignedSense)
			return (List) senses;
		else
			return (List) senses.subList(1, senses.size());
	}

	/** Internal interface that is used by the parsers. */
	public List<WiktionarySense> senses() {
		return senses;
	}
	
	/** Identify the first sense of this entry with the given marker. */
	public WiktionarySense findSenseByMarker(final String marker) {
		for (WiktionarySense sense : senses)
			if (marker.equals(sense.getMarker()))
				return sense;
		
		return null;
	}

	
	// -- Combination --
	
	public List<IWikiString> getGlosses() {
		List<IWikiString> result = new ArrayList<IWikiString>();
		for (IWiktionarySense sense : getSenses(true)) {
			IWikiString gloss = sense.getGloss();
			if (gloss != null && gloss.getText().length() > 0)
				result.add(gloss);
		}
		return result;
	}
	
	public List<IWikiString> getExamples() {
		List<IWikiString> result = new ArrayList<IWikiString>();
		for (IWiktionarySense sense : getSenses(true)) {
			List<IWikiString> examples = sense.getExamples();
			if (examples != null)
				result.addAll(examples);
		}
		return result;
	}

	public List<IQuotation> getQuotations() {
		List<IQuotation> result = new ArrayList<IQuotation>();
		for (IWiktionarySense sense : getSenses(true)) {
			List<IQuotation> quotations = sense.getQuotations();
			if (quotations != null)
				result.addAll(quotations);
		}
		return result;
	}

	public List<IWiktionaryRelation> getRelations() {
		List<IWiktionaryRelation> result = new ArrayList<IWiktionaryRelation>();
		for (IWiktionarySense sense : getSenses(true)) {
			List<IWiktionaryRelation> relations = sense.getRelations();
			if (relations != null)
				result.addAll(relations);
		}
		return result;
	}

	public List<IWiktionaryRelation> getRelations(final RelationType relationType) {
		List<IWiktionaryRelation> result = new ArrayList<IWiktionaryRelation>();
		for (IWiktionarySense sense : getSenses(true)) {
			List<IWiktionaryRelation> relations = sense.getRelations(relationType);
			if (relations != null)
				result.addAll(relations);
		}
		return result;
	}

	public List<IWikiString> getReferences() {
		List<IWikiString> result = new ArrayList<IWikiString>();
		for (IWiktionarySense sense : getSenses(true)) {
			List<IWikiString> references = sense.getReferences();
			if (references != null)
				result.addAll(references);
		}
		return result;
	}
	
	public List<IWiktionaryTranslation> getTranslations() {
		List<IWiktionaryTranslation> result = new ArrayList<IWiktionaryTranslation>();
		for (IWiktionarySense sense : getSenses(true)) {
			List<IWiktionaryTranslation> translations = sense.getTranslations();
			if (translations != null)
				result.addAll(translations);
		}
		return result;
	}

	public List<IWiktionaryTranslation> getTranslations(final ILanguage language) {
		List<IWiktionaryTranslation> result = new ArrayList<IWiktionaryTranslation>();
		for (IWiktionarySense sense : getSenses(true)) {
			List<IWiktionaryTranslation> translations = sense.getTranslations(language);
			if (translations != null)
				result.addAll(translations);
		}
		return result;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + ":" + index + ":" + wordLanguageStr + ":" + getPartOfSpeech();
	}
	
}
