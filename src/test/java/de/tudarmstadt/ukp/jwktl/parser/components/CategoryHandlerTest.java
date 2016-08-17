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
package de.tudarmstadt.ukp.jwktl.parser.components;

import java.util.Collections;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CategoryHandlerTest {
	private static final String CATEGORY_FOO = "[[Category:Foo]]";
	private static final String CATEGORY_BAR = "[[Category:Bar]]";
	private static final String CATEGORY_COLON_FOO = "[[:Category:Foo]]";
	// https://www.mediawiki.org/wiki/Help:Categories#Sort_key
	private static final String CATEGORY_FOO_SORT_KEY = "[[Category:Foo|sort]]";

	private CategoryHandler categoryHandler;

	@Before
	public void setUp() throws Exception {
		categoryHandler = new CategoryHandler("Category");
	}

	@Test
	public void testCanHandleNoMatch() throws Exception {
		assertFalse(categoryHandler.canHandle("XXX"));
	}

	@Test
	public void testCanHandleMatchSingleLine() throws Exception {
		assertTrue(categoryHandler.canHandle(CATEGORY_FOO));
	}

	@Test
	public void testCanHandleMatchTrailingWhitespace() throws Exception {
		assertTrue(categoryHandler.canHandle(CATEGORY_FOO+"    "));
	}

	@Test
	public void testCanHandleMatchLeadingWhitespace() throws Exception {
		assertTrue(categoryHandler.canHandle("   "+CATEGORY_FOO));
	}

	@Test
	public void testCanHandleMatchEmptyString() throws Exception {
		assertFalse(categoryHandler.canHandle(""));
	}

	@Test
	public void testCanHandleMatchNull() throws Exception {
		assertFalse(categoryHandler.canHandle(null));
	}

	@Test(expected = IllegalStateException.class)
	public void testNoMatchThrowsIllegalStateException() throws Exception {
		parseCategories("XXX");
	}

	@Test(expected = IllegalStateException.class)
	public void testEmptyStringThrowsIllegalStateException() throws Exception {
		assertEquals(emptyList(), parseCategories(""));
	}

	@Test(expected = IllegalStateException.class)
	public void testNullThrowsIllegalStateException() throws Exception {
		assertEquals(emptyList(), parseCategories(new String[] {null}));
	}

	@Test
	public void testSingleCategory() throws Exception {
		assertEquals(singletonList("Foo"), parseCategories(CATEGORY_FOO));
	}

	@Test
	public void testSingleCategoryColon() throws Exception {
		assertEquals(singletonList("Foo"), parseCategories(CATEGORY_COLON_FOO));
	}

	@Test
	public void testSingleCategorySortKey() throws Exception {
		assertEquals(singletonList("Foo"), parseCategories(CATEGORY_FOO_SORT_KEY));
	}

	@Test
	public void testSingleCategoryLeadingWhitespace() throws Exception {
		assertEquals(singletonList("Foo"), parseCategories("      "+CATEGORY_FOO));
	}

	@Test
	public void testSingleCategoryTrailingWhitespace() throws Exception {
		assertEquals(singletonList("Foo"), parseCategories(CATEGORY_FOO+"        "));
	}

	@Test
	public void testMultipleCategories() throws Exception {
		assertEquals(asList("Bar", "Foo"),
			parseCategories(CATEGORY_FOO, CATEGORY_BAR));
	}

	@Test
	public void testMultipleDuplicatedCategories() throws Exception {
		assertEquals(singletonList("Foo"),
				parseCategories(CATEGORY_FOO, CATEGORY_FOO));
	}

	@Test
	public void testMultipleCategoriesSingleLine() throws Exception {
		assertEquals(asList("Bar", "Foo"),
				parseCategories(CATEGORY_FOO + " " + CATEGORY_BAR));
		assertEquals(asList("Bar", "Foo"),
				parseCategories("  "+CATEGORY_FOO + "    " + CATEGORY_BAR+" "));
	}

	private List<String> parseCategories(String... input) {
		WiktionaryPage page = new WiktionaryPage();
		ParsingContext context = new ParsingContext(page);
		for (String s : input) {
			categoryHandler.processHead(s, context);
		}
		categoryHandler.fillContent(context);
		List<String> categories =  page.getCategories();
		Collections.sort(categories);
		return categories;
	}
}
