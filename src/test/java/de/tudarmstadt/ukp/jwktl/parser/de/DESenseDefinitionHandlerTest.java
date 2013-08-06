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
package de.tudarmstadt.ukp.jwktl.parser.de;

import java.util.Iterator;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DESenseDefinitionHandler;

/**
 * Test case for {@link DESenseDefinitionHandler}.
 * @author Christian M. Meyer
 */
public class DESenseDefinitionHandlerTest extends DEWiktionaryEntryParserTest {
	
	/***/
	public void testAberration() throws Exception {
		IWiktionaryPage page = parse("Aberration.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionarySense> senseIter = entry.getSenses().iterator();
		assertWiktionarySense(1, "[[Astronomie]]: [[scheinbar]]e [[Ortsveränderung]] eines [[Gestirn]]s aufgrund der [[Erdbewegung]] und der Endlichkeit der [[Lichtgeschwindigkeit]]"
				+ "\n*[[jährlich]]e ''Aberration'': aufgrund des [[Erdumlauf]]s um die [[Sonne]]"
				+ "\n*[[täglich]]e ''Aberration'': aufgrund der [[Erdrotation]]", senseIter.next());
		assertWiktionarySense(2, "''[[Optik]]'': [[Abbildungsfehler]] von [[Linse]]n:"
				+ "\n*[[chromatisch]]e ''Aberration'': [[Strahl]]en unterschiedlicher [[Wellenlänge]] werden unterschiedlich stark gebrochen"
				+ "\n*[[sphärisch]]e ''Aberration'': [[Randstrahl]]en werden stärker gebrochen als [[achsennah]]e Strahlen", senseIter.next());
		assertWiktionarySense(3, "''[[Biologie]]:'' [[Abweichung]] von den [[Artmerkmal]]en", senseIter.next());
		assertWiktionarySense(4, "''[[Psychologie]]:'' eine leichte psychische Störung", senseIter.next());
		assertFalse(senseIter.hasNext());
	}
	
	/***/
	public void testApril() throws Exception {
		IWiktionaryPage page = parse("April.txt");
		assertEquals(1, page.getEntry(0).getSenseCount());
		IWiktionarySense sense = page.getEntry(0).getSense(1);
		assertWiktionarySense(1, "der vierte Monat des Gregorianischen Kalenders", sense);
		assertEquals("2", sense.getMarker());
		assertEquals(1, sense.getRelations(RelationType.HYPERNYM).size());
	}

	/***/
	public void testKunsttherapie() throws Exception {
		IWiktionaryPage page = parse("Kunsttherapie.txt");
		assertEquals(2, page.getEntry(0).getSenseCount());
		IWiktionarySense sense = page.getEntry(0).getSense(1);
		assertWiktionarySense(1, "Therapie mit bildnerischen Medien", sense);
		assertEquals("1", sense.getMarker());
		sense = page.getEntry(0).getSense(2);
		assertWiktionarySense(2, "[[Determinativkompositum]] aus ''[[Kunst]]'' und ''[[Therapie]]''", sense);
		assertEquals(null, sense.getMarker());
	}

	protected void assertWiktionarySense(int expectedIndex, 
			final String expectedDefinition, final IWiktionarySense actual) {
		assertEquals(expectedIndex, actual.getIndex());
//		System.out.println(">" + expectedDefinition + "<\n>" + actual.getGloss() + "<");
		assertEquals(expectedDefinition, actual.getGloss().getText());
	}

}
