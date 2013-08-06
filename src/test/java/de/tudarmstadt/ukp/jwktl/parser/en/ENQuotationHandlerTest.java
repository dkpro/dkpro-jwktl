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
package de.tudarmstadt.ukp.jwktl.parser.en;

import java.util.Iterator;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.IQuotation;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENQuotationHandler;

/**
 * Test case for {@link ENQuotationHandler}.
 * @author Christian M. Meyer
 */
public class ENQuotationHandlerTest extends ENWiktionaryEntryParserTest {

	/***/
	public void testBe() throws Exception {
		IWiktionaryPage page = parse("be.txt");
		IWiktionarySense sense = page.getEntry(0).getSense(3);
		Iterator<IQuotation> quotationIter = sense.getQuotations().iterator();
		IQuotation quotation = quotationIter.next();
		assertEquals("'''1526''', ''Bible'', tr. William Tyndale, Matthew 2:", quotation.getSource().getText());
		assertEquals(1, quotation.getLines().size());
		assertEquals("Rachel wepynge ffor her chyldren, and wolde nott be comforted because they '''were''' not.", quotation.getLines().get(0).getText());
		quotation = quotationIter.next();
		assertEquals("'''c. 1600''', [[w:William Shakespeare|William Shakespeare]], ''[[s:Hamlet|Hamlet]]'':", quotation.getSource().getText());
		assertEquals(1, quotation.getLines().size());
		assertEquals("''To '''be''' or not to '''be''', that is the question.''", quotation.getLines().get(0).getText());
		assertFalse(quotationIter.hasNext());
		
		sense = page.getEntry(0).getSense(10);
		quotationIter = sense.getQuotations().iterator();
		quotation = quotationIter.next();
//		assertEquals("{{quote-book|year=1995|author=C. K. Ogden|title=Psyche: An Annual General and Linguistic Psychology 1920-1952|pageurl=http://books.google.com.br/books?id=43QCzKUXkBYC&amp;pg=PA13#v=onepage&amp;q&amp;f=false|isbn=9780415127790|page=13|publisher=C. K. Ogden|passage=Study courses of Esperanto and Ido have '''been''' broadcast.}}", quotation.getSource().getText());
		assertNull(quotation.getSource());
		assertEquals(1, quotation.getLines().size());
		assertEquals("{{quote-book|year=1995|author=C. K. Ogden|title=Psyche: An Annual General and Linguistic Psychology 1920-1952|pageurl=http://books.google.com.br/books?id=43QCzKUXkBYC&amp;pg=PA13#v=onepage&amp;q&amp;f=false|isbn=9780415127790|page=13|publisher=C. K. Ogden|passage=Study courses of Esperanto and Ido have '''been''' broadcast.}}", quotation.getLines().get(0).getText());
		assertFalse(quotationIter.hasNext());
		
		sense = page.getEntry(0).getSense(11);
		quotationIter = sense.getQuotations().iterator();
		quotation = quotationIter.next();
//		assertEquals("{{quote-book|year=1995|author=C. K. Ogden|title=Psyche: An Annual General and Linguistic Psychology 1920-1952|pageurl=http://books.google.com.br/books?id=43QCzKUXkBYC&amp;pg=PA13#v=onepage&amp;q&amp;f=false|isbn=9780415127790|page=13|publisher=C. K. Ogden|passage=In the possibility of radio uses of a constructed language — and such experiments '''are''' proving successful — vast sums of money and untold social forces may be involved.}}", quotation.getSource().getText());
		assertNull(quotation.getSource());
		assertEquals(1, quotation.getLines().size());
		assertEquals("{{quote-book|year=1995|author=C. K. Ogden|title=Psyche: An Annual General and Linguistic Psychology 1920-1952|pageurl=http://books.google.com.br/books?id=43QCzKUXkBYC&amp;pg=PA13#v=onepage&amp;q&amp;f=false|isbn=9780415127790|page=13|publisher=C. K. Ogden|passage=In the possibility of radio uses of a constructed language — and such experiments '''are''' proving successful — vast sums of money and untold social forces may be involved.}}", quotation.getLines().get(0).getText());
		assertFalse(quotationIter.hasNext());

		sense = page.getEntry(0).getSense(12);
		quotationIter = sense.getQuotations().iterator();
		quotation = quotationIter.next();
		assertEquals("'''1850''', [[w:Dante Gabriel Rossetti|Dante Gabriel Rossetti]], ''The Blessed Damozel'', lines 67-68", quotation.getSource().getText());
		assertEquals(1, quotation.getLines().size());
		assertEquals("‘I wish that he '''were''' come to me, / For he will come,’ she said.", quotation.getLines().get(0).getText());		
		quotation = quotationIter.next();
		assertEquals("'''1922''', [[w:A. E. Housman|A. E. Housman]], [[w:Last Poems|Last Poems]] XXV, line 13", quotation.getSource().getText());
		assertEquals(1, quotation.getLines().size());
		assertEquals("''The King with half the East at heel '''is''' marched from lands of morning;''", quotation.getLines().get(0).getText());
		assertFalse(quotationIter.hasNext());
	}

	/***/
	public void testNonsense() throws Exception {
		IWiktionaryPage page = parse("nonsense.txt");
		IWiktionarySense sense = page.getEntry(0).getSense(3);
		Iterator<IQuotation> quotationIter = sense.getQuotations().iterator();
		IQuotation quotation = quotationIter.next();
		assertEquals("'''2008''', &quot;Nick Leeson has some lessons for this collapse&quot;, Telegraph.co.uk, Oct 9, 2008", quotation.getSource().getText());
		assertEquals(1, quotation.getLines().size());
		assertEquals("and central banks lend vast sums against marshmallow backed securities, or other '''nonsenses''' creative bankers dreamed up.", quotation.getLines().get(0).getText());
		assertFalse(quotationIter.hasNext());
	}
	
	/***/
	public void testPlant() throws Exception {
		IWiktionaryPage page = parse("plant.txt");
		IWiktionarySense sense = page.getEntry(1).getSense(3);
		Iterator<IQuotation> quotationIter = sense.getQuotations().iterator();
		IQuotation quotation = quotationIter.next();
//		assertEquals("{{quote-news|year=2011|date=January 15|author=Sam Sheringham|title=Chelsea 2 - 0 Blackburn Rovers|work=BBC|url=http://news.bbc.co.uk/sport2/hi/football/eng_prem/9358426.stm|page=|passage=First Anelka curled a shot wide from just outside the box, then Lampard '''planted''' a header over the bar from Bosingwa's cross. }}", quotation.getSource().getText());
		assertNull(quotation.getSource());
		assertEquals(1, quotation.getLines().size());
		assertEquals("{{quote-news |year=2011 |date=January 15 |author=Sam Sheringham |title=Chelsea 2 - 0 Blackburn Rovers |work=BBC |url=http://news.bbc.co.uk/sport2/hi/football/eng_prem/9358426.stm |page= |passage=First Anelka curled a shot wide from just outside the box, then Lampard '''planted''' a header over the bar from Bosingwa's cross. }}", quotation.getLines().get(0).getText());
		assertFalse(quotationIter.hasNext());
		
		sense = page.getEntry(1).getSense(4);
		quotationIter = sense.getQuotations().iterator();
		quotation = quotationIter.next();
		assertEquals("'''2007''', Richard Laymon, ''Savage'', page 118:", quotation.getSource().getText());
		assertEquals(1, quotation.getLines().size());
		assertEquals("Sarah, she kissed each of her grandparents on the forehead. They were '''planted''' in a graveyard behind the church.", quotation.getLines().get(0).getText());
		assertFalse(quotationIter.hasNext());
	}

}
