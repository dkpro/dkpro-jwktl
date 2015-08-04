/*******************************************************************************
 * Copyright 2015
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

import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;

public interface IWordFormHandler {
	/**
	 * Start parsing the specified text for inflected word forms. The
	 * extracted forms can be accessed using {@link #getWordForms()}
	 * once all lines have been parsed.
	 *
	 * @param line a line of wikitext
	 * @return whether the handler could parse the line
	 */
	boolean parse(String line);

	/**
	 * @return a list of extracted word forms, or an empty list.
	 */
	List<IWiktionaryWordForm> getWordForms();

	/**
	 * @return the extracted gender, or null.
	 */
	GrammaticalGender getGender();
}
