---
layout: page-fullwidth
title: "Getting started"
permalink: "/documentation/getting-started/"
---

Introduction
------------

OmegaWiki is a free, multilingual lexical semantic resource built around the concept of multilingual Synsets. Although this resource is interesting for NLP applications, the only way to access it so far has been the website <http://www.omegawiki.org>. For large-scale NLP tasks, efficient programmatic access to the knowledge therein is required. The OmegeWiki API provides structured access to all information in this resource like definitions, translations and semantic relations.

Database dumps
--------------

To use the OmegaWiki API, you first have the obtain the 'Lexical data dump' described at <http://www.omegawiki.org/Help:Downloading_the_data#SQL_Database_dump> and import it into a local database. That's it, no further preprocessing is necessary. We tested it on MySQL only, however, it should also work on other database systems.

Setting up JOWKL
----------------

For using JOWKL in your own Java project you have to integrate it into your project using the Maven dependency:

		<dependency>
		  <groupId>org.dkpro.jowkl</groupId>
		  <artifactId>dkpro-jowkl</artifactId>
		  <version>1.0.0</version>
		</dependency>


Code Examples
-------------

Here is a code snippet that shows how information can be obtained from OmegaWiki using the API.

	//Set up the database
	String ow_host = "localhost";
	String ow_db = "OmegaWikiDB";
	String ow_user = "user";
	String ow_pass = "pwd";
	String db_driver = "com.mysql.jdbc.Driver"; //just an example, other drivers should work too
	String db_vendor = "mysql";
	int ow_language= OWLanguage.English;
	DatabaseConfiguration dbConfig_ow = new DatabaseConfiguration(ow_host,ow_db,db_driver,db_vendor, ow_user, ow_pass, ow_language);
	//Create the OmegaWiki object
	OmegaWiki ow = new OmegaWiki(dbConfig_ow);
	//Retrieve all senses for the English word "table
	Set<DefinedMeaning> meanings = ow.getDefinedMeaningByWord("table", ow_language);
	//For all senses...
	for(DefinedMeaning dm : meanings)
	{
		//Retrieve the English definitions
		Set<TranslatedContent> glosses = dm.getGlosses(ow_language);
		for (TranslatedContent tc : glosses)
		{
			System.out.println("Definiton: "+tc.getGloss());
		}
		//Retrieve the translation for all languages
		Set<SynTrans> translations = dm.getSynTranses();
		for (SynTrans st :translations)
		{
			System.out.println(OWLanguage.getName(st.getSyntrans().getLanguageId()) + " translation: "+ st.getSyntrans().getSpelling());
		}
		//Retrieve relations to other senses
		Map<DefinedMeaning,Integer> links = dm.getDefinedMeaningLinksAll();
		for (DefinedMeaning dm_target : links.keySet())
		{
			System.out.println(DefinedMeaningLinkType.getName(links.get(dm_target))+" relation with target "+ dm_target.getSpelling());
		}
	}
