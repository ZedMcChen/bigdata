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
                topHits = findTopHits(logFile, LogEntry::getRefererUrl, topHitCount);
                break;
                
            case DATE:
                topHits = findTopHits(logFile, LogRecord::getDate, topHitCount);
                break;
                
            case DAY:
                topHits = findTopHits(logFile, LogRecord::getDay, topHitCount);
                break;
                
            case STATUS:
                topHits = findTopHits(logFile, LogEntry::getResponseStatus, topHitCount);
                break;
            
            case COOKIE_TOKEN:
                topHits = findTopHits(logFile, log -> log.getCookie(cliParser.getCookieToken()), topHitCount);
                break;

            default:
                System.err.println("Unrecognised hit dimension \"" + hitDimension + "\" requested");
                System.exit(-1);
        }
        if (topHits != null) {
            topHits.forEach(e -> System.out.println(e.getValue() + ": " + e.getKey()));
        }
    }

    private static List<Entry<String, Long>> findTopHits(String logFile, Function<? super LogRecord, String> mapper, int topHitCount) {
        return GzipFiles.lines(Paths.get(logFile))
        .parallel()
        .map(LogEntry::parse)
        .filter(LogEntry::isGood)
        .map(LogRecord::parse)
        .map(mapper)
        .collect(groupingBy(Function.identity(), counting()))
        .entrySet()
        .stream()
        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
        .limit(topHitCount)
        .collect(Collectors.toList());
    }

}
