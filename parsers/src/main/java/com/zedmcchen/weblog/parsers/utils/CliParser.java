/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.weblog.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author zhiming
 *
 */
public class CliParser {
	private String defaultDimension = "ip";
	private String    defaultCount = "10";
	private CommandLine line = null;
	
	public CliParser(String[] args) {
		Options options = new Options();
		options.addOption("h", "help", false, "show this help message");
		options.addOption("d", "dimension", true, "the usage dimension to analyse");
		options.addOption("c", "count", true, "the top number of result entries to display");

		CommandLineParser parser = new DefaultParser();
		
		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(this.getClass().getName() + " [options] [gzipped log file]\n Options:", options);
			throw new IllegalArgumentException(e);
		}
	}
	
	public String getDimension() {
		return line.getOptionValue("d", defaultDimension);
	}

	public int getCount() {
		return Integer.parseInt(line.getOptionValue("c", defaultCount));
	}
	
	public String[] getTargets() {
		return line.getArgs();
	}
}
