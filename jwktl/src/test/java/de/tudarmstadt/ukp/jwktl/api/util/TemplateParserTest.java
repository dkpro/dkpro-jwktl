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
import de.tudarmstadt.ukp.jwktl.api.util.TemplateParser.EtymologyTemplateHandler;

/**
 * Test case for {@link TemplateParser}.
 * @author Christian M. Meyer
 */
public class TemplateParserTest extends TestCase {

	/***/
	public void testTemplateParser() {
		String text = "From {{etyl|fr}}...";
		assertEquals(text, TemplateParser.parse(text, null));
		
		text = "From {{etyl|fr}}...{{etyl|de}}.. End.";
		assertEquals(text, TemplateParser.parse(text, null));
		
		text = "From {{etyl|enm}} {{term|dogge|lang=enm}}, from {{etyl|ang}} {{term|docga|lang=ang||hound, powerful breed of dog}}, a pet-form diminutive of {{etyl|ang|-}} {{recons|docce|docce|lang=ang|muscle}} (found in compound {{term|fingerdocce||lang=ang|finger-muscle}} with suffix {{term|-ga|-ga|lang=ang}} (compare {{term|frocga||lang=ang|frog}}, {{term|picga||lang=ang|pig}}), from {{etyl|gem-pro|en}} {{recons|dukkōn||power, strength, muscle|lang=gem-pro}}. More at [[dock]]. In the 16th century, it superseded {{etyl|ang|-}} {{term|hund|lang=ang}} and was adopted by many continental European languages.<ref>{{R:Online Etymology Dictionary|dog}}</ref>";
//		assertEquals("From Middle English dogge, from Old English docga (“hound, powerful breed of dog”), a pet-form diminutive of Old English *docce (“muscle”) (found in compound fingerdocce (“finger-muscle”) with suffix -ga (compare frocga (“frog”), picga (“pig”)), from Proto-Germanic *dukkōn (“power, strength, muscle”). More at [[dock]]. In the 16th century, it superseded Old English hund and was adopted by many continental European languages.<ref></ref>",
		assertEquals("From Middle English dogge, from Old English docga (“hound, powerful breed of dog”), a pet-form diminutive of Old English *docce (“muscle”) (found in compound fingerdocce (“finger-muscle”) with suffix -ga (compare frocga (“frog”), picga (“pig”)), from {gem-pro} *dukkōn (“power, strength, muscle”). More at [[dock]]. In the 16th century, it superseded Old English hund and was adopted by many continental European languages.<ref></ref>",				
				TemplateParser.parse(text, new EtymologyTemplateHandler()));
	}
	
	/*public void testEtymology() {
		String text = "From {{etyl|enm}} {{term|dogge|lang=enm}}, from {{etyl|ang}} {{term|docga|lang=ang||hound, powerful breed of dog}}, a pet-form diminutive of {{etyl|ang|-}} {{recons|docce|docce|lang=ang|muscle}} (found in compound {{term|fingerdocce||lang=ang|finger-muscle}} with suffix {{term|-ga|-ga|lang=ang}} (compare {{term|frocga||lang=ang|frog}}, {{term|picga||lang=ang|pig}}), from {{etyl|gem-pro|en}} {{recons|dukkōn||power, strength, muscle|lang=gem-pro}}. More at [[dock]]. In the 16th century, it superseded {{etyl|ang|-}} {{term|hund|lang=ang}} and was adopted by many continental European languages.<ref>{{R:Online Etymology Dictionary|dog}}</ref>";
		assertEquals("From Middle English dogge, from Old English docga (“hound, powerful breed of dog”), a pet-form diminutive of Old English *docce (“muscle”) (found in compound fingerdocce (“finger-muscle”) with suffix -ga (compare frocga (“frog”), picga (“pig”)), from Proto-Germanic *dukkōn (“power, strength, muscle”). More at dock. In the 16th century, it superseded Old English hund and was adopted by many continental European languages.", 
				WikiString.parseEtymology(text));
	}*/
}
