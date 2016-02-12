/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.log.parsers.utils;

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
    private String cookieToken = "JSESSIONID";
    private String[] targets;
    
    public CliParser(String[] args) {
        Options options = new Options();
        options.addOption("c", "count", true, "the number of result entries to display [default: 10]");
        options.addOption("d", "dimension", true, "the usage dimension to analyse, one of " 
                                               + "USER_IP, DATE, DAY, REQUEST_URL, REFERER_URL, RESPONSE_STATUS, BYTE_COUNT, COOKIE_TOKEN " 
                                               + " [default: USER_IP]");
        options.addOption("t", "token", true, "the cookie token to analyse [default: JSESSIONID]");
        options.addOption("h", "help", false, "show this help message");

        CommandLineParser parser = new DefaultParser();
        
        try {
            CommandLine line = parser.parse(options, args);
            
            if (line.hasOption("d")) {
                String dimension = line.getOptionValue("d");
                this.hitDimension = HitDimension.valueOf(dimension);
            }
            
            if (line.hasOption("t")) {
                this.cookieToken = line.getOptionValue("t");
            }
            
            if (line.hasOption("c")) {
                String countStr = line.getOptionValue("c");
                this.topHitCount = Integer.parseInt(countStr);
            }
            
            this.targets = line.getArgs();
            
            if (this.targets.length < 1) {
                throw new IllegalArgumentException("Not log file specified");
            }
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.setWidth(100);
            String jarName = JarLocator.findJar(this.getClass());
            formatter.printHelp("java -jar " + jarName + " [options] <gzipped log file>\n Options:", options);
            throw new IllegalArgumentException(e);
        }
    }
    
    public HitDimension getHitDimension() {
        return this.hitDimension;
    }

    public int getTopHitCount() {
        return this.topHitCount;
    }
    
    public String getCookieToken() {
        return this.cookieToken;
    }
    
    public String[] getTargets() {
        return this.targets;
    }
}
