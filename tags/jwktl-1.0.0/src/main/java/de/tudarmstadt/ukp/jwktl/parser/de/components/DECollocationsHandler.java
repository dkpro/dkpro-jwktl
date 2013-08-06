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
package de.tudarmstadt.ukp.jwktl.parser.de.components;

import de.tudarmstadt.ukp.jwktl.api.RelationType;

/**
 * Parser component for extracting collocatoins from the German Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class DECollocationsHandler extends DERelationHandler {
	
	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public DECollocationsHandler() {
		super(RelationType.CHARACTERISTIC_WORD_COMBINATION, "Charakteristische Wortkombinationen");
	}
	
	@Override
	protected String addDelimiters(final String text) {
		return super.addDelimiters(text.replace("''", ""));
	}
	
}

