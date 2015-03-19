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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.apache.tools.bzip2.CBZip2InputStream;

/**
 * A parser which can deal with MediaWiki multistreams.
 *
 * Multistreams consist of several concatenated bz2 stream which typically only contain
 * 100 pages. The index file (*-multistream-index.txt.bz2) is an index into this file, in the format
 * <code>offset:page_id:page_title</code>, e.g.
 * <pre>
 *  654:6:Wiktionary:Welcome, newcomers
 *  654:7:Wiktionary:GNU Free Documentation License
 *  ...
 * </pre>
 * The actual parsing is delegated to a standard {@link XMLDumpParser}.
 */
class MultistreamXMLDumpParser {
	private static final Logger logger = Logger.getLogger(MultistreamXMLDumpParser.class.getName());
	private static final String MEDIAWIKI_OPENING = "<mediawiki>";
	private static final String MEDIAWIKI_CLOSING = "</mediawiki>";

	private final XMLDumpParser parser;

	public MultistreamXMLDumpParser(XMLDumpParser parser) {
		this.parser = parser;
	}

	public void parseMultistream(File dumpFile, File indexFile, MultistreamFilter filter) throws IOException {
		parse(dumpFile, collectOffsets(indexFile, filter));
	}

	protected void parse(final File dumpFile, TreeSet<Long> offsets) throws IOException {
		if (offsets.isEmpty()) {
			throw new IOException("no valid offsets");
		}
		RandomAccessFile file = new RandomAccessFile(dumpFile, "r");
		offsets.add(0L); // make sure header / siteInfo gets parsed
		try {
			for (long offset : offsets) {
				logger.fine("parsing contents at offset "+offset);
				parser.parseStream(getInputStreamAtOffset(file, offset));
			}
		} finally {
			file.close();
		}
	}

	protected TreeSet<Long> collectOffsets(File indexFile, MultistreamFilter filter) throws IOException {
		logger.fine("parsing index file " + indexFile);
		BufferedReader bufferedReader = null;
		try {
			final long start = System.currentTimeMillis();
			final TreeSet<Long> offsets = new TreeSet<Long>();
			bufferedReader = new BufferedReader(new InputStreamReader(parser.openBz2File(indexFile)));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				final String[] split = line.split(":", 3);
				final long offset = Long.parseLong(split[0]);
				final long pageId = Long.parseLong(split[1]);
				final String pageTitle = split[2];

				if (filter.accept(pageId, pageTitle)) {
					offsets.add(offset);
				}
			}
			logger.fine(String.format("done in %d ms, offsets to parse: %s",
					System.currentTimeMillis() - start, offsets));
			return offsets;
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
	}

	private InputStream getInputStreamAtOffset(final RandomAccessFile file, long offset) throws IOException {
		if (offset + 2 >= file.length()) {
			throw new IOException("read past EOF");
		}
		file.seek(offset + 2); // skip past 'BZ' header
		InputStream is = new CBZip2InputStream(new FileInputStream(file.getFD()) {
			@Override
			public void close() throws IOException {
			}
		});
		if (offset == 0) {
			return new SequenceInputStream(is, new ByteArrayInputStream(MEDIAWIKI_CLOSING.getBytes()));
		} else {
			return new SequenceInputStream(
					new ByteArrayInputStream(MEDIAWIKI_OPENING.getBytes()),
					new SequenceInputStream(is,
							new ByteArrayInputStream(MEDIAWIKI_CLOSING.getBytes())));
		}
	}
}
