/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.log.parsers.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhiming
 *
 */
public class LogEntry {
    protected String userIp;
    protected String remoteLogname;
    protected String remoteUser;
    protected String dateTimeString;
    protected String requestMethod;
    protected String requestUrl;
    protected String protocolVersion;
    protected String responseStatus;
    protected String byteCount;
    protected String refererUrl;
    protected String userAgent;

    protected String cookieString;

    protected boolean good;

    protected LogEntry() {}

    protected LogEntry(String line) {
        Matcher matcher = pattern.matcher(line.replace("\\\"", ""));
        if (matcher.matches()) {
            this.userIp = matcher.group(1);
            this.remoteLogname = matcher.group(2);
            this.remoteUser = matcher.group(3);
            this.dateTimeString = matcher.group(4);
            this.requestMethod = matcher.group(5);
            this.requestUrl = matcher.group(6);
            this.protocolVersion = matcher.group(7);
            this.responseStatus = matcher.group(8);
            this.byteCount = matcher.group(9);
            this.refererUrl = matcher.group(10);
            this.userAgent = matcher.group(11);
            this.cookieString = matcher.group(12);
            this.good = true;

        } else {
            this.good = false;
        }
    }
    
    public String getUserIp() {
        return this.userIp;
    }

    public String getDateTimeString() {
        return this.dateTimeString;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public String getResponseStatus() {
        return this.responseStatus;
    }

    public String getByteCount() {
        return this.byteCount;
    }

    public String getRefererUrl() {
        return this.refererUrl;
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

    @Override
    public String toString() {
        return String.format("%s %s %s [%s] \"%s %s %s\" %s %s \"%s\" \"%s\" \"%s\"",
                this.userIp, this.remoteLogname, this.remoteUser, this.dateTimeString, 
                this.requestMethod, this.requestUrl, this.protocolVersion, this.responseStatus, this.byteCount,
                this.refererUrl, this.userAgent, this.cookieString);
    }

    private static String logEntryPattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"([^ \"]+) ([^ \"]+) ([^ \"]+)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\" \"([^\"]+)\".*?";

    private static Pattern pattern = Pattern.compile(logEntryPattern);

    public static LogEntry parse(String line) {
        return new LogEntry(line);
    }
}

