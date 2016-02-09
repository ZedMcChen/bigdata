/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.weblog.parsers.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhiming
 *
 */
public class LogEntry {
    private String userIp;
    private String remoteLogname;
    private String remoteUser;
    private String dateTimeString;
    private String requestMethod;
    private String requestUrl;
    private String protocolVersion;
    private String responseStatus;
    private String byteCount;
    private String refererUrl;
    private String userAgent;

    private String cookieString;

    private boolean good;

    protected LogEntry() {}

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

    // ip         -      -       [datetime -offset]              "request"                        code    length  "referrer"  "browser     "cookies"   other stuff
    private static String logEntryPattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"([^ \"]+) ([^ \"]+) ([^ \"]+)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\" \"([^\"]+)\".*?";

    private static Pattern pattern = Pattern.compile(logEntryPattern);

    public static LogEntry parse(String line) {
        Matcher matcher = pattern.matcher(line.replace("\\\"", ""));
        LogEntry entry = new LogEntry();
        if (matcher.matches()) {
            entry.userIp = matcher.group(1);
            entry.remoteLogname = matcher.group(2);
            entry.remoteUser = matcher.group(3);
            entry.dateTimeString = matcher.group(4);
            entry.requestMethod = matcher.group(5);
            entry.requestUrl = matcher.group(6);
            entry.protocolVersion = matcher.group(7);
            entry.responseStatus = matcher.group(8);
            entry.byteCount = matcher.group(9);
            entry.refererUrl = matcher.group(10);
            entry.userAgent = matcher.group(11);
            entry.cookieString = matcher.group(12);
            entry.good = true;

        } else {
            entry.good = false;
        }
        return entry;
    }
}

