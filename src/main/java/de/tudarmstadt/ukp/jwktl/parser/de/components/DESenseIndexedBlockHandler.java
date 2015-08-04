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
package de.tudarmstadt.ukp.jwktl.parser.de.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Abstract parser component for extracting sense-disambiguated information 
 * items from the German Wiktionary (e.g., example sentences, 
 * semantic relations, translations). 
 * @author Christian M. Meyer
 */
public abstract class DESenseIndexedBlockHandler<InformationType> 
		extends DEBlockHandler {

	protected static final Pattern INDEX_PATTERN = Pattern.compile("^\\s*\\[([^\\[\\]]{0,20}?)\\](.*)$");
	
	protected Map<Integer, List<String>> indexedInformation;
	protected Set<Integer> indexSet;

	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public DESenseIndexedBlockHandler(final String... labels) {
		super(labels);
	}

	@Override
	public boolean processHead(final String text, final ParsingContext context) {
		indexedInformation = new TreeMap<Integer, List<String>>();
		return super.processHead(text, context);
	}
	
	@Override
	public boolean processBody(final String textLine, final ParsingContext context) {
		String text = textLine.trim();
		if (text.startsWith("::")) {
			// Append to previous index set.
			text = text.substring(2).trim();
			Matcher matcher = INDEX_PATTERN.matcher(text);
			if (matcher.find()) {
				String indexStr = matcher.group(1);
				text = matcher.group(2);
				Set<Integer> subIndexSet = StringUtils.compileIndexSet(indexStr);
				if (subIndexSet != null && subIndexSet.size() > 0) {
					indexSet = subIndexSet;
					for (Integer idx : indexSet)
						addIndexedLine(idx, text);
				} else
					for (Integer idx : indexSet)
						appendIndexedLine(idx, text);
			} else
			if (indexSet != null)
				for (Integer idx : indexSet)
					appendIndexedLine(idx, text);
			/*if (indexSet != null)
				for (Integer idx : indexSet)
					appendIndexedLine(idx, text);*/			
		} else 
		if (text.startsWith(":")) {
			// Determine index set and add the information.
			text = text.substring(1).trim();
			Matcher matcher = INDEX_PATTERN.matcher(text);
			if (matcher.find()) {
				String indexStr = matcher.group(1);
				text = matcher.group(2);
				indexSet = StringUtils.compileIndexSet(indexStr);
				for (Integer idx : indexSet)
					addIndexedLine(idx, text);
			} else {
				indexSet = new HashSet<Integer>();
				indexSet.add(0);
				addIndexedLine(0, text);
			}
		} else {
			// Append to previous index set.
			if (indexSet != null)
				for (Integer idx : indexSet)
					appendIndexedLine(idx, text);
		}
		return super.processBody(textLine, context);
	}
	
	protected void addIndexedLine(int index, final String text) {
		if (text.isEmpty())
			return;
		if (index < 0)
			index = 0;
		
		List<String> lines = indexedInformation.get(index);
		if (lines == null) {
			lines = new ArrayList<String>();
			indexedInformation.put(index, lines);
		}
		lines.add(text);
	}
		
	protected void appendIndexedLine(int index, final String text) {
		if (text.isEmpty())
			return;
		if (index < 0)
			index = 0;
		
		List<String> lines = indexedInformation.get(index);
		if (lines == null) {
			lines = new ArrayList<String>();
			indexedInformation.put(index, lines);
		} 
		if (lines.size() > 0) {
			lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "\n" + text);
		} else
			lines.add(text);
	}
		
	protected abstract List<InformationType> extract(int index, final String text);

	public void fillContent(final ParsingContext context) {
		WiktionaryEntry posEntry = context.findEntry();
		for (Entry<Integer, List<String>> indexedLine : indexedInformation.entrySet()) {
			WiktionarySense sense = posEntry.findSenseByMarker(Integer.toString(indexedLine.getKey())); //TODO: index set as a list of strings?
			for (String line : indexedLine.getValue()) {
				List<InformationType> extractedInformation = extract(indexedLine.getKey(), line);
				if (extractedInformation != null) {
					for (InformationType info : extractedInformation)
						if (sense != null)
							updateSense(sense, info);
						else
							updatePosEntry(posEntry, info);
				}
			}
		}
	}

	protected abstract void updateSense(final WiktionarySense sense, final InformationType info);

	protected abstract void updatePosEntry(final WiktionaryEntry posEntry, final InformationType info);

}
