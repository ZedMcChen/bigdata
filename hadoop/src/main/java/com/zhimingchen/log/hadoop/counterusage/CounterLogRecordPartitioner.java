/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.log.hadoop.counterusage;

import org.apache.hadoop.mapreduce.Partitioner;

import com.zhimingchen.log.hadoop.parser.WritableLogRecord;

/**
 * @author zhiming
 *
 */
public class CounterLogRecordPartitioner extends Partitioner<SessionTimePair, WritableLogRecord> {

    @Override
    public int getPartition(SessionTimePair stPair, WritableLogRecord logRecord, int numPartitions) {
        return stPair.getSessionId().hashCode() % numPartitions;
    }

}
