package com.zhimingchen.log.hadoop.counterusage;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import com.zhimingchen.log.hadoop.counterusage.CounterLogRecordReducer;
import com.zhimingchen.log.hadoop.parser.WritableLogRecord;

public class CounterLogRecordReducerTest {
    private String logLine1 = "198.50.157.18 - - [09/Jul/2013:01:59:59 -0400] \"GET /store/10.1002/div.3783/asset/3783_ftp.pdf HTTP/1.1\" "
            + "200 38057 \"http://google.com/search\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0\" "
            + "\"JSESSIONID=sessionID; utc=data\"";
    
    private String logLine2 = "198.50.157.18 - - [09/Jul/2013:02:59:59 -0400] \"GET /store/10.1002/div.3783/asset/3783_ftp.pdf HTTP/1.1\" "
            + "200 38057 \"http://google.com/search\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0\" "
            + "\"JSESSIONID=sessionID; utc=data\"";
    
    private WritableLogRecord logRecord1;
    private WritableLogRecord logRecord2;

    @Before
    public void setup() {
        this.logRecord1 = WritableLogRecord.parse(logLine1);
        this.logRecord2 = WritableLogRecord.parse(logLine2);
    }

    @Test
    public void shouldReduceLogRecordCorrectly() throws IOException {
        new ReduceDriver<SessionTimePair, WritableLogRecord, Text, WritableLogRecord>()
                .withReducer(new CounterLogRecordReducer())
                .withInput(new SessionTimePair(logRecord1.getCookie("JSESSIONID"), logRecord1.getEpochSeconds()), Arrays.asList(WritableLogRecord.parse(logLine1), WritableLogRecord.parse(logLine2)))
                .withOutput(new Text("sessionID"), WritableLogRecord.parse(logLine1))
                .withOutput(new Text("sessionID"), WritableLogRecord.parse(logLine2))
                .runTest();
    }
}
