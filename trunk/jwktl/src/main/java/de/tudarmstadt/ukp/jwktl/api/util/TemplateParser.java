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
package de.tudarmstadt.ukp.jwktl.api.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Static helper class for parsing wiki markup text that contains templates. 
 * The template parser identifies templates in the given wiki text and 
 * replaces them as specified by a {@link ITemplateHandler}. 
 * @author Christian M. Meyer
 */
public /*static*/ class TemplateParser {

	/**
	 * A template is a reusable pattern that can be added to any wiki page. The 
	 * template is being substituted by its definition upon rendering the wiki 
	 * page to HTML. A template has a unique name. It can be equipped with 
	 * multiple parameters. The parameters are divided into numbered and named
	 * parameters. The former are indexed by integers (starting with 0); the
	 * latter are key-value parirs. 
	 * @author Christian M. Meyer
	 */
	public static class Template {
		
		protected String name;
		protected Map<String, String> namedParams;
		protected List<String> numberedParams;
		
		/** Creates a new template with the given name. */
		public Template(final String name) {
			this.name = name;
			namedParams = new LinkedHashMap<String, String>();
			numberedParams = new ArrayList<String>();
		}
		
		/** Returns the name of the template. */
		public String getName() {
			return name;
		}

		/** Add a numbered parameter with the given value. */
		public void addParam(final String value) {
			numberedParams.add(value);
		}

		/** Add a named parameter with the given key and value. */
		public void addParam(final String key, final String value) {
			if (namedParams.containsKey(key)) {
				int suffix = 2;
				while (namedParams.containsKey(key + suffix))
					suffix++;
				namedParams.put(key + suffix, value);
			} else
				namedParams.put(key, value);
		}

		/** Return the numbered parameter with the given index or <code>null/<code> 
		 *  if no such parameter exists. */
		public String getNumberedParam(int idx) {
			return numberedParams.get(idx);
		}
		
		/** Return the named parameter with the given key or <code>null/<code> 
		 *  if no such parameter exists. */
		public String getNamedParam(final String key) {
			return namedParams.get(key);
		}

		/** Iterate over all numbered parameters. */
		public Iterable<String> getNumberedParams() {
			return numberedParams;
		}
		
		/** Iterate over all named parameters. */
		public Iterable<Entry<String, String>> getNamedParams() {
			return namedParams.entrySet();
		}
		
		/** Returns the number of all parameters (including numbered and named
		 *  paramters). */
		public int getParamsCount() {
			return numberedParams.size() + namedParams.size();
		}
		
		/** Returns number of all numbered parameters. */
		public int getNumberedParamsCount() {
			return numberedParams.size();
		}
		
		/** Returns number of all named parameters. */
		public int getNamedParamsCount() {
			return namedParams.size();
		}
		
	}
	
	/**
	 * Interface that is expected by the {@link TemplateParser}. The template 
	 * parser invokes an implementation of this interface to decide what is
	 * to be done with a template.
	 * @author Christian M. Meyer
	 */
	public interface ITemplateHandler {
		
		/** Invoked by the {@link TemplateParser} for every template that occurs
		 *  in a text. The parser replaces the template by the result of this 
		 *  method. If <code>null</code> is returned, no substitution is made,
		 *  but the original template string remains in the text. */
		public String handle(final Template template);
		
	}
	
	/**
	 * Implementation of an {@link ITemplateHandler} for parsing etymology 
	 * information in Wiktionary.
	 * @author Christian M. Meyer
	 */
	public static class EtymologyTemplateHandler implements ITemplateHandler {

		public String handle(final Template template) {
			if ("etyl".equals(template.getName())) {
				String languageCode = template.getNumberedParam(0);
				ILanguage language = Language.findByCode(languageCode);
				if (language == null)
					return "{" + languageCode + "}";
				else
					return language.getName();				
			} else			
			if ("term".equals(template.getName())) {
				String term = template.getNumberedParam(0);
				if (template.getNumberedParamsCount() >= 3)
					return term + " (“" + template.getNumberedParam(2) + "”)";
				else
					return term;
			} else			
			if ("recons".equals(template.getName())) {
				String term = template.getNumberedParam(0);
				if (template.getNumberedParamsCount() >= 3)
					return "*" + term + " (“" + template.getNumberedParam(2) + "”)";
				else
					return "*" + term;
			}
			
			// Remove other templates.
			return "";
		}
	}
	
	/** Parse the given wiki text and substitute each template in the text 
	 *  using the specified template handler. */
	public static String parse(final String wikiText, 
			final ITemplateHandler handler) {
		StringBuilder result = new StringBuilder();
		String text = wikiText;
		int startIdx;
		do {
			startIdx = text.indexOf("{{");
			if (startIdx >= 0) {
				result.append(text.substring(0, startIdx));
				int endIdx = text.indexOf("}}", startIdx);
				if (endIdx >= 0) {
					boolean handled = false;
					String templateText = text.substring(startIdx + 2, endIdx);
					if (handler != null) {
						Template template = parseTemplate(templateText);
						if (template != null) {
							String replacement = handler.handle(template);
							if (replacement != null) {
								result.append(replacement);
								handled = true;
							}
						}
					}
					if (!handled)
						result.append("{{").append(templateText).append("}}");
					text = text.substring(endIdx + 2);
				} else
					text = text.substring(startIdx + 2);
			}
		} while (startIdx >= 0);
		result.append(text);
		return result.toString();
	}

	/** Creates a {@link Template} from the given text. That is, the 
	 *  template's name and parameters are extracted from the text and
	 *  stored in the template instance. */
	public static Template parseTemplate(final String templateText) {
		// Split the template text to access the parameters.
		String[] params = StringUtils.split(templateText, '|');
		if (params.length < 1)
			return null;
		
		Template result = new Template(params[0]);
		int idx = -1;
		for (String param : params) {
			// Skip the template name.
			if (idx < 0) {
				idx++;
				continue;
			}
			
			// Check for named params.
			int j = param.indexOf('=');
			if (j >= 0) {
				String key = param.substring(0, j);
				String value = param.substring(j + 1);				
				result.addParam(key, value);
			} else
				result.addParam(param);			
		}
		return result;
	}
	
	
}
