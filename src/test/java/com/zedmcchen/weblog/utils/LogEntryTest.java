package com.zedmcchen.weblog.utils;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

public class LogEntryTest {
	@Test
	public void shouldParseApacheServerLog() throws URISyntaxException {
		String filename = "testdata/access_10.txt.gz";
		Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
		
		GzipFiles.lines(path)
		         .mapToInt(LogEntry::parse)
		         .sum();
		
		assertTrue(false);
	}
}
