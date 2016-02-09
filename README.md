Copyright Zhiming Chen 2016

This repo contains java programs for log analysis. 
The current implementation is for Apache extended logs.
However, extension to other log formats is straight forward.

parsers: command line tool to parse logs and obtain top hit counts.
		 The tool is implemented using Java 8 streams.
		 To use, create the jar file, then run the java program, as in
		 $ mvn package
		 $ java -jar parsers-0.1-SNAPSHOT-jar-with-dependencies.jar
		 



