/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.counter.deduplicate;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.zhimingchen.common.utils.CliParser;
import com.zhimingchen.counter.parser.WritableLogRecord;

/**
 * @author zhiming
 *
 */
public class LogRecordParser extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        
        args = new GenericOptionsParser(conf, args).getRemainingArgs();
        CliParser cliParser = new CliParser(args);
        
        Job job = Job.getInstance(conf);
        job.setJobName("Counter log parser");
        job.setJarByClass(getClass());
        
        job.setMapperClass(LogRecordMapper.class);
        job.setReducerClass(LogRecordReducer.class);
        
        job.setPartitionerClass(LogRecordPartitioner.class);
        job.setGroupingComparatorClass(SessionGroupingComparator.class);
        job.setSortComparatorClass(SessionTimeSortComparator.class);
        
        job.setMapOutputKeyClass(SessionTimePair.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(WritableLogRecord.class);
        
        FileInputFormat.addInputPath(job, new Path(cliParser.getInputDir()));
        FileOutputFormat.setOutputPath(job, new Path(cliParser.getOutputDir()));
        
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        try {
            int exitCode = ToolRunner.run(new LogRecordParser(), args);
            System.out.println("All done");
            System.exit(exitCode);
        } catch (IllegalArgumentException e) {
            System.exit(-1);
        }
    }

}
