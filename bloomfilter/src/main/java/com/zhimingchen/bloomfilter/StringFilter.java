/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.bloomfilter;

import org.apache.hadoop.util.bloom.BloomFilter;
import org.apache.hadoop.util.bloom.Key;
import org.apache.hadoop.util.hash.Hash;

/**
 * @author zhiming
 *
 */
public class StringFilter {
    private final double ln2 = Math.log(2);
    
    private BloomFilter bloomFilter;
    
    private final int    dataSetSize;
    private final double falsePositiveProbability;
    
    private int vectorSize;
    private int numberHashFunction;
    
    public StringFilter(int dataSetSize, double falsePositiveProbability) {
        this.dataSetSize = dataSetSize;
        this.falsePositiveProbability = falsePositiveProbability;
        
        double probabilityLogValue = -Math.log(falsePositiveProbability);
        this.vectorSize = ((int) (this.dataSetSize * probabilityLogValue / (ln2 * ln2))) + 1;
        this.numberHashFunction = ((int) (probabilityLogValue / ln2)) + 1;
        this.bloomFilter = new BloomFilter(this.vectorSize, this.numberHashFunction, Hash.MURMUR_HASH);
    }
    
    public boolean probablyContains(Key key) {
        return bloomFilter.membershipTest(key);
    }

    public boolean probablyContains(String keyString) {
        Key key = new Key();
        key.set(keyString.getBytes(), 1.0);
        return probablyContains(key);
    }

    public int getDataSetSize() {
        return this.dataSetSize;
    }
    
    public double getFalsePositiveProbability() {
        return this.falsePositiveProbability;
    }
    
    public BloomFilter get() {
        return this.bloomFilter;
    }
    
    public void add(Key key) {
        this.bloomFilter.add(key);
    }
    
    public void add(String keyString) {
        Key key = new Key(keyString.getBytes());
        this.bloomFilter.add(key);
    }
    
}
