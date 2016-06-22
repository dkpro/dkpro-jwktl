---
layout: page-fullwidth
title: "Getting started"
permalink: "/documentation/getting-started/"
---

Installing JWKTL
----------------

Once you have downloaded the Wiktionary dump file, set up a Java project and include JWKTL in your classpath. You also need to download the Oracle Berkeley DB Java Edition (version 4.0.92 or higher) from http://www.oracle.com/technology/software/products/berkeley-db/je/index.html and add the corresponding JAR to your classpath. Please have a look at Oracle's documentation to find out how this works.


Obtaining Wiktionary data
-------------------------

The first step towards using JWKTL is obtaining the actual Wiktionary data. JWKTL is able to process the XML dump files containing the wiki markup of the Wiktionary articles. These dump files are released by the Wikimedia foundation under a free license. Choose the Wiktionary language edition and data from the download page at http://dumps.wikimedia.org/backup-index.html (e.g., "enwiktionary" for the English Wiktionary) and save the corresponding `pages-articles.xml` dump (i.e., the dump containing at least "articles, templates, media/file descriptions, and primary meta-pages") to your hard disk.


Parsing the data
----------------

Before JWKTL is ready to use, you need to parse the obtaining Wiktionary dump file. The rationale behind this is to get in a position to efficiently access the Wiktionary data within a productive application environment by separating out all preparatory matters in a parsing step. In this step, the wiki syntax is being parsed by JWKTL and stored in a Berkeley DB. The parsing methods are based on text mining methods, which  obviously require some computation time. This is, however, a one-time effort. The resulting database can then be repeatedly and quickly accessed, as discussed in the next section.

To achieve that, create a new Java project and add JWKTL to your classpath as described in the first section. Create a new class and run the parser using the following sample code:

{% highlight java %}
public static void main(String[] args) throws Exception {
  File dumpFile = new File(PATH_TO_DUMP_FILE);
  File outputDirectory = new File(TARGET_DIRECTORY);
  boolean overwriteExisting = OVERWRITE_EXISTING_FILES;
    
  JWKTL.parseWiktionaryDump(dumpFile, outputDirectory, overwriteExisting);
}
{% endhighlight %}

Make sure to set the following parameters:
* `PATH_TO_DUMP_FILE`: The path to the Wiktionary dump file as downloaded in the preceding step. The dump file can be either an uncompressed XML file (fast) or a bz2 archive of the XML file (a bit slower).
* `TARGET_DIRECTORY`: The name of the output folder, where the parsed Wiktionary database should be placed (needs to be a valid directory).
* `OVERWRITE_EXISTING_FILES`: An existing Wiktionary database in the target directory will only be overwritten if this parameter is set to `true`.

If you everything worked fine until now, run your code and get yourself a coffee -- parsing a dump file usually requires between five minutes and two hours, depending on the language edition and your hardware. It might be necessary to grant some additional heap space to the Java virtual machine. Use the `Xmx` parameter to to that (e.g., `java -Xmx1200m ...`). Please refer to the Java documentation for more information. Once the parsing process has terminated, you should find a number of Berkeley DB files in your target directory. This is your parsed Wiktionary data.


Accessing the data
------------------

Accessing the parsed Wiktionary data is straightforward: Setup a JWKTL database connection and start querying the data you need. The basic code is:

{% highlight java %}
  // Connect to the Wiktionary database.
  File wiktionaryDirectory = new File(TARGET_DIRECTORY);
  IWiktionaryEdition wkt = JWKTL.openEdition(wiktionaryDirectory);

  //TODO: Query the data you need.

  // Close the database connection.
  wkt.close();
{% endhighlight %}

where `TARGET_DIRECTORY` is the directory containing the parsed Wiktionary data described in the previous section. If you're able to run this piece of code, you're ready to use JWKTL although nothing has happened (visually) so far.

Using the JWKTL database connection, you can access the individual information types encoded in Wiktionary. Learn more about how to do that by taking a look at 
* the [JWKTL architecture](/dkpro-jwktl/documentation/architecture/) overview,
* our selection of [JWKTL use cases](/dkpro-jwktl/documentation/use-cases/), and
* the Javadoc documentation.
