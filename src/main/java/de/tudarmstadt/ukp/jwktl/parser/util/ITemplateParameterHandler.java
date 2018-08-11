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
package de.tudarmstadt.ukp.jwktl.parser.util;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;

/**
 * Implementation of this interface are used to handle parameters of templates.
 * 
 * @author Alexey Valikov
 */
public interface ITemplateParameterHandler {

	/**
	 * Parameter handlers may keep state while processing Wiktionary entries. This
	 * method will be called for each new Wiktionary entry before handling.
	 */
	public void reset();

	/**
	 * Checks if this handler can handle the given template parameter.
	 * 
	 * @param label
	 *            parameter label.
	 * @param value
	 *            parameter value, may be <code>null</code>.
	 * @param wordForm
	 *            word form.
	 * @param context
	 *            parsing context.
	 * @return <code>true<code> if this handler can handle the given parameter,
	 *         <code>false<code> otherwise.
	 */
	public boolean canHandle(String label, String value, WiktionaryWordForm wordForm, ParsingContext context);

	/**
	 * Handles the given parameter. This should only be called if
	 * {@see #canHandle(String, String, WiktionaryWordForm, ParsingContext)}
	 * returned true.
	 * 
	 * @param label
	 *            parameter label.
	 * @param value
	 *            parameter value, may be <code>null</code>.
	 * @param wordForm
	 *            word form.
	 * @param context
	 *            parsing context.
	 */
	public void handle(String label, String value, WiktionaryWordForm wordForm, ParsingContext context);
}
