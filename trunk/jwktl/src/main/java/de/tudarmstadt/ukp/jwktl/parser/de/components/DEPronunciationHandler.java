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
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IPronunciation.PronunciationType;
import de.tudarmstadt.ukp.jwktl.api.entry.Pronunciation;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Parser component for extracting pronunciations from the German Wiktionary. 
 * @author Christian M. Meyer
 */
public class DEPronunciationHandler extends DEBlockHandler {

	protected List<Pronunciation> pronunciations;
	protected String parentContext;
	
	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public DEPronunciationHandler() {		
		super("Aussprache");
	}
	
	@Override
	public boolean processHead(final String textLine, final ParsingContext context) {
		pronunciations = new ArrayList<Pronunciation>();
		return true;
	}

	@Override
	public boolean processBody(final String textLine, final ParsingContext context) {
		// Detect global context.
		String t = textLine;
		PronunciationType contextType = null;
		int level = 0;
		while (t.charAt(0) == '*' || t.charAt(0) == ':') {
			t = t.substring(1);
			level++;
		}
		if (level == 1)
			parentContext = null;
				
		String[] markers = new String[]{"{{IPA}}", "[[Hilfe:IPA|IPA]]"};
		for (String marker : markers)
			if (t.startsWith(marker)) {
				contextType = PronunciationType.IPA;
				t = t.substring(marker.length());
			}
		markers = new String[]{"{{SAMPA}}", "[[Hilfe:SAMPA|SAMPA]]", "[[Hilfe:SAMPA|X-SAMPA]]"};
		for (String marker : markers)
			if (t.startsWith(marker)) {
				contextType = PronunciationType.SAMPA;
				t = t.substring(marker.length());
			}
		markers = new String[]{"{{Hörbeispiele}}", "[[Hilfe:Hörbeispiele|Hörbeispiele]]"};
		for (String marker : markers)
			if (t.startsWith(marker)) {
				contextType = PronunciationType.AUDIO;
				t = t.substring(marker.length());
			}
		
		// Detect local context and pronunciation entry.
//		System.out.println(">>>" + t);
		String ctx = (parentContext == null ? "" : parentContext);
		int curlyBrackets = 0;
		int sqBrackets = 0;
		int startIdx = -1;
		//int endIdx = -1;
		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) == '{') {
				curlyBrackets++;
				if (curlyBrackets >= 2)
					startIdx = i;
			} else
			if (t.charAt(i) == '}') {
				curlyBrackets--;
				if (curlyBrackets <= 0) {
					String tmpl = t.substring(startIdx + 1, i - 1);
					if (tmpl.startsWith("Lautschrift|")) {
						// IPA or SAMPA
						tmpl = tmpl.substring(12);
						int idx = tmpl.indexOf('|');
						if (idx > 0) {
							ctx = ctx + " " + tmpl.substring(idx + 1);
							tmpl = tmpl.substring(0, idx); 
						}
						ctx = ctx.trim();
						pronunciations.add(new Pronunciation(contextType == null
								? PronunciationType.IPA : contextType,
								tmpl, ctx));
						//System.out.println(tmpl + " " + ctx);
					} else if (tmpl.startsWith("Audio|")) {
						// Audio file.
						tmpl = tmpl.substring(6);
						int idx = tmpl.indexOf('|');
						if (idx > 0) {
							ctx = ctx + " " + tmpl.substring(idx + 1);
							tmpl = tmpl.substring(0, idx); 
						}
						ctx = ctx.trim();
						pronunciations.add(new Pronunciation(contextType == null
								? PronunciationType.AUDIO : contextType,
								tmpl, ctx));
						//System.out.println(tmpl + " " + ctx);
					} else {
						// Else: context label.
						ctx = ctx + " " + tmpl;
					}
				}
			} else
			if (t.charAt(i) == '[' && curlyBrackets == 0) {
				sqBrackets++;
				if (sqBrackets >= 1)
					startIdx = i;
			} else
			if (t.charAt(i) == ']' && curlyBrackets == 0) {
				sqBrackets--;
				if (sqBrackets <= 0) {
					int endIdx = i;
					while (t.charAt(endIdx) == ']')
						endIdx--;
					String tmpl = t.substring(startIdx + 1, endIdx + 1);
					ctx = ctx.trim();
					if (contextType == null)
						parentContext = tmpl;
					else
						pronunciations.add(new Pronunciation(contextType, tmpl, ctx));
//					pronunciations.add(new Pronunciation(contextType == null
//							? PronunciationType.IPA : contextType,
//							tmpl, ctx));
				}
			} else
			if (t.charAt(i) == ',') {
				if (curlyBrackets <= 0)
					ctx = (parentContext == null ? "" : parentContext);
			}
		}
		
		return false;
	}

	public void fillContent(final ParsingContext context) {
		WiktionaryEntry posEntry = context.findEntry();
		if (pronunciations != null)
			for (Pronunciation pronunciation : pronunciations)
				posEntry.addPronunciation(pronunciation);
	}
	
	/** Returns the list of all extracted pronunctions. */
	public List<Pronunciation> getPronunciations() {
		return pronunciations;
	}

}
