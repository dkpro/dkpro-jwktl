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
package de.tudarmstadt.ukp.jwktl.parser.entry;

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Factory for {@link WiktionaryEntry}s used by the parsers.
 * @author Christof Müller
 * @author Lizhen Qu
 */
public abstract class EntryFactory {

	/** Checks if the current page contains a {@link WiktionaryEntry} 
	 *  matching for the current context (part of speech, language, etc.)
	 *  and returns it. If no entry could be found, a new one is created
	 *  (using {@link #createEntry(ParsingContext)}). */
	public WiktionaryEntry findEntry(final ParsingContext context) {
		ILanguage language = context.getLanguage();
		PartOfSpeech partOfSpeech = context.getPartOfSpeech();
		
		List<WiktionaryEntry> posEntryList = new ArrayList<WiktionaryEntry>();
		for (WiktionaryEntry entry : context.getPage().entries())
			if (PartOfSpeech.equals(partOfSpeech, entry.getPartOfSpeech()) && Language.equals(language, entry.getWordLanguage()))
				posEntryList.add(entry);			
//		List<WiktionaryEntry> posEntryList = word.getPosEntries(partOfSpeech, language);
		
		if (posEntryList.size() == 0) {
			WiktionaryEntry entry = createEntry(context);
			context.getPage().addEntry(entry);
			return entry;
		} else
			return posEntryList.get(posEntryList.size() - 1);		
	}
	
	/** Create a new {@link WiktionaryEntry}  based on the current context.
	 *  This method always creates a new entry. Use 
	 *  {@link #findEntry(ParsingContext)} if you want to use existing
	 *  entries (which is usually the case). */
	public WiktionaryEntry createEntry(final ParsingContext context) {
		ILanguage language = context.getLanguage();
		PartOfSpeech partOfSpeech = context.getPartOfSpeech();
		String header = context.getHeader();
		
		WiktionaryEntry entry = context.getPage().createEntry();
		entry.setWordLanguage(language);
		entry.addPartOfSpeech(partOfSpeech);
		if (header != null)
			entry.setHeader(header);
		entry.setWordEtymology(context.getEtymology());
		return entry;
	}

	/** Converts the given string into a part of speech enum type. */
	public abstract PartOfSpeech findPartOfSpeech(final String name);
	
}
