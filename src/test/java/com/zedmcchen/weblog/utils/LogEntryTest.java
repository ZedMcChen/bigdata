package com.zedmcchen.weblog.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

public class LogEntryTest {

	@Test
	public void shouldCreateLogEntryFromString() {
		String logLine = "198.50.157.18 - - [09/Jul/2013:01:59:59 -0400] \"GET /store/10.1002/div.3783/asset/3783_ftp.pdf HTTP/1.1\" "
				       + "200 38057 \"http://google.com/search\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0\" "
				       + "\"JSESSIONID=sessionID; utc=data\"";
		
		LogEntry logEntry = LogEntry.parse(logLine);
		
		assertThat(logEntry.getUserIp(), is("198.50.157.18"));
		assertThat(logEntry.getDateAndTime(), is("09/Jul/2013:01:59:59 -0400"));
		assertThat(logEntry.getRequest(), is("GET /store/10.1002/div.3783/asset/3783_ftp.pdf HTTP/1.1"));
		assertThat(logEntry.getStatusCode(), is("200"));
		assertThat(logEntry.getContentLength(), is("38057"));
		assertThat(logEntry.getReferer(), is("http://google.com/search"));
		assertThat(logEntry.getUserAgent(), is("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0"));
		assertThat(logEntry.getCookieString(), is("JSESSIONID=sessionID; utc=data"));
	}

	@Test
	public void shouldParseApacheServerLog() throws URISyntaxException {
		String filename = "testdata/access_10.txt.gz";
		Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
		
		long count = GzipFiles.lines(path)
				 .map(LogEntry::parse)
		         .filter(LogEntry::isGood)
		         .count();
		
		assertThat(count, is(10L));
	}
}
