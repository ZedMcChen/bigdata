package com.zedmcchen.weblog.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.time.Instant;

public class LogRecordTest {

	@Test
	public void shouldCreateLogRecordFromString() {
		String logLine = "198.50.157.18 - - [09/Jul/2013:01:59:59 -0400] \"GET /store/10.1002/div.3783/asset/3783_ftp.pdf HTTP/1.1\" "
				       + "200 38057 \"http://google.com/search\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0\" "
				       + "\"JSESSIONID=sessionID; utc=data\"";
		
		LogEntry logEntry = LogEntry.parse(logLine);
		LogRecord logRecord = LogRecord.parse(logEntry);
		
		assertThat(logRecord.getUserIp(), is("198.50.157.18"));
		assertThat(logRecord.getEpochSeconds(), is(1373349599L));
		assertThat(logRecord.getRequestMethod(), is("GET"));
		assertThat(logRecord.getRequestURL(), is("/store/10.1002/div.3783/asset/3783_ftp.pdf"));
		assertThat(logRecord.getStatusCode(), is(200));
		assertThat(logRecord.getContentLength(), is(38057));
		assertThat(logRecord.getReferer(), is("http://google.com/search"));
		assertThat(logRecord.getUserAgent(), is("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0"));
		assertThat(logRecord.getCookies().size(), is(2));
		assertThat(logRecord.getCookie("JSESSIONID"), is("sessionID"));
		assertThat(logRecord.getCookie("utc"), is("data"));
	}
}
