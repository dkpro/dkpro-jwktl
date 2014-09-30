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

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;

/**
 * A generic iterator that is used for Wiktionary data objects (i.e.,
 * {@link IWiktionaryPage}, {@link IWiktionaryEntry}, 
 * {@link IWiktionarySense}). The iterator supports both the usage as 
 * {@link Iterator} and as {@link Iterable}.
 * <p>
 * Caveat: the latter provides always the same instance, so use the
 * WiktionaryIterator as an {@link Iterable} only once or fetch another
 * WiktionaryIterator for other uses. This should save some memory <i>and</i>
 * allow convenient usage in numerous situations. Besides, the implementation 
 * also allows to react on stopping the iteration, which is the case if
 * (a) all items have been traversed, or (b) the user manually stops the
 * iteration by invoking the {@link #close()} method. Implementing the 
 * corresponding hotspot offers the possibility of closing a connection
 * or freeing a resource after iteration. The iterator is read-only.
 * @param <IterableType> the object type that is the subject of iteration. 
 * @author Christian M. Meyer
 */
public interface IWiktionaryIterator<IterableType> 
		extends Iterable<IterableType>, Iterator<IterableType> {

	/** Stops the iteration. This method is automatically called after all 
	 *  items are traversed, but needs to be called manually if the iteration
	 *  is stopped before. Closing the iterator is necessary to guarantee 
	 *  data consistency and proper resource management. Closing an
	 *  iterator multiple times has no effect. After an iterator is closed,
	 *  {@link #hasNext()} will always result in <code>false</code>. */
	public void close();
	
	/** Returns true if the iterator has been closed, which is the case
	 *  after {@link #close()} has been called or after the last element
	 *  has been retrieved using the {@link #next()} method. */
	public boolean isClosed();
	
}
