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
package de.tudarmstadt.ukp.jwktl.parser.wikisaurus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.IWiktionaryPageParser;
import de.tudarmstadt.ukp.jwktl.parser.IWritableWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENSenseIndexedBlockHandler;
import de.tudarmstadt.ukp.jwktl.parser.util.IDumpInfo;

/**
 * (Yet experimental) parser for the Wikisaurus entries (i.e., wiki pages
 * in the Wikisaurus namespace that contain thesaurus-like information). 
 * @author Yevgen Chebotar
 * @author Christian M. Meyer
 */
public class WikisaurusArticleParser implements IWiktionaryPageParser {

	protected IWritableWiktionaryEdition wiktionaryDB;
	protected List<WikisaurusEntry> entryQueue;
	protected String currentTitle;
	protected String currentNamespace;

	protected Map<String, Integer> notFoundRelation;
	protected Map<String, RelationType> relTypeMap;
	
	/** Instanciates the parser for the given database. */
	public WikisaurusArticleParser(final IWritableWiktionaryEdition wiktionaryDB) {
		this.wiktionaryDB = wiktionaryDB;
		entryQueue = new LinkedList<WikisaurusEntry>();
		
		notFoundRelation = new HashMap<String, Integer>();
		relTypeMap = new HashMap<String, RelationType>();
		relTypeMap.put("synonyms", RelationType.SYNONYM);
		relTypeMap.put("synonym", RelationType.SYNONYM);
		relTypeMap.put("ambiguous synonyms", RelationType.SYNONYM);
		relTypeMap.put("antonyms", RelationType.ANTONYM);
		relTypeMap.put("antonym", RelationType.ANTONYM);
		relTypeMap.put("hyponyms", RelationType.HYPONYM);
		relTypeMap.put("instances", RelationType.HYPONYM);
		relTypeMap.put("hypernym", RelationType.HYPERNYM);
		relTypeMap.put("hypernyms", RelationType.HYPERNYM);
		relTypeMap.put("descendants", RelationType.DESCENDANT);
		relTypeMap.put("holonyms", RelationType.HOLONYM);
		relTypeMap.put("meronyms", RelationType.MERONYM);
		relTypeMap.put("troponyms", RelationType.TROPONYM);
		relTypeMap.put("coordinate terms", RelationType.COORDINATE_TERM);		
		relTypeMap.put("pseudo-synonyms", RelationType.SEE_ALSO);
		relTypeMap.put("near synonyms", RelationType.SEE_ALSO);
		relTypeMap.put("near antonyms", RelationType.SEE_ALSO);
		relTypeMap.put("related terms", RelationType.SEE_ALSO);		
		relTypeMap.put("various", RelationType.SEE_ALSO);
		relTypeMap.put("see also", RelationType.SEE_ALSO);		
		relTypeMap.put("idiomatic synonyms", RelationType.SYNONYM);
		relTypeMap.put("idioms/phrases", RelationType.SYNONYM);
		relTypeMap.put("idioms", RelationType.SYNONYM);
	}
	
	public void onParserStart(final IDumpInfo dumpInfo) {}
	public void onSiteInfoComplete(final IDumpInfo dumpInfo) {}
	public void onClose(final IDumpInfo dumpInfo) {}
	
	public void onPageStart() {}
	public void onPageEnd() {}
	
	public void setAuthor(String author) {}
	public void setRevision(long revisionId) {}
	public void setTimestamp(Date timestamp) {}
	public void setPageId(long pageId) {}

	public void setTitle(final String title, final String namespace) {
		currentTitle = title;
		currentNamespace = namespace;
	}
	
	public void setText(String text) {
		if (!"Wikisaurus".equals(currentNamespace))
			return;
		
		for (WikisaurusEntry entry : parseWikisaurusEntries(currentTitle, text))
			saveWikisaurusEntry(entry, true);
	}

	protected Set<WikisaurusEntry> parseWikisaurusEntries(final String title, 
			final String text) {
		Set<WikisaurusEntry> result = new HashSet<WikisaurusEntry>();		
		BufferedReader reader = new BufferedReader(new StringReader(text));
		String line;
		ILanguage currentLang = null;
		PartOfSpeech currentPos = null;
		RelationType currentRelType = null;
		boolean inList = false;
		boolean inRelation = false;
		boolean inSense = false;
		WikisaurusEntry wikisaurusSense = null;
		try {			
			while ((line = reader.readLine()) != null) {				
				line = line.trim();
				if (line.isEmpty())
					continue;
				
				int countSectionIdentifier = 0;
				while (countSectionIdentifier < line.length() && line.charAt(countSectionIdentifier) == '=')
					countSectionIdentifier++;				
				line = line.replace("=", "");
				
				if (wikisaurusSense != null 
						&& countSectionIdentifier >=2 
						&& (countSectionIdentifier < 4 
								|| (countSectionIdentifier == 4 && line.startsWith("{{ws sense")))) {
					result.add(wikisaurusSense);
					wikisaurusSense = null;
				}
					
				if (countSectionIdentifier == 2) { // Language
					currentLang = Language.findByName(line);
					inRelation = false;
					inSense = false;
				} else 
				if (countSectionIdentifier == 3) { // POS
					currentPos = PartOfSpeech.findByName(line); // TODO: language-specific POS tags?
					inRelation = false;
					inSense = false;
				} else 
				if (countSectionIdentifier == 4 && line.startsWith("{{ws sense")) { // Sense
					String senseDef = extractSenseDefinition(line);
					wikisaurusSense = new WikisaurusEntry(title, currentPos, currentLang, senseDef);
					inRelation = false;
					inSense = true;
				} else 
				if ((countSectionIdentifier == 5 || countSectionIdentifier == 4) && inSense) { // Relation type			
					currentRelType = relTypeMap.get(line.trim().toLowerCase());
					inRelation = true;
					if(currentRelType == null){
						System.out.println(title + " RELATION NOT FOUND: " + line);
						if (notFoundRelation.containsKey(line))
							notFoundRelation.put(line, notFoundRelation.get(line) + 1);
						else 
							notFoundRelation.put(line, 1);
					}
				} else 
				if (line.startsWith("{{ws beginlist")) {
					inList = true;
				} else 
				if (line.startsWith("{{ws endlist")) {
					inList = false;
				} else 
				if (line.startsWith("{{ws|") && inRelation && inList) {											
					String[] target = extractRelTarget(line);	
					if (currentRelType != null)
						wikisaurusSense.addRelation(target[0], target[1], currentRelType);
				}
			}
			
			if (wikisaurusSense != null)
				result.add(wikisaurusSense);
		} catch (IOException e) {
			throw new RuntimeException("Error while parsing text of Wikisaurus page " + title, e);
		}
		
		return result;
	}
	
	public void onParserEnd(final IDumpInfo dumpInfo) {
		// Save the remaining entries.
		for (WikisaurusEntry entry : entryQueue)
			saveWikisaurusEntry(entry, false);
		entryQueue.clear();
	}

	protected void saveWikisaurusEntry(final WikisaurusEntry wikisaurusEntry,
			boolean allowCaching) {
		// Find the Wiktionary page for this Wikisaurus entry.
		WiktionaryPage page = wiktionaryDB.getPageForWord(wikisaurusEntry.getTitle());
		if (page == null) {
			if (allowCaching)
				entryQueue.add(wikisaurusEntry);
			else
				System.err.println("PAGE NOT FOUND: " + wikisaurusEntry.getTitle());
			return;
		}
		
		// Find the Wiktionary entry within the Wiktionary page.
		for (WiktionaryEntry entry : page.entries()) {
			if (!Language.equals(entry.getWordLanguage(), wikisaurusEntry.getLanguage()))
				continue;
			if (!PartOfSpeech.equals(entry.getPartOfSpeech(), wikisaurusEntry.getPartOfSpeech()))
				continue;
			
			WiktionarySense sense = ENSenseIndexedBlockHandler.findMatchingSense(wikisaurusEntry.getSenseDefinition(), entry);
			if (sense == null) {
				System.err.println("Unable to find source word sense: " + wikisaurusEntry);
				continue;
			}
			
			for (WiktionaryRelation relation : wikisaurusEntry.getRelations())							
					sense.addRelation(relation);
		}
		
		wiktionaryDB.savePage((WiktionaryPage) page);		
	}
	
	/** Extracts sense definition from Wikisaurus line. */
	protected String extractSenseDefinition(String wsSense) {
		wsSense = wsSense.replace("}", "").replace("{", "");
		String[] parts = wsSense.split("\\|");
		if (parts.length > 1)
			return parts[1];
		else 
			return "";
	}
	
	/** Extracts relation target and target sense definition (if exists). */
	protected String[] extractRelTarget(String wsRel) {		
		String[] result = {"",""};
		
		int lastBracket = wsRel.lastIndexOf('}');
		if (lastBracket > 0)
			wsRel = wsRel.substring(0, lastBracket);
		else 
			return result;
		
		wsRel = wsRel.replace("}", "").replace("{", "");
		String parts[] = wsRel.split("\\|");
		if (parts.length > 2) {
			result[0] = parts[1];
			if (!parts[2].equals("-"))
				result[1] = parts[2];
		} else 
		if(parts.length > 1)
			result[0] = parts[1];
		return result;
	}

}
