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

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.parser.util.IDumpInfo;

/**
 * Generic interface for writable Wiktionary language editions used by the
 * parsers to store the extracted entries and information types. 
 * Implementations of this class may provide a database or file system 
 * connection to persistently store the extracted information. 
 * @author Christian M. Meyer
 */
public interface IWritableWiktionaryEdition extends IWiktionaryEdition {

	WiktionaryPage getPageForId(long id);
	
	WiktionaryPage getPageForWord(final String word);
	
	/** Hotspot called after parsing has finished to save the metadata
	 *  of the dump file and the basic parsing statistics. */
	void saveProperties(final IDumpInfo dumpInfo)
			throws WiktionaryException;

	/** Adds the given page to the Wiktionary edition (e.g., storing it
	 *  in a database). */
	void savePage(final WiktionaryPage page);

	/** Force a database commit of the pages saved so far. */
	void commit();
	
	/** Assigns the given language to the Wiktionary edition. */
	void setLanguage(final ILanguage language);

	/** Sorts the entries by word form before assigning an ID to them.
	 *  THIS METHOD IS KEPT FOR COMPATIBILITY. YOU SHOULD NOT USE THIS
	 *  METHOD. */
	void setEntryIndexByTitle(boolean entryIndexByTitle);
	
}
