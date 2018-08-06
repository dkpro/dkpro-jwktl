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

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import junit.framework.TestCase;

public class ParsingContextTest extends TestCase {
	
	public void testGenderIndex() {
		final ParsingContext context = new ParsingContext(new WiktionaryPage());
		assertEquals(null, context.getDefaultGenderByIndex());
		assertEquals(null, context.getGenderByIndex(1));
		context.addGenderToIndex(GrammaticalGender.MASCULINE, 3);
		assertEquals(GrammaticalGender.MASCULINE, context.getGenderByIndex(3));
		context.addDefaultGenderToIndex(GrammaticalGender.NEUTER);
		assertEquals(GrammaticalGender.NEUTER, context.getDefaultGenderByIndex());
	}
}