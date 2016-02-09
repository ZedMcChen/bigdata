package com.zedmcchen.log.parsers.parser;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LogRecordTest {

    @Test
    public void shouldCreateLogRecordFromString() {
        String logLine = "198.50.157.18 - - [09/Jul/2013:22:59:59 -0400] \"GET /store/10.1002/div.3783/asset/3783_ftp.pdf HTTP/1.1\" "
                       + "200 38057 \"http://google.com/search\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0\" "
                       + "\"JSESSIONID=sessionID; utc=data\"";
        
        LogRecord logRecord = LogRecord.parse(logLine);
        
        assertTrue(logRecord.isGood());
        assertThat(logRecord.getUserIp(), is("198.50.157.18"));
        assertThat(logRecord.getDateTimeString(), is("09/Jul/2013:22:59:59 -0400"));
        assertThat(logRecord.getDate(), is("2013-07-09"));
        assertThat(logRecord.getDay(), is("TUESDAY"));
        assertThat(logRecord.getRequestMethod(), is("GET"));
        assertThat(logRecord.getRequestUrl(), is("/store/10.1002/div.3783/asset/3783_ftp.pdf"));
        assertThat(logRecord.getResponseStatus(), is("200"));
        assertThat(logRecord.getContentLength(), is(38057));
        assertThat(logRecord.getRefererUrl(), is("http://google.com/search"));
        assertThat(logRecord.getUserAgent(), is("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0"));
        assertThat(logRecord.getCookies().size(), is(2));
        assertThat(logRecord.getCookie("JSESSIONID"), is("sessionID"));
        assertThat(logRecord.getCookie("utc"), is("data"));
        assertThat(logRecord.getCookie("no_existent"), is("COOKIE_NOT_FOUND"));
        assertThat(logRecord.toString(), is(logLine));
    }
}
