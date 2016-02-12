/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.counter.deduplicate;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.zhimingchen.counter.parser.WritableLogRecord;

/**
 * @author zhiming
 *
 */

// map to JSESSIONID:LogRecord, or IP:LogRecord if JSESSIONID is absent.
public class LogRecordMapper extends Mapper<LongWritable, Text, SessionTimePair, WritableLogRecord> {
    @Override
    public void map(LongWritable key, Text logLine, Context context) throws IOException, InterruptedException {
        WritableLogRecord logRecord = WritableLogRecord.parse(logLine.toString());
        if (logRecord.isGood()){
            String sessionCookie = logRecord.getCookie("JSESSIONID");
            if (sessionCookie == "COOKIE_NOT_FOUND") {
                sessionCookie = logRecord.getUserIp();
            }
            context.write(new SessionTimePair(sessionCookie, logRecord.getEpochSeconds()), logRecord);
        }
    }
}
