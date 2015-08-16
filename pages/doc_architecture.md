---
layout: page-fullwidth
title: "JWKTL Architecture"
permalink: "/documentation/architecture/"
---

This page gives and overview on the most important classes used by JWKTL. Make sure you followed the [GettingStarted getting started] guide before proceeding. 

The main entry point is the `JWKTL` class, which facilitates parsing an XML dump file and accessing the parsed data. The latter should equip you with an instance of `IWiktionaryEdition`, JWKTL's representation of a single parsed Wiktionary language edition (e.g., the English Wiktionary). Using the `IWiktionaryEdition` instance, you can access the different information types encoded in Wiktionary. The following class diagram shows the most important classes in this context:

![JWKTL Architecture](/dkpro-jwktl/images/JWKTL-overview.png)


Article pages
-------------

The primary building blocks of Wiktionary are article pages, which contain lexicographic information on a specific word form (e.g., "boat"). This can include multiple parts of speech (e.g., the noun and the verb "plant") and multiple languages (e.g., "fog" in English and Hungarian). JWKTL makes use of the `IWiktionaryPage` interface to represent these article pages. They may be queried either by their unique ID or the described word form. For each page, the title (i.e., the word form), the date, revision ID, and author of the last revision, the language of the embracing Wiktionary language edition, the set of language codes occuring as interwiki-links, and the target of a possible redirection. Code example:

{% highlight java %}
	// Query by ID.
	IWiktionaryPage page = wkt.getPageForId(7377);
	System.out.println(WiktionaryFormatter.instance().formatHeader(page));
	
	// Query by word form.
	page = wkt.getPageForWord("boat");
	System.out.println(WiktionaryFormatter.instance().formatHeader(page));
{% endhighlight %}

  
Lexical entries
---------------

Each combination of language and part of speech described on an article page yields a separate instance of `IWiktionaryEntry` (i.e., multiple lexical entries sharing the same page). Besides the language and the part of speech, an entry encodes the etymology, gender (if applicable), pronunciations, inflected word forms, as well as links to a related entry containing more information (e.g., in case of alternative spellings). The entries can be accessed using the `IWiktionaryPage` instance they are contained in or using the API directly. Note that search queries are case sensitive by default and thus return different results for looking up "rom", "Rom", or "ROM". An optional parameter can be used to initiate case insensitive search. Code example:

{% highlight java %}
	// Access by page.
	List<IWiktionaryEntry> entries = page.getEntries();
	for (IWiktionaryEntry entry : entries)
	  System.out.println(WiktionaryFormatter.instance().formatHeader(entry));
	
	// Query by word form (case sensitive).
	entries = wkt.getEntriesForWord("boat");
	for (IWiktionaryEntry entry : entries)
	  System.out.println(WiktionaryFormatter.instance().formatHeader(entry));
	
	// Query by word form (case insensitive).
	entries = wkt.getEntriesForWord("rom", true);
	for (IWiktionaryEntry entry : entries)
	  System.out.println(WiktionaryFormatter.instance().formatHeader(entry));
{% endhighlight %}


Word senses
-----------

Finally, each lexical entry can distinguish multiple word senses, which are represented in JWKTL as `IWiktionarySense` instances. This class allows accessing the sense definition (gloss), example sentences, quotations, semantic relations, translations, and references. The senses are associated with a running index in the order of their definition within a Wiktionary entry. Since there are often information items that are not associated to any word sense, there is also an unassiged sense containing all of this information. This sense has the running index 0. Code example:

{% highlight java %}
	IWiktionaryEntry entry = entries.get(0);
	
	// Enumerate senses.
	for (IWiktionarySense sense : entry.getSenses())
	  System.out.println(WiktionaryFormatter.instance().formatHeader(sense));	
	
	// Access first sense.
	IWiktionarySense sense = entry.getSense(1);
	System.out.println(WiktionaryFormatter.instance().formatHeader(sense));
	
	// Access second sense.
	sense = entry.getSense(2);
	System.out.println(WiktionaryFormatter.instance().formatHeader(sense));
	
	// Access unassigned semantic information.
	sense = entry.getUnassignedSense();
	System.out.println(WiktionaryFormatter.instance().formatHeader(sense));
{% endhighlight %}

Having understood this general architecture, you can now take a look at specific [JWKTL use cases](/dkpro-jwktl/documentation/use-cases/) to extract a certain kind of information type. In addition to that, the Javadoc documentation gives further information on using the API and its different methods.
