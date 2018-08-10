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
package de.tudarmstadt.ukp.jwktl.parser.de.components.nountable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.PatternUtils;

public abstract class DEWordFormNounTablePatternIndexParameterHandler {

	protected final Pattern pattern;
	protected final DEWordFormNounTableExtractor nounTableHandler;

	public DEWordFormNounTablePatternIndexParameterHandler(DEWordFormNounTableExtractor nounTableHandler, String regex) {
		Objects.requireNonNull(nounTableHandler, "nounTableHandler must not be null.");
		Objects.requireNonNull(regex, "regex must not be null.");
		this.nounTableHandler = nounTableHandler;
		this.pattern = Pattern.compile(regex);
	}

	public boolean canHandle(WiktionaryWordForm wordForm, String label, String value, ParsingContext context) {
		Matcher matcher = pattern.matcher(label);
		return matcher.find();
	}

	public void handle(WiktionaryWordForm wordForm, String label, String value, ParsingContext context) {
		Matcher matcher = pattern.matcher(label);
		if (matcher.find()) {
			final Integer index = PatternUtils.extractIndex(matcher);
			handleIfFound(wordForm, label, index, value, matcher, context);
		}
	}

	public abstract void handleIfFound(WiktionaryWordForm wordForm, String label, Integer index, String value, Matcher matcher,
			ParsingContext context);

}
