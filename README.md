
This repo contains java programs for log analysis. 
The current implementation is for Apache extended logs.
However, extension to other log formats is straight forward.

logparsers: Command line tool to parse logs and obtain top hit counts.
            
	    The tool is implemented using Java 8 streams. To use, create the jar file, 
	    then run the java program, as in
	    
            $ mvn package
            $ java -jar logparsers-0.1-SNAPSHOT-jar-with-dependencies.jar
         

counter:    Log record deduplication based on COUNTER (http://www.projectcounter.org/)
            usage guidelines. 
	    
	    The guidelines stipulate that repeated requests for
	    the same URLs within a 30 second moving window in a user session must be
	    discounted. This can be technical challenging especially if access logs come
	    from distributed web servers without central logging.
	    
	    The implementation is in Hadoop. The key is obviously session ID. However, 
	    a secondary key (epoch seconds of the request times) is used to sort requests
	    in chronological order for the deduplication operation.
	    
	    Epoch time is used for sorting to cater for distributed web servers and other
	    time changes such as summer times.
	    
	    
citation:   A tool that parses Common Crawl data set (https://commoncrawl.org/) to 
            extract citation data. 
	    
	    The extracted data are dumped directly into MongoDB.
	    
	    The implementation here uses Hadoop, the reducer can be redundant, it is 
	    here to generate some processing stats only.
	    
	    Web pages are parsed to extract document identities that match the specified 
	    DOI patterns (in Java class DoiPattern). The real world DOI patterns are much 
	    much more complex and it may be necessary to have a lookup table to ensure 
	    that extracted IDs are really DOIs. A popular lookup implementation is to 
	    use a set of bloomfilters.
	    
	    The MongoDB connector for Hadoop 
	    (https://docs.mongodb.org/ecosystem/tools/hadoop/) 
	    is not used here for it uses the old Hadoop API. Rather, MongoDB Java 
	    connector is used. In this demo, a helper class DbConnector is employed. 
	    In a production environment, the connector should be retrieved through, 
	    e.g. JNDI or Spring framework.
	    
	    
 

