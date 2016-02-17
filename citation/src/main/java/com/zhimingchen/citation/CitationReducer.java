/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citation;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author zhiming
 *
 */
public class CitationReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value: values) {
            sum = sum + value.get();
        }
        context.write(key, new IntWritable(sum));
    }
}
