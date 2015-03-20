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
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import junit.framework.TestCase;

public class ChainedCBZip2InputStreamTest extends TestCase {
	public void testConsumeWholeStream() throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		InputStream stream =
				new ChainedCBZip2InputStream(new File("src/test/resources/enwiktionary-20150224-pages-articles-multistream.xml.bz2"));
		long count = 0;
		int n;
		byte[] buffer = new byte[8192];
		while ((n = stream.read(buffer)) != -1) {
			count += n;
			md5.update(buffer, 0, n);
		}
		String signature = new BigInteger(1, md5.digest()).toString(16);
		assertEquals(1800617, count);
		assertEquals("bde6a439065407c9c74c83b1f2f97520", signature);

		stream.close();
	}
}
