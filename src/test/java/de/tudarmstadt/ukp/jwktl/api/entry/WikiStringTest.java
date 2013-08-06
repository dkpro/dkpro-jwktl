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

import junit.framework.TestCase;

/**
 * Test case for {@link WikiString}.
 * @author Christian M. Meyer
 */
public class WikiStringTest extends TestCase {

	/***/
	public void testPlainText() {
		String text = "* {{sense|of or pertaining to the abdomen}} [[ventral]]";
		WikiString w = new WikiString(text);
		assertEquals("ventral", w.getPlainText());
		
		text = "# {{zoology|obsolete}} Belonging to the [[abdominales|Abdominales]]; as, ''abdominal'' fishes.";
		w = new WikiString(text);
		assertEquals("# Belonging to the Abdominales; as, abdominal fishes.", w.getPlainText());
		
		text = ":[1] [[eukaryotisch]]es [[Lebewesen|Lebw.]], das keine [[Photosynthese]] betreiben kann, [[Sauerstoff]] zur [[Atmung]] benötigt und tierischen und/oder pflanzlichen Organismen als [[Nahrung]] zu sich nimmt";
		w = new WikiString(text);
		assertEquals("[1] eukaryotisches Lebw., das keine Photosynthese betreiben kann, Sauerstoff zur Atmung benötigt und tierischen und/oder pflanzlichen Organismen als Nahrung zu sich nimmt", w.getPlainText());

		text = ":[1] \"Die ''Welt'' ist schon oft mit einem Narrenhause verglichen worden.\"<ref>[http://www.humanist.de/religion/pfaffe.html Otto von Corvin, Der Pfaffenspiegel] </ref>";
		w = new WikiString(text);
		assertEquals("[1] \"Die Welt ist schon oft mit einem Narrenhause verglichen worden.\"", w.getPlainText());

		text = ":[1–10] {{Wikipedia|Welt (Begriffsklärung)}}";
		w = new WikiString(text);
		assertEquals("[1–10]", w.getPlainText());
	}

}
