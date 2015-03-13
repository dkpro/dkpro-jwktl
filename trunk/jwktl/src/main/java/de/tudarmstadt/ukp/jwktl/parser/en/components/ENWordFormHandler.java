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
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryWordForm;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalDegree;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalGender;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalNumber;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalPerson;
import de.tudarmstadt.ukp.jwktl.api.util.GrammaticalTense;
import de.tudarmstadt.ukp.jwktl.api.util.NonFiniteForm;
import de.tudarmstadt.ukp.jwktl.api.util.TemplateParser;
import de.tudarmstadt.ukp.jwktl.api.util.TemplateParser.ITemplateHandler;
import de.tudarmstadt.ukp.jwktl.api.util.TemplateParser.Template;

/**
 * Parser component for extracting inflected word forms from 
 * the English Wiktionary. 
 * @author Christian M. Meyer
 */
public class ENWordFormHandler implements ITemplateHandler, IWordFormHandler {

	private static final Logger logger = Logger.getLogger(ENWordFormHandler.class.getName());
	
	protected List<IWiktionaryWordForm> wordForms;
	protected String lemma;

	/** Initializes the handler for the specified lemma. The lemma is
	 *  required since the inflection templates often defines only affixed
	 *  that are to be added to the lemma. */
	public ENWordFormHandler(final String lemma) {
		this.lemma = lemma;
		wordForms = new ArrayList<IWiktionaryWordForm>();
	}

	public String handle(final Template template) {
        if ("en-noun".equals(template.getName())) {
            handleNounTemplate(template);
        } else if ("en-proper noun".equals(template.getName())) {
            handleProperNounTemplate(template);
        } else if ("en-verb".equals(template.getName())) {
            handleVerbTemplate(template);
        } else if ("en-adj".equals(template.getName())) {
            handleAdjectiveTemplate(template);
        }
        return null;
    }
	
	protected void handleNounTemplate(final Template template) {
		boolean hasPlural = false;
		for (Entry<String, String> par : template.getNamedParams())
			if (par.getKey().startsWith("pl")) {
				wordForms.add(createPlural(null, par.getValue()));
				hasPlural = true;
			}
		
		// http://en.wiktionary.org/wiki/Template:en-noun
		if (template.getNumberedParamsCount() == 0) {
			if (!hasPlural)
				wordForms.add(createPlural(lemma, "s"));
		} else
		if (template.getNumberedParamsCount() == 1) {
			String param1 = template.getNumberedParam(0); 
			if ("-".equals(param1))
				wordForms.add(createPlural(null, null)); // uncountable
			else
			if ("!".equals(param1))
				logger.finer("Not attested word form: " + template); // not attested
			else
			if ("?".equals(param1))
				logger.finer("Unknown word form: " + template); // unknown
			else
				wordForms.add(createPlural(lemma, param1));
		} else
		if (template.getNumberedParamsCount() == 2) {
			String param1 = template.getNumberedParam(0);
			String param2 = template.getNumberedParam(1);
			if ("-".equals(param1) ) {
				wordForms.add(createPlural(lemma, param2)); // usually uncountable
			} else
			if ("-".equals(param2)) {							
				wordForms.add(createPlural(lemma, param1)); // countable and uncountable
			} else
			if ("!".equals(param1))
				logger.finer("Not attested word form: " + template); // not attested
			else
			if ("?".equals(param1))
				logger.finer("Unknown word form: " + template); // unknown
			else
			if ("?".equals(param2))
				wordForms.add(createPlural(lemma, param1)); // unknown
			else
				wordForms.add(createPlural(null, param1 + param2)); // unknown
		}
	}
	
	protected void handleProperNounTemplate(final Template template) {
		// http://en.wiktionary.org/wiki/Template:en-noun
		if (template.getNumberedParamsCount() == 0) {
			wordForms.add(createPlural(null, null)); // uncountable
		} else
		if (template.getNumberedParamsCount() == 1) {
			String param1 = template.getNumberedParam(0); 
			wordForms.add(createPlural(lemma, param1));
		} else
		if (template.getNumberedParamsCount() == 2) {
			String param1 = template.getNumberedParam(0);
			String param2 = template.getNumberedParam(1);
			if ("-".equals(param1) ) {
				wordForms.add(createPlural(lemma, param2)); // usually uncountable
			} else
			if ("-".equals(param2)) {							
				wordForms.add(createPlural(lemma, param1)); // countable and uncountable
			} else
			if ("!".equals(param1))
				logger.finer("Not attested word form: " + template); // not attested
			else
			if ("?".equals(param1))
				logger.finer("Unknown word form: " + template); // unknown
			else
			if ("?".equals(param2))
				wordForms.add(createPlural(lemma, param1)); // unknown
			else
				wordForms.add(createPlural(null, param1 + param2)); // unknown
		}
		for (Entry<String, String> par : template.getNamedParams())
			if (par.getKey().startsWith("pl"))
				wordForms.add(createPlural(null, par.getValue()));
	}

	protected void handleVerbTemplate(final Template template) {
		// http://en.wiktionary.org/wiki/Template:en-verb
		if (template.getNumberedParamsCount() == 0) {
			wordForms.add(createFormThirdPerson(lemma + "s"));
			wordForms.add(createFormPresentParticiple(lemma + "ing"));
			wordForms.add(createFormSimplePast(lemma + "ed"));
			wordForms.add(createFormPastParticiple(lemma + "ed"));
		} else
		if (template.getNumberedParamsCount() == 2) {
			String param1 = template.getNumberedParam(0);
			String param2 = template.getNumberedParam(1);
			if ("es".equals(param2)) {
				wordForms.add(createFormThirdPerson(param1 + "es"));
				wordForms.add(createFormPresentParticiple(param1 + "ing"));
				wordForms.add(createFormSimplePast(param1 + "ed"));
				wordForms.add(createFormPastParticiple(param1 + "ed"));
			} else 
			if ("d".equals(param2)) {
				wordForms.add(createFormThirdPerson(param1 + "s"));
				wordForms.add(createFormPresentParticiple(param1 + "ing"));
				wordForms.add(createFormSimplePast(param1 + "d"));
				wordForms.add(createFormPastParticiple(param1 + "d"));
			} else
			if ("ing".equals(param2)) {
				wordForms.add(createFormThirdPerson(lemma + "s"));
				wordForms.add(createFormPresentParticiple(param1 + "ing"));
				wordForms.add(createFormSimplePast(lemma + "d"));
				wordForms.add(createFormPastParticiple(lemma + "d"));
			}
		} else
		if (template.getNumberedParamsCount() == 3) {
			String param1 = template.getNumberedParam(0);
			String param2 = template.getNumberedParam(1);
			String param3 = template.getNumberedParam(2);
			if ("es".equals(param3)) {
				wordForms.add(createFormThirdPerson(param1 + param2 + "es"));
				wordForms.add(createFormPresentParticiple(param1 + param2 + "ing"));
				wordForms.add(createFormSimplePast(param1 + param2 + "ed"));
				wordForms.add(createFormPastParticiple(param1 + param2 + "ed"));
			} else 
			if ("ed".equals(param3) && "i".equals(param2)) {
				wordForms.add(createFormThirdPerson(param1 + param2 + "es"));
				wordForms.add(createFormPresentParticiple(lemma + "ing"));
				wordForms.add(createFormSimplePast(param1 + param2 + "ed"));
				wordForms.add(createFormPastParticiple(param1 + param2 + "ed"));
			} else 
			if ("ed".equals(param3)) {
				wordForms.add(createFormThirdPerson(lemma + "s"));
				wordForms.add(createFormPresentParticiple(param1 + param2 + "ing"));
				wordForms.add(createFormSimplePast(param1 + param2 + "ed"));
				wordForms.add(createFormPastParticiple(param1 + param2 + "ed"));
			} else 
			if ("ing".equals(param3)) {
				wordForms.add(createFormThirdPerson(lemma + "s"));
				wordForms.add(createFormPresentParticiple(param1 + param2 + "ing"));
				wordForms.add(createFormSimplePast(lemma + "d"));
				wordForms.add(createFormPastParticiple(lemma + "d"));
			} else {
				wordForms.add(createFormThirdPerson(param1));
				wordForms.add(createFormPresentParticiple(param2));
				wordForms.add(createFormSimplePast(param3));
				wordForms.add(createFormPastParticiple(param3));
			}
		} else
		if (template.getNumberedParamsCount() == 4) {
			String param1 = template.getNumberedParam(0);
			String param2 = template.getNumberedParam(1);
			String param3 = template.getNumberedParam(2);
			String param4 = template.getNumberedParam(3);
			wordForms.add(createFormThirdPerson(param1));
			wordForms.add(createFormPresentParticiple(param2));
			wordForms.add(createFormSimplePast(param3));
			wordForms.add(createFormPastParticiple(param4));
		}
	}
	
	protected void handleAdjectiveTemplate(final Template template) {
		// http://en.wiktionary.org/wiki/Template:en-adj
		if (template.getNumberedParamsCount() == 0) {
			wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
			wordForms.add(createAdjectiveForm("more " + lemma, GrammaticalDegree.COMPARATIVE));
			wordForms.add(createAdjectiveForm("most " + lemma, GrammaticalDegree.SUPERLATIVE));
		} else
		if (template.getNumberedParamsCount() == 1) {
			String param1 = template.getNumberedParam(0); 
			if ("er".equals(param1)) {
				wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
				wordForms.add(createAdjectiveForm(lemma + "er", GrammaticalDegree.COMPARATIVE));
				wordForms.add(createAdjectiveForm(lemma + "est", GrammaticalDegree.SUPERLATIVE));
			} else
			if ("-".equals(param1)) { // not comparable
				wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
				wordForms.add(createAdjectiveForm(null, GrammaticalDegree.COMPARATIVE));
				wordForms.add(createAdjectiveForm(null, GrammaticalDegree.SUPERLATIVE));
			} else
			if ("?".equals(param1))
				logger.finer("Unknown word form: " + template); // unknown
			else {
				wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
				wordForms.add(createAdjectiveForm(param1, GrammaticalDegree.COMPARATIVE));
				wordForms.add(createAdjectiveForm("most " + lemma, GrammaticalDegree.SUPERLATIVE));	
			}
		} else
		if (template.getNumberedParamsCount() == 2) {
			String param1 = template.getNumberedParam(0);
			String param2 = template.getNumberedParam(1);
			if ("er".equals(param2)) {
				wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
				wordForms.add(createAdjectiveForm(param1 + "er", GrammaticalDegree.COMPARATIVE));
				wordForms.add(createAdjectiveForm(param1 + "est", GrammaticalDegree.SUPERLATIVE));
			} else 
			if ("er".equals(param1) && "more".equals(param2)) {
				wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
				wordForms.add(createAdjectiveForm(lemma + "er", GrammaticalDegree.COMPARATIVE));
				wordForms.add(createAdjectiveForm("more " + lemma, GrammaticalDegree.COMPARATIVE));
				wordForms.add(createAdjectiveForm(lemma + "est", GrammaticalDegree.SUPERLATIVE));
				wordForms.add(createAdjectiveForm("most " + lemma, GrammaticalDegree.SUPERLATIVE));
			} else
			if ("-".equals(param1)) { // not generally comparable
				wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
				wordForms.add(createAdjectiveForm(param2, GrammaticalDegree.COMPARATIVE));
				if ("-".equals(param2))
					wordForms.add(createAdjectiveForm(param2, GrammaticalDegree.SUPERLATIVE));
				else
					wordForms.add(createAdjectiveForm("most " + lemma, GrammaticalDegree.SUPERLATIVE));					
			} else {
				wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
				wordForms.add(createAdjectiveForm(param1, GrammaticalDegree.COMPARATIVE));
				wordForms.add(createAdjectiveForm(param2, GrammaticalDegree.SUPERLATIVE));
			}
		} else
		if (template.getNumberedParamsCount() == 3) {
			String param1 = template.getNumberedParam(0);
			String param2 = template.getNumberedParam(1);
			String param3 = template.getNumberedParam(2);
			if ("-".equals(param1)) {
				if ("er".equals(param3)) {
					wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
					wordForms.add(createAdjectiveForm(param2 + "er", GrammaticalDegree.COMPARATIVE));
					wordForms.add(createAdjectiveForm(param2 + "est", GrammaticalDegree.SUPERLATIVE));
				} else 
				if ("er".equals(param2) && "more".equals(param3)) {
					wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
					wordForms.add(createAdjectiveForm(lemma + "er", GrammaticalDegree.COMPARATIVE));
					wordForms.add(createAdjectiveForm("more " + lemma, GrammaticalDegree.COMPARATIVE));
					wordForms.add(createAdjectiveForm(lemma + "est", GrammaticalDegree.SUPERLATIVE));
					wordForms.add(createAdjectiveForm("most " + lemma, GrammaticalDegree.SUPERLATIVE));
				} else {
					wordForms.add(createAdjectiveForm(lemma, GrammaticalDegree.POSITIVE));
					wordForms.add(createAdjectiveForm(param2, GrammaticalDegree.COMPARATIVE));
					wordForms.add(createAdjectiveForm(param3, GrammaticalDegree.SUPERLATIVE));
				}
			}
		}
	}

	protected WiktionaryWordForm createWordForm(String wordForm) {
		if (wordForm != null) {
			wordForm = wordForm.trim();
			if ("-".equals(wordForm))
				wordForm = null;
		}
		WiktionaryWordForm result = new WiktionaryWordForm(wordForm);
		return result;
	}

	protected IWiktionaryWordForm createPlural(String wordForm,
			final String pluralParam) {
		if ("s".equals(pluralParam) || "es".equals(pluralParam)) 
			wordForm = wordForm + pluralParam;
		else
			wordForm = pluralParam;
		WiktionaryWordForm result = createWordForm(wordForm);
		result.setNumber(GrammaticalNumber.PLURAL);
		return result;
	}
	
	protected IWiktionaryWordForm createFormThirdPerson(final String wordForm) {
		WiktionaryWordForm result = createWordForm(wordForm);
		result.setPerson(GrammaticalPerson.THIRD);
		result.setNumber(GrammaticalNumber.SINGULAR);
		result.setTense(GrammaticalTense.PRESENT);
		return result;
	}

	protected IWiktionaryWordForm createFormPresentParticiple(final String wordForm) {
		WiktionaryWordForm result = createWordForm(wordForm);
		result.setNonFiniteForm(NonFiniteForm.PARTICIPLE);
		result.setTense(GrammaticalTense.PRESENT);
		return result;
	}

	protected IWiktionaryWordForm createFormSimplePast(final String wordForm) {
		WiktionaryWordForm result = createWordForm(wordForm);
		result.setTense(GrammaticalTense.PAST);
		return result;
	}

	protected IWiktionaryWordForm createFormPastParticiple(final String wordForm) {
		WiktionaryWordForm result = createWordForm(wordForm);
		result.setNonFiniteForm(NonFiniteForm.PARTICIPLE);
		result.setTense(GrammaticalTense.PAST);
		return result;
	}

	protected IWiktionaryWordForm createAdjectiveForm(final String wordForm,
			final GrammaticalDegree degree) {
		WiktionaryWordForm result = createWordForm(wordForm);
		result.setDegree(degree);
		return result;
	}
	
	@Override
    public boolean parse(final String line) {
        if (line.startsWith("{{en-")) {
            TemplateParser.parse(line, this);
            return true;
        } else {
            return false;
        }
	}
	
	@Override
    public List<IWiktionaryWordForm> getWordForms() {
		return wordForms;
	}

    @Override
    public GrammaticalGender getGender() {
        return null;
    }

    // TODO: Adverb!
	// Template:en-plural noun
	// Template:en-pron
	
}
