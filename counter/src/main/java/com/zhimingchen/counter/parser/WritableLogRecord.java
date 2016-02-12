/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.counter.parser;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

import com.zhimingchen.log.parsers.parser.LogRecord;

/**
 * @author zhiming
 *
 */
public class WritableLogRecord extends LogRecord implements Writable {
    
    public WritableLogRecord() {}
    
    public WritableLogRecord(WritableLogRecord logRecord) {
        this.good = logRecord.good;
        this.userIp =  logRecord.userIp;
        this.remoteLogname =  logRecord.remoteLogname;
        this.remoteUser =  logRecord.remoteUser;
        this.dateTimeString =  logRecord.dateTimeString;
        this.requestMethod =  logRecord.requestMethod;
        this.requestUrl =  logRecord.requestUrl;
        this.protocolVersion =  logRecord.protocolVersion;
        this.responseStatus =  logRecord.responseStatus;
        this.byteCount =  logRecord.byteCount;
        this.refererUrl =  logRecord.refererUrl;
        this.userAgent =  logRecord.userAgent;
        this.cookieString =  logRecord.cookieString;
        
        processLogEntry();
    }
    
    protected WritableLogRecord(String logLine) {
        super(logLine);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeBoolean(this.good);
        if (this.good) {
            out.writeUTF(this.userIp);
            out.writeUTF(this.remoteLogname);
            out.writeUTF(this.remoteUser);
            out.writeUTF(this.dateTimeString);
            out.writeUTF(this.requestMethod);
            out.writeUTF(this.requestUrl);
            out.writeUTF(this.protocolVersion);
            out.writeUTF(this.responseStatus);
            out.writeUTF(this.byteCount);
            out.writeUTF(this.refererUrl);
            out.writeUTF(this.userAgent);
            out.writeUTF(this.cookieString);
        }        
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.good = in.readBoolean();
        if (this.good) {
            this.userIp = in.readUTF();
            this.remoteLogname = in.readUTF();
            this.remoteUser = in.readUTF();
            this.dateTimeString = in.readUTF();
            this.requestMethod = in.readUTF();
            this.requestUrl = in.readUTF();
            this.protocolVersion = in.readUTF();
            this.responseStatus = in.readUTF();
            this.byteCount = in.readUTF();
            this.refererUrl = in.readUTF();
            this.userAgent = in.readUTF();
            this.cookieString = in.readUTF();
            
            processLogEntry();
        }
    }
    
    public static WritableLogRecord parse(String logLine) {
        return new WritableLogRecord(logLine);
    }
}
