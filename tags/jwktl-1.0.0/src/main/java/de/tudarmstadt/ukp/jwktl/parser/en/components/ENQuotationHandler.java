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

import de.tudarmstadt.ukp.jwktl.api.entry.Quotation;
import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * Parser component for extracting quotations from the English Wiktionary. 
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
public class ENQuotationHandler extends ENBlockHandler {
	
	protected List<Quotation> quotations;
	
	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public ENQuotationHandler() {
		super("Quotations");
	}
	
	@Override
	public boolean processHead(final String textLine, final ParsingContext context) {
		quotations = new ArrayList<Quotation>();
		return super.processHead(textLine, context);
	}
	
	@Override
	public boolean processBody(final String textLine, final ParsingContext context) {
		String line = textLine.trim();
		if (line.startsWith("|")) 
			return extractQuotation("*" + line, true, context);
		else
			return extractQuotation(line, false, context);
	}
	
	/** Extract a quotation from the given line and add it to the internal list. 
	 *  @param additionalLine if <code>false</code> adds a new quotation to 
	 *  	the list and otherwise appends the quotation to the last one. */
	public boolean extractQuotation(final String textLine, 
			boolean additionalLine, final ParsingContext context) {
		String line = textLine.trim();
		if (!line.startsWith("*")) 
			return false;
		
		line = line.substring(1).trim();
		if (line.startsWith(":")) {
			if (quotations.size() > 0){
				Quotation q = quotations.get(quotations.size() - 1);
				while (line.startsWith(":"))
					line = line.substring(1);
				q.addLine(new WikiString(line.trim()));
			}
		} else
		if (additionalLine) {
			if (!quotations.isEmpty()) {
				Quotation quot = quotations.get(quotations.size() - 1);
				int idx = quot.getLines().size() - 1;
				if (idx >= 0) {
					line = quot.getLines().get(idx).getText() + " " + line;
					quot.getLines().set(idx, new WikiString(line.trim()));
				} else
					quot.getLines().add(new WikiString(line.trim()));
			}
		} else {
			Quotation quotationEntry = new Quotation();
			if (line.startsWith("{{"))
				quotationEntry.addLine(new WikiString(line.trim()));
			else
				quotationEntry.setSource(new WikiString(line.trim()));
			quotations.add(quotationEntry);
		}
		return false;
	}

	public void fillContent(final ParsingContext context) {
		WiktionaryEntry posEntry = context.findEntry();
		for (Quotation quotation : quotations)
			posEntry.getUnassignedSense().addQuotation(quotation);
	}
	
	/** Returns the list of all quotations. */
	public List<Quotation> getQuotations() {
		return quotations;
	}

}
