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

import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.IWiktionaryEntryParser;
import de.tudarmstadt.ukp.jwktl.parser.WiktionaryEntryParser;
import de.tudarmstadt.ukp.jwktl.parser.components.CategoryHandler;
import de.tudarmstadt.ukp.jwktl.parser.components.InterwikiLinkHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DECollocationsHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEEntryFactory;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEEntryLinkHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEEtymologyHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEPartOfSpeechHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEPronunciationHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEReferenceHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DERelationHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DESenseDefinitionHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DESenseExampleHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DETranslationHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEWordFormHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEWordLanguageHandler;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * An implementation of the {@link IWiktionaryEntryParser} interface for 
 * parsing the contents of article pages from the German Wiktionary. 
 * @author Christian M. Meyer
 */
public class DEWiktionaryEntryParser extends WiktionaryEntryParser {

	/** Initializes the German entry parser. That is, the language and the
	 *  redirection pattern is defined, and the handlers for extracting
	 *  the information from the article constituents are registered. */
	public DEWiktionaryEntryParser() {
		super(Language.GERMAN, "WEITERLEITUNG");
		
		// Fixed name handlers.
		register(new DEEtymologyHandler());
		register(new DEPronunciationHandler());
		register(new DESenseDefinitionHandler());
		register(new DESenseExampleHandler());		
		register(new DERelationHandler(RelationType.SYNONYM, "Synonyme"));
		register(new DERelationHandler(RelationType.ANTONYM,  "Gegenworte", "Gegenwörter"));
		register(new DERelationHandler(RelationType.HYPERNYM, "Oberbegriffe"));
		register(new DERelationHandler(RelationType.HYPONYM, "Unterbegriffe"));
		register(new DERelationHandler(RelationType.ETYMOLOGICALLY_RELATED_TERM, "Sinnverwandte Wörter"));
		register(new DERelationHandler(RelationType.DERIVED_TERM, "Abgeleitete Begriffe"));
		register(new DECollocationsHandler());
		register(new DETranslationHandler());
		register(new DEReferenceHandler());
		
		// Pattern-based handlers.
		register(new CategoryHandler("Kategorie"));
		register(new InterwikiLinkHandler("Kategorie"));
		register(new DEWordLanguageHandler());
		register(new DEPartOfSpeechHandler());
		register(new DEEntryLinkHandler());
		register(new DEWordFormHandler());
	}
	
	@Override
	protected ParsingContext createParsingContext(final WiktionaryPage page) {
		return new ParsingContext(page, new DEEntryFactory());
	}
	
	/** Checks if it is start of new section. Symbols are =, {{, [[ or 
	 *  '''[section]'''. */
	protected boolean isStartOfBlock(String line) {
		return line.startsWith("=")
			|| line.startsWith("{{")
			|| line.startsWith("[[") 
			|| (line.startsWith("'''") && line.endsWith("'''"));
	}

}
