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
package de.tudarmstadt.ukp.jwktl.parser.en;

import java.util.Iterator;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENSenseHandler;

/**
 * Test case for {@link ENSenseHandler}.
 * @author Christian M. Meyer
 */
public class ENSenseHandlerTest extends ENWiktionaryEntryParserTest {

	public void testHead() throws Exception {
		IWiktionaryPage page = parse("head.txt");
		Iterator<IWiktionarySense> senseIter = page.getEntry(0).getSenses().iterator();
		assertEquals("{{countable}} The part of the [[body]] of an animal or human which contains the [[brain]], [[mouth]]{{,}} and main [[sense]] [[organs]].", senseIter.next().getGloss().getText());
		assertEquals("{{uncountable}} [[mental|Mental]] or [[emotional]] [[aptitude]] or [[skill]].", senseIter.next().getGloss().getText());
		assertEquals("{{countable}} [[mind|Mind]]; one's own [[thought]]s.", senseIter.next().getGloss().getText());
		assertEquals("{{countable}} The [[topmost]], [[foremost]], or [[leading]] part.", senseIter.next().getGloss().getText());
		assertEquals("The end of a rectangular [[table]] furthest from the entrance; traditionally considered a seat of honor.", senseIter.next().getGloss().getText());
		assertEquals("{{billiards}} The end of a [[pool]] table opposite the end where the balls have been [[rack]]ed.", senseIter.next().getGloss().getText());
		assertEquals("{{countable}} The [[principal]] [[operative]] part of a machine."
				+ "\nThe end of a [[hammer]], [[axe]], {{soplink|golf|club}}{{,}} or similar [[implement]] used for striking other objects."
				+ "\nThe end of a [[nail]], [[screw]], [[bolt]]{{,}} or similar [[fastener]] which is opposite the [[point]]; usually [[blunt]] and relatively [[wide]]."
				+ "\nThe [[sharp]] end of an [[arrow]], [[spear]]{{,}} or [[pointer]]."
				+ "\n{{lacrosse}} The top part of a [[lacrosse stick]] that holds the [[ball]].", senseIter.next().getGloss().getText());
		assertEquals("The [[source]] of a [[river]]; the end of a [[lake]] where a river flows into it.", senseIter.next().getGloss().getText());
		assertEquals("{{rfc-sense}} The front, as of a [[queue]].", senseIter.next().getGloss().getText());
		assertEquals("[[headway|Headway]]; [[progress]].", senseIter.next().getGloss().getText());
		assertEquals("The foam that forms on top of [[beer]] or other carbonated [[beverage]]s.", senseIter.next().getGloss().getText());
		assertEquals("{{countable}} [[leader|Leader]]; [[chief]]; [[mastermind]].", senseIter.next().getGloss().getText());
		assertEquals("A [[headmaster]] or [[headmistress]].", senseIter.next().getGloss().getText());
		assertEquals("A [[headache]]; especially one resulting from [[intoxication]].", senseIter.next().getGloss().getText());
		assertEquals("A clump of [[leave]]s or [[flower]]s; a [[capitulum]].", senseIter.next().getGloss().getText());
		assertEquals("{{anatomy}} The rounded part of a bone fitting into a depression in another bone to form a ball-and-socket [[joint]].", senseIter.next().getGloss().getText());
		assertEquals("An individual [[person]].", senseIter.next().getGloss().getText());
		assertEquals("{{uncountable|[[w:Measure word|measure word]] for [[livestock]] and [[game]]}} A single [[animal]].", senseIter.next().getGloss().getText());
		assertEquals("The population of [[game]].", senseIter.next().getGloss().getText());
		assertEquals("Topic; [[subject]].", senseIter.next().getGloss().getText());
		assertEquals("{{linguistics}} A [[morpheme]] that determines the category of a [[compound]] or the word that determines the [[syntactic]] type of the [[phrase]] of which it is a member.", senseIter.next().getGloss().getText());
		assertEquals("{{jazz}} The principal [[melody]] or [[theme]] of a piece.", senseIter.next().getGloss().getText());
		assertEquals("{{British|geology}} Deposits near the top of a [[geological]] [[succession]].", senseIter.next().getGloss().getText());
		assertEquals("{{medicine}} The end of an [[abscess]] where [[pus]] collects.", senseIter.next().getGloss().getText());
		assertEquals("{{uncountable}} [[denouement]]; [[crisis]]", senseIter.next().getGloss().getText());
		assertEquals("A [[machine]] element which reads or writes [[electromagnetic]] signals to or from a storage medium.", senseIter.next().getGloss().getText());
		assertEquals("{{music}} The [[headstock]] of a [[guitar]].", senseIter.next().getGloss().getText());
		assertEquals("{{music}} A [[drum head]], the [[membrane]] which is hit to produce [[sound]].", senseIter.next().getGloss().getText());
		assertEquals("{{engineering}} The end cap of a cylindrically-shaped [[pressure vessel]].", senseIter.next().getGloss().getText());
		assertEquals("{{automotive}} The [[cylinder head]], a platform above the [[cylinder]]s in an [[internal combustion engine]], containing the [[valve]]s and [[spark plug]]s.", senseIter.next().getGloss().getText());
		assertEquals("A buildup of [[fluid]] [[pressure]], often quantified as [[pressure head]].", senseIter.next().getGloss().getText());
		assertEquals("{{fluid dynamics}} The difference in [[elevation]] between two points in a [[column]] of fluid, and the resulting [[pressure]] of the fluid at the lower point.", senseIter.next().getGloss().getText());
		assertEquals("{{fluid dynamics}} More generally, [[energy]] in a mass of fluid divided by its [[weight]].", senseIter.next().getGloss().getText());
		assertEquals("{{nautical}} The [[top]] edge of a [[sail]].", senseIter.next().getGloss().getText());
		assertEquals("{{nautical}} The [[bow]] of a nautical vessel.", senseIter.next().getGloss().getText());
		assertEquals("{{nautical}} The [[toilet]] of a [[ship]].", senseIter.next().getGloss().getText());
		assertEquals("{{uncountable|slang}} [[fellatio|Fellatio]] or [[cunnilingus]]; [[oral sex]].", senseIter.next().getGloss().getText());
		assertEquals("{{slang}} The [[glans penis]].", senseIter.next().getGloss().getText());
		assertEquals("{{countable|slang}} A heavy or [[habitual]] user of [[illicit]] [[drug]]s.", senseIter.next().getGloss().getText());
		assertEquals("{{British}} A [[headland]].", senseIter.next().getGloss().getText());
		assertFalse(senseIter.hasNext());
	}

	public void testForGoodMeasure() throws Exception {
		IWiktionaryPage page = parse("for_good_measure.txt");
		Iterator<IWiktionarySense> senseIter = page.getEntry(0).getSenses().iterator();
		assertEquals("{{idiomatic}} In excess of the minimum required; Added as an [[extra]]", senseIter.next().getGloss().getText());
		assertFalse(senseIter.hasNext());
	}

	public void testBatsman() throws Exception {
		IWiktionaryPage page = parse("batsman.txt");
		Iterator<IWiktionarySense> senseIter = page.getEntry(0).getSenses().iterator();
		assertEquals("{{cricket}} A [[player]] of the [[batting]] [[side]] now on the [[field]]", senseIter.next().getGloss().getText());
		assertEquals("{{cricket}} The [[player]] now [[receiving]] [[strike]]; the [[striker]]", senseIter.next().getGloss().getText());
		assertEquals("{{cricket}} Any player selected for his or her [[team]] principally to [[bat]], as opposed to a [[bowler]]", senseIter.next().getGloss().getText());
		assertFalse(senseIter.hasNext());
	}

	public void testGenderPortugueseNounMasculine() throws Exception {
		IWiktionaryPage page = parse("escritorio.txt");
		assertEquals(GrammaticalGender.MASCULINE, page.getEntry(0).getGender());
	}

	public void testGenderGermanNeuter() throws Exception {
		IWiktionaryPage page = parse("boot.txt");
		assertEquals(GrammaticalGender.NEUTER, page.getEntry(0).getGender());
	}

	public void testGenderSpanishFeminine() throws Exception {
		IWiktionaryPage page = parse("bamba.txt");
		assertEquals(GrammaticalGender.FEMININE, page.getEntry(0).getGender());
	}

	public void testGetExamples() throws Exception {
		IWiktionaryPage page = parse("cheio.txt");
		final IWiktionaryEntry entry = page.getEntry(0);

		final List<IWikiString> examples = entry.getExamples();
		assertEquals(4, examples.size());

		final IWiktionarySense senseFull = entry.getSense(1);
		final IWiktionarySense senseCovered = entry.getSense(2);
		final IWiktionarySense senseFedUp = entry.getSense(3);

		assertEquals(2, senseFull.getExamples().size());
		assertEquals(1, senseCovered.getExamples().size());
		assertEquals(1, senseFedUp.getExamples().size());

		assertEquals("{{usex|lang=pt|A rua está '''cheia''' de trânsito|The street is full of traffic.}}", senseFull.getExamples().get(0).getText());
		assertEquals("{{usex|lang=pt|Estou '''cheio'''.|I'm full (not hungry anymore).}}", senseFull.getExamples().get(1).getText());

		assertEquals("A rua está '''cheia''' de óleo. – The street is covered with oil.", senseCovered.getExamples().get(0).getText());
		assertEquals("{{usex|lang=pt|Estou '''cheio''' dele.|I'm fed up with him.}}", senseFedUp.getExamples().get(0).getText());
	}

	public void testGetExamplesForSubSense() throws Exception {
		IWiktionaryPage page = parse("head.txt");
		// The [[principal]] [[operative]] part of a machine.
		final IWiktionarySense senseWithSubSenses = page.getEntry(0).getSense(7);
		final List<IWikiString> examples = senseWithSubSenses.getExamples();
		assertEquals(2, examples.size());
		assertEquals("''Hit the nail on the '''head'''!''", examples.get(0).getText());
		assertEquals("''The '''head''' of the compass needle is pointing due north.''", examples.get(1).getText());
	}
}
