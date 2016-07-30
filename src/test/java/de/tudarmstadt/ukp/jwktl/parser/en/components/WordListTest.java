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

import java.util.Collections;
import java.util.Iterator;

import junit.framework.TestCase;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class WordListTest extends TestCase {

	public void testParseEmptyLine() throws Exception {
		final WordList parsed = WordList.parse("");
		assertEquals(0, parsed.size());
	}

	public void testParseNullLine() throws Exception {
		try {
			WordList.parse(null);
			fail("expected null pointer");
		} catch (NullPointerException ignored) {
		}
	}

	public void testWordListImplementsIterable() {
		final WordList list = new WordList(null, asList("1", "2"));
		final Iterator<String> iterator = list.iterator();
		assertTrue(iterator.hasNext());
		assertEquals("1", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("2", iterator.next());
		assertFalse(iterator.hasNext());
	}

	public void testParseLineWithOneWord() throws Exception {
		final WordList parsed = WordList.parse("foo");
		assertEquals(singletonList("foo"), parsed.words);
	}

	public void testParseCommaSeparatedLine() throws Exception {
		final WordList parsed = WordList.parse("foo, baz, fruz");
		assertEquals(3, parsed.size());
		assertEquals(asList("foo", "baz", "fruz"), parsed.words);
	}

	public void testParseLineWithCorrectlyFormattedComment() throws Exception {
		final WordList parsed = WordList.parse("(''sense one''): foo, baz, fruz");
		assertEquals(3, parsed.size());
		assertEquals(asList("foo", "baz", "fruz"), parsed.words);
		assertEquals("sense one", parsed.comment);
	}

	public void testParseLineWithMalformedComment() throws Exception {
		final WordList parsed = WordList.parse("(sense one) foo, baz, fruz");
		assertEquals(3, parsed.size());
		assertEquals(asList("foo", "baz", "fruz"), parsed.words);
		assertEquals("sense one", parsed.comment);
	}

	public void testParseLineWithCommentFormattedAsTemplate() {
		final WordList parsed = WordList.parse("{{sense|one|two}} [[foo]]");
		assertEquals("one|two", parsed.comment);
		assertEquals(Collections.singletonList("foo"), parsed.words);
	}

	public void testOnlyExtractLinkedComponentsOneItem() {
		final WordList parsed = WordList.parse(" {{l/pt|casa}}");
		assertEquals(singletonList("casa"), parsed.words);
	}

	public void testOnlyExtractLinkedComponentsOneItemWithoutInitialWhitespace() {
		final WordList parsed = WordList.parse("{{l/pt|casa}}");
		assertEquals(singletonList("casa"), parsed.words);
	}

	public void testOnlyExtractLinkedComponentsTwoItems() {
		final WordList parsed = WordList.parse(" {{l/pt|casa}}, [[casa de banho]]");
		assertEquals(asList("casa", "casa de banho"), parsed.words);
	}

	public void ignoreTestOnlyExtractLinkedComponents() {
		// TODO: currently gets parsed incorrectly
		final WordList parsed = WordList.parse(" {{l/pt|casa roubada, trancas à porta}} — Robbed house, locked doors.");
		assertEquals(1, parsed.size());
		assertEquals(singletonList("casa roubada, trancas à porta"), parsed.words);
	}
}
