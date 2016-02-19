/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citationmvc.model;

/**
 * @author zhiming
 *
 */
public class CitationCount {
    private String doi;
    private int    count;

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
