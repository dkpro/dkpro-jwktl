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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

/**
 * Test case for {@link PatternUtils}.
 * 
 * @author Alexey Valikov
 */
public class PatternUtilsTest extends TestCase {

	/***/
	public void testExtractIndex() {
		Pattern pattern = Pattern.compile("^Group$|^Group\\s([1-9,a-z])$");
		try {
			assertEquals(null, PatternUtils.extractIndex(matcher(pattern, "Puorg")));
			fail("Extracting index from non-matched matcher must fail.");
		} catch (IllegalArgumentException iaex) {
			assertTrue(true);
		}
		assertEquals(null, PatternUtils.extractIndex(matcher(pattern, "Group")));
		assertEquals(Integer.valueOf(1), PatternUtils.extractIndex(matcher(pattern, "Group 1")));
		assertEquals(Integer.valueOf(8), PatternUtils.extractIndex(matcher(pattern, "Group 8")));
		try {
			PatternUtils.extractIndex(matcher(pattern, "Group q"));
			fail("Extracting index from non-integer group must fail.");
		} catch (NumberFormatException nfex) {
			assertTrue(true);
		}
	}

	private static Matcher matcher(Pattern pattern, String str) {
		Matcher matcher = pattern.matcher(str);
		matcher.find();
		return matcher;
	}
}