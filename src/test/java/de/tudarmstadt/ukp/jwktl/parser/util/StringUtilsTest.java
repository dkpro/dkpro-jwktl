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

import java.util.Set;

import junit.framework.TestCase;

/**
 * Test case for {@link StringUtils}.
 * @author Christian M. Meyer
 */
public class StringUtilsTest extends TestCase {

	/***/
	public void testStrip() {
		assertEquals("", StringUtils.strip("", " :"));
		assertEquals("white space", StringUtils.strip(" white space  ", " "));
		assertEquals(" Section ", StringUtils.strip("=== Section ===", "{}=:"));
		assertEquals("Section", StringUtils.strip("=== Section ===", "{}=: "));
		assertEquals("Section", StringUtils.strip("{{Section}}", "{}=:"));
	}
	
	/***/
	public void testCompileIndexSet() {
		Set<Integer> indexSet;
		indexSet = StringUtils.compileIndexSet("");
		assertTrue(indexSet.remove(-1));
		assertTrue(indexSet.isEmpty());		
		indexSet = StringUtils.compileIndexSet("[]");
		assertTrue(indexSet.remove(-1));
		assertTrue(indexSet.isEmpty());		
		indexSet = StringUtils.compileIndexSet("?");
		assertTrue(indexSet.remove(-1));
		assertTrue(indexSet.isEmpty());
		
		indexSet = StringUtils.compileIndexSet("[1]");
		assertTrue(indexSet.remove(1));
		assertTrue(indexSet.isEmpty());
		indexSet = StringUtils.compileIndexSet("[20]");
		assertTrue(indexSet.remove(20));
		assertTrue(indexSet.isEmpty());
		indexSet = StringUtils.compileIndexSet(" [ 2 ] ");
		assertTrue(indexSet.remove(2));
		assertTrue(indexSet.isEmpty());
		
		indexSet = StringUtils.compileIndexSet("[1,3, 4,15 , 10,17]");
		assertTrue(indexSet.remove(1));
		assertTrue(indexSet.remove(3));
		assertTrue(indexSet.remove(4));
		assertTrue(indexSet.remove(15));
		assertTrue(indexSet.remove(10));
		assertTrue(indexSet.remove(17));
		assertTrue(indexSet.isEmpty());		

		indexSet = StringUtils.compileIndexSet("[3-5]");
		assertTrue(indexSet.remove(3));
		assertTrue(indexSet.remove(4));
		assertTrue(indexSet.remove(5));
		assertTrue(indexSet.isEmpty());		

		indexSet = StringUtils.compileIndexSet("[ 2,3-5, 4 a ,10-11,4 ,5@]");
		assertTrue(indexSet.remove(2));
		assertTrue(indexSet.remove(3));
		assertTrue(indexSet.remove(4));
		assertTrue(indexSet.remove(5));
		assertTrue(indexSet.remove(10));
		assertTrue(indexSet.remove(11));
		assertTrue(indexSet.isEmpty());
	}
	
}
