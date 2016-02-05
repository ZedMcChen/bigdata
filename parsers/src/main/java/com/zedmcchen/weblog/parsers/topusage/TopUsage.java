/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.weblog.parsers.topusage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.zedmcchen.weblog.parsers.parser.LogEntry;
import com.zedmcchen.weblog.parsers.utils.GzipFiles;


import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;;

/**
 * @author zhiming
 *
 */
public class TopUsage {
	public static void main(String[] args) {
		
		long maxSize = 10L;
		if (args.length!=2) {
			System.err.println("Usage: LogAnalysis <log file>");
			System.exit(-1);
		}
		
		String inFile = args[0];
		
		GzipFiles.lines(Paths.get(inFile))
			.parallel()
			.map(LogEntry::parse)
			.filter(LogEntry::isGood)
			.map(LogEntry::getUserIp)
			.collect(groupingBy(Function.identity(), counting()))
			.entrySet()
			.stream()
			.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
			.limit(maxSize);
					
	}

	private static void writeToFile(FileWriter fw, String ip, long count) {
		try {
			fw.write(ip + " : " + count + "\n");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static void writeToFile(FileWriter fw, String ip) {
		try {
			fw.write(ip + "\n");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
