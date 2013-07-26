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
package de.tudarmstadt.ukp.jwktl.parser.en.components;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.IPronunciation;
import de.tudarmstadt.ukp.jwktl.api.IPronunciation.PronunciationType;
import de.tudarmstadt.ukp.jwktl.api.entry.Pronunciation;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Parser component for extracting pronunciations from the English Wiktionary. 
 * @author Christian M. Meyer
 */
public class ENPronunciationHandler extends ENBlockHandler {	
	
	protected static final Pattern PRONUNCIATION_CONTEXT = Pattern.compile("\\{\\{(?:a|sense)\\|([^\\}\\|]+?)\\}\\}");
	protected static final Pattern PRONUNCIATION_IPA = Pattern.compile("\\{\\{IPA\\|([^\\}]+?)(?:\\|lang=\\w+)?\\}\\}");
	protected static final Pattern PRONUNCIATION_SAMPA = Pattern.compile("\\{\\{SAMPA\\|([^\\}\\|]+?)\\}\\}");
	protected static final Pattern PRONUNCIATION_AUDIO = Pattern.compile("\\{\\{audio\\|([^\\}\\|]+?)(?:\\|([^\\}\\|]+?))?\\}\\}");
	protected static final Pattern PRONUNCIATION_RYHME = Pattern.compile("\\{\\{rhymes\\|([^\\}\\|]+?)\\}\\}");
	
	protected List<IPronunciation> pronunciations;

	public boolean canHandle(String blockHeader) {
		blockHeader = StringUtils.strip(blockHeader, "{}=: 1234567890").toLowerCase();
		if ("pronunciation".equals(blockHeader) || "pronuncaition".equals(blockHeader)
				|| "pronunceation".equals(blockHeader) || "pronunciaton".equals(blockHeader))
			return true;
		
		return false;
	}
	
	@Override
	public boolean processHead(final String textLine, final ParsingContext context) {
		pronunciations = new ArrayList<IPronunciation>();
		return super.processHead(textLine, context);
	}

	@Override
	public boolean processBody(final String textLine, final ParsingContext context) {
		StringBuilder ctx = new StringBuilder();
		Matcher matcher = PRONUNCIATION_CONTEXT.matcher(textLine); 
		while (matcher.find())
			ctx.append(" ").append(matcher.group(1));
//		System.out.println("  ctx = " + ctx.toString().trim());
				
		matcher = PRONUNCIATION_IPA.matcher(textLine); 
		if (matcher.find())
			pronunciations.add(new Pronunciation(PronunciationType.IPA, 
					matcher.group(1), ctx.toString().trim()));
		matcher = PRONUNCIATION_SAMPA.matcher(textLine); 
		if (matcher.find())
			pronunciations.add(new Pronunciation(PronunciationType.SAMPA, 
					matcher.group(1), ctx.toString().trim()));
		//TODO: remove lang= param from ipa and sampa text  
		//TODO: english pronunciation key/AHD
		//TODO: separate property for sense
		matcher = PRONUNCIATION_AUDIO.matcher(textLine); 
		if (matcher.find()) {
			String note = ctx + " " + matcher.group(2);
			pronunciations.add(new Pronunciation(PronunciationType.AUDIO, 
					matcher.group(1), note.trim()));
		}
		matcher = PRONUNCIATION_RYHME.matcher(textLine); 
		if (matcher.find())
			pronunciations.add(new Pronunciation(PronunciationType.RHYME, 
					matcher.group(1), ctx.toString().trim()));
		
		/*System.out.println(">>>>" + textLine);
		for (Pronunciation p : pronunciations)
			System.out.println(p.getType() + ": " + p.getText() + " " + p.getNote());
		pronunciations.clear();*/
		
		return false;
	}
	
	public void fillContent(final ParsingContext context) {		
		// There is no PosEntry yet - store the pronunciations in the context
		// and add them later on (in ENWordLanguageHandler).
		context.setPronunciations(pronunciations);
	}

	/** Returns the list of all extracted pronunciations. */
	public List<IPronunciation> getPronunciations() {
		return pronunciations;
	}
	
}
