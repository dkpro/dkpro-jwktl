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

import com.sleepycat.persist.model.Persistent;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalAspect;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalDegree;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalMood;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalPerson;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalTense;
import de.tudarmstadt.ukp.jwktl.api.util.NonFiniteForm;

/**
 * Implementation of the {@link IWiktionaryWordForm} interface. Instances
 * of this class represent inflected word forms.
 * @author Christian M. Meyer
 */
@Persistent
public class WiktionaryWordForm implements IWiktionaryWordForm {

	protected String wordForm;
	protected GrammaticalNumber grammaticalNumber;
	protected GrammaticalCase grammaticalCase;
	protected GrammaticalPerson grammaticalPerson;
	protected GrammaticalTense grammaticalTense;
	protected GrammaticalMood grammaticalMood;
	protected GrammaticalDegree grammaticalDegree;
	protected GrammaticalAspect grammaticalAspect;
	protected NonFiniteForm nonFiniteForm;
	
	/** Instanciates a new, empty word form. */
	public WiktionaryWordForm() {}
	
	/** Instanciates a new word form with the given written form. */
	public WiktionaryWordForm(final String wordForm) {
		this.wordForm = wordForm;
	}

	public String getWordForm() {
		return wordForm;
	}
	
	/** Assign the given written form to this instance. */
	public void setWordForm(String wordForm) {
		this.wordForm = wordForm;
	}

	public GrammaticalNumber getNumber() {
		return grammaticalNumber;
	}
	
	/** Assign the given grammatical number to this word form. */
	public void setNumber(final GrammaticalNumber grammaticalNumber) {
		this.grammaticalNumber = grammaticalNumber;
	}

	public GrammaticalCase getCase() {
		return grammaticalCase;
	}
	
	/** Assign the given grammatical case to this word form. */
	public void setCase(final GrammaticalCase grammaticalCase) {
		this.grammaticalCase = grammaticalCase;
	}
	
	public GrammaticalPerson getPerson() {
		return grammaticalPerson;
	}
	
	/** Assign the given grammatical person to this word form. */
	public void setPerson(final GrammaticalPerson grammaticalPerson) {
		this.grammaticalPerson = grammaticalPerson;
	}
	
	public GrammaticalTense getTense() {
		return grammaticalTense;
	}
	
	/** Assign the given grammatical tense to this word form. */
	public void setTense(final GrammaticalTense grammaticalTense) {
		this.grammaticalTense = grammaticalTense;
	}
	
	public GrammaticalMood getMood() {
		return grammaticalMood;
	}
	
	/** Assign the given grammatical mood to this word form. */
	public void setMood(final GrammaticalMood grammaticalMood) {
		this.grammaticalMood = grammaticalMood;
	}
	
	public GrammaticalDegree getDegree() {
		return grammaticalDegree;
	}
	
	/** Assign the given grammatical degree to this word form. */
	public void setDegree(final GrammaticalDegree grammaticalDegree) {
		this.grammaticalDegree = grammaticalDegree;
	}
	
	public GrammaticalAspect getAspect() {
		return grammaticalAspect;
	}
	
	/** Assign the given grammatical aspect to this word form. */
	public void setAspect(final GrammaticalAspect grammaticalAspect) {
		this.grammaticalAspect = grammaticalAspect;
	}
	
	public NonFiniteForm getNonFiniteForm() {
		return nonFiniteForm;
	}
	
	/** Assign the given type of non finite word form. */
	public void setNonFiniteForm(final NonFiniteForm nonFiniteForm) {
		this.nonFiniteForm = nonFiniteForm;
	}

}
