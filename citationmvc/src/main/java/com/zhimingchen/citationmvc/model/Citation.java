/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citationmvc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author zhiming
 *
 */

@Document(collection="citedDois")
public class Citation {
    @Id
    private String id;
    
    private String doi;
    
    @Field("containingUrlHash")
    private String urlHash;
    
    @Field("containingUrl")
    private String url;

    public String getDoi() {
        return this.doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getUrlHash() {
        return this.urlHash;
    }

    public void setUrlHash(String urlHash) {
        this.urlHash = urlHash;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
