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
package de.tudarmstadt.ukp.jwktl.api;

import java.util.List;

/**
 * Represents a text that contains wiki markup. In addition to the original
 * text containing the wiki markup, the interface allows extracting a list
 * of wiki-internal and external links as well as a plain text representation
 * (i.e., a text without markup).    
 * @author Christian M. Meyer
 * @author Christof Müller
 * @author Lizhen Qu
 */
public interface IWikiString {

	/** Returns the original text including all wiki markup. */
	public String getText();
	
	/** Parses the original text to filter out all wiki markup and thus
	 *  returns a human-readable version of the text. Note that the parsing 
	 *  might be done on demand, so avoid invoking this method repeatedly
	 *  for the same text. */
	public String getPlainText();

	/** Returns a list of wiki-internal links. That is, all substrings
	 *  enclosed by two square brackets. Link captions will be removed.
	 *  If no wiki links are found, an empty list will be returned. Note that 
	 *  the parsing might be done on demand, so avoid invoking this method 
	 *  repeatedly for the same text. */
	public List<String> getWikiLinks();

	/* Returns a list of external links. That is, all valid URLs in the 
	 *  original text. If no external links are found, an empty list will 
	 *  be returned. Note that the parsing might be done on demand, so 
	 *  avoid invoking this method repeatedly for the same text. */
//	public List<ExternalLink> getExternalLinks();

}
