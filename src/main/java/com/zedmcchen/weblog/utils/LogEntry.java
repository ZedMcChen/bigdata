/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.weblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhiming
 *
 */
public class LogEntry {
    											// ip         -      -       [datetime -offset]            "request" code    length  "referrer"  "browser" 
		//private static String logEntryPattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"";
	                                            // ip         -      -       [datetime -offset]            "request" code    length  "referrer"  "browser      seconds milis  forwardedtos" 
        //private static String logEntryPattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\" (\\d+) (\\d+) \"([^\"]+)\"";

        private static String logEntryPattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\" \"([^\"]+)\" (.+?)";

        private static Pattern pattern = Pattern.compile(logEntryPattern);

    public static Integer parse(String line) {
    	Matcher matcher = pattern.matcher(line.replace("\\\"", ""));
    	if (matcher.matches()) {
    		System.out.println("****" + line);
    	    System.out.println("IP Address: " + matcher.group(1));
    	    System.out.println("Date&Time: " + matcher.group(4));
    	    System.out.println("Request: " + matcher.group(5));
    	    System.out.println("Response: " + matcher.group(6));
    	    System.out.println("Bytes Sent: " + matcher.group(7));
    	    if (!matcher.group(8).equals("-"))
    	      System.out.println("Referer: " + matcher.group(8));
    	    System.out.println("Browser: " + matcher.group(9));
    	    if (!matcher.group(10).equals("-"))
      	      System.out.println("Cookies: " + matcher.group(10));

    		return 1;
    	} else {
    		System.out.println("----" + line);
    		return 0;
    	}
    }
}

