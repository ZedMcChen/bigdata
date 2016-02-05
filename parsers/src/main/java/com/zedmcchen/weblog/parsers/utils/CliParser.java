/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.weblog.parsers.utils;

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
	private HitDimension hitDimension = HitDimension.USER_IP;
	private int topHitCount = 10;
	private String[] targets;
	
	public CliParser(String[] args) {
		Options options = new Options();
		options.addOption("h", "help", false, "show this help message");
		options.addOption("d", "dimension", true, "the usage dimension to analyse");
		options.addOption("c", "count", true, "the top number of result entries to display");

		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine line = parser.parse(options, args);
			
			if (line.hasOption("d")) {
				String dimension = line.getOptionValue("d");
				this.hitDimension = HitDimension.valueOf(dimension);
			}
			
			if (line.hasOption("c")) {
				String countStr = line.getOptionValue("c");
				this.topHitCount = Integer.parseInt(countStr);
			}
			
			this.targets = line.getArgs();
		} catch (ParseException | IllegalArgumentException | NullPointerException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(this.getClass().getName() + " [options] [gzipped log file]\n Options:", options);
			throw new IllegalArgumentException(e);
		}
	}
	
	public HitDimension getHitDimension() {
		return this.hitDimension;
	}

	public int getTopHitCount() {
		return this.topHitCount;
	}
	
	public String[] getTargets() {
		return this.targets;
	}
}
