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

/**
 * A handler encapsulated the extraction of the information items encoded in 
 * a certain article constituent. There might be, for example, a handler 
 * for extracting pronunciation information. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public interface IBlockHandler {
	
	/** Return <code>true</code> if the handler requests to process the article
	 *  constituent starting at the given line of text. */
	public boolean canHandle(final String blockHeader);
	
	/** If the handler requested to process this constituent, this hotspot 
	 *  will be called for processing the section header of this 
	 *  article constituent. Return <code>true</code> if the handler
	 *  requests to handle also the body of this constituent. */
	public boolean processHead(final String line, final ParsingContext context);

	/** If the handler requested to process the body of this constituent, this 
	 *  hotspot will be called for processing each line of the constituent's 
	 *  body. Return <code>true</code> if the handler requests to handle also
	 *  the next line using this handler. */
	public boolean processBody(final String line, final ParsingContext context);

	/** This hotspot is invoked if the parser releases this handler. It can be 
	 *  used to store the extracted information to the Wiktionary data
	 *  objects stored in the parsing context. */
	public void fillContent(final ParsingContext context);
	
}
