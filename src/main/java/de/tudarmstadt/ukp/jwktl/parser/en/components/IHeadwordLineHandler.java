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

import java.util.regex.Pattern;
import java.util.stream.Stream;

interface IHeadwordLineHandler {
	Pattern LEGACY_PATTERN   = Pattern.compile("\\A'''[^']+'''");

	default boolean isTemplate(String line) {
		return line.startsWith("{{");
	}

	default boolean isExcludedTemplate(String line) {
		return Stream.of(
			"{{wikipedia",
			"{{slim-wikipedia",
			"{{wiki}}",
			"{{wikispecies",
			"{{wikiversity",
			"{{wikiquote",
			"{{commons",
			"{{attention",
			"{{rfc",
			"{{examples",
			"{{enum|",
			"{{no entry"
		).anyMatch(templ -> line.toLowerCase().contains(templ));
	}

	default boolean isLegacyHeader(String line) {
		return LEGACY_PATTERN.matcher(line).find();
	}

	default boolean isHeadwordLine(String line) {
		return isLegacyHeader(line) || (isTemplate(line) && !isExcludedTemplate(line));
	}
}
