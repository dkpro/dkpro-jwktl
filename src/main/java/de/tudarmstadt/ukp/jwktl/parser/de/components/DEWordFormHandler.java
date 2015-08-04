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
package de.tudarmstadt.ukp.jwktl.parser.de.components;

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalAspect;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalCase;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalDegree;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalMood;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalPerson;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalTense;
import de.tudarmstadt.ukp.jwktl.api.util.NonFiniteForm;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Parser component for extracting inflected word forms from the 
 * German Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class DEWordFormHandler extends DEBlockHandler {

	protected enum TableType {
		NOUN_TABLE,
		VERB_TABLE,
		ADJECTIVE_TABLE;
	}
	
	protected List<IWiktionaryWordForm> wordForms;
	protected TableType tableType;
	
	public boolean canHandle(final String blockHeader) {
		if (blockHeader == null || blockHeader.isEmpty())
			return false;
		if (!blockHeader.startsWith("{{"))
			return false;
		
		tableType = null;		
		if (blockHeader.startsWith("{{Substantiv-Tabelle")
				|| blockHeader.startsWith("{{Deutsch Substantiv"))
			tableType = TableType.NOUN_TABLE;
		else
		if (blockHeader.startsWith("{{Verb-Tabelle"))
			tableType = TableType.VERB_TABLE;
		else
		if (blockHeader.startsWith("{{Adjektiv-Tabelle")
				|| blockHeader.startsWith("{{Deutsch Adjektiv Übersicht"))
			tableType = TableType.ADJECTIVE_TABLE;
		
//		if (tableType == null && (blockHeader.startsWith("{{Deutsch ") || blockHeader.contains("Tabelle")))
//			System.err.println("Unhandled inflection table: " + blockHeader);
		
		return (tableType != null);
	}
	
	public boolean processHead(final String textLine, final ParsingContext context) {
		wordForms = new ArrayList<IWiktionaryWordForm>();
		return true;
	}
	
	public boolean processBody(final String textLine, final ParsingContext context) {
		if (textLine.isEmpty())
			return true;
		if (textLine.contains("}}"))
			return false;
//		if (!textLine.startsWith("|"))
//			return false;
		
		// Separate labels from word form. 
		String label = textLine;
		while (label.startsWith("|"))
			label = label.substring(1);
		
		int idx = label.indexOf('=');
		if (idx >= 0) {
			// Extract word form.
			String labelWordForm = label.substring(idx + 1).trim();
			labelWordForm = labelWordForm.replace("<center>", "");
			labelWordForm = labelWordForm.replace("</center>", "");
			labelWordForm = labelWordForm.replace("<small>", "");
			labelWordForm = labelWordForm.replace("</small>", "");
			labelWordForm = labelWordForm.replace("<br>", "\t");
			labelWordForm = labelWordForm.replace("<br />", "\t");
			labelWordForm = labelWordForm.replace("</br>", "\t");
			labelWordForm = labelWordForm.replace("&lt;br /&gt;", "\t");
			labelWordForm = labelWordForm.trim();
			if (labelWordForm.isEmpty())
				return true;
			
			label = label.substring(0, idx).trim();
			if (label.toLowerCase().startsWith("bild") 
					|| label.startsWith("Weitere_Konjugationen")
					|| label.startsWith("Weitere Konjugationen"))
				return true;
			
			do {
				int variantIdx = labelWordForm.indexOf("\t");
				if (variantIdx < 0)
					variantIdx = labelWordForm.length();
				
				String wordFormStr = labelWordForm.substring(0, variantIdx);
				if (variantIdx + 1 < labelWordForm.length())
					variantIdx++;
				labelWordForm = labelWordForm.substring(variantIdx);
				
				if (wordFormStr.isEmpty()) 
					continue;
				if ("-".equals(wordFormStr) || "—".equals(wordFormStr))
					wordFormStr = null;
				WiktionaryWordForm wordForm = new WiktionaryWordForm(wordFormStr);

				// Extract grammatical properties.
				 if (label.equals("Hilfsverb") || label.equals("Hilfsverb2")) {
					//TODO save the modal verb.
					 return true;
				}

				boolean skip = false;
				if (tableType == TableType.NOUN_TABLE) {
					// Inflection table for nouns.
					extractNounTable(wordForm, label);
					if (wordForm.getCase() == null && wordForm.getNumber() == null)
						skip = true;
					
				} else				
				if (tableType == TableType.VERB_TABLE) {
					// Inflection table for verbs.
					extractVerbTable(wordForm, label);
					
					if (wordForm.getPerson() == null && wordForm.getTense() == null
							&& wordForm.getMood() == null && wordForm.getNumber() == null
							&& wordForm.getAspect() == null && wordForm.getNonFiniteForm() == null)
						skip = true;
							
				} else
				if (tableType == TableType.ADJECTIVE_TABLE) {
					// Inflection table for adjectives.
					extractAdjectiveTable(wordForm, label);
					if (wordForm.getDegree() == null)
						skip = true;
					
				} else
					return true;				
				
//				if (skip)			
//				System.out.println(label + " -> " + wordForm.getWordForm() 
//						+ " " + wordForm.getCase() 
//						+ "/" + wordForm.getNumber()
//						+ "/" + wordForm.getPerson()
//						+ "/" + wordForm.getTense()
//						+ "/" + wordForm.getMood());
				
				if (!skip)
					wordForms.add(wordForm);				
			} while (!labelWordForm.isEmpty());			
		}		
		
		return true;
	}

	protected void extractNounTable(final WiktionaryWordForm wordForm, String label) {
//		if ("die Koseworte".equals(wordForm.getWordForm()))
//			System.out.println("X");
		if (label.endsWith("1") || label.endsWith("2") 
				|| label.endsWith("3") || label.endsWith("4"))
			label = label.substring(0, label.length() - 1).trim();
		
		// Number.
		if (label.endsWith(" Singular") || label.endsWith("SINGULAR") || label.endsWith(" (Einzahl)"))
			wordForm.setNumber(GrammaticalNumber.SINGULAR);
		else
		if (label.endsWith(" Plural") || label.endsWith("PLURAL") || label.endsWith(" (Mehrzahl)"))
			wordForm.setNumber(GrammaticalNumber.PLURAL);
				
		// Case.
		if (label.startsWith("Nominativ") || label.startsWith("Wer oder was?"))
			wordForm.setCase(GrammaticalCase.NOMINATIVE);
		else
		if (label.startsWith("Genitiv") || label.startsWith("Wessen?"))
			wordForm.setCase(GrammaticalCase.GENITIVE);
		else
		if (label.startsWith("Dativ") || label.startsWith("Wem?"))
			wordForm.setCase(GrammaticalCase.DATIVE);
		else
		if (label.startsWith("Akkusativ") || label.startsWith("Wen?"))
			wordForm.setCase(GrammaticalCase.ACCUSATIVE);
	}

	protected void extractVerbTable(final WiktionaryWordForm wordForm, final String label) {
		// Person and number.
		if (label.endsWith("_ich")) {
			wordForm.setPerson(GrammaticalPerson.FIRST);
			wordForm.setNumber(GrammaticalNumber.SINGULAR);
		} else
		if (label.endsWith("_du")) {
			wordForm.setPerson(GrammaticalPerson.SECOND);
			wordForm.setNumber(GrammaticalNumber.SINGULAR);
		} else
		if (label.endsWith("_er, sie, es") || label.endsWith("_es")) {
			wordForm.setPerson(GrammaticalPerson.THIRD);
			wordForm.setNumber(GrammaticalNumber.SINGULAR);
		} else
		if (label.endsWith("_wir")) {
			wordForm.setPerson(GrammaticalPerson.FIRST);
			wordForm.setNumber(GrammaticalNumber.PLURAL);
		} else
		if (label.endsWith("_ihr")) {
			wordForm.setPerson(GrammaticalPerson.SECOND);
			wordForm.setNumber(GrammaticalNumber.PLURAL);
		} else
		if (label.endsWith("_sie")) {
			wordForm.setPerson(GrammaticalPerson.THIRD);
			wordForm.setNumber(GrammaticalNumber.PLURAL);
		}
		
		// Mood, tense, aspect and non-finite forms.
		if (label.startsWith("Gegenwart_")) {
			wordForm.setMood(GrammaticalMood.INDICATIVE);
			wordForm.setTense(GrammaticalTense.PRESENT);
		} else
		if (label.startsWith("1.Vergangenheit_")) {
			wordForm.setMood(GrammaticalMood.INDICATIVE);			
			wordForm.setTense(GrammaticalTense.PAST);
		} else
		if (label.startsWith("Konjunktiv II")) {
			wordForm.setMood(GrammaticalMood.CONJUNCTIVE);
			wordForm.setTense(GrammaticalTense.PAST);
		} else
		if (label.startsWith("Konjunktiv I")) {
			wordForm.setMood(GrammaticalMood.CONJUNCTIVE);
			wordForm.setTense(GrammaticalTense.PRESENT);
		} else
		if (label.startsWith("Befehl_")) {
			wordForm.setMood(GrammaticalMood.IMPERATIVE);
		} else
		if (label.equals("Partizip II")) {
			wordForm.setNonFiniteForm(NonFiniteForm.PARTICIPLE);
			wordForm.setAspect(GrammaticalAspect.PERFECT);
		} else
		if (label.equals("Partizip I")) {
			wordForm.setNonFiniteForm(NonFiniteForm.PARTICIPLE);
			wordForm.setAspect(GrammaticalAspect.IMPERFECT);
		}		
	}

	protected void extractAdjectiveTable(final WiktionaryWordForm wordForm, final String label) {
		if (label.equals("Positiv") || label.equals("Grundform"))
			wordForm.setDegree(GrammaticalDegree.POSITIVE);
		else
		if (label.equals("Komparativ") || label.equals("1. Steigerung"))
			wordForm.setDegree(GrammaticalDegree.COMPARATIVE);
		else
		if (label.equals("Superlativ") || label.equals("2. Steigerung"))
			wordForm.setDegree(GrammaticalDegree.SUPERLATIVE);				
	}
	
	public void fillContent(final ParsingContext context) {
		WiktionaryEntry posEntry = context.findEntry();
		for (IWiktionaryWordForm wordForm : wordForms)
			posEntry.addWordForm(wordForm);
	}

}
