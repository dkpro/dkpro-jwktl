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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Parser component for extracting sense definitions from the German Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class DESenseDefinitionHandler extends DEBlockHandler{

	protected static final Pattern INDEX_PATTERN = Pattern.compile("^\\s*\\[([^\\[\\]]{0,20}?)\\](.*)$");
	
	protected Map<Integer, String> definitions;
	protected Set<Integer> indexSet;
	
	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public DESenseDefinitionHandler() {
		super("Bedeutungen", "Bedeutung");
	}

	@Override
	public boolean processHead(String text, ParsingContext context) {
		definitions = new TreeMap<Integer, String>();
		return true;
	}
	
	@Override
	public boolean processBody(final String textLine, final ParsingContext context) {
		String definition;
		if (textLine.startsWith("::")) {
			// Subsense: append to previous sense definition.
			definition = textLine.substring(2).trim();
			if (indexSet != null)
				for (Integer idx : indexSet)
					appendDefinition(idx, definition);
			else {
				Matcher matcher = INDEX_PATTERN.matcher(definition);
				if (matcher.find()) {
					String indexStr = matcher.group(1);
					definition = matcher.group(2).trim();
					Set<Integer> newIndexSet = StringUtils.compileIndexSet(indexStr);
					for (Integer idx : newIndexSet)
						appendDefinition(idx, definition);
				} else
					appendDefinition(Integer.MAX_VALUE, definition.trim());
			}
			
		} else
		if (textLine.startsWith(":")) {
			// Sense: Create a new definition.
			definition = textLine.substring(1);
//			int i = definition.indexOf(']');
//			if (i > 0) {
				Matcher matcher = INDEX_PATTERN.matcher(definition);
				if (matcher.find()) {
					String indexStr = matcher.group(1);
					definition = matcher.group(2).trim();
					indexSet = StringUtils.compileIndexSet(indexStr);
					for (Integer idx : indexSet)
						appendDefinition(idx, definition);
				} else {
					indexSet = null;
					appendDefinition(Integer.MAX_VALUE, definition.trim());
				}
				
				/*String indexStr = definition.substring(0, i);
				definition = definition.substring(i + 1).trim();
				indexSet = StringUtils.compileIndexSet(indexStr);
				for (Integer idx : indexSet)
					appendDefinition(idx, definition);*/
//			}
		}
		return false;
	}
	
	protected void appendDefinition(final Integer idx, final String definition) {
		String def = definitions.get(idx);
		if (def == null)
			definitions.put(idx, definition);
		else
			definitions.put(idx, def + "\n" + definition);
//		System.out.println(idx + ": " + definitions.get(idx) + "X\n");
	}

	/**
	 * <p>Use NumeratedListProcessor to read lists of glosses and put them into a SenseEntry object. 
	 */
	public void fillContent(final ParsingContext context) {
		WiktionaryEntry posEntry = context.findEntry();
		for (Entry<Integer, String> definition : definitions.entrySet()) {
			if (definition.getValue().isEmpty())
				continue; // Skip empty definitions!
			
			WiktionarySense sense = posEntry.createSense();
			if (definition.getKey() < Integer.MAX_VALUE)
				sense.setMarker(Integer.toString(definition.getKey()));
			sense.setGloss(new WikiString(definition.getValue()));
			posEntry.addSense(sense);
		}		
	}
	
}
