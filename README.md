
This repo contains java programs for log analysis. 
The current implementation is for Apache extended logs.
However, extension to other log formats is straight forward.

logparsers: command line tool to parse logs and obtain top hit counts.
            The tool is implemented using Java 8 streams.
            To use, create the jar file, then run the java program, as in
            $ mvn package
            $ java -jar parsers-0.1-SNAPSHOT-jar-with-dependencies.jar
         

counter:    Log record deduplication based on COUNTER (http://www.projectcounter.org/)
            usage guidelines. The guidelines stipulate that repeated requests for
	    the same URLs within a 30 second moving window in a user session must be
	    discounted. This can be technical challenging especially if access logs come
	    from distributed web servers without central logging.
	    
	    The implementation is in HADOOP. The key is obviously session ID. However, 
	    a secondary key (epoch seconds of the request times) is used to sort requests
	    in chronological order for the deduplication operation.
	    
	    Epoch time is used for sorting to cater for distributed web servers and other
	    time changes such as summer times.
	    
	    



