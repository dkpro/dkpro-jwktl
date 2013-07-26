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

import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IPronunciation;
import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.IWiktionaryEntryParser;
import de.tudarmstadt.ukp.jwktl.parser.entry.EntryFactory;

/**
 * Data object for information on the {@link IWiktionaryEntryParser}. This 
 * entry parser creates and maintains an instance of this type to share 
 * information on the dump file with all its registed 
 * {@link IBlockHandler}s.
 * @author Christian M. Meyer
 */
public class ParsingContext {

	protected WiktionaryPage page;
//	protected WiktionaryEntry entry;
	protected EntryFactory entryFactory;

	protected ILanguage language;
	protected PartOfSpeech partOfSpeech;
	protected String header;
	protected IWikiString etymology;
	protected List<IPronunciation> pronunciations;

	/** Create a new parsing context for the given Wiktionary page. */
	public ParsingContext(final WiktionaryPage page) {
		this(page, null);
	}
	

	/** Create a new parsing context for the given Wiktionary page and
	 *  use the specified entry factory to construct lexical entries. */
	public ParsingContext(final WiktionaryPage page, 
			final EntryFactory entryFactory) {
		this.page = page;
		this.entryFactory = entryFactory;
	}
	
	/** Returns the current {@link WiktionaryPage} that is being parsed. */
	public WiktionaryPage getPage() {
		return page;
	}
	
	/** Returns the {@link WiktionaryEntry} that is currently being
	 *  parsed. */
	public WiktionaryEntry findEntry() {
		return entryFactory.findEntry(this);
	}

	/** Returns the {@link ILanguage} of the current 
	 *  {@link IWiktionaryEntry}. */
	public ILanguage getLanguage() {
		return language;
	}
	
	/** Save the specified language in the context object. If the language
	 *  changes, the header, part of speech tag, etymology, and pronunciations
	 *  are reset to <code>null</code>. */
	public void setLanguage(final ILanguage language) {
		if (!Language.equals(language, this.language)) {		
			// Language has changed: reset part of speech, etymology, and pronunciation.
			this.language = language;
			header = null;
			partOfSpeech = null;
			etymology = null;
			pronunciations = null;			
		}		
	}

	/** Returns the header of the current{@link IWiktionaryEntry}. The header 
	 *  usually corresponds to the lemma of the dictionary article. */
	public String getHeader() {
		return header;
	}
	
	/** Save the specified header in the context object. */
	public void setHeader(final String header) {
		this.header = header;
	}
	
	/** Returns the {@link PartOfSpeech} of the current 
	 *  {@link IWiktionaryEntry}. */
	public PartOfSpeech getPartOfSpeech() {
		return partOfSpeech;
	}
	
	/** Save the specified part of speech in the context object. */
	public void setPartOfSpeech(final PartOfSpeech partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	
	/** Returns the etymology of the current {@link IWiktionaryEntry}. */
	public IWikiString getEtymology() {
		return etymology;
	}
	
	/** Save the specified etymology in the context object. */
	public void setEtymology(final IWikiString etymology) {
		this.etymology = etymology;
	}
	
	/** Returns the pronunciations of the current {@link IWiktionaryEntry}. */
	public List<IPronunciation> getPronunciations() {
		return pronunciations;
	}
	
	/** Save the specified pronunciations in the context object. */
	public void setPronunciations(List<IPronunciation> pronunciations) {
		this.pronunciations = pronunciations;
	}
	
}
