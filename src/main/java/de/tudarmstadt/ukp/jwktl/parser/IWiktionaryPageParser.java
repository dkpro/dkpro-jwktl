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

import de.tudarmstadt.ukp.jwktl.parser.util.IDumpInfo;

/**
 * Generic interface for parsing XML dumps in a MediaWiki format.
 * @author Christian M. Meyer
 */
public interface IWiktionaryPageParser {

	/** Hotspot that is invoked upon starting the parser. */
	public void onParserStart(final IDumpInfo dumpInfo);

	/** Hotspot that is invoked after the siteinfo header has been read. At
	 *  this point in time, the dump info contains all information,
	 *  including dump language and namespaces. */
	public void onSiteInfoComplete(final IDumpInfo dumpInfo);

	/** Hotspot that is invoked when the parser is about to end. Use this
	 *  method for writing any owing information to a file or database. For
	 *  closing and cleaning up resources, you should, however, use the
	 *  {@link #onClose(IDumpInfo)} hotspot. */
	public void onParserEnd(final IDumpInfo dumpInfo);

	/** Hotspot that is invoked after the parser has finished its work. This
	 *  method is supposed to close and cleanup any resources (e.g., closing
	 *  a database connection). It is called after all 
	 *  {@link #onParserEnd(IDumpInfo)} calls have been handled. */
	public void onClose(final IDumpInfo dumpInfo);

	/** Hotspot that is invoked upon starting a new article page. */
	public void onPageStart();

	/** Hotspot that is invoked upon finishing the current article page. */
	public void onPageEnd();

	/** Hotspot that is invoked after the current page's author is read. */
	public void setAuthor(final String author);

	/** Hotspot that is invoked after the current page's revision id is read. */
	public void setRevision(long revisionId);

	/** Hotspot that is invoked after the current page's timestamp is read. */
	public void setTimestamp(final Date timestamp);

	/** Hotspot that is invoked after the current page's id is read. */
	public void setPageId(long pageId);

	/** Hotspot that is invoked after the current page's title is read. 
	 * @param namespace */
	public void setTitle(final String title, final String namespace);

	/** Hotspot that is invoked after the current page's text is read. */
	public void setText(final String text);

}
