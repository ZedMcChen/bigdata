/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.common.utils;

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
    private String inputDir;
    private String outputDir;
    
    public CliParser(String[] args) {
        Options options = new Options();
        options.addOption("i", "input Directory", true, "the input directory containing log files");
        options.addOption("o", "input Directory", true, "the output directory to hold processed data");
        options.addOption("h", "help", false, "show this help message");

        CommandLineParser parser = new DefaultParser();
        
        try {
            CommandLine line = parser.parse(options, args);
            
            if (line.hasOption("i")) {
                this.inputDir = line.getOptionValue("i");
            } else {
                throw new ParseException("");
            }
            
            if (line.hasOption("o")) {
                this.outputDir = line.getOptionValue("o");
            } else {
                throw new ParseException("");
            }
            
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.setWidth(100);
            String jarName = JarLocator.findJar(this.getClass());
            formatter.printHelp("java -jar " + jarName + " [options] \n Options:", options);
            throw new IllegalArgumentException(e);
        }
    }

    public String getInputDir() {
        return inputDir;
    }

    public String getOutputDir() {
        return outputDir;
    }
    

}
