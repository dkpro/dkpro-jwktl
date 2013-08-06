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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Helper class for segmenting word lists separated by comma, semicolon, 
 * line breaks, etc. This is, for example, the case for semantic relations
 * which are often encoded as comma-separated lists. 
 * @author Christof Müller
 * @author Lizhen Qu
 */
public class WordListProcessor {

	protected final static Pattern HTML_REMOVER = Pattern.compile("<[^>]+>");
	protected final static Pattern ESCAPE_DELIMITER1 = Pattern.compile("(\\[\\[[^\\]⁋]*)⁋([,;][^\\]⁋]*\\]\\])");
	protected final static Pattern ESCAPE_DELIMITER2 = Pattern.compile("(\\{\\{[^\\}⁋]*)⁋([,;][^\\}⁋]*\\}\\})");
	protected final static Pattern ESCAPE_DELIMITER3 = Pattern.compile("(''[^'⁋]*)⁋([,;][^'⁋]*'')");
	protected final static Pattern REFERENCE_PATTERN = Pattern.compile("<ref>.*?</ref>"); 
	protected final static Pattern SUPERSCRIPT_PATTERN = Pattern.compile("<sup>\\[\\d+\\]</sup>");
	
	protected String escapeDelimiters(final String text) {
		StringBuilder result = new StringBuilder();
		boolean inComment = false;
		String commentStart = "";
		String commentEnd = "";
		char[] t = text.toCharArray();
		for (int i = 0; i < t.length; i++) {
			if (!inComment) {
				// Find comment start markers.
				if (t[i] == '[') {
					if ("[".equals(commentStart)) {
						commentStart = "[[";
						inComment = true;
					} else
						commentStart = "[";
				} else if (t[i] == '{') {
					if ("{".equals(commentStart)) {
						commentStart = "{{";
						inComment = true;
					} else
						commentStart = "{";
				} else if (t[i] == '\'') {
					if ("'".equals(commentStart)) {
						commentStart = "''";
						inComment = true;
					} else
						commentStart = "'";
				}
			} else {
				// Find comment end markers.
				if (t[i] == ']' && "[[".equals(commentStart)) {
					if ("]".equals(commentEnd)) {
						commentEnd = "";
						commentStart = "";
						inComment = false;
					} else
						commentEnd = "]";
				} else if (t[i] == '}' && "{{".equals(commentStart)) {
					if ("}".equals(commentEnd)) {
						commentEnd = "";
						commentStart = "";
						inComment = false;
					} else
						commentEnd = "}";
				} else if (t[i] == '\'' && "''".equals(commentStart)) {
					if ("'".equals(commentEnd)) {
						commentEnd = "";
						commentStart = "";
						inComment = false;
					} else
						commentEnd = "'";
				}
			}
			
			// Remove delimiters if in comment.
			if (!inComment || t[i] != '⁋')
				result.append(t[i]);					
		}
		return result.toString();
	}
	
	/** Splits the given text by comma, semicolon, line break, etc. and 
	 *  removes multiple types of special characters and affixes. The
	 *  resulting segments are returned as a list of strings. */
	public List<String> splitWordList(final String text) {
		List<String> result = new ArrayList<String>();
		String t = text + "⁋";
		t = t.replace('\n', '⁋');
		t = REFERENCE_PATTERN.matcher(t).replaceAll("");
		t = SUPERSCRIPT_PATTERN.matcher(t).replaceAll(""); //TODO: Extract sense!
		t = HTML_REMOVER.matcher(t).replaceAll("");
		t = t.replace("&quot;", "\"");
		t = t.replace(",", "⁋,");
		t = t.replace(";", "⁋;");
		//System.out.println(t);
		//t = BRACKETED_DELIMITER.matcher(t).replaceAll("$1$2$3$4$5$6");
		//t = ESCAPE_DELIMITER1.matcher(t).replaceAll("$1$2");
		//t = ESCAPE_DELIMITER2.matcher(t).replaceAll("$1$2");
		//t = ESCAPE_DELIMITER3.matcher(t).replaceAll("$1$2");
		t = escapeDelimiters(t);			
		//System.out.println(t);
		t = t.replace("⁋;", "⁋");
		t = t.replace("⁋,", "⁋");
		t = t.replace("]] or [[", "]]⁋[[");
		t = t.replace("]] and [[", "]]⁋[[");
		t = t.replace(" - ", "⁋");
		//t = t.replace(" / ", "⁋");
		int j = t.indexOf(" / "); // Use ' / ' only as a delimiter if there are at least two of them!
		if (j >= 0) {
			j = t.indexOf(" / ", j);
			if (j >= 0) {
				t = t.replace(" / ", "⁋");
				//System.out.println(t);
			}
		}
		//System.out.println(t);
		int delim;
		do {
			delim = t.indexOf('⁋');
			if (delim >= 0) {
				String word = t.substring(0, delim);
				if (word.length() > 0) {
					// Normalize the word.
					word = word.trim();
					if (word.toLowerCase().startsWith("see also"))
						word = word.substring(8).trim();
					if (word.toLowerCase().startsWith("see"))
						word = word.substring(3).trim();
					if (word.startsWith(":"))
						word = word.substring(1).trim();
					word = deWikify(word).trim();
					word = removeBrackets(word).trim();
					word = removeTemplates(word).trim();
					word = removeComments(word).trim();
					if (word.toLowerCase().startsWith("see also"))
						word = word.substring(8).trim();
					if (word.toLowerCase().startsWith("see"))
						word = word.substring(3).trim();
					if (word.startsWith(":"))
						word = word.substring(1).trim();
					if (word.endsWith("."))
						word = word.substring(0, word.length() - 1).trim();
					if (word.endsWith(","))
						word = word.substring(0, word.length() - 1).trim();
					
					// Check for slashes.
					word = word.replace(" / ", "/");
					word = word.replace("/ ", "/");
					int i = word.indexOf('/');
					if (word.length() > 0) {
						if (i >= 0 && word.indexOf(' ') < 0) {
							do {
								result.add(word.substring(0, i));
								word = word.substring(i + 1);
								i = word.indexOf('/');
							} while (i >= 0);
							result.add(word);
						} else
							result.add(word);
					}
				}
				t = t.substring(delim + 1);
			}
		} while (delim >= 0);
		return result;
	}
	
	protected String deWikify(final String word) {
		StringBuilder result = new StringBuilder();
		String t = word;
		int i;
		do {
			i = t.indexOf("[[");
			if (i >= 0) {
				result.append(t.substring(0, i));
				t = t.substring(i + 2);
				
				i = t.indexOf("]]");
				if (i >= 0) {
					String wikifiedText = t.substring(0, i);
					t = t.substring(i + 2);
					
					i = wikifiedText.indexOf('|');
					if (i >= 0)
						wikifiedText = wikifiedText.substring(i + 1); //TODO: save link target.
					result.append(wikifiedText);
				}
			} else
				break;
		} while (true);
		result.append(t);
		return result.toString();
	}
	
	protected String removeBrackets(final String word) {
		StringBuilder result = new StringBuilder();
		String t = word;
		int i;
		do {
			i = t.indexOf("(");
			if (i >= 0) {
				result.append(t.substring(0, i));
				t = t.substring(i + 1);
				
				i = t.indexOf(")");
				if (i >= 0) {
					//TODO: save bracketed comment!
					//String wikifiedText = t.substring(0, i);
					t = t.substring(i + 1);
				}
			} else
				break;
		} while (true);
		result.append(t);
		return result.toString();
	}

	protected String removeComments(final String word) {
		StringBuilder result = new StringBuilder();
		String t = word;
		int i;
		do {
			i = t.indexOf("''");
			if (i >= 0) {
				result.append(t.substring(0, i));
				t = t.substring(i + 2);
				
				i = t.indexOf("''");
				if (i >= 0) {
					//TODO: save bracketed comment!
					//String wikifiedText = t.substring(0, i);
					t = t.substring(i + 2);
				}
			} else
				break;
		} while (true);
		result.append(t);
		return result.toString();
	}
	
	protected String removeTemplates(final String word) {
		StringBuilder result = new StringBuilder();
		String t = word;
		int i;
		do {
			i = t.indexOf("{{");
			if (i >= 0) {
				result.append(t.substring(0, i));
				t = t.substring(i + 2);
				
				i = t.indexOf("}}");
				if (i >= 0) {
					String templateText = t.substring(0, i);
					t = t.substring(i + 2);

					// Process template
					i = templateText.indexOf('|');
					if (i >= 0) {
						String templateName = templateText.substring(0, i);
						// Link template
						if ("l".equals(templateName)) {
							int idx = templateText.indexOf('|', i + 1);
							if (idx >= 0)
								result.append(templateText.substring(idx + 1));
							else
								result.append(templateText.substring(i + 1));
						} /*else
						if ("italbrac".equals(templateName)
								|| "qualifier".equals(templateName)
								|| "context".equals(templateName)
								|| "a".equals(templateName)
								|| "i".equals(templateName)
								|| "gloss".equals(templateName)
								|| "sense".equals(templateName)
								) {
							// TODO: save bracketed comment!
						} //else
							//result.append(templateText.substring(i + 1));
							//System.err.println("Unknown template: " + templateText);					
						 */
					}
				}
			} else
				break;
		} while (true);
		result.append(t);
		return result.toString().trim();
	}

}
