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
package de.tudarmstadt.ukp.jwktl.parser.util;

import java.util.List;

import junit.framework.TestCase;

public class WordListProcessorTest extends TestCase {
    private WordListProcessor processor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        processor = new WordListProcessor();
    }

    public void testSplitWordListWithNull() throws Exception {
        final List<String> words = processor.splitWordList(null);
        assertEquals(words.size(), 0);
    }

    public void testSplitWordListWithNoItem() throws Exception {
        final List<String> words = processor.splitWordList("");
        assertEquals(words.size(), 0);
    }

    public void testSplitWordListWithOneSimpleTemplateItem() throws Exception {
        final List<String> words = processor.splitWordList("{{l|fr|foo}}");
        assertEquals(words.size(), 1);
        assertEquals(words.get(0), "foo");
    }


    public void testSplitWordListWithTwoSimpleTemplateItems() throws Exception {
        final List<String> words = processor.splitWordList("{{l|fr|foo}}, {{l|uk|bar}}");
        assertEquals(words.size(), 2);
        assertEquals(words.get(0), "foo");
        assertEquals(words.get(1), "bar");
    }

    public void testSplitWordListWithTwoComplexTemplateItems() throws Exception {
        final List<String> words = processor.splitWordList("{{l|hi|बरामदा|tr=barāmdā|sc=Deva}}, {{l|hi|बरण्डा|tr=baraṇḍā|sc=Deva}}");
        assertEquals(words.size(), 2);
        assertEquals(words.get(0), "बरामदा");
        assertEquals(words.get(1), "बरण्डा");
    }

}
