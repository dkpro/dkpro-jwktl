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
package de.tudarmstadt.ukp.jwktl.parser.en.components;

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.entry.Quotation;

/**
 * Helper class for storing parsed sense definitions, examples, and quotations.
 * Instances of this class only exist while parsing a dump file; they will
 * be converted into proper {@link IWiktionarySense} as soon as the meaning
 * constituent has been completely parsed.
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class EnGlossEntry {
	
	private String definition = "";
	// a list of examples
	private List<String> exampleList = new ArrayList<String>();
	// a list of quotation
	private List<Quotation> quotationList = new ArrayList<Quotation>();
	
	/** Initializes the entry with the specified definition. */
	public EnGlossEntry(String gloss){
		this.definition = gloss;
	}
	/** Returns the list of example sentences. */
	public List<String> getExampleList() {
		return exampleList;
	}

	/** Add the specified example sentence to the list. */
	public void addExample(String example){
		exampleList.add(example);
	}
	
	/** Append the specified example sentence to the last example
	 *  sentences. The two examples are combined with a space and then
	 *  trimmed. */
	public void appendExample(String example) {
		if (exampleList.isEmpty())
			return;
		
		int idx = exampleList.size() - 1;
		example = exampleList.get(idx) + " " + example;
		exampleList.set(idx, example.trim());
	}
	
	/** Add specified quotation to the list. */
	public void addQuotation(Quotation quotation){
		quotationList.add(quotation);
	}

	/** Returns the list of quotations. */
	public List<Quotation> getQuotations() {
		return quotationList;
	}
	

	/** Returns the sense definition. */
	public String getDefinition() {
		return definition;
	}
	
	/** Replace the sense definition with the specified one. */
	public void setGloss(final String definition) {
		this.definition = definition;
	}
	
}
