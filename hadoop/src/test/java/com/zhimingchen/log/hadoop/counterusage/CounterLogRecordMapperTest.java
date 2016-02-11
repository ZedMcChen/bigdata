package com.zhimingchen.log.hadoop.counterusage;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import com.zhimingchen.log.hadoop.counterusage.CounterLogRecordMapper;
import com.zhimingchen.log.hadoop.parser.WritableLogRecord;

public class CounterLogRecordMapperTest {
    private String logLine1 = "198.50.157.18 - - [09/Jul/2013:01:59:59 -0400] \"GET /store/10.1002/div.3783/asset/3783_ftp.pdf HTTP/1.1\" "
            + "200 38057 \"http://google.com/search\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0\" "
            + "\"JSESSIONID=sessionID; utc=data\"";
    
    private String logLine2 = "198.50.157.18 - - [09/Jul/2013:01:59:59 -0400] \"GET /store/10.1002/div.3783/asset/3783_ftp.pdf HTTP/1.1\" "
            + "200 38057 ";
    
    private String logLine3 = "198.50.157.18 - - [09/Jul/2013:01:59:59 -0400] \"GET /store/10.1002/div.3783/asset/3783_ftp.pdf HTTP/1.1\" "
            + "200 38057 \"http://google.com/search\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0\" "
            + "\"utc=data\"";
    
    private WritableLogRecord logRecord1;
    private WritableLogRecord logRecord3;

    @Before
    public void setup() {
        this.logRecord1 = WritableLogRecord.parse(logLine1);
        this.logRecord3 = WritableLogRecord.parse(logLine3);
    }

    @Test
    public void shouldMapLogRecordCorrectly() throws IOException {
        new MapDriver<LongWritable, Text, SessionTimePair, WritableLogRecord>()
                .withMapper(new CounterLogRecordMapper())
                .withInput(new LongWritable(0L), new Text(logLine1))
                .withInput(new LongWritable(0L), new Text(logLine2))
                .withInput(new LongWritable(0L), new Text(logLine3))
                .withOutput(new SessionTimePair("sessionID", logRecord1.getEpochSeconds()), logRecord1)
                .withOutput(new SessionTimePair("198.50.157.18", logRecord3.getEpochSeconds()), logRecord3)
                .runTest();
    }
}
