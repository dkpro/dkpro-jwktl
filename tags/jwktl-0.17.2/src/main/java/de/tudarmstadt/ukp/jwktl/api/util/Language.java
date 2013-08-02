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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation of the {@link ILanguage} interface. This class should
 * not be instanciated by yourself. Use the static methods to find registered 
 * instances - either by language code or name.
 * @author Christian M. Meyer
 */
public class Language implements ILanguage {

	protected String code;
	protected String name;
	protected String iso639_3;
	protected String iso639_2b;
	protected String iso639_2t;
	protected String iso639_1;
		
	protected Language(final String code, final String name, 
			final String iso639_3, final String iso639_2b,
			final String iso639_2t, final String iso639_1) {
		this.code = code;
		this.name = name;
		this.iso639_3 = iso639_3;
		this.iso639_2b = iso639_2b;
		this.iso639_2t = iso639_2t;
		this.iso639_1 = iso639_1;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getISO639_3() {
		return iso639_3;
	}
	
	public String getISO639_2B() {
		return iso639_2b;
	}
	
	public String getISO639_2T() {
		return iso639_2t;
	}

	public String getISO639_1() {
		return iso639_1;
	}
	
	public int compareTo(final ILanguage other) {
		return (equals(other) ? 0 : code.compareTo(other.getCode()));
	}
		
	@Override
	public boolean equals(final Object other) {
		if (other == null || !(other instanceof ILanguage))
			return false;
		else
			return code.equals(((ILanguage) other).getCode());
	}
	
	@Override
	public int hashCode() {
		return code.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}
	

	// -- Static interface --
	
	/** The English language. */
	public static final ILanguage ENGLISH = get("eng");
	/** The German language. */
	public static final ILanguage GERMAN = get("deu");
	/** The Russian language. */
	public static final ILanguage RUSSIAN = get("rus");
	
	private static boolean initialized;
	private static Map<String, ILanguage> languageIndex;
	private static Map<String, String> additionalCodeIndex;
	private static Map<String, String> additionalNameIndex;
	
	// Avoid two threads interleaving!
	private static synchronized void initialize() {
		if (initialized)
			return;
		
		languageIndex = new TreeMap<String, ILanguage>();
		additionalCodeIndex = new TreeMap<String, String>();
		additionalNameIndex = new TreeMap<String, String>();
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					Language.class.getResourceAsStream("language_codes.txt"), "UTF-8"));
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					// Extract the fields.
					String[] fields = new String[8];
					int i;
					int idx = 0;
					do {
						i = line.indexOf('\t');
						if (i >= 0) {
							fields[idx++] = line.substring(0, i);
							line = line.substring(i + 1);
						}
					} while (i >= 0);
					if (idx < 0)
						continue;
					fields[idx] = line;

					// Save the main language entry.
					ILanguage language = new Language(fields[0], fields[1],
							fields[2], fields[3], fields[4], fields[5]);
					languageIndex.put(language.getCode(), language);
					additionalCodeIndex.put(language.getCode(), language.getCode());
					additionalNameIndex.put(language.getName().toLowerCase(), language.getCode());
					
					// Save additional language codes.
					String additionalCodes = fields[6];
					if (!additionalCodes.isEmpty()) {
						do {
							i = additionalCodes.indexOf(';');
							if (i >= 0) {
								String addCode = additionalCodes.substring(0, i);
								additionalCodeIndex.put(addCode, language.getCode());
								additionalCodes = additionalCodes.substring(i + 1);
							}
						} while (i >= 0);
						additionalCodeIndex.put(additionalCodes, language.getCode());
					}
					
					// Save additional language names.
					String additionalNames = fields[7];
					if (!additionalNames.isEmpty()) {
						do {
							i = additionalNames.indexOf(';');
							if (i >= 0) {
								String addName = additionalNames.substring(0, i);
								additionalNameIndex.put(addName.toLowerCase(), language.getCode());
								additionalNames = additionalNames.substring(i + 1);
							}
						} while (i >= 0);
						additionalNameIndex.put(additionalNames.toLowerCase(), language.getCode());
					}
				}
			} finally {
				reader.close();
			}
			
			initialized = true;
		} catch (IOException e) {
			throw new RuntimeException("Unable to load language code index", e);
		}
	}

	/** Returns the language with the given internal code. Note that the
	 *  internal codes roughly correspond to ISO 639-3 code, but also model
	 *  some extensions to this. Use {@link #findByCode(String)} if you are
	 *  unsure about your code. If no language could be found, 
	 *  <code>null</code> is returned. */
	public static ILanguage get(final String code) {
		initialize();
		return (code == null ? null : languageIndex.get(code));
	}
	
	/** Find the language with the given code. The method checks both for the
	 *  internal language codes and for any ISO 639 code. If no language 
	 *  could be found, <code>null</code> is returned. */
	public static ILanguage findByCode(final String code) {
		initialize();
		return get(additionalCodeIndex.get(code));
	}
	
	/** Find the language with the given name. The method checks both for the
	 *  canonical English name as well as alternative names in other languages
	 *  or spelling errors found in Wiktionary. If no language could be found,
	 *  <code>null</code> is returned. */
	public static ILanguage findByName(final String name) {
		if (name == null)
			return null;
		initialize();
		return get(additionalNameIndex.get(name.trim().toLowerCase()));
	}

	/** Tests if the specified languages are equal. The method returns 
	 * <code>true</code> if both languages are <code>null</code>, but 
	 * <code>false</code> if only one of them is <code>null</code>. */
	public static boolean equals(final ILanguage language1, 
			final ILanguage language2) {
		if (language1 == language2)
			return true;
		else
		if (language1 == null || language2 == null)
			return false;
		else
			return language1.equals(language2);
	}
	
}
