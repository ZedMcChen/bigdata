package com.zedmcchen.weblog.parsers.utils;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CliParserTest {
	@Test
	public void shouldUseDefaultValuesForOptions() throws ParseException {
		CliParser parser = new CliParser(null);
		
		assertThat(parser.getHitDimension(), is(HitDimension.USER_IP));
		assertThat(parser.getTopHitCount(), is(10));
	}

	@Test
	public void shouldRetrieveOptionsFromCli() throws ParseException {
		
		CliParser parser = new CliParser(new String[] {"-d", "REQUEST_URL", "-c", "20"});
		
		assertThat(parser.getHitDimension(), is(HitDimension.REQUEST_URL));
		assertThat(parser.getTopHitCount(), is(20));
	}

	@Test
	public void shouldRetrieveOptionsFromCliWithTarget() throws ParseException {
		
		String[] args = new String[] {"-d", "REFERER", "-c", "30", "filename"};
		CliParser parser = new CliParser(args);
		
		assertThat(parser.getHitDimension(), is(HitDimension.REFERER));
		assertThat(parser.getTopHitCount(), is(30));
		assertThat(parser.getTargets(), is(new String[] {"filename"}));
	}

	@Test (expected=IllegalArgumentException.class)
	public void shouldFailWithWrongOption() throws ParseException {
		
		String[] args = new String[] {"-a", "IP", "-c", "30", "filename"};
		new CliParser(args);
	}

	@Test (expected=IllegalArgumentException.class)
	public void shouldFailWithWrongValue() throws ParseException {
		
		String[] args = new String[] {"-d", "url2", "-c", "30", "filename"};
		new CliParser(args);
	}
}
