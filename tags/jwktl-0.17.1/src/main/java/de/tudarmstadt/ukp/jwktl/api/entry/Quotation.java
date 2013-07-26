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
package de.tudarmstadt.ukp.jwktl.api.entry;

import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import de.tudarmstadt.ukp.jwktl.api.IQuotation;
import de.tudarmstadt.ukp.jwktl.api.IWikiString;

/**
 * <p>A quotation consists of a quotation source and a list of real quotations.
 * <p>For example:
 * <p>quotation source:2001 — Eoin Colfer, Artemis Fowl, p 221
 * <p>quotations:Then you could say that the doorway exploded.  But the particular verb doesn't do the action justice.  Rather, it shattered into infinitesimal pieces.
 * @author Lizhen Qu
 *
 */
@Persistent
public class Quotation implements IQuotation {
	
	/** The source of a quotation, i.e. its author and publishing reference. */
	private IWikiString source;
	
	/** A list of text lines sharing the same source. */
	private List<IWikiString> lines;
	
	/** Initializes the list of text lines. */
	public Quotation() {
		lines = new ArrayList<IWikiString>();
	}
	
	public IWikiString getSource() {
		return source;
	}
	
	/** Assigns the given {@link WikiString} as the source of the quotation,
	 *  i.e. its author and publishing reference. */
	public void setSource(final IWikiString source) {
		this.source = source;
	}
	
	/** Add a quotation line, i.e. the text of a quotation. */
	public void addLine(final IWikiString line) {
		lines.add(line);
	}
	
	public List<IWikiString> getLines() {
		return lines;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(source.getPlainText()).append("\n");
		for (IWikiString statement : lines)
			result.append(statement.getPlainText()).append("\n");
		return result.toString();
	}

}
