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
package de.tudarmstadt.ukp.jwktl;

import java.io.File;
import java.util.logging.LogManager;

import junit.framework.TestCase;

/**
 * Abstract test case for JWKTL.
 * @author Christian M. Meyer
 */
public abstract class WiktionaryTestCase extends TestCase {

	protected static final File RESOURCE_PATH = new File("src/test/resources");
	
	protected File workDir;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		workDir = new File("target/test-output/" 
				+ getClass().getName() + "_" + this.getName());
		deleteDirectory(workDir);
		workDir.mkdir();
	}
	
	@Override
	protected void tearDown() throws Exception {
		deleteDirectory(workDir);
		super.tearDown();
	}
	
	protected static boolean deleteDirectory(final File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++)
				if (files[i].isDirectory()) {
					if (!deleteDirectory(files[i]))
						System.err.println("Unable to delete dir: " + files[i]);
				} else {
					if (!files[i].delete())
						System.err.println("Unable to delete file: " + files[i]);
				}
		}
		return path.delete();
	}

	static {
		LogManager.getLogManager().reset();
	}
	
}
