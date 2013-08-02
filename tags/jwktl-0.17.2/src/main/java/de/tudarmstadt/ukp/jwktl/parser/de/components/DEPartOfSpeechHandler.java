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

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Parser component for extracting part of speech tags from the German Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class DEPartOfSpeechHandler extends DEBlockHandler {

	protected static final Pattern POS_PATTERN = Pattern.compile("\\{\\{Wortart\\|([^\\|\\}]+)(?:\\|([^\\|\\}]+))?\\}\\}");
	protected static final Pattern GENDER_PATTERN = Pattern.compile("\\b(?:\\{\\{)?([mfnw]|sächlich)(?:\\}\\})?\\.?\\b");
	
	protected DEEntryFactory entryFactory = new DEEntryFactory();

	protected List<PartOfSpeech> posList = new LinkedList<PartOfSpeech>();
	protected GrammaticalGender gender;
	
	/** Check if the given text contains a part of speech header.
	 */
	public boolean canHandle(String blockHeader) {
		if (blockHeader == null || blockHeader.isEmpty())
			return false;
		
		blockHeader = blockHeader.trim();
		if ((blockHeader.startsWith("===") || blockHeader.startsWith("'''"))
				&& blockHeader.length() > 7) {				
			Matcher matcher = POS_PATTERN.matcher(blockHeader);
			return matcher.find();
		} else
			return false;
	}
	
	/** Extract the part of speech tags and additional grammatical 
	 *  information. */
	@Override
	public boolean processHead(final String text, final ParsingContext context) {
		PartOfSpeech partOfSpeech = null;
		posList.clear();
		gender = null;
		
		// Extract POS.
		String textLine = text.trim();
		if (textLine.isEmpty())
			return true;
		
		Matcher headBlockMatcher = POS_PATTERN.matcher(textLine);
		while (headBlockMatcher.find()) {
			// Extract language if not yet extracted.
			if (headBlockMatcher.groupCount() >= 2
					&& context.getLanguage() == null) {
				ILanguage language = Language.findByName(headBlockMatcher.group(2));
				context.setLanguage(language);
			}

			String posLabel = headBlockMatcher.group(1);
			PartOfSpeech pos = entryFactory.findPartOfSpeech(posLabel); 
			if (pos != null) {
				if (partOfSpeech == null) {
					partOfSpeech = pos;
					context.setPartOfSpeech(partOfSpeech);
				} else
					posList.add(pos);
			}
		}
		
		// Extract gender.
		Matcher matcher = GENDER_PATTERN.matcher(textLine);
		if (matcher.find()) {
			String genderText = matcher.group(1);			
			if ("m".equals(genderText))
				gender = GrammaticalGender.MASCULINE;
			if ("f".equals(genderText) || "w".equals(genderText))
				gender = GrammaticalGender.FEMININE;
			if ("n".equals(genderText) || "sächlich".equals(genderText))
				gender = GrammaticalGender.NEUTER;
		}
		
		return true;
	}

	/** Set the part of pseech tags and additional grammatical information. */
	public void fillContent(final ParsingContext context) {	
		WiktionaryEntry entry = entryFactory.createEntry(context);
		for (PartOfSpeech posLabel : posList)
			entry.addPartOfSpeech(posLabel);
		entry.setGender(gender);
		context.getPage().addEntry(entry);
	}

}
