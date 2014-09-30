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
package de.tudarmstadt.ukp.jwktl.parser.components;

import de.tudarmstadt.ukp.jwktl.parser.util.IBlockHandler;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;
import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;

/**
 * Abstract parser component for processing article constituents. The handler
 * can be initialized with a set of fixed labels that denote the header of 
 * an article constituent that is to be parsed by this handler.
 * @author Christian M. Meyer
 * @author Lizhen Qu
 */
/**
 * Default implementation of the {@link IBlockHandler} interface that serves 
 * as a base class for parsing any article constituent.
 */
public abstract class BlockHandler implements IBlockHandler {

	protected String[] labels;

	/** Initializes the block handler for parsing all sections starting with 
	 *  one of the specified labels. */
	public BlockHandler(final String... labels) {
		this.labels = labels;
	}
	
	public boolean canHandle(String blockHeader) {
		blockHeader = StringUtils.strip(blockHeader, "{}=: ");
		for (String label : labels)
			if (label.equals(blockHeader))
				return true;
		
		return false;
	}

	public boolean processHead(final String text, final ParsingContext context) {
		return true;
	}
	
	public boolean processBody(final String textLine, final ParsingContext context) {
		return false;
	}

	public void fillContent(final ParsingContext context) {}
	
	protected String[] getLabels() {
		return labels;
	}
	
}
