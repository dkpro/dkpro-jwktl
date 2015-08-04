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

import de.tudarmstadt.ukp.jwktl.api.entry.WikiString;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

public class ENUsageNotesHandler extends ENBlockHandler {
	private StringBuilder usageNotes;

	public ENUsageNotesHandler() {
		super("Usage notes");
	}

	@Override
	public boolean processHead(String text, ParsingContext context) {
		usageNotes = new StringBuilder();
		return super.processHead(text, context);
	}

	@Override
	public boolean processBody(String textLine, ParsingContext context) {
		textLine = textLine.trim();
		if (!textLine.isEmpty()) {
			usageNotes.append(textLine).append("\n");
		}
		return super.processBody(textLine, context);
	}

	@Override
	public void fillContent(ParsingContext context) {
		if (usageNotes.length() > 0) {
			WiktionaryEntry entry = context.findEntry();
			if (entry == null) {
				throw new RuntimeException("entry is null");
			}
			entry.setUsageNotes(new WikiString(usageNotes.toString().trim()));
		}
		super.fillContent(context);
	}
}
