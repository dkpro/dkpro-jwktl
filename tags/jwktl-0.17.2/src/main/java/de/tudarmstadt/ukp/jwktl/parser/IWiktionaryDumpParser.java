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

import java.io.File;

import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;

/**
 * Parser for Wiktionary dump files obtained from 
 * http://download.wikimedia.org/backup-index.html.
 * @author Christian M. Meyer 
 */
public interface IWiktionaryDumpParser {

	/** Starts the parsing of the given dump file. The file can be either
	 *  bzip2-compressed or the extracted XML version.
	 *  @throws WiktionaryException in case of any parser errors. */
	public void parse(final File dumpFile) throws WiktionaryException;
	
	/** Register the given {@link IWiktionaryPageParser}. The registered 
	 *  parser will then be notified once a Wiktionary-related XML tag
	 *  has been processed. */
	public void register(final IWiktionaryPageParser pageParser);
	
	/** Returns the list of all registered {@link IWiktionaryPageParser}s. */
	public Iterable<IWiktionaryPageParser> getPageParsers();
	
}
