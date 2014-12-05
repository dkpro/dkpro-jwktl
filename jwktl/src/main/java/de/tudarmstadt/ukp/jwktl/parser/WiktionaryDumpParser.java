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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.util.DumpInfo;
import de.tudarmstadt.ukp.jwktl.parser.util.IDumpInfo;

/**
 * Extension of the {@link XMLDumpParser} that reads the different XML tags
 * of the Wiktionary XML dump file format and provides hotspots for each
 * type of information. A number of {@link IWiktionaryPageParser}s can
 * be registered for this dump parser. The page parsers are called whenever
 * a certain information has been read. Different page parsers can, for
 * example, handle different page types or namespaces. 
 * @author Christian M. Meyer
 */
public class WiktionaryDumpParser extends XMLDumpParser {

	private static final Logger logger = Logger.getLogger(WiktionaryDumpParser.class.getName());
	
	protected List<IWiktionaryPageParser> parserRegistry;
	protected boolean inPage;
	protected DumpInfo dumpInfo;

	// Should not be static (not thread-safe!)
	protected DateFormat timestampFormat;
	
	/** Initializes the dump parser and registers the given page parsers. */
	public WiktionaryDumpParser(final IWiktionaryPageParser... pageParsers) {
		timestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		timestampFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		parserRegistry = new LinkedList<IWiktionaryPageParser>();
		for (IWiktionaryPageParser pageParser : pageParsers)
			register(pageParser);
	}
	
	public void register(final IWiktionaryPageParser pageParser) {
		parserRegistry.add(pageParser);
	}
	
	public Iterable<IWiktionaryPageParser> getPageParsers() {
		return parserRegistry;
	}
	
	@Override
	public void parse(final File dumpFile) throws WiktionaryException {
		dumpInfo = new DumpInfo(dumpFile, this);
		super.parse(dumpFile);
		onClose();
	}
	
	@Override
	protected void onParserStart() {
		super.onParserStart();
		inPage = false;
		dumpInfo.reset();
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.onParserStart(dumpInfo);
	}

	protected void onSiteInfoComplete() {
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.onSiteInfoComplete(dumpInfo);
	}
	
	@Override
	protected void onParserEnd() {
		super.onParserEnd();
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.onParserEnd(dumpInfo);
	}
	
	
	protected void onClose() {
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.onClose(dumpInfo);
	}
	
	@Override
	protected void onElementStart(final String name, final XMLDumpHandler handler) {
		if ("page".equals(name)) {
			inPage = true;
			onPageStart();
		}
	}
	
	@Override
	protected void onElementEnd(final String name, final XMLDumpHandler handler) {
		// Check URL.
		if ("base".equals(name)) {
			setBaseURL(handler.getContents());
		} else
		
		// Add a namespace.
		if ("namespace".equals(name) && handler.hasContents()) {
			addNamespace(handler.getContents());
		} else
			
		// Siteinfo complete.
		if ("siteinfo".equals(name)) {
			onSiteInfoComplete();
		} else	
			

		// Article.
		if ("page".equals(name)) {
			inPage = false;
			onPageEnd();
		}
		
		// Article contents.
		if (inPage) {
			if ("page".equals(handler.getParent())) {
				if ("id".equals(name))
					setPageId(Long.parseLong(handler.getContents()));					
				else
				if ("title".equals(name))
					setTitle(handler.getContents());
			} else
			if ("revision".equals(handler.getParent())) {
				if ("id".equals(name))
					setRevision(Integer.parseInt(handler.getContents()));
				else
				if ("timestamp".equals(name))
					try {
						setTimestamp(parseTimestamp(handler.getContents()));
					} catch (ParseException e) {
						setTimestamp(null);
					}
				else
				if ("text".equals(name)) {
					setText(handler.getContents());
				}
			} else
			if ("contributor".equals(handler.getParent())) {
				if ("username".equals(name))
					setAuthor(handler.getContents());
			}
		}
	}

	protected void onPageStart() {
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.onPageStart();
	}
	
	protected void onPageEnd() {
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.onPageEnd();
		
		dumpInfo.incrementProcessedPages();
		if (dumpInfo.getProcessedPages() % 25000 == 0)
			logger.info("Parsed " + dumpInfo.getProcessedPages() + " pages");
	}

	protected void setBaseURL(final String baseURL) {
		dumpInfo.setDumpLanguage(resolveLanguage(baseURL));
	}
	
	protected static ILanguage resolveLanguage(final String baseURL) {
		int idx = baseURL.indexOf("://");
		String language = baseURL.substring(idx + 3, idx + 5);
		return Language.findByCode(language);
	}

	protected void addNamespace(final String namespace) {
		dumpInfo.addNamespace(namespace);
	}

	protected void setAuthor(final String author) {
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.setAuthor(author);
	}

	protected void setRevision(long revisionId) {
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.setRevision(revisionId);
	}

	protected void setTimestamp(final Date timestamp) {
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.setTimestamp(timestamp);
	}

	protected void setPageId(long pageId) {
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.setPageId(pageId);
	}

	protected void setTitle(String title) {
		String namespace = null;
		int idx = title.indexOf(':');
		if (idx >= 0) {
			namespace = title.substring(0, idx);
			if (!dumpInfo.hasNamespace(namespace))
				namespace = null;
			else
				title = title.substring(idx + 1);
		}
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.setTitle(title, namespace);
	}

	protected void setText(final String text) {
		for (IWiktionaryPageParser pageParser : parserRegistry)
			pageParser.setText(text);
	}
		
	protected Date parseTimestamp(final String dateString) throws ParseException {
		return timestampFormat.parse(dateString);
	}

	/** Returns information on the current dump file and its parsing 
	 *  progress. The result is <code>null</code> if the parser has not
	 *  yet been started (i.e., the {@link #parse(File)} method has not
	 *  been called). */
	public IDumpInfo getDumpInfo() {
		return dumpInfo;
	}
	
}
