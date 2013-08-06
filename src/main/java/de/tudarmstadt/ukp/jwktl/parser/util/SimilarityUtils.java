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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Some convenient string utilities.
 * 
 * @author Lizhen Qu
 *
 */
public class SimilarityUtils {
	
	protected static final Pattern NGRAM_PATTERN = Pattern.compile("^_?[^0-9\\?!\\-_/]*_?$");
	
  /**
   * Compute N Grams.
   * @param startOrder
   * @param maxOrder
   * @param text
   * @return a n gram to frequency map.
   */
	protected static Map<String, Integer> computeNGrams(int startOrder, 
			int maxOrder, final String text) {
		Map<String, Integer> ngram2count = new HashMap<String, Integer>();
		String[] tokens = text.split("\\s");
		
		for (int order = startOrder; order <= maxOrder; ++order) {
			for (String token : tokens) {
				token = "_" + token + "_";

				for (int i = 0; i < (token.length() - order + 1); i++) {
					String ngram = token.substring(i, i + order);

					Matcher matcher = NGRAM_PATTERN.matcher(ngram);
					if (!matcher.find()) {
						continue;
					} else if (!ngram2count.containsKey(ngram)) {
						ngram2count.put(ngram, 1);
					} else {
						int score = ngram2count.remove(ngram);
						ngram2count.put(ngram, ++score);
					}
				}
			}
		}
		
		if (ngram2count.containsKey("_")) {
			int blanksScore = ngram2count.remove("_");
			ngram2count.put("_", blanksScore / 2);
		}
		return ngram2count;
	}
	
	/**
	 * <p>Calculate word frequency.
	 * 
	 * @param text a text to process
	 * @return a map of word to frequency.
	 */
	protected static Map<String, Integer> computeWord2count(final String text) {
		Map<String, Integer> word2count = new HashMap<String, Integer>();
		Pattern wordPattern = Pattern.compile("\\w+");
		Matcher matcher = wordPattern.matcher(text);
		while(matcher.find()) {
			String w = matcher.group().toLowerCase();
			if (word2count.containsKey(w))
				word2count.put(w, word2count.get(w) + 1);
			else
				word2count.put(w, 1);
		}
		return word2count;
	}
	
	/**
	 * <p>Calculate similarity between two sets of n grams
	 * 
	 * @param ngramsA a set of n grams
	 * @param ngramsB a set of n grams
	 * @return the similarity value.
	 */
	protected static double similarity(final Map<String, Integer> ngramsA,
			final Map<String, Integer> ngramsB) {
		double a = 0d;
		double b = 0d;
		double common = 0;
		double all = 0;
		Map<String, Integer> ngramAll = new HashMap<String, Integer>();
		ngramAll.putAll(ngramsB);
		for (Entry<String, Integer> entry : ngramsA.entrySet()) {
			int value = 0;
			if (ngramAll.containsKey(entry.getKey())) {
				if (ngramAll.get(entry.getKey()) < entry.getValue()) {
					ngramAll.put(entry.getKey(), entry.getValue());
					value = entry.getValue();
				} else
					value = ngramAll.get(entry.getKey());
			} else {
				value = entry.getValue();
				ngramAll.put(entry.getKey(), value);
			}
			all += value;
		}

		for (Integer nGramACount : ngramsA.values())
			a += Math.log(nGramACount / all);
		for (Integer nGramBCount : ngramsB.values())
			b += Math.log(nGramBCount / all);

		for (Entry<String, Integer> ngramA : ngramsA.entrySet()) {
			if (ngramsB.containsKey(ngramA.getKey())) {
				int count = ngramAll.get(ngramA.getKey());
				if (count < ngramA.getValue())
					common += Math.log(count / all);
				else
					common += Math.log(ngramA.getValue() / all);
			}
		}
		return 2 * common / (a + b);
	}
    
	/**
	 * <p>Calculate similarity between two text based on trigram. 
	 * @param textA text A
	 * @param textB text B
	 * @return similarity value
	 */
	public static double similarity(final String textA, final String textB) {
		Map<String, Integer> ngramA = computeNGrams(3, 3, textA);
		Map<String, Integer> ngramB = computeNGrams(3, 3, textB);
		
		return similarity(ngramA, ngramB);
	}
	
	/**
	 * <p>Calculate string similarity based on ugram of words. 
	 * 
	 * @param textA text A
	 * @param textB text B
	 * @return similarity value
	 */
	public static double wordSim(final String textA, final String textB) {
		Map<String, Integer> ngramA = computeWord2count(textA);
		Map<String, Integer> ngramB = computeWord2count(textB);

		return similarity(ngramA, ngramB);
	}
	
	/*
	 * <p>Trigram similarity measure
	 * 
	 * @param textA text A
	 * @param textB text B
	 * @return trigram similarity value
	 * /
	public static double triSim(final String textA, final String textB) {
		Map<String, Integer> ngramA = computeNGrams(3, 3, textA);
		Map<String, Integer> ngramB = computeNGrams(3, 3, textB);
		int common = 0;
		int allA = 0;
		int allB = 0;
		for (int count : ngramA.values()) {
			allA += count;
		}
		for (int count : ngramB.values()) {
			allB += count;
		}
		for (String ngram : ngramA.keySet()) {
			if (ngramB.containsKey(ngram)) {
				int countA = ngramA.get(ngram);
				int countB = ngramB.get(ngram);
				if (countA < countB) {
					common += countA;
				} else {
					common += countB;
				}
			}

		}
		return 1.0 / (1.0 + allA + allB - 2 * common);
	}	*/
	
}
