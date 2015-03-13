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

import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENNonEngWordFormHandler;
import junit.framework.TestCase;

import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender.FEMININE;
import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender.MASCULINE;
import static de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender.NEUTER;

public class ENNonEngWordFormHandlerTest extends TestCase {
    private ENNonEngWordFormHandler handler;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        handler = new ENNonEngWordFormHandler();
    }

    public void testHandleDeNoun() {
        assertGender(NEUTER, "{{de-noun|n|Boots|gen2=Bootes|Boote|Bötchen}}");
    }

    public void testHandlePtNoun() {
        assertGender(MASCULINE, "{{pt-noun|m|s}}");
    }

    public void testHandlePtNounWithTwoGendersDashed() {
        assertGender(MASCULINE, "{{pt-noun|m-f|s}}");
        // TODO: need to change data model to reflect nouns having more than one gender
    }

    public void testHandlePtNounWithTwoGenders() {
        assertGender(MASCULINE, "{{pt-noun|mf|s}}");
        // TODO: need to change data model to reflect nouns having more than one gender
    }

    public void testHandleHead() {
        assertGender(FEMININE, "{{head|fr|noun|g=f}}");
    }

    public void testHandleHeadWithSeparateGenderSpecifier() {
        assertGender(FEMININE, "{{head|pt|plural}} {{g|f}}");
    }

    private void assertGender(GrammaticalGender expectedGender, String line) {
        assertTrue("could not parse "+line, handler.parse(line));
        assertEquals(expectedGender, handler.getGender());
    }
}
