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
package de.tudarmstadt.ukp.jwktl.parser.util;

import java.util.Objects;
import java.util.regex.Matcher;

public class PatternUtils {

	private PatternUtils() {
	}

	/**
	 * Extracts the index from the given <b>previously matched/found</b >regex
	 * matcher. If the matcher was not matched yet, throws an exception. Otherwise
	 * searches for the first non-<code>null</code> group, parse it as an integer
	 * and returns the result. If no non-<code>null</code> groups are found, returns
	 * <code>null</code>.
	 * 
	 * @param matcher
	 *            regular expression matcher.
	 * @return Extracted index or <code>null</code>.
	 * @throws NumberFormatException
	 *             If value of the first non-<code>null</code> group could not be
	 *             parsed as an integer.
	 * @throws IllegalArgumentException If the matcher was not matched yet.
	 */
	public static Integer extractIndex(Matcher matcher) throws NumberFormatException, IllegalArgumentException {
		Objects.requireNonNull(matcher, "matcher must not be null.");
		try {
			if (matcher.start() < 0) {
				throw new IllegalArgumentException("The matcher was not matched yet.");
			}
		} catch (IllegalStateException isex) {
			throw new IllegalArgumentException("The matcher was not matched yet.", isex);
		}
		for (int index = 1; index <= matcher.groupCount(); index++) {
			String group = matcher.group(index);
			if (group != null) {
				return Integer.valueOf(group);
			}
		}
		return null;
	}

}
