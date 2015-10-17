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

import com.sleepycat.persist.model.Persistent;
import de.tudarmstadt.ukp.jwktl.api.IWikiString;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryExample;

@Persistent
public class WiktionaryExample implements IWiktionaryExample {
	protected IWikiString example;
	protected IWikiString translation;

	public WiktionaryExample() {}

	public WiktionaryExample(IWikiString example) {
		this(example, null);
	}

	public WiktionaryExample(IWikiString example, IWikiString translation) {
		this.example = example;
		this.translation = translation;
	}

	@Override
	public String getText() {
		return example.getText();
	}

	@Override
	public IWikiString getExample() {
		return example;
	}

	@Override
	public IWikiString getTranslation() {
		return translation;
	}
}
