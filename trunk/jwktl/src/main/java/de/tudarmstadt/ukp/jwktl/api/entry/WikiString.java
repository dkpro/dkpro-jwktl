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
package de.tudarmstadt.ukp.jwktl.api.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.sleepycat.persist.model.Persistent;

import de.tudarmstadt.ukp.jwktl.api.IWikiString;

/**
 * Implementation of {@link IWikiString} that parses the original text (in
 * wiki markup language) on demand instead of storing the parsed information
 * in the database. Note that this used to be different prior to JWKTL 0.15.4. 
 * @author Christian M. Meyer
 */
@Persistent
public class WikiString implements IWikiString {

	protected String text;
	
	/** Create a new, empty wiki string. */
	// For persistence.
	public WikiString() {}
	
	/** Create a new wiki string for the given wiki markup text. */
	public WikiString(final String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	/** Assign the given text as the new wiki markup text. */
	public void setText(final String text) {
		this.text = text;
	}
	
	/** @see #getText(). */
	public String getTextIncludingWikiMarkup() {
		return getText();
	}
	
	protected static final Pattern COMMENT_PATTERN = Pattern.compile("<!--.+?-->");
	protected static final Pattern QUOTES_PATTERN = Pattern.compile("'''?");
	protected static final Pattern WIKILINK_PATTERN = Pattern.compile("\\[\\[((?:[^|\\]]+?\\|)*)([^|\\]]+?)\\]\\]");
//	protected static final Pattern EXTERNAL_LINK_PATTERN = Pattern.compile("\\[\\w+://\\w+\\]");
	protected static final Pattern TEMPLATE_PATTERN = Pattern.compile("\\{\\{.+?\\}\\}");
	protected static final Pattern REFERENCES_PATTERN = Pattern.compile("<ref[^>]*>.+?</ref>");
	protected static final Pattern HTML_PATTERN = Pattern.compile("<[^>]+>");
	protected static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s\\s+");
	
	public String getPlainText() {
		return makePlainText(text);
	}

	public List<String> getWikiLinks() {		
		List<String> result = new ArrayList<String>();
		int startIndex = text.indexOf("[[");
		while (startIndex != -1) {
			int endIndex = text.indexOf("]]", startIndex);
			if (endIndex != -1) {
				String wikifyBlock = text.substring(startIndex + 2, endIndex);
				int midIndex = wikifyBlock.lastIndexOf("|");
				if (midIndex != -1) {
					String word = wikifyBlock.substring(midIndex + 1, wikifyBlock.length());
					result.add(word);
				} else
					result.add(wikifyBlock);
				startIndex = text.indexOf("[[",endIndex);				
			} else
				startIndex = -1;
		}	
		return result;
	}

	/*public List<ExternalLink> getExternalLinks() {
		List<ExternalLink> result = new ArrayList<ExternalLink>();
		
		StringBuilder textBuffer = new StringBuilder();
		StringBuilder linkBuffer = new StringBuilder();
		boolean inBlock = false;
		boolean inURL = false;
		int n = text.length();
		for (int i = 0; i < n; i++) {
			char current = text.charAt(i);
			if (!inBlock) {
				if (current == '[' && (i + 1) < n) {
					if ('[' != text.charAt(i+1)) {
						inBlock = true;
						inURL = true;						
					} else
						i++;
				}
			} else 
			if (current == ']') {
				inBlock = false;
				ExternalLink_Impl link = new ExternalLink_Impl();
				link.setLabel(textBuffer.toString());
				link.setUrl(linkBuffer.toString());					
				if (link.getUrl().contains("wikipedia.org"))
					link.setNamespace("wikipedia");
				result.add(link);
			} else 
			if (!inURL) {
				textBuffer.append(current);
			} else
			if (current == ' ' || current == '\t') {
				inURL = false;
			} else {
				linkBuffer.append(current);
			}
		}
		return result;
	}*/
	
	@Override
	public String toString() {
		return getPlainText();
	}

	/** Transforms the given wiki markup text into a plain text version. 
	 *  That is, wiki links, templates, and typographic markers are being 
	 *  removed or substituted in order to obtain a human-readable text. */
	public static String makePlainText(final String wikiText) {
		String result = wikiText;
		result = result.replace("\t", " ");
		result = COMMENT_PATTERN.matcher(result).replaceAll("");	
		result = QUOTES_PATTERN.matcher(result).replaceAll("");
		result = WIKILINK_PATTERN.matcher(result).replaceAll("$2");
//		result = EXTERNAL_LINK_PATTERN.matcher(result).replaceAll("");
		result = REFERENCES_PATTERN.matcher(result).replaceAll("");
		result = TEMPLATE_PATTERN.matcher(result).replaceAll("");
		result = HTML_PATTERN.matcher(result).replaceAll("");
		result = result.replace("’", "'");
		result = result.replace("�", "'");
		result = result.replace("°", "");
		result = WHITESPACE_PATTERN.matcher(result).replaceAll(" ");
		while (result.length() > 0 && "*: ".contains(result.substring(0, 1)))
			result = result.substring(1);
		return result.trim();
	}
	
}
