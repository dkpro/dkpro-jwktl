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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tools.bzip2.CBZip2InputStream;

/**
 * An input stream which keeps decompressing data from the file / stream until
 * it hits EOF. Useful for decoding files which contain multiple bz2 streams.
 */
class ChainedCBZip2InputStream extends InputStream {
	private final InputStream underlying;
	private InputStream currentBZ2Stream;

	public ChainedCBZip2InputStream(File input) throws FileNotFoundException {
		this(new BufferedInputStream(new FileInputStream(input)));
	}

	public ChainedCBZip2InputStream(InputStream input) throws FileNotFoundException {
		this.underlying = input;
	}

	@Override
	public int read() throws IOException {
		if (currentBZ2Stream == null) {
			final byte[] header = new byte[2];
			final int headerBytes = underlying.read(header);
			if (headerBytes == -1) {
				return -1;
			} else if (headerBytes != 2) {
				throw new IOException("could not read header");
			} else if (header[0] != 'B' || header[1] != 'Z') {
				throw new IOException("invalid bz2 header");
			}
			currentBZ2Stream = new CBZip2InputStream(underlying);
		}

		final int read = currentBZ2Stream.read();
		if (read == -1) {
			currentBZ2Stream = null;
			return read();
		} else {
			return read;
		}
	}

	@Override
	public void close() throws IOException {
		underlying.close();
	}
}
