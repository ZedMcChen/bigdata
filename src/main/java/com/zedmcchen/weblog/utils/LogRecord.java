/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.weblog.utils;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhiming
 *
 */
public class LogRecord {
    private String  userIp;
    private long    epochSeconds;
    private String  requestMethod;
    private String  requestURL;
    private int     statusCode;
    private int     contentLength;
    private String  referer;
    private String  userAgent;
    private Cookies cookies;

    private LogRecord() {}
    
	public String getUserIp() {
		return userIp;
	}
	public long getEpochSeconds() {
		return epochSeconds;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public String getRequestURL() {
		return requestURL;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public int getContentLength() {
		return contentLength;
	}
	public String getReferer() {
		return referer;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public Cookies getCookies() {
		return cookies;
	}
	public String getCookie(String name) {
		return cookies.getCookie(name);
	}
    
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss XX");
    public static LogRecord parse(LogEntry logEntry) {
    	
    	LogRecord logRecord = new LogRecord();
        logRecord.userIp = logEntry.getUserIp();

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(logEntry.getDateAndTime(), dateTimeFormatter);
        logRecord.epochSeconds = zonedDateTime.toEpochSecond();
        
        String[] substr = logEntry.getRequest().split("\\s+");
        logRecord.requestMethod = substr[0];
        logRecord.requestURL = substr[1];
        
        logRecord.statusCode = Integer.parseInt(logEntry.getStatusCode());
        logRecord.contentLength = Integer.parseInt(logEntry.getContentLength());
        
        logRecord.referer = logEntry.getReferer();
        logRecord.userAgent = logEntry.getUserAgent();
        
        logRecord.cookies = Cookies.parse(logEntry.getCookieString());
        return logRecord;
    }
}
