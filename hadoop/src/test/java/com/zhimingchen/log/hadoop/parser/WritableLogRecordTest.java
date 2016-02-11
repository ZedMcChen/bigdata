package com.zhimingchen.log.hadoop.parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class WritableLogRecordTest {
    private String logLine = "198.50.157.18 - - [09/Jul/2013:01:59:59 -0400] \"GET /store/10.1002/div.3783/asset/3783_ftp.pdf HTTP/1.1\" "
            + "200 38057 \"http://google.com/search\" \"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0\" "
            + "\"JSESSIONID=sessionID; utc=data\"";

    private WritableLogRecord logRecord;
    
    @Before
    public void setup() {
        this.logRecord = WritableLogRecord.parse(logLine);
    }
    
    @Test
    public void shouldWriteAndReadGoodRecordCorrectly() throws IOException {
        // save to a bytearrayoutput stream
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteStream);
        this.logRecord.write(dataOutputStream);
        dataOutputStream.close();
        
        // now reads back in
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteStream.toByteArray());
        DataInputStream dataInputStream = new DataInputStream(byteInputStream);
        WritableLogRecord newLogRecord = new WritableLogRecord();
        newLogRecord.readFields(dataInputStream);
        dataInputStream.close();
        
        assertThat(newLogRecord.toString(), is(logLine));
        assertThat(newLogRecord.getZonedDateTime(), is(this.logRecord.getZonedDateTime()));
        assertThat(newLogRecord.getContentLength(), is(this.logRecord.getContentLength()));
        assertThat(newLogRecord.getCookies(), is(this.logRecord.getCookies()));
    }

    @Test
    public void shouldWriteAndReadBadRecordCorrectly() throws IOException {
        WritableLogRecord badRecord = WritableLogRecord.parse("bad record");
        // save to a bytearrayoutput stream
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteStream);
        badRecord.write(dataOutputStream);
        dataOutputStream.close();
        
        // now reads back in
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteStream.toByteArray());
        DataInputStream dataInputStream = new DataInputStream(byteInputStream);
        WritableLogRecord newLogRecord = new WritableLogRecord();
        newLogRecord.readFields(dataInputStream);
        dataInputStream.close();
        
        assertFalse(newLogRecord.isGood());
    }
}
