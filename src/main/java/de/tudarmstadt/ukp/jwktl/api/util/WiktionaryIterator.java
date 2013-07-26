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
import java.util.NoSuchElementException;

/**
 * Default implementation of the {@link IWiktionaryIterator} interface.
 * @author Christian M. Meyer
 */
public abstract class WiktionaryIterator<Type> 
		implements IWiktionaryIterator<Type> {

	protected Type nextValue;
	protected boolean closed;

	public Iterator<Type> iterator() {
		return this;
	}

	public boolean hasNext() {
		if (closed)
			return false;
		if (nextValue != null)
			return true;

		nextValue = fetchNext();
		if (nextValue == null)
			close(); // Auto-close on exit.
			return (nextValue != null);
	}

	public Type next() {
		if (hasNext()) {
			Type value = nextValue;
			nextValue = null;
			return value;
		} else
			throw new NoSuchElementException();
	}

	/** Hotspot for fetching the next element for iteration. If there are no
	 *  elements left, <code>null</code> is to be returned, which causes the 
	 *  iterator to return <code>false</code> for the next {@link #hasNext()}. */
	protected abstract Type fetchNext();

    /** Hotspot that is invoked after closing the iteration, i.e. either all
     *  items are traversed or manual termination. The hotspot is called only
     *  once. */
	protected abstract void doClose(); 

	public void remove() {
		throw new UnsupportedOperationException("JWKTL access is read-only.");
	}
	
	public void close() {
		if (!closed) {
			doClose();
			closed = true;
		}
	}
	
	public boolean isClosed() {
		return closed;
	}

}
