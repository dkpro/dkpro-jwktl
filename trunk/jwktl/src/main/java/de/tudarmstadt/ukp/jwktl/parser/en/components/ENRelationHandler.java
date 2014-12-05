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
package de.tudarmstadt.ukp.jwktl.parser.en.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.WordListProcessor;

/**
 * Parser component for extracting semantic relations from the 
 * English Wiktionary. 
 * @author Christian M. Meyer
 * @author Christof Müller
 * @author Lizhen Qu
 */
public class ENRelationHandler extends ENSenseIndexedBlockHandler {

	protected RelationType relationType;
	
	/** Initializes the block handler for the given relation type and 
	 *  section headers. */
	public ENRelationHandler(final RelationType relationType, final String... labels) {
		super(labels);
		this.relationType = relationType;
	}

	@Deprecated
	protected static final Pattern PATTERN_WORD = Pattern.compile("\\w+");
	
	protected List<List<String>> relationList;
	/** sense index to comment*/
	protected Map<Integer,String> index2comment;		
	protected String comment;

	@Override
	public boolean processHead(String textLine, ParsingContext context) {
		relationList = new ArrayList<List<String>>(); 
		index2comment = new HashMap<Integer,String>();
		return super.processHead(textLine, context);
	}

	protected List<String> parseWordList(final String text) {
		comment = "";
		List<String> result = new ArrayList<String>();

		int braceStartIndex = text.indexOf("(''");
		int braceEndIndex = -1;
		int curlyStartIndex = text.indexOf("{{");
		int curlyEndIndex = text.indexOf("}}");
		int startIndex = -1;
		int endIndex = -1;
		if((braceStartIndex != -1 && curlyStartIndex == -1) || (braceStartIndex != -1 && curlyStartIndex != -1 && braceStartIndex < curlyStartIndex)){
			int endOffset = 3;
			braceEndIndex = text.indexOf("'')", braceStartIndex);
			if(braceEndIndex == -1){
				braceEndIndex = text.indexOf(")", braceStartIndex);
				endOffset = 1;
			}
			if(braceEndIndex == -1){
				braceEndIndex = text.indexOf("''", braceStartIndex+3);
				endOffset = 2;
			}
			if(braceStartIndex + 3 < braceEndIndex){
				String s = text.substring(braceStartIndex+3,braceEndIndex);
				//startIndex = braceStartIndex;
				endIndex = braceEndIndex + endOffset;
				comment = s;
			}
		}else{	
			//CM for preventing bug added third 
			if(curlyStartIndex != -1 && curlyEndIndex != -1 && (curlyEndIndex >= curlyStartIndex)){
				int midIndex = text.indexOf('|',curlyStartIndex);
				if(midIndex != -1 && midIndex < curlyEndIndex){					
					String templateName = text.substring(curlyStartIndex + 2, midIndex);
					if ("l".equals(templateName)) {
						startIndex = -1;
						endIndex = -1;
						curlyEndIndex = -1;
					} else
						comment = text.substring(midIndex+1,curlyEndIndex);				
				}else{
					comment = text.substring(curlyStartIndex+2,curlyEndIndex);
				}
				//startIndex = curlyStartIndex;		// bug fix: this would cause to jump into (startIndex>0) and parse the words before the curly brackets, i.e. empty syns or nothing
				endIndex = curlyEndIndex + 2;
			}
		}
		
		WordListProcessor wordListFilter = new WordListProcessor();
		String relationStr = text;
		if (startIndex > 0)
			relationStr = text.substring(0, startIndex);
		else 
		if (endIndex > 0 && endIndex < text.length())
			relationStr = text.substring(endIndex);
		else 
		if (startIndex == -1 && endIndex == -1)
			relationStr = text;
		else
			return result;
		
		result.addAll(wordListFilter.splitWordList(relationStr));
		return result;
	}

	/**
	 * Extract word list from the given text line
	 */
	public boolean processBody(final String text, final ParsingContext context) {
		String line = text.trim();
				
		if(!line.isEmpty() && line.startsWith("*")) {
			String content = line.substring(1);
			List<String> wordList = parseWordList(content);
			if (relationType == RelationType.DESCENDANT && wordList.size() > 1)
				wordList = fixDescendantWordList(wordList);
			relationList.add(wordList);
			
			int synIndex = relationList.size() - 1;
			Matcher matcher = PATTERN_WORD.matcher(comment);
			if (matcher.find())
				index2comment.put(synIndex, comment);
		}
		return false;
	}

	/**
	 * Add word list to senseEntry. addSemanticWord and addUnclassifiedWords will be called to decide where to store the word list.
	 */
	public void fillContent(final ParsingContext context) {
		WiktionaryEntry posEntry = context.findEntry();	
		if (posEntry == null)
			throw new RuntimeException("posEntry is null " + context.getPartOfSpeech());
		
		for (int i = 0 ; i < relationList.size() ; i++){
			String comment = index2comment.get(i);
			WiktionarySense sense = findMatchingSense(comment, posEntry);
			for (String target : relationList.get(i))
				if (sense != null)
					updateSense(sense, new WiktionaryRelation(target, relationType));
				else
					updatePosEntry(posEntry, new WiktionaryRelation(target, relationType));
		}
	}

	protected void updateSense(final WiktionarySense sense, final IWiktionaryRelation relation) {
		sense.addRelation(relation);
	}

	protected void updatePosEntry(final WiktionaryEntry posEntry, final IWiktionaryRelation relation) {
		posEntry.getUnassignedSense().addRelation(relation);
	}

	private List<String> fixDescendantWordList(List<String> wordList) {
		String firstWord = wordList.get(0);
		final int colon = (firstWord == null ? -1 : firstWord.indexOf(':'));
		if (colon != -1) {
			List<String> fixed = new ArrayList<String>(wordList.size());
			fixed.add(firstWord);

			String language = firstWord.substring(0, colon);
			for (int i = 1; i < wordList.size(); i++) {
				String word = wordList.get(i);
				if (word.indexOf(':') == -1)
					fixed.add(language + ": " + word);
				else
					fixed.add(word);
			}
			return fixed;
		} else
			return wordList;
	}

}
