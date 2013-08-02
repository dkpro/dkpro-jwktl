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
package de.tudarmstadt.ukp.jwktl.api.filter;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;

/**
 * Interface for implementing a filter for {@link IWiktionaryPage}s.
 * That is, a possibility for selecting which pages are to be processed
 * (i.e., accepted) or skipped (i.e., filtered out).
 * @author Christian M. Meyer
 */
public interface IWiktionaryPageFilter {

	/** Return <code>true</code> if the given page should be accepted or 
	 *  <code>false</code> if it should be filtered out. */
	public boolean accept(final IWiktionaryPage page);
	
}
