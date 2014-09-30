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

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.parser.IWiktionaryDumpParser;

/**
 * Default implementation of the {@link IDumpInfo} interface.
 * @author Christian M. Meyer
 */
public class DumpInfo implements IDumpInfo {

	protected File dumpFile;
	protected ILanguage dumpLanguage;
	protected Set<String> namespaces;
	protected int processedPages;
	protected IWiktionaryDumpParser parser;
	
	/** Instanciate the dump info object for the given dump file and parser 
	 *  object. */
	public DumpInfo(final File dumpFile, final IWiktionaryDumpParser parser) {
		this.dumpFile = dumpFile;
		this.parser = parser;
		reset();
	}

	public void reset() {
		namespaces = new HashSet<String>();
		processedPages = 0;
	}
	
	public File getDumpFile() {
		return dumpFile;
	}
		
	public ILanguage getDumpLanguage() {
		return dumpLanguage;
	}
	
	/** Assign the specified dump language. */
	public void setDumpLanguage(final ILanguage dumpLanguage) {
		this.dumpLanguage = dumpLanguage;
	}
	
	public Set<String> getNamespaces() {
		return namespaces;
	}

	public boolean hasNamespace(final String namespace) {
		return namespaces.contains(namespace);
	}
	
	/** Add the given namespace to the list of namespaces defined for 
	 *  this dump file. */
	public void addNamespace(final String namespace) {
		namespaces.add(namespace);
	}
	
	public int getProcessedPages() {
		return processedPages;
	}
	
	/** Increment the number of processed pages by one. */
	public void incrementProcessedPages() {
		processedPages++;
	}
	
	public IWiktionaryDumpParser getParser() {
		return parser;
	}

}
