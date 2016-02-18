package com.zhimingchen.common.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import com.zhimingchen.common.utils.CliParser;

public class CliParserTest {
    @Test
    public void shouldRetrieveOptionsFromCli() throws ParseException {
        
        CliParser parser = new CliParser(new String[] {"-i", "input", "-o", "output"});
        
        assertThat(parser.getInputDir(), is("input"));
        assertThat(parser.getOutputDir(), is("output"));
    }

    @Test (expected=IllegalArgumentException.class)
    public void shouldFailWithNoOption() throws ParseException {
        
        String[] args = new String[] {""};
        new CliParser(args);
    }

    @Test (expected=IllegalArgumentException.class)
    public void shouldFailWithWrongOptionValue() throws ParseException {
        
        String[] args = new String[] {"-j", "input", "-o", "output"};
        new CliParser(args);
    }

}
