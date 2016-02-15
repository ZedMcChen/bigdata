package com.zhimingchen.bloomfilter;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StringFilterTest {
    
    final int dataSetSize = 500000;
    final double falsePositiveProbability = 0.0001;
    
    private StringFilter stringFilter;
    
    @Before
    public void setup() {
        this.stringFilter = new StringFilter(this.dataSetSize, this.falsePositiveProbability);
        
        for (int i=0; i<this.dataSetSize; i++) {
            String keyString = String.format("%07d", 2*i);
            this.stringFilter.add(keyString);
        }
    }

    @Test
    public void shouldFindAllTruePositives() {
        int count = 0;
        for (int i=0; i<this.dataSetSize; i++) {
            String keyString = String.format("%07d",  2*i);
            if (this.stringFilter.probablyContains(keyString)) {
                count++;
            }
        }
        assertThat(count, is(this.dataSetSize));
    }

    @Test
    public void shouldHaveSpecifiedFalsePositiveProbability() {
        int count = 0;
        for (int i=0; i<this.dataSetSize; i++) {
            int j = 2 * i + 1;
            String keyString = String.format("%07d", j);
            if (this.stringFilter.probablyContains(keyString)) {
                count++;
            }
        }
        
        double actualProbability = (double) count / this.dataSetSize;
        double specifiedProbability = this.stringFilter.getFalsePositiveProbability();
        double diff = Math.abs(actualProbability - specifiedProbability);
        
        // Check the actual probability is within 10% of the specified probability
        assertTrue(diff / specifiedProbability < 0.1);
    }
}
