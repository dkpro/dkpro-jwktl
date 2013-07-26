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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import junit.framework.TestCase;
import de.tudarmstadt.ukp.jwktl.api.util.WiktionaryIterator;

/**
 * Test case for {@link WiktionaryIterator}.
 * @author Christian M. Meyer
 */
public class WiktionaryIteratorTest extends TestCase {
	
	private class MyWiktionaryIterator extends WiktionaryIterator<String> {

		protected void doClose() {
			if (wasClosed)
				fail("Iteration was closed twice!");
			wasClosed = true;
		}

		protected String fetchNext() {
			return (queue.isEmpty() ? null : queue.poll());
		}
		
	}
	
	protected boolean wasClosed;
	protected Queue<String> queue = new LinkedList<String>();
	
	/***/
	public void testSimpleIterator() {
		WiktionaryIterator<String> iter;
		
		// Normal iteration, 2 elements.
		queue.clear(); wasClosed = false;
		queue.offer("test1");
		queue.offer("test2");
		iter = new MyWiktionaryIterator();
		assertTrue(iter.hasNext()); assertFalse(iter.isClosed());
		assertEquals("test1", iter.next());
		assertTrue(iter.hasNext()); assertFalse(iter.isClosed());
		assertEquals("test2", iter.next());
		assertFalse(iter.hasNext()); assertTrue(iter.isClosed());
		assertTrue(queue.isEmpty()); assertTrue(wasClosed);
				
		// Normal iteration, 1 element.
		queue.clear(); wasClosed = false;
		queue.offer("");
		iter = new MyWiktionaryIterator();
		assertTrue(iter.hasNext()); assertFalse(iter.isClosed());
		assertEquals("", iter.next());
		assertFalse(iter.hasNext()); assertTrue(iter.isClosed());
		assertTrue(queue.isEmpty()); assertTrue(wasClosed);
		
		// Normal iteration, 0 elements.
		queue.clear(); wasClosed = false;
		iter = new MyWiktionaryIterator();
		assertFalse(iter.hasNext()); assertTrue(iter.isClosed());
		assertTrue(queue.isEmpty()); assertTrue(wasClosed);
		
		// Manual termination.
		queue.clear(); wasClosed = false;
		queue.offer("test1");
		queue.offer("test2");
		queue.offer("test3");
		iter = new MyWiktionaryIterator();
		assertTrue(iter.hasNext()); assertFalse(iter.isClosed());
		assertEquals("test1", iter.next());
		assertTrue(iter.hasNext()); assertFalse(iter.isClosed());
		assertFalse(queue.isEmpty()); assertFalse(wasClosed);
		iter.close();
		assertFalse(iter.hasNext()); assertTrue(iter.isClosed());
		assertFalse(queue.isEmpty());  assertTrue(wasClosed);
		try {
			iter.next();
			fail("NoSuchElementException expected");
		} catch (NoSuchElementException e) {}
		
		// Multiple terminations.
		wasClosed = false;
		iter.close();
		assertFalse(iter.hasNext()); assertTrue(iter.isClosed());
		assertFalse(queue.isEmpty()); 
		assertFalse(wasClosed); // the close() method should not be called twice!
		try {
			iter.next();
			fail("NoSuchElementException expected");
		} catch (NoSuchElementException e) {}
		
		// No multiple iterations.
		queue.clear(); wasClosed = false;
		queue.offer("test1");
		queue.offer("test2");
		queue.offer("test3");
		queue.offer("test4");
		iter = new MyWiktionaryIterator();
		assertTrue(iter.hasNext()); assertFalse(iter.isClosed());
		assertEquals("test1", iter.next());
		assertTrue(iter.hasNext()); assertFalse(iter.isClosed());
		Iterator<String> iter2 = iter.iterator();
		assertTrue(iter2.hasNext()); assertFalse(iter.isClosed());
		assertEquals("test2", iter2.next());
		assertTrue(iter.hasNext()); assertFalse(iter.isClosed());
		assertEquals("test3", iter.next());
		assertTrue(iter2.hasNext()); assertFalse(iter.isClosed());
		assertEquals("test4", iter2.next());		
		assertFalse(iter.hasNext()); assertTrue(iter.isClosed());
		assertFalse(iter2.hasNext());
		assertTrue(queue.isEmpty()); assertTrue(wasClosed);
		
		// Read only.
		queue.clear(); wasClosed = false;
		queue.offer("test");
		iter = new MyWiktionaryIterator();
		assertTrue(iter.hasNext()); assertFalse(iter.isClosed());
		try {
			iter.remove();
			fail("UnsupportedOperationException expected");
		} catch (UnsupportedOperationException e) {}
		iter.close();
	}
	
}
