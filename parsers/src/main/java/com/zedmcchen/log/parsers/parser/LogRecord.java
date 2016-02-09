/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.log.parsers.parser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhiming
 *
 */
public class LogRecord extends LogEntry {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss XX");

    private LogEntry         logEntry;
    private ZonedDateTime    zonedDateTime;
    private int              contentLength;
    private Cookies          cookies;

    protected LogRecord() {}
    
    public ZonedDateTime getZonedDateTime() {
        return this.zonedDateTime;
    }
    
    public String getDate () {
        return this.zonedDateTime.toLocalDate().toString();
    }
    
    public String getDay() {
        return this.zonedDateTime.getDayOfWeek().toString();
    }
    
    public int getContentLength() {
        return this.contentLength;
    }
    
    public Cookies getCookies() {
        return this.cookies;
    }
    public String getCookie(String name) {
        return cookies.getCookie(name);
    }
    
    @Override
    public String getUserIp() {
        return this.logEntry.getUserIp();
    }

    @Override
    public String getDateTimeString() {
        return this.logEntry.getDateTimeString();
    }

    @Override
    public String getRequestMethod() {
        return this.logEntry.getRequestMethod();
    }

    @Override
    public String getRequestUrl() {
        return this.logEntry.getRequestUrl();
    }

    @Override
    public String getProtocolVersion() {
        return this.logEntry.getProtocolVersion();
    }

    @Override
    public String getResponseStatus() {
        return this.logEntry.getResponseStatus();
    }

    @Override
    public String getByteCount() {
        return this.logEntry.getByteCount();
    }

    @Override
    public String getRefererUrl() {
        return this.logEntry.getRefererUrl();
    }

    @Override
    public String getUserAgent() {
        return this.logEntry.getUserAgent();
    }

    @Override
    public String getCookieString() {
        return this.logEntry.getCookieString();
    }

    @Override
    public boolean isGood() {
        return this.logEntry.isGood();
    }
    
    @Override
    public String toString() {
        return this.logEntry.toString();
    }

    public static LogRecord parse(LogEntry logEntry) {
        
        LogRecord logRecord = new LogRecord();
        logRecord.logEntry = logEntry;
        
        if (logEntry.isGood()) {
            logRecord.zonedDateTime = ZonedDateTime.parse(logRecord.getDateTimeString(), dateTimeFormatter);
            logRecord.contentLength = Integer.parseInt(logRecord.getByteCount());
            logRecord.cookies = Cookies.parse(logRecord.getCookieString());
        }
        
        return logRecord;
    }

    public static LogRecord parse(String logLine) {
        
        LogEntry logEntry = LogEntry.parse(logLine);
        LogRecord logRecord = LogRecord.parse(logEntry);
        
        return logRecord;
    }
}
