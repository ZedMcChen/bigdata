package com.zedmcchen.weblog.parsers.utils;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CliParserTest {
	@Test
	public void shouldUseDefaultValuesForOptions() throws ParseException {
		CliParser parser = new CliParser(null);
		
		assertThat(parser.getDimension(), is("ip"));
		assertThat(parser.getCount(), is(10));
	}

	@Test
	public void shouldRetrieveOptionsFromCli() throws ParseException {
		
		CliParser parser = new CliParser(new String[] {"-d", "url", "-c", "20"});
		
		assertThat(parser.getDimension(), is("url"));
		assertThat(parser.getCount(), is(20));
	}

	@Test
	public void shouldRetrieveOptionsFromCliWithTarget() throws ParseException {
		
		String[] args = new String[] {"-d", "url2", "-c", "30", "filename"};
		CliParser parser = new CliParser(args);
		
		assertThat(parser.getDimension(), is("url2"));
		assertThat(parser.getCount(), is(30));
		assertThat(parser.getTargets(), is(new String[] {"filename"}));
	}

	@Test (expected=IllegalArgumentException.class)
	public void shouldFailGettingCliOption() throws ParseException {
		
		String[] args = new String[] {"-a", "url2", "-c", "30", "filename"};
		CliParser parser = new CliParser(args);
	}
}
