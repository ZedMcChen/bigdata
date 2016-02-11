/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.log.hadoop.counterusage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.zhimingchen.log.hadoop.parser.WritableLogRecord;

/**
 * @author zhiming
 *
 */
public class CounterLogRecordReducer extends Reducer<SessionTimePair, WritableLogRecord, Text, WritableLogRecord> {
    @Override
    public void reduce(SessionTimePair stp, Iterable<WritableLogRecord> logRecords, Context context) throws IOException, InterruptedException {
        Map<String, Long> urlLastRequestTime = new HashMap<>();
        
        for (WritableLogRecord record: logRecords) {
            String requestUrl = record.getRequestUrl();
            if (urlLastRequestTime.containsKey(requestUrl)) {
                long elapsedTime = record.getEpochSeconds() - urlLastRequestTime.get(requestUrl);
                if (elapsedTime > 30) {
                    context.write(new Text(stp.getSessionId()), new WritableLogRecord(record));
                }
            } else {
                context.write(new Text(stp.getSessionId()), new WritableLogRecord(record));
            }
            urlLastRequestTime.put(record.getRequestUrl(), record.getEpochSeconds());
        }
    }
}
