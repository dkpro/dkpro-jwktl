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
package de.tudarmstadt.ukp.jwktl.parser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;

/**
 * Test case for {@link XMLDumpParser}.
 * @author Christian M. Meyer
 */
public class XMLDumpParserTest extends TestCase {
	
	private static class MyXMLDumpParser extends XMLDumpParser {

		private Queue<String> expectedValues;
		
		protected MyXMLDumpParser(Queue<String> expectedValues) {
			this.expectedValues = expectedValues;
		}
		
		public void register(final IWiktionaryPageParser pageParser) {}
		
		public Iterable<IWiktionaryPageParser> getPageParsers() {
			return null;
		}
		
		@Override
		protected void onParserStart() {
			super.onParserStart();
			assertEquals(expectedValues.poll(), "onParserStart");
		}

		protected void onElementStart(final String name, final XMLDumpHandler handler) {
			assertEquals(expectedValues.poll(), "onElementStart: " + name);
		}
		
		protected void onElementEnd(final String name, final XMLDumpHandler handler) {
			assertEquals(expectedValues.poll(), "onElementEnd: " + name);
		}
		
		@Override
		protected void onParserEnd() {
			assertEquals(expectedValues.poll(), "onParserEnd");
			assertTrue(expectedValues.isEmpty());
			super.onParserEnd();
		}
		
	}

	/***/
	public void testParsedInformation() {
		/*<dump_parser_test>
		  <header>
		    <param name="param1" value="value1" />
		    <param name="param1" value="value2" />
		  </header>
		  <element id="1">
		    Some text content
		  </element>
		  <element id="2">
		    Some text content
		  </element>
		</dump_parser_test>*/
		
		Queue<String> expectedValues = new LinkedList<String>();
		expectedValues.offer("onParserStart");
		expectedValues.offer("onElementStart: dump_parser_test");
			expectedValues.offer("onElementStart: header");
				expectedValues.offer("onElementStart: param");
				expectedValues.offer("onElementEnd: param");
				expectedValues.offer("onElementStart: param");
				expectedValues.offer("onElementEnd: param");
			expectedValues.offer("onElementEnd: header");
			expectedValues.offer("onElementStart: element");
			expectedValues.offer("onElementEnd: element");
			expectedValues.offer("onElementStart: element");
			expectedValues.offer("onElementEnd: element");
		expectedValues.offer("onElementEnd: dump_parser_test");
		expectedValues.offer("onParserEnd");
				
		MyXMLDumpParser parser = new MyXMLDumpParser(expectedValues);
		parser.parse(new File("src/test/resources/XMLDumpParserTest.xml"));
		assertTrue(expectedValues.isEmpty());
	}
	
	/***/
	public void testBzip2Stream() {
		Queue<String> expectedValues = new LinkedList<String>();
		expectedValues.offer("onParserStart");
		expectedValues.offer("onElementStart: dump_parser_test");
			expectedValues.offer("onElementStart: header");
				expectedValues.offer("onElementStart: param");
				expectedValues.offer("onElementEnd: param");
				expectedValues.offer("onElementStart: param");
				expectedValues.offer("onElementEnd: param");
			expectedValues.offer("onElementEnd: header");
			expectedValues.offer("onElementStart: element");
			expectedValues.offer("onElementEnd: element");
			expectedValues.offer("onElementStart: element");
			expectedValues.offer("onElementEnd: element");
		expectedValues.offer("onElementEnd: dump_parser_test");
		expectedValues.offer("onParserEnd");
				
		MyXMLDumpParser parser = new MyXMLDumpParser(expectedValues);
		parser.parse(new File("src/test/resources/XMLDumpParserTest.xml.bz2"));
		assertTrue(expectedValues.isEmpty());
	}
	
	/***/
	public void testErrors() {
		XMLDumpParser parser = new XMLDumpParser() {			
			protected void onElementStart(final String name, final XMLDumpHandler handler) {}			
			protected void onElementEnd(final String name, final XMLDumpHandler handler) {}
			public void register(final IWiktionaryPageParser pageParser) {}
			public Iterable<IWiktionaryPageParser> getPageParsers() { 
				return null; 
			}
		};
		
		// Missing file.
		try {
			parser.parse(new File("missing_dump$@%.xml"));
			fail("WiktionaryException/IOException expected");
		} catch (WiktionaryException e) {
			assertTrue(e.getCause() instanceof IOException);
		}

		// Invalid XML.
		try {
			parser.parse(new File("src/test/resources/XMLDumpParserErrorXMLTest.xml"));
			fail("WiktionaryException/SAXException expected");
		} catch (WiktionaryException e) {
			assertTrue(e.getCause() instanceof SAXException);
		}
		
		// Empty Bzip2 file.
		try {
			parser.parse(new File("src/test/resources/XMLDumpParserErrorEmptyTest.xml.bz2"));
			fail("WiktionaryException/IOException expected");
		} catch (WiktionaryException e) {
			assertTrue(e.getCause() instanceof IOException);
		}
		
		// Erroneous Bzip2 header.
		try {
			parser.parse(new File("src/test/resources/XMLDumpParserErrorHeaderTest.xml.bz2"));
			fail("WiktionaryException/IOException expected");
		} catch (WiktionaryException e) {
			assertTrue(e.getCause() instanceof IOException);
		}
	}
	
}
