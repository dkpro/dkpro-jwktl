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

import de.tudarmstadt.ukp.jwktl.api.IPronunciation;
import de.tudarmstadt.ukp.jwktl.api.IPronunciation.PronunciationType;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.entry.Pronunciation;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEPronunciationHandler;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Test case for {@link DEPronunciationHandler}.
 * @author Christian M. Meyer
 */
public class DEPronunciationHandlerTest extends DEWiktionaryEntryParserTest {

	/***/
	public void testBrathaehnchen() throws Exception {
		IWiktionaryPage page = parse("Brathaehnchen.txt");		
		Iterator<IPronunciation> iter = page.getEntry(0).getPronunciations().iterator();
		assertPronunciation(PronunciationType.IPA, "ˈbʀaːthɛːnçən", "", iter.next());
		assertPronunciation(PronunciationType.IPA, "ˈbʀaːthɛːnçn̩", "", iter.next());
		assertPronunciation(PronunciationType.IPA, "ˈbʀaːthɛːnçən", "Pl.", iter.next());
		assertPronunciation(PronunciationType.IPA, "ˈbʀaːthɛːnçn̩", "", iter.next());
		assertFalse(iter.hasNext());
	}
	
	/***/
	public void testJanuar() throws Exception {
		IWiktionaryPage page = parse("Januar.txt");		
		Iterator<IPronunciation> iter = page.getEntry(0).getPronunciations().iterator();
		assertPronunciation(PronunciationType.IPA, "ˈjanu̯aːɐ̯", "", iter.next());
		assertPronunciation(PronunciationType.IPA, "ˈjanu̯aːʀə", "Pl.", iter.next());
		assertPronunciation(PronunciationType.AUDIO, "De-Januar.ogg", "Januar", iter.next());
		assertPronunciation(PronunciationType.AUDIO, "De-Januare.ogg", "Pl. Januare", iter.next());
		assertFalse(iter.hasNext());
	}

	/***/
	public void testWorker() throws Exception {
		DEPronunciationHandler w = new DEPronunciationHandler();
		w.processHead(null, new ParsingContext(null));
		w.processBody(":{{IPA}} {{Lautschrift|ambiʦi̯oˈniːɐ̯t}}, {{Komp.}} {{Lautschrift|ambiʦi̯oˈniːɐ̯tɐ}}, {{Sup.}} {{Lautschrift|ambiʦi̯oˈniːɐ̯təstən}}, {{Lautschrift|ambiʦi̯oˈniːɐ̯təstn̩}}", null);
		w.processBody(":{{Hörbeispiele}} {{fehlend}}", null);
		
		w.processBody(":[[Hilfe:IPA|IPA]]: {{Lautschrift|haˈloː}}, {{Pl.}} {{Lautschrift|haˈloːs}}", null);
		w.processBody(":[[Hilfe:Hörbeispiele|Hörbeispiele]]: {{Audio|De-Hallo.ogg|Hallo}}, {{Pl.}} {{fehlend}}", null);
		
		w.processBody(":[[Hilfe:IPA|IPA]]: [ɪntɛnˈʦǐoːn], {{Pl.}} [ɪntɛnˈʦǐoːnən]", null);
		w.processBody(":[[Hilfe:SAMPA|SAMPA]]: [IntEn&quot;tsio:n], {{Pl.}} [IntEn&quot;tsio:n@n]", null);
		w.processBody(":[[Hilfe:Hörbeispiele|Hörbeispiele]]: {{fehlend}}, {{Pl.}} {{fehlend}} ", null);
		
		w.processBody("*[[にっぽん]]", null);
		w.processBody("*:[[Hilfe:IPA|IPA]]: {{Lautschrift|nʲipːoɴ}}", null);
		w.processBody("*:[[Hilfe:SAMPA|X-SAMPA]]: {{Lautschrift|nˈip:oN}}", null);
		w.processBody("*[[にほん]]", null);
		w.processBody("*:[[Hilfe:IPA|IPA]]: {{Lautschrift|nʲihoɴ}}", null);
		w.processBody("*:[[Hilfe:SAMPA|X-SAMPA]]: {{Lautschrift|nˈihoN}}", null);
		w.processBody("*[[ひのもと]] (poetisch)", null);
		w.processBody("*:[[Hilfe:IPA|IPA]]: {{Lautschrift|çinomoto}}", null);
		w.processBody("*:[[Hilfe:SAMPA|X-SAMPA]]: {{Lautschrift|Cinomoto}}", null); 
		
		Iterator<Pronunciation> iter = w.getPronunciations().iterator();
		assertPronunciation(PronunciationType.IPA, "ambiʦi̯oˈniːɐ̯t", "", iter.next());
		assertPronunciation(PronunciationType.IPA, "ambiʦi̯oˈniːɐ̯tɐ", "Komp.", iter.next());
		assertPronunciation(PronunciationType.IPA, "ambiʦi̯oˈniːɐ̯təstən", "Sup.", iter.next());
		assertPronunciation(PronunciationType.IPA, "ambiʦi̯oˈniːɐ̯təstn̩", "", iter.next());
		assertPronunciation(PronunciationType.IPA, "haˈloː", "", iter.next());
		assertPronunciation(PronunciationType.IPA, "haˈloːs", "Pl.", iter.next());
		assertPronunciation(PronunciationType.AUDIO, "De-Hallo.ogg", "Hallo", iter.next());
		assertPronunciation(PronunciationType.IPA, "ɪntɛnˈʦǐoːn", "", iter.next());
		assertPronunciation(PronunciationType.IPA, "ɪntɛnˈʦǐoːnən", "Pl.", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "IntEn&quot;tsio:n", "", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "IntEn&quot;tsio:n@n", "Pl.", iter.next());
		assertPronunciation(PronunciationType.IPA, "nʲipːoɴ", "にっぽん", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "nˈip:oN", "にっぽん", iter.next());
		assertPronunciation(PronunciationType.IPA, "nʲihoɴ", "にほん", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "nˈihoN", "にほん", iter.next());
		assertPronunciation(PronunciationType.IPA, "çinomoto", "ひのもと", iter.next());
		assertPronunciation(PronunciationType.SAMPA, "Cinomoto", "ひのもと", iter.next());
		assertFalse(iter.hasNext());
	}

	
	protected static void assertPronunciation(final PronunciationType type, 
			final String text, final String note, final IPronunciation actual) {
		assertEquals(type, actual.getType());
		assertEquals(text, actual.getText());
		assertEquals(note, actual.getNote());
	}

}
