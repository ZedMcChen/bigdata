/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.weblog.parsers.tophits;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.zedmcchen.weblog.parsers.parser.LogEntry;
import com.zedmcchen.weblog.parsers.parser.LogRecord;
import com.zedmcchen.weblog.parsers.utils.CliParser;
import com.zedmcchen.weblog.parsers.utils.GzipFiles;
import com.zedmcchen.weblog.parsers.utils.HitDimension;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;;

/**
 * @author zhiming
 *
 */
public class TopHit {
	
	public static void main(String[] args) {
		
		CliParser cliParser = new CliParser(args);
		
		String logFile = (cliParser.getTargets())[0];
		
		HitDimension hitDimension = cliParser.getHitDimension();
		int          topHitCount = cliParser.getTopHitCount();
		
		List<Map.Entry<String, Long>> topHits = null;
		
		switch (hitDimension) {
			case USER_IP:
				topHits = findTopHits(logFile, LogEntry::getUserIp, topHitCount);
				break;
				
			case REFERER:
				topHits = findTopHits(logFile, LogEntry::getReferer, (l) -> !l.equals("-"), topHitCount);
				break;
				
			default:
				System.err.println("Unrecognised hit dimension \"" + hitDimension + "\" requested");
				System.exit(-1);
		}
		if (topHits != null)
		    System.out.println(topHits);
	}

	private static List<Entry<String, Long>> findTopHits(String logFile, Function<? super LogEntry, String> mapper, int topHitCount) {
		return GzipFiles.lines(Paths.get(logFile))
		.parallel()
		.map(LogEntry::parse)
		.filter(LogEntry::isGood)
		.map(mapper)
		.collect(groupingBy(Function.identity(), counting()))
		.entrySet()
		.stream()
		.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
		.limit(topHitCount)
		.collect(Collectors.toList());
	}

	private static List<Entry<String, Long>> findTopHits(String logFile, Function<? super LogEntry, String> mapper, 
			                                 Predicate<? super String> predicate, int topHitCount) {
		return GzipFiles.lines(Paths.get(logFile))
		.parallel()
		.map(LogEntry::parse)
		.filter(LogEntry::isGood)
		.map(mapper)
		.filter(predicate)
		.collect(groupingBy(Function.identity(), counting()))
		.entrySet()
		.stream()
		.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
		.limit(topHitCount)
		.collect(Collectors.toList());
	}
}
