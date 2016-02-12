/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.counter.deduplicate;

import org.apache.hadoop.mapreduce.Partitioner;

import com.zhimingchen.counter.parser.WritableLogRecord;

/**
 * @author zhiming
 *
 */
public class LogRecordPartitioner extends Partitioner<SessionTimePair, WritableLogRecord> {

    @Override
    public int getPartition(SessionTimePair stPair, WritableLogRecord logRecord, int numPartitions) {
        return stPair.getSessionId().hashCode() % numPartitions;
    }

}
