/*******************************************************************************
 * Copyright 2015
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
package de.tudarmstadt.ukp.jwktl.parser;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
public interface MultistreamFilter {
	/** @return whether to include the page with pageId and pageTitle in the parse */
	boolean accept(long pageId, String pageTitle);

	/** A filter which includes only page titles contained in  the specified list */
	class IncludingNames implements MultistreamFilter {
		private final List<String> pageNames;

		public IncludingNames(String... pageNames) {
			this(Arrays.asList(pageNames));

		}
		public IncludingNames(List<String> pageNames) {
			this.pageNames = pageNames;
		}

		@Override
		public boolean accept(long pageId, String pageTitle) {
			return pageNames.contains(pageTitle);
		}
	}
}
