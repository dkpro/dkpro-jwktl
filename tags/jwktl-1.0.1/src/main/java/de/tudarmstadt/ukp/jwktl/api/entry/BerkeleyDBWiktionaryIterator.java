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

import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityCursor;

import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.util.WiktionaryIterator;

/**
 * Implementation of the {@link WiktionaryIterator} for the use of a
 * Berkeley DB {@link EntityCursor} as a source of elements. The cursor
 * is passed to the constructor and automatically closed upon manually
 * termination of the iteration or after all elements have been traversed.
 * Additionally, a hotspot is provided to react on the return of an
 * element of the cursor to, e.g., initialize the entity. It is
 * possible to convert the stored entity to a more general type
 * using different type parameters.
 * @param <OutputType> the class type that is returned for each
 *     fetched element.
 * @param <InputType> the class type the stored entities have. It
 *     is necessary that the input type is the same or a subclass
 *     of the type specified as output.
 */
public class BerkeleyDBWiktionaryIterator<OutputType, InputType extends OutputType> 
		extends WiktionaryIterator<OutputType> {

	protected BerkeleyDBWiktionaryEdition edition;
	protected EntityCursor<InputType> cursor;

	/** Initializes the iterator for the specified cursor. */
	public BerkeleyDBWiktionaryIterator(final BerkeleyDBWiktionaryEdition edition,
			final EntityCursor<InputType> cursor) {
		this.edition = edition;
		this.cursor = cursor;
		edition.openCursors.add(cursor);
	}

	protected OutputType fetchNext() {
		try {
			if (closed)
				return null;

			do {
				InputType next = cursor.next();
				if (next == null)
					return null;

				OutputType result = loadEntity(next);
				if (result != null)
					return result;
			} while (true);

			//return loadEntity(cursor.next());
		} catch (DatabaseException e) {
			throw new WiktionaryException(e);
		}
	}

	/** Hotspot that is invoked when returning an entity. It can, e.g.,
	 *  be used to initialize the entity before usage. */
	protected OutputType loadEntity(final InputType entity) {
		return entity;
	}

	protected void doClose() {
		try {
			cursor.close();
			edition.openCursors.remove(cursor);
		} catch (DatabaseException e) {
			throw new WiktionaryException(e);
		}
	}

}
