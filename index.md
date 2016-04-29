---
#
# Use the widgets beneath and the content will be
# inserted automagically in the webpage. To make
# this work, you have to use › layout: frontpage
#
layout: frontpage
title: "DKPro JWKTL"
---

Java Wiktionary Library
-----------------------

An application programming interface for the free multilingual online dictionary Wiktionary. Wiktionary (<http://www.wiktionary.org>) is collaboratively constructed by volunteers and continually growing. JWKTL enables efficient and structured access to the information encoded in the English, the German, and the Russian Wiktionary language editions, including sense definitions, part of speech tags, etymology, example sentences, translations, semantic relations, and many other lexical information types. The API was first described in an [LREC 2008](http://www.ukp.tu-darmstadt.de/fileadmin/user_upload/Group_UKP/publikationen/2008/lrec08_camera_ready.pdf) paper. The Russian JWKTL parser is based on [Wikokit](https://github.com/componavt/wikokit).


Publications and Citation Information
-------------------------------------

A more detailed description of Wiktionary and JWKTL is available in our scientific articles:

> Christian M. Meyer and Iryna Gurevych: Wiktionary: A new rival for expert-built lexicons? Exploring the possibilities of collaborative lexicography, Chapter 13 in S. Granger & M. Paquot (Eds.): Electronic Lexicography, pp. 259–291, Oxford: Oxford University Press, November 2012. [(download)](http://www.ukp.tu-darmstadt.de/publications/details/?no_cache=1&tx_bibtex_pi1%5Bpub_id%5D=TUD-CS-2012-0008)   
   
> Christian M. Meyer and Iryna Gurevych: OntoWiktionary – Constructing an Ontology from the Collaborative Online Dictionary Wiktionary, chapter 6 in M. T. Pazienza and A. Stellato (Eds.): Semi-Automatic Ontology Development: Processes and Resources, pp. 131–161, Hershey, PA: IGI Global, February 2012. [(download)](http://www.ukp.tu-darmstadt.de/publications/details/?no_cache=1&tx_bibtex_pi1%5Bpub_id%5D=TUD-CS-2011-0202)   
   
> Torsten Zesch, Christof Müller, and Iryna Gurevych: Extracting Lexical Semantic Knowledge from Wikipedia and Wiktionary, in: Proceedings of the 6th International Conference on Language Resources and Evaluation (LREC), pp. 1646–1652, May 2008. Marrakech, Morocco. [(download)](http://www.ukp.tu-darmstadt.de/publications/details/?no_cache=1&tx_bibtex_pi1%5Bpub_id%5D=TUD-CS-2008-4)

Please cite a JWKTL-related article if you use the software in your scientific work. 


License and Availability
------------------------

The latest version of JWKTL is available via [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22jwktl%22). If you use Maven as your build tool, then you can add JWKTL as a dependency in your pom.xml file:

		<dependency>
		  <groupId>de.tudarmstadt.ukp.jwktl</groupId>
		  <artifactId>jwktl</artifactId>
		  <version>1.1.0</version>
		</dependency>

JWKTL is available as open source software under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) (ASL). The software thus comes "as is" without any warranty (see license text for more details). JWKTL makes use of [Berkeley DB Java Edition](http://www.oracle.com/technetwork/products/berkeleydb/downloads/index-098622.html) 5.0.73 ([Sleepycat License](http://download.oracle.com/maven/com/sleepycat/je/license.txt)), [Apache Ant](http://archive.apache.org/dist/ant/source/) 1.7.1 ([ASL](http://www.apache.org/licenses/LICENSE-2.0)), [Xerces](http://xerces.apache.org/xerces2-j/) 2.9.1 ([ASL](http://www.apache.org/licenses/LICENSE-2.0)), [JUnit](http://junit.org/) 4.10 ([CPL](http://www.opensource.org/licenses/cpl1.0.txt)).

Some classes have been taken from the [Wikokit](http://code.google.com/p/wikokit/) project (available under multiple licenses, redistributed under the [ASL](http://www.apache.org/licenses/LICENSE-2.0) license). See NOTICE.txt for further details. 


Documentation
-------------

Learn how to use JWKTL by consulting

* our [getting started](/dkpro-jwktl/documentation/getting-started/) guide,
* an overview on the [architecture of JWKTL](/dkpro-jwktl/documentation/architecture/),
* the selection of [JWKTL use cases](/dkpro-jwktl/documentation/use-cases/), and
* the Javadoc API documentation. 


Feedback
--------

We would love to know if you use JWKTL in your work or research. If you like JWKTL, have questions, comments or just want to tell us that you use JWKTL, please write us a small post to the [JWKTL Users](https://groups.google.com/forum/#!forum/jwktl-users) group. 


About DKPro JWKTL
-----------------

Prior to being available as open source software, JWKTL has been a research project at the [Ubiquitous Knowledge Processing (UKP) Lab](http://www.ukp.tu-darmstadt.de/) of the Technische Universität Darmstadt, Germany. The following people have mainly contributed to this project (in alphabetical order):

* Jan Berkel
* Yevgen Chebotar
* Iryna Gurevych
* Christian M. Meyer
* Christof Müller
* Lizhen Qu
* Torsten Zesch 
