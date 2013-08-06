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
package de.tudarmstadt.ukp.jwktl.api.util;

import java.util.Iterator;

/**
 * Generic implementation for an iterator of iterators. That is, an object, 
 * which is initialized with an iterator of type OuterType. This iterator is 
 * being iterated an converted into an iterator of type IterableType. For each
 * element of the outer iterator, all elements of the inner iterator are then
 * traversed. For example, a hierarchical iterator of outer type 
 * WiktionaryEntry and inner type WiktionarySense enumerates all senses of 
 * entry1, then all senses of entry2, etc.
 * @param <IterableType> The type the inner iterator.
 * @param <OuterType> The type the outer iterator.
 * @author Christian M. Meyer
 */
public abstract class HierarchicalWiktionaryIterator<IterableType, OuterType> 
		extends WiktionaryIterator<IterableType> {

	protected Iterator<OuterType> outerIterator;
	protected IWiktionaryIterator<IterableType> innerIterator;
	
	/** Initialize the iterator for the given outer type. */
	public HierarchicalWiktionaryIterator(final Iterator<OuterType> outerIterator) {
		this.outerIterator = outerIterator;
	}
	
	protected IterableType fetchNext() {
		if (innerIterator != null && innerIterator.hasNext())
			return innerIterator.next();
		if (!outerIterator.hasNext())
			return null;
		
		OuterType outer = outerIterator.next();
		innerIterator = getInnerIterator(outer);
		if (innerIterator != null && innerIterator.hasNext())
			return innerIterator.next();
		else
			return null;
	}
	
	protected abstract IWiktionaryIterator<IterableType> getInnerIterator(
			final OuterType outer);

	protected void doClose() {
		if (innerIterator != null)
			innerIterator.close();		
	}
	
}
