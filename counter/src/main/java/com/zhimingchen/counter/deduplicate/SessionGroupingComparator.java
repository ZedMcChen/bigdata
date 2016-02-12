/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.counter.deduplicate;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author zhiming
 *
 */
public class SessionGroupingComparator extends WritableComparator {
    protected SessionGroupingComparator() {
        super(SessionTimePair.class, true);
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public int compare(WritableComparable p1, WritableComparable p2) {
        SessionTimePair stp1 = (SessionTimePair) p1;
        SessionTimePair stp2 = (SessionTimePair) p2;
        
        return stp1.getSessionId().compareTo(stp2.getSessionId());
    }
}
