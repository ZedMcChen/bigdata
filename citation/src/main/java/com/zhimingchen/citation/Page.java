/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citation;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.Writable;

/**
 * @author zhiming
 *
 */
public class Page implements Writable {
    private String      url;
    private String      content;
    private Set<String> citedDois;

    public Page() {}

    public Page(String url, String content, Set<String> citedDois) {
        this.url = url;
        this.content = content;
        this.citedDois = citedDois;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getCitedDois() {
        return this.citedDois;
    }

    public void setCitedDois(Set<String> citedDois) {
        this.citedDois = new HashSet<>();
        this.citedDois.addAll(citedDois);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.url);
        out.writeUTF(this.content);
        if (this.citedDois != null) {
            out.writeInt(this.citedDois.size());
            for (String doi: this.citedDois) {
                out.writeUTF(doi);
            }
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.url = in.readUTF();
        this.content = in.readUTF();
        int sz = in.readInt();
        this.citedDois = new HashSet<String>();
        for (int i=0; i<sz; i++) {
            String doi = in.readUTF();
            this.citedDois.add(doi);
        }
    }
}
