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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;

/**
 * Utility class for producing simple string representations for the most
 * commonly used JWKTL data objects. The string representations are designed
 * for human perception rather than as an interoperable data exchange format.
 * @author Christian M. Meyer
 */
public class WiktionaryFormatter {

	// -- Singleton interface --
	
	private static WiktionaryFormatter instance;
	
	/** Returns the static singleton reference. */
	public static WiktionaryFormatter instance() {
		if (instance == null)
			instance = new WiktionaryFormatter();
		return instance;
	}
	
	
	// -- Format methods --

	/** Returns the ID and the title of the given page. */
	public String formatHeader(final IWiktionaryPage page) {
		if (page == null)
			return "";
		return new StringBuilder()
				.append(page.getId()).append(" ")
				.append(page.getTitle()).toString();
	}
	
	/** Returns the entry key, word, word language, and part of speech
	 *  of the given entry. */
	public String formatHeader(final IWiktionaryEntry entry) {
		if (entry == null)
			return "";
		return new StringBuilder()
				.append(entry.getKey()).append(" ")
				.append(entry.getWord()).append(" (")
				.append(entry.getWordLanguage()).append(", ")
				.append(entry.getPartOfSpeech()).append(")").toString();
	}

	/** Returns the sense key, word, sense index, and definition of
	 *  the given word sense. */
	public String formatHeader(final IWiktionarySense sense) {
		return new StringBuilder()
				.append(sense.getKey()).append(" ")
				.append(sense.getEntry().getWord()).append(" [")
				.append(sense.getIndex()).append("]: ")
				.append(sense.getGloss().getPlainText()).toString();
	}

	/** Returns a string representation of the given page using separate 
	 *  prefixed lines for each information type. */
	public String formatPage(final IWiktionaryPage page, final ILanguage... languages) {
		StringBuilder result = new StringBuilder();
		for (IWiktionaryEntry entry : page.getEntries()) {
			// Restrict to the specified languages (if any).
			if (languages != null && languages.length > 0) {
				boolean found = false;
				for (ILanguage l : languages)
					if (Language.equals(l, entry.getWordLanguage())) {
						found = true;
						break;
					}
				if (!found)
					continue;
			}
			
			result.append(formatEntry(entry));
		}

		for (String ws : page.getCategories())
			result.append("  CAT: ").append(ws).append("\n");
		TreeSet<String> iwl = new TreeSet(page.getInterWikiLinks());
		result.append("  INT: ").append(Arrays.toString(iwl.toArray())).append("\n");
		
		return result.toString();
	}
	
	/** Returns a string representation of the given entry using separate 
	 *  prefixed lines for each information type. */
	public String formatEntry(final IWiktionaryEntry entry) {
		StringBuilder result = new StringBuilder();
		result.append(entry.getWord()).append("/")
				.append(entry.getPartOfSpeech()).append(" ")
				.append(formatLanguage(entry.getPage().getEntryLanguage()))
				.append("/")
				.append(formatLanguage(entry.getWordLanguage())).append("\n");
		
		if (entry.getPronunciations() != null)
			for (IPronunciation p : entry.getPronunciations())
				result.append("  PRO: ").append(p.getType().name())
						.append(" ").append(p.getText()).append("\n");	
			
		if (entry.getWordEtymology() != null)
			result.append("  ETY: ").append(entry.getWordEtymology().getText()).append("\n");
		
		for (IWiktionarySense sense : entry.getSenses())
			result.append(formatSense(sense));
			
		for (IWikiString ws : entry.getReferences())
			result.append("  REF: ").append(ws.getText()).append("\n");
		if (entry.getEntryLink() != null)
			result.append("  LNK: ").append(entry.getEntryLinkType())
					.append(": ").append(entry.getEntryLink()).append("\n");
		
		return result.toString();
	}
		
	/** Returns a string representation of the given word sense using separate 
	 *  prefixed lines for each information type. */
	public String formatSense(final IWiktionarySense sense) {
		StringBuilder result = new StringBuilder();
		String senseIdx = (sense.getIndex() == 0 
				? "-" 
				: Integer.toString(sense.getIndex()));
				
		if (sense.getGloss() != null)
			result.append("  GLS [").append(senseIdx).append("] ")
					.append(sense.getGloss().getText()).append("\n");
				
		if (sense.getExamples() != null)
			for (IWikiString ws : sense.getExamples())
				result.append("  EXP [").append(senseIdx).append("] ")
						.append(ws.getText()).append("\n");
				
		if (sense.getQuotations() != null)
			for (IQuotation quotation : sense.getQuotations()) {
				result.append("  QTN [").append(senseIdx).append("] ")
						.append(quotation.getSource().getText()).append("\n");
				for (IWikiString ws : quotation.getLines())
					result.append("    ").append(ws.getText()).append("\n");
			}
		
		ArrayList<String> sortList = new ArrayList<String>();
		if (sense.getRelations() != null) {
			sortList.clear();
			for (IWiktionaryRelation relation : sense.getRelations())
				sortList.add("  REL [" + senseIdx + "] " 
						+ relation.getRelationType() + ": "
						+ relation.getTarget());
				Collections.sort(sortList);
				for (String s : sortList)
					result.append(s);
		}

		if (sense.getTranslations() != null) {
			sortList.clear();
			for (IWiktionaryTranslation trans : sense.getTranslations())
				sortList.add("  TRL [" + senseIdx + "] " 
						+ formatLanguage(trans.getLanguage()) + ": "
						+ trans.getTranslation());
				Collections.sort(sortList);
				for (String s : sortList)
					result.append(s).append("\n");
		}
		
		return result.toString();
	}

	protected static String formatLanguage(final ILanguage language) {
		if (language == null)
			return "NULL";
		else
			return language.getName().toUpperCase();
	}

}
