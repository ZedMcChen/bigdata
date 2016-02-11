/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.log.hadoop.counterusage;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author zhiming
 *
 */
public class SessionTimeSortComparator extends WritableComparator {
    protected SessionTimeSortComparator() {
        super(SessionTimePair.class, true);
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public int compare(WritableComparable p1, WritableComparable p2) {
        SessionTimePair stp1 = (SessionTimePair) p1;
        SessionTimePair stp2 = (SessionTimePair) p2;
        
        int cmp = stp1.getSessionId().compareTo(stp2.getSessionId());
        
        if (cmp == 0) {
            return stp1.getEpochTime().compareTo(stp2.getEpochTime());
        }
        
        return cmp;
    }
}
