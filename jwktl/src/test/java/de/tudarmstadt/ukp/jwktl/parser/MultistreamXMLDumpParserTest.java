/*******************************************************************************
 * Copyright 2015
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
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

public class MultistreamXMLDumpParserTest extends TestCase {
	private File multistreamDump;
	private File multistreamDumpIndex;
	private AtomicInteger pageCount;
	private MultistreamXMLDumpParser subject;

	private XMLDumpParser parser = new XMLDumpParser() {
		protected void onElementStart(final String name, final XMLDumpHandler handler) {
			if ("page".equals(name)) {
				pageCount.incrementAndGet();
			}
		}
		protected void onElementEnd(final String name, final XMLDumpHandler handler) {}
		public void register(final IWiktionaryPageParser pageParser) {}
		public Iterable<IWiktionaryPageParser> getPageParsers() {
			return null;
		}
	};

	@Override
	public void setUp() throws Exception {
		super.setUp();
		pageCount = new AtomicInteger();
		multistreamDump = new File(getClass().getResource("/enwiktionary-20150224-pages-articles-multistream.xml.bz2").getFile());
		multistreamDumpIndex = new File(getClass().getResource("/enwiktionary-20150224-pages-articles-multistream-index.txt.bz2").getFile());
		subject = new MultistreamXMLDumpParser(parser);
	}

	public void testParseWithOffsets() throws Exception {
		TreeSet<Long> offsets = new TreeSet<Long>();
		offsets.add(654L);
		offsets.add(261373L);
		subject.parse(multistreamDump, offsets);
		assertEquals(200, pageCount.get());
	}

	public void testCollectOffsets() throws Exception {
		final TreeSet<Long> offsets = subject.collectOffsets(multistreamDumpIndex, new MultistreamFilter() {
			@Override public boolean accept(long pageId, String pageTitle) {
				return pageTitle.equals("nonsense") || pageTitle.equals("abattoir");
			}
		});
		assertEquals(2, offsets.size());
		assertTrue(offsets.contains(654L));
		assertTrue(offsets.contains(261373L));
	}
}
