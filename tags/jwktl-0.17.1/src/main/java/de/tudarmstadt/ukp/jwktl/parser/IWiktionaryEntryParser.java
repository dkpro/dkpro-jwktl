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
package de.tudarmstadt.ukp.jwktl.parser;

import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;

/**
 * A parser for separating an article page's text into individual 
 * Wiktionary word entries.
 * @author Christian M. Meyer
 */
public interface IWiktionaryEntryParser {

	/** Creates Wiktionary word entry instances from the provided text, and 
	 *  adds them to the given article page. 
	 *  @throws WiktionaryException in case of any parser errors. */
	public void parse(final WiktionaryPage page, final String text) 
			throws WiktionaryException;
	
}
