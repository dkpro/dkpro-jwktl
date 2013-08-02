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

import java.io.File;
import java.util.Set;

import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.parser.IWiktionaryDumpParser;
import de.tudarmstadt.ukp.jwktl.parser.IWiktionaryPageParser;

/**
 * Data object for information on the {@link IWiktionaryDumpParser}. This 
 * dump parser creates and maintains an instance of this type to share 
 * information on the dump file with all its registed 
 * {@link IWiktionaryPageParser}.
 * @author Christian M. Meyer
 */
public interface IDumpInfo {

	/** Returns the current Wiktionary XML dump file, which is being parsed. */
	public File getDumpFile();
		
	/** Returns the language of the Wiktionary edition this dump file 
	 *  belongs to. The language is automatically determined from the 
	 *  base URL of the Wiktionary edition. */
	public ILanguage getDumpLanguage();
	
	/** Returns a set containing all namespaces registered in the siteinfo
	 *  header of the XML dump file. */
	public Set<String> getNamespaces();

	/** Returns <code>true</code> if the specified namespace has been
	 *  defined within the siteinfo header of the XML dump file. */
	public boolean hasNamespace(final String namespace);
	
	/** Returns the number of pages in the dump file that have been
	 *  processed. */
	public int getProcessedPages();
	
	/** Returns the Wiktionary dump parser instance that maintains this
	 *  dump info object. */
	public IWiktionaryDumpParser getParser();

	/** Reset the dump information, such the number of processed pages. This 
	 *  is to be called before a new dump file is being processed. Users of 
	 *  this interface should normally not invoke this method. */
	public void reset();
	
}
