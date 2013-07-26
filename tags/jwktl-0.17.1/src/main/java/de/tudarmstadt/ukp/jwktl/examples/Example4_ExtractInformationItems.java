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
package de.tudarmstadt.ukp.jwktl.examples;

import java.io.File;

import de.tudarmstadt.ukp.jwktl.JWKTL;
import de.tudarmstadt.ukp.jwktl.api.IQuotation;
import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryTranslation;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.util.Language;

/**
 * Simple example for accessing specific information types for the 
 * word <i>boat</i>.
 * @author Yevgen Chebotar
 * @author Christian M. Meyer
 */
public class Example4_ExtractInformationItems {
		
	protected static void extractDescriptions(final IWiktionaryEdition wkt) {
		// Sense definition.
		IWiktionaryPage page = wkt.getPageForWord("boat");
		IWiktionaryEntry entry = page.getEntry(0);
	  IWiktionarySense sense = entry.getSense(1);
	  System.out.println(sense.getGloss().getText());
	  System.out.println(sense.getGloss().getPlainText());
	  
	  // Example sentences.
	  page = wkt.getPageForWord("boat");
	  entry = page.getEntry(0);
	  sense = entry.getSense(1);
	  if (sense.getExamples() != null)
	  	for (IWikiString example : sense.getExamples())
	  		System.out.println(example.getText());
	  
	  // Quotations.
	  page = wkt.getPageForWord("boat");
	  entry = page.getEntry(0);
	  sense = entry.getSense(1);
	  if (sense.getQuotations() != null)
	  	for (IQuotation quotation : sense.getQuotations()) {
	  		for (IWikiString line : quotation.getLines())
	  			System.out.println(line.getText());
	  		System.out.println("--" + quotation.getSource().getText());
	  	}	  
	}
	
	protected static void extractRelations(final IWiktionaryEdition wkt) {
		// Synonyms of boat.
		IWiktionaryPage page = wkt.getPageForWord("boat");
		for (IWiktionaryEntry entry : page.getEntries())
			if (entry.getPartOfSpeech() == PartOfSpeech.NOUN)
				for (IWiktionaryRelation relation : entry.getRelations(RelationType.SYNONYM))
					System.out.println(relation.getTarget());

		// All relations of boat.
		page = wkt.getPageForWord("boat");
		for (IWiktionaryEntry entry : page.getEntries())
			if (entry.getPartOfSpeech() == PartOfSpeech.NOUN)
				for (IWiktionaryRelation relation : entry.getRelations())
					System.out.println(relation.getRelationType() + ": " + relation.getTarget());

		// Sense-disambiguated relations.
		page = wkt.getPageForWord("boat");
	  IWiktionaryEntry entry = page.getEntry(0);
	  IWiktionarySense sense = entry.getSense(1);
	  for (IWiktionaryRelation relation : sense.getRelations(RelationType.SYNONYM))
			System.out.println(relation.getTarget());

	  // Unassigned relations.
	  page = wkt.getPageForWord("boat");
	  entry = page.getEntry(0);
	  IWiktionarySense unassigned = entry.getUnassignedSense();
	  for (IWiktionaryRelation relation : unassigned.getRelations(RelationType.SYNONYM))
			System.out.println(relation.getTarget());
	}
	
	protected static void extractTranslations(final IWiktionaryEdition wkt) {
		// German translations of boat.
		IWiktionaryPage page = wkt.getPageForWord("boat");
		for (IWiktionaryEntry entry : page.getEntries())
			if (entry.getPartOfSpeech() == PartOfSpeech.NOUN)
				for (IWiktionaryTranslation translation : entry.getTranslations(Language.GERMAN))
					System.out.println(translation.getTranslation());

		// All translations of boat.
		page = wkt.getPageForWord("boat");
		for (IWiktionaryEntry entry : page.getEntries())
			if (entry.getPartOfSpeech() == PartOfSpeech.NOUN)
				for (IWiktionaryTranslation translation : entry.getTranslations())
					System.out.println(translation.getLanguage() + ": " + translation.getTranslation());

		// Sense-disambiguated translations.
		page = wkt.getPageForWord("boat");
	  IWiktionaryEntry entry = page.getEntry(0);
	  IWiktionarySense sense = entry.getSense(1);
	  for (IWiktionaryTranslation translation : sense.getTranslations(Language.GERMAN))
	      System.out.println(translation.getTranslation());

	  // Unassigned translations.
	  page = wkt.getPageForWord("boat");
	  entry = page.getEntry(0);
	  IWiktionarySense unassigned = entry.getUnassignedSense();
	  for (IWiktionaryTranslation translation : unassigned.getTranslations(Language.GERMAN))
	      System.out.println(translation.getTranslation());
	  wkt.close();
	}

	/** Runs the example.
	 *  @param args name of the directory of parsed Wiktionary data. */
	public static void main(String[] args) {
		if (args.length != 1)
			throw new IllegalArgumentException("Too few arguments. "
						+ "Required arguments: <PARSED-WIKTIONARY>");
		
		// Create new IWiktionaryEdition for our parsed data.
		String wktPath = args[0];
		IWiktionaryEdition wkt = JWKTL.openEdition(new File(wktPath));
		
		extractDescriptions(wkt);
		extractRelations(wkt);
		extractTranslations(wkt);

		wkt.close();
	}

}
