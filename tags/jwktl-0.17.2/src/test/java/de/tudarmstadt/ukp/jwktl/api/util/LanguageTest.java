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

import junit.framework.TestCase;

/**
 * Test case for {@link Language}.
 * @author Christian M. Meyer
 */
public class LanguageTest extends TestCase {

	/***/
	public void testConstants() {
		assertEquals("eng", Language.ENGLISH.getCode());
		assertEquals("English", Language.ENGLISH.getName());
		assertEquals("deu", Language.GERMAN.getCode());
		assertEquals("German", Language.GERMAN.getName());
		assertEquals("rus", Language.RUSSIAN.getCode());
		assertEquals("Russian", Language.RUSSIAN.getName());
	}
	
	/***/
	public void testGet() {
		assertEquals("eng", Language.get("eng").getCode());
		assertEquals("deu", Language.get("deu").getCode());
		assertNull(Language.get("en"));
		assertNull(Language.get("de"));
		assertNull(Language.get("ger"));
	}
	
	/***/
	public void testFindByCode() {
		assertEquals("eng", Language.findByCode("eng").getCode());
		assertEquals("deu", Language.findByCode("deu").getCode());
		assertEquals("eng", Language.findByCode("en").getCode());
		assertEquals("deu", Language.findByCode("de").getCode());
		assertEquals("deu", Language.findByCode("ger").getCode());
	}

	/***/
	public void testFindByName() {
		assertEquals("eng", Language.findByName("English").getCode());
		assertEquals("deu", Language.findByName("German").getCode());
		assertEquals("zza", Language.findByName("Dimili").getCode());
		assertEquals("zza", Language.findByName("Kirmanjki").getCode());
		assertEquals("zza", Language.findByName("Kirdki").getCode());
		assertEquals("zza", Language.findByName("Dimli").getCode());
		assertEquals("zza", Language.findByName("Zazaki").getCode());
		assertEquals("zza", Language.findByName("Zaza").getCode());
		
		assertEquals("eng", Language.findByName("english").getCode());
		assertEquals("eng", Language.findByName("ENGLISH").getCode());
		assertEquals("deu", Language.findByName("german").getCode());
		assertEquals("deu", Language.findByName("GERMAN").getCode());
		
		assertEquals("Xart-tok", Language.findByName("tokipona").getCode());
		assertEquals("nmn", Language.findByName("!xóõ").getCode());
		assertEquals("nmn", Language.findByName("!Xóõ").getCode());
		assertEquals("nmn", Language.findByName("!Xóõ").getCode());
		assertEquals("nmn", Language.findByName("!Xóõ").getCode());
		assertEquals("nmn", Language.findByName("!xóõ").getCode());
		assertEquals("nmn", Language.findByName("ǃXóõ").getCode());		
	}
	
	/***/
	public void testISOCodes() {
		assertEquals("en", Language.findByName("English").getISO639_1());
		assertEquals("eng", Language.findByName("English").getISO639_2B());
		assertEquals("eng", Language.findByName("English").getISO639_2T());
		assertEquals("eng", Language.findByName("English").getISO639_3());
		
		assertEquals("de", Language.findByName("German").getISO639_1());
		assertEquals("ger", Language.findByName("German").getISO639_2B());
		assertEquals("deu", Language.findByName("German").getISO639_2T());
		assertEquals("deu", Language.findByName("German").getISO639_3());

		assertEquals("", Language.findByName("Dimili").getISO639_1());
		assertEquals("zza", Language.findByName("Dimili").getISO639_2B());
		assertEquals("zza", Language.findByName("Dimili").getISO639_2T());
		assertEquals("zza", Language.findByName("Dimili").getISO639_3());

		assertEquals("", Language.findByName("Aasáx").getISO639_1());
		assertEquals("", Language.findByName("Aasáx").getISO639_2B());
		assertEquals("", Language.findByName("Aasáx").getISO639_2T());
		assertEquals("aas", Language.findByName("Aasáx").getISO639_3());
		
		assertEquals("", Language.findByName("Tokipona").getISO639_1());
		assertEquals("", Language.findByName("Tokipona").getISO639_2B());
		assertEquals("", Language.findByName("Tokipona").getISO639_2T());
		assertEquals("", Language.findByName("Tokipona").getISO639_3());
	}
	
}
