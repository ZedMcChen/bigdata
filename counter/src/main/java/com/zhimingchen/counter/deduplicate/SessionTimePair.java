/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.counter.deduplicate;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 * @author zhiming
 *
 */
public class SessionTimePair implements WritableComparable<SessionTimePair> {
    private Text         sessionId;
    private LongWritable epochTime;
    
    public SessionTimePair() {
        set(new Text(), new LongWritable(1L));
    }
    
    public SessionTimePair(String sessionId, long epochTime) {
        set(new Text(sessionId), new LongWritable(epochTime));
    }
    
    private void set(Text sessionId, LongWritable epochTime) {
        this.sessionId = sessionId;
        this.epochTime = epochTime;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.sessionId.write(out);
        this.epochTime.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.sessionId.readFields(in);
        this.epochTime.readFields(in);
    }

    @Override
    public int compareTo(SessionTimePair stPair) {
        int cmp = this.sessionId.compareTo(stPair.sessionId);
        
        if (cmp != 0) 
            return cmp;
        else
            return this.epochTime.compareTo(stPair.epochTime);
    }

    public Text getSessionId() {
        return sessionId;
    }

    public void setSessionId(Text sessionId) {
        this.sessionId = sessionId;
    }

    public LongWritable getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(LongWritable epochTime) {
        this.epochTime = epochTime;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof SessionTimePair) {
            SessionTimePair stPair = (SessionTimePair) o;
            
            return new EqualsBuilder().append(this.sessionId, stPair.sessionId)
                               .append(this.epochTime, stPair.epochTime)
                               .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.sessionId)
                             .append(this.epochTime)
                             .toHashCode();
    }
}
