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

import java.util.Date;

import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.parser.util.IDumpInfo;

/**
 * Abstract base class for implementations of the 
 * {@link IWiktionaryPageParser} interface. The class manages the dump info
 * and the current namespace, as well as the creation of a 
 * {@link WiktionaryPage}.
 * @param <PageType> The type of page to create, usually 
 *   {@link WiktionaryPage}. 
 * @author Christian M. Meyer 
 */
public abstract class WiktionaryPageParser<PageType extends WiktionaryPage> 
		implements IWiktionaryPageParser {

	protected PageType page;
	protected IDumpInfo dumpInfo;
	protected String currentNamespace;

	@Override
	public void onParserStart(final IDumpInfo dumpInfo) {
		this.dumpInfo = dumpInfo;
	}
	
	public void onSiteInfoComplete(final IDumpInfo dumpInfo) {}
	
	public void onParserEnd(final IDumpInfo dumpInfo) {}

	public void onClose(IDumpInfo dumpInfo) {}
	
	public void onPageStart() {
		page = createPage();
		page.setEntryLanguage(dumpInfo.getDumpLanguage());
	}
	
	public void onPageEnd() {}

	protected abstract PageType createPage();

	public void setPageId(long pageId) {
		page.setId(pageId);
	}

	public void setTitle(final String title, final String namespace) {
		currentNamespace = namespace;
		page.setTitle(title);
	}

	public void setAuthor(final String author) {
		page.setAuthor(author);
	}

	public void setRevision(long revisionId) {
		page.setRevision(revisionId);
	}
	
	public void setTimestamp(final Date timestamp) {
		page.setTimestamp(timestamp);
	}
	
	public abstract void setText(final String text);

}
