/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.martinkl.warc.mapreduce.WARCInputFormat;
import com.zhimingchen.common.utils.CliParser;

/**
 * @author zhiming
 *
 */
public class CitationDriver extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        args = new GenericOptionsParser(conf, args).getRemainingArgs();
        
        CliParser cliParser = new CliParser(args);
        
        conf.set("db.host", "localhost");
        conf.set("db.port", "27017");
        conf.set("db.database.name", "citation");
        conf.set("db.collection.name", "citedDois");

        Job job = Job.getInstance(conf);
        job.setJobName("Citation Driver");
        job.setJarByClass(getClass());
        
        job.setNumReduceTasks(0);
        job.setMapperClass(CommonCrawlMapper.class);
        job.setReducerClass(CitationReducer.class);
        job.setInputFormatClass(WARCInputFormat.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
 
        FileInputFormat.addInputPath(job, new Path(cliParser.getInputDir()));
        FileOutputFormat.setOutputPath(job, new Path(cliParser.getOutputDir()));
        
        return job.waitForCompletion(true) ? 0: 1;
    }

    public static void main(String[] args) throws Exception {
        try {
            int exitCode = ToolRunner.run(new CitationDriver(), args);
            System.out.println("All Done");
            System.exit(exitCode);
        } catch (IllegalArgumentException e) {
            System.exit(-1);
        }
        
    }

}
