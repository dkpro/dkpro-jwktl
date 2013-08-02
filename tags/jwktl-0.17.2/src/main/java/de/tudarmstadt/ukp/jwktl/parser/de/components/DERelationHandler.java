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
package de.tudarmstadt.ukp.jwktl.parser.de.components;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;

/**
 * Parser component for extracting semantic relations from the German Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class DERelationHandler extends DESenseIndexedBlockHandler<IWiktionaryRelation> {

	protected RelationType relationType;
	protected String notes;
	
	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public DERelationHandler(final RelationType relationType, final String... labels) {
		super(labels);
		this.relationType = relationType;
	}

	protected List<IWiktionaryRelation> extract(int index, final String text) {
		String delimitedText = text.trim();
		if (delimitedText.isEmpty())
			return null;
		
		List<IWiktionaryRelation> result = new ArrayList<IWiktionaryRelation>();		
		delimitedText = addDelimiters(delimitedText);
//		System.out.println(delimitedText);		
		int delim;
		do {
			delim = delimitedText.indexOf('⁋');
			if (delim >= 0) {
				String word = delimitedText.substring(0, delim);
				delimitedText = delimitedText.substring(delim + 1);
				notes = "";
//				System.out.println(word);
				word = normalizeWord(word);
//String w = normalizeWord(word);
//System.out.println(">" + word + "<  >" + w + "<  //" + notes); word = w;
				if (word == null || word.isEmpty())
					continue;

				// Check for slashes.
				int i = word.indexOf('/');
				if (i >= 0 && word.indexOf(' ') < 0) {
					do {
						result.add(new WiktionaryRelation(word.substring(0, i), relationType));
						word = word.substring(i + 1);
						i = word.indexOf('/');
					} while (i >= 0);
				}
				result.add(new WiktionaryRelation(word, relationType));				
			}
		} while (delim >= 0);
		return result;
	}

	protected String normalizeWord(final String word) {
		if (word.isEmpty()) 
			return word;

		// Normalize the word.
		String result = word.trim();
		if (result.toLowerCase().startsWith("see also"))
			result = result.substring(8).trim();
		if (result.toLowerCase().startsWith("see"))
			result = result.substring(3).trim();
		if (result.startsWith(":"))
			result = result.substring(1).trim();
		result = deWikify(result).trim();
		result = removeBrackets(result).trim();
		result = removeTemplates(result).trim();
		result = removeComments(result).trim();
		if (result.toLowerCase().startsWith("see also"))
			result = result.substring(8).trim();
		if (result.toLowerCase().startsWith("see"))
			result = result.substring(3).trim();
		if (result.startsWith(":"))
			result = result.substring(1).trim();
		if (result.endsWith("."))
			result = result.substring(0, result.length() - 1).trim();
		if (result.endsWith(","))
			result = result.substring(0, result.length() - 1).trim();
		result = result.replace(" / ", "/");
		result = result.replace("/ ", "/");
		if (result.startsWith("siehe unten"))
			result = "";
		int idx = result.indexOf(": ");
		if (idx >= 0) {
			notes = result.substring(0, idx);
			result = result.substring(idx + 1).trim();
		}
		if ("=".equals(result))
			result = "";
		return result;
	}

	protected final static Pattern REFERENCE_PATTERN = Pattern.compile("<ref>.*?</ref>"); 
	protected final static Pattern SUPERSCRIPT_PATTERN = Pattern.compile("<sup>\\[\\d+\\]</sup>");
	protected final static Pattern HTML_REMOVER = Pattern.compile("<[^>]+>");
	
	protected String addDelimiters(final String text) {
		String result = text + "⁋";
		result = result.replace('\n', '⁋');
		result = REFERENCE_PATTERN.matcher(result).replaceAll("");
		result = SUPERSCRIPT_PATTERN.matcher(result).replaceAll(""); //TODO: Extract sense!
		result = HTML_REMOVER.matcher(result).replaceAll("");
		result = result.replace("&quot;", "\"");
		result = result.replace(",", "⁋,"); //TODO: check if there should be an ordering of the delimiters0 e.g. ;,/
		result = result.replace(";", "⁋;");
		result = escapeDelimiters(result);
		result = result.replace("⁋;", "⁋");
		result = result.replace("⁋,", "⁋");
		result = result.replace("]] or [[", "]]⁋[[");
		result = result.replace("]] and [[", "]]⁋[[");
		result = result.replace(" — ", "⁋");
		result = result.replace(" - ", "⁋");
		int j = result.indexOf(" / "); // Use ' / ' only as a delimiter if there are at least two of them!
		if (j >= 0) {
			j = result.indexOf(" / ", j);
			if (j >= 0)
				result = result.replace(" / ", "⁋");
		}
		return result;
	}

	protected String escapeDelimiters(final String text) {
		StringBuilder result = new StringBuilder();
		boolean inComment = false;
		String commentStart = "";
		String commentEnd = "";
		char[] t = text.toCharArray();
		for (int i = 0; i < t.length; i++) {
			if (!inComment) {
				// Find comment start markers.
				if (t[i] == '(') {
					commentStart = "(";
					inComment = true;
				} else
				if (t[i] == '[') {
					if ("[".equals(commentStart)) {
						commentStart = "[[";
						inComment = true;
					} else
						commentStart = "[";
				} else 
				if (t[i] == '{') {
					if ("{".equals(commentStart)) {
						commentStart = "{{";
						inComment = true;
					} else
						commentStart = "{";
				} else 
				if (t[i] == '\'') {
					if ("'".equals(commentStart)) {
						commentStart = "''";
						inComment = true;
					} else
						commentStart = "'";
				}
			} else {
				// Find comment end markers.
				if (t[i] == ')' && "(".equals(commentStart)) {
					commentEnd = "";
					commentStart = "";
					inComment = false;
				} else
				if (t[i] == ']' && "[[".equals(commentStart)) {
					if ("]".equals(commentEnd)) {
						commentEnd = "";
						commentStart = "";
						inComment = false;
					} else
						commentEnd = "]";
				} else 
				if (t[i] == '}' && "{{".equals(commentStart)) {
					if ("}".equals(commentEnd)) {
						commentEnd = "";
						commentStart = "";
						inComment = false;
					} else
						commentEnd = "}";
				} else 
				if (t[i] == '\'' && "''".equals(commentStart)) {
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
notes += " BB" + t.substring(0, i);
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
notes += " CC" + t.substring(0, i);
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
notes += " TT" + templateText;

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
	
	// ----------------------------------------------------------------
	
	protected void updateSense(final WiktionarySense sense, final IWiktionaryRelation relation) {
		sense.addRelation(relation);
	}

	protected void updatePosEntry(final WiktionaryEntry posEntry, final IWiktionaryRelation relation) {
		posEntry.getUnassignedSense().addRelation(relation);
	}

}
