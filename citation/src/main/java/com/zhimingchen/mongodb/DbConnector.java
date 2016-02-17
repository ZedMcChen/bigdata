/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.mongodb;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * @author zhiming
 *
 */
public class DbConnector {
    private final String      host;
    private final int         port;
    private final MongoClient mongoClient;
    
    protected DbConnector(String host, int port) {
        this.host = host;
        this.port = port;
        
        try {
            this.mongoClient = new MongoClient(host, port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static DbConnector createDbConnector(String host, int port) {
        return new DbConnector(host, port);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
    
    public DB getDB(String dbName) {
        return mongoClient.getDB(dbName);
    }

    public DBCollection getCollection(String dbName, String collName) {
        return mongoClient.getDB(dbName).getCollection(collName);
    }

}
