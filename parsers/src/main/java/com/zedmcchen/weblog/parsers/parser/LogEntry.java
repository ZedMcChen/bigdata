/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.weblog.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhiming
 *
 */
public class LogEntry {
	    private String userIp;
	    private String dateAndTime;
	    private String request;
	    private String statusCode;
	    private String contentLength;
	    private String referer;
	    private String userAgent;
	    private String cookieString;
	    
	    private boolean good;
	    
	    private LogEntry() {}
	    
		public String getUserIp() {
			return this.userIp;
		}

		public String getDateAndTime() {
			return this.dateAndTime;
		}

		public String getRequest() {
			return this.request;
		}

		public String getStatusCode() {
			return this.statusCode;
		}

		public String getContentLength() {
			return this.contentLength;
		}

		public String getReferer() {
			return this.referer;
		}

		public String getUserAgent() {
			return this.userAgent;
		}

		public String getCookieString() {
			return this.cookieString;
		}

		public boolean isGood() {
			return good;
		}

		// ip         -      -       [datetime -offset]              "request" code    length  "referrer"  "browser     "cookies"   other stuff
        private static String logEntryPattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\" \"([^\"]+)\".*?";

        private static Pattern pattern = Pattern.compile(logEntryPattern);

    public static LogEntry parse(String line) {
    	Matcher matcher = pattern.matcher(line.replace("\\\"", ""));
    	LogEntry entry = new LogEntry();
    	if (matcher.matches()) {
    		entry.userIp = matcher.group(1);
    		entry.dateAndTime = matcher.group(4);
    		entry.request = matcher.group(5);
    		entry.statusCode = matcher.group(6);
    		entry.contentLength = matcher.group(7);
    		entry.referer = matcher.group(8);
    		entry.userAgent = matcher.group(9);
    		entry.cookieString = matcher.group(10);
    		entry.good = true;

    	} else {
    		entry.good = false;
    	}
    	return entry;
    }
}

