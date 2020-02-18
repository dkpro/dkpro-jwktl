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
import de.tudarmstadt.ukp.jwktl.api.util.TemplateParser;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Parser component for extracting pronunciations from the English Wiktionary.
 * @author Christian M. Meyer
 */
public class ENPronunciationHandler extends ENBlockHandler {

	private static final Pattern PRONUNCIATION_CONTEXT = Pattern.compile("\\{\\{(?:a|sense)\\|([^}|]+?)}}");
	private static final Pattern PRONUNCIATION = Pattern.compile("\\{\\{IPA\\|.+?}}");
	private static final Pattern PRONUNCIATION_AUDIO = Pattern.compile("\\{\\{audio\\|.+?}}");
	private static final Pattern PRONUNCIATION_RYHME = Pattern.compile("\\{\\{rhymes\\|.+?}}");
	private static final Pattern PRONUNCIATION_RAW = Pattern.compile("\\{\\{\\w+-(?:IPA|pron(?:unciation)?)(?:\\|.*?)?}}");
	private static final String LANG = "lang";

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
		pronunciations = new ArrayList<>();
		return super.processHead(textLine, context);
	}

	@Override
	public boolean processBody(final String textLine, final ParsingContext context) {
		final StringBuilder ctx = new StringBuilder();
		Matcher matcher = PRONUNCIATION_CONTEXT.matcher(textLine); 
		while (matcher.find())
			ctx.append(" ").append(matcher.group(1));

		final Matcher pronunMatcher = PRONUNCIATION.matcher(textLine);
		while (pronunMatcher.find()) {
			TemplateParser.parse(pronunMatcher.group(), template -> {
				final PronunciationType type = PronunciationType.valueOf(template.getName());
				final String deprecatedLang = template.getNamedParam(LANG);
				final int firstIndex = deprecatedLang == null ? 1 : 0;
				for (int i = firstIndex; i<template.getNumberedParamsCount(); i++) {
					final String pronunciation = template.getNumberedParam(i);
					if (pronunciation != null && !pronunciation.trim().isEmpty()) {
						pronunciations.add(new Pronunciation(type, pronunciation.trim(), ctx.toString().trim()));
					}
				}
				return null;
			});
		}
		matcher = PRONUNCIATION_RAW.matcher(textLine);
		while (matcher.find()) {
			pronunciations.add(new Pronunciation(PronunciationType.RAW, matcher.group(0), null));
		}

		//TODO: english pronunciation key/AHD
		//TODO: separate property for sense
		matcher = PRONUNCIATION_AUDIO.matcher(textLine);
		if (matcher.find()) {
			TemplateParser.parse(matcher.group(), template -> {
				final String deprecatedLang = template.getNamedParam(LANG);
				final int firstIndex = deprecatedLang == null ? 1 : 0;
				final String file = template.getNumberedParam(0 + firstIndex);
				final String note = template.getNumberedParam(1 + firstIndex);
				pronunciations.add(new Pronunciation(PronunciationType.AUDIO, file, note == null ? null : note.trim()));
				return null;
			});
		}
		matcher = PRONUNCIATION_RYHME.matcher(textLine);
		if (matcher.find())
			TemplateParser.parse(matcher.group(), template -> {
				final String deprecatedLang = template.getNamedParam(LANG);
				final int firstIndex = deprecatedLang == null ? 1 : 0;
				for (int i = firstIndex; i<template.getNumberedParamsCount(); i++) {
					final String rhyme = template.getNumberedParam(i);
					if (rhyme != null) {
						pronunciations.add(new Pronunciation(PronunciationType.RHYME, rhyme.trim(), ctx.toString().trim()));
					}
				}
				return null;
			});
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
