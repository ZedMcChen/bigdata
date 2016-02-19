/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citationmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * @author zhiming
 *
 */
@Configuration
@EnableMongoRepositories(basePackages="com.zhimingchen.citationmvc.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "citation";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient();
    }

}
