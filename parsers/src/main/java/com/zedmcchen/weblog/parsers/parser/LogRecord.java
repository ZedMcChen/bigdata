/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.weblog.parsers.parser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhiming
 *
 */
public class LogRecord {
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss XX");

	private String  userIp;
    private ZonedDateTime    dateTime;
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
	public ZonedDateTime getDateTime() {
		return dateTime;
	}
	
	public String getDate () {
		return dateTime.toLocalDate().toString();
	}
	
	public String getDay() {
		return dateTime.getDayOfWeek().toString();
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
    
    public static LogRecord parse(LogEntry logEntry) {
    	
    	LogRecord logRecord = new LogRecord();
        logRecord.userIp = logEntry.getUserIp();

        logRecord.dateTime = ZonedDateTime.parse(logEntry.getDateAndTime(), dateTimeFormatter);
        
        String[] substr = logEntry.getRequest().split("\\s+");
        logRecord.requestMethod = substr[0];
        logRecord.requestURL = substr[1];
        
        logRecord.statusCode = Integer.parseInt(logEntry.getStatus());
        logRecord.contentLength = Integer.parseInt(logEntry.getContentLength());
        
        logRecord.referer = logEntry.getReferer();
        logRecord.userAgent = logEntry.getUserAgent();
        
        logRecord.cookies = Cookies.parse(logEntry.getCookieString());
        return logRecord;
    }
}
