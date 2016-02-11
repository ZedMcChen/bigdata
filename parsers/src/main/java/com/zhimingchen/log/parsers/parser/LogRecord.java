/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.log.parsers.parser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhiming
 *
 */
public class LogRecord extends LogEntry {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss XX");

    private ZonedDateTime    zonedDateTime;
    private int              contentLength;
    private Cookies          cookies;

    protected LogRecord() {}
    
    protected LogRecord(LogEntry logEntry) {
        this.good = logEntry.good;
        if (this.good) {
            this.userIp = logEntry.userIp;
            this.remoteLogname = logEntry.remoteLogname;
            this.remoteUser = logEntry.remoteUser;
            this.dateTimeString = logEntry.dateTimeString;
            this.requestMethod = logEntry.requestMethod;
            this.requestUrl = logEntry.requestUrl;
            this.protocolVersion = logEntry.protocolVersion;
            this.responseStatus = logEntry.responseStatus;
            this.byteCount = logEntry.byteCount;
            this.refererUrl = logEntry.refererUrl;
            this.userAgent = logEntry.userAgent;
            this.cookieString = logEntry.cookieString;

            processLogEntry();

        }
    }
    protected LogRecord(String line) {
        super(line);
        processLogEntry();
    }

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
    
    private void processLogEntry() {
        if (this.isGood()) {
            this.zonedDateTime = ZonedDateTime.parse(this.getDateTimeString(), dateTimeFormatter);
            this.contentLength = Integer.parseInt(this.getByteCount());
            this.cookies = Cookies.parse(this.getCookieString());
        }
    }

    public static LogRecord parse(LogEntry logEntry) {
        return new LogRecord(logEntry);
    }

    public static LogRecord parse(String logLine) {
        return new LogRecord(logLine);
    }
}
