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
package de.tudarmstadt.ukp.jwktl.parser;

import java.io.File;

import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;

public interface IWiktionaryMultistreamDumpParser extends IWiktionaryDumpParser {
	/**
	 * Parses a multistream XML dump file
	 *
	 * @param multistreamDumpFile the dumpfile (<code>*-pages-articles-multistream-index.txt.bz2</code>)
	 * @param indexFile           the matching index file (<code>*-pages-articles-multistream.xml.bz2</code>)
	 * @param filter              the filter to use to constrain the parsed pages
	 * @throws de.tudarmstadt.ukp.jwktl.api.WiktionaryException
	 */
	void parseMultistream(File multistreamDumpFile,
						  File indexFile,
						  MultistreamFilter filter) throws WiktionaryException;
}
