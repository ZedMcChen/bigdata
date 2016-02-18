/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citation;

import java.io.IOException;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.martinkl.warc.WARCWritable;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.zhimingchen.citation.utils.DoiUtils;
import com.zhimingchen.citation.utils.Md5Utils;
import com.zhimingchen.mongodb.DbConnector;

/**
 * @author zhiming
 *
 */
public class CommonCrawlMapper extends Mapper<LongWritable, WARCWritable, Text, IntWritable> {
    static DbConnector dbConnector = null;
    static String dbName = null;
    static String collName = null;
    
    private DBCollection dbColl = null;
    
    @Override
    public void setup(Context context) {
        if (dbConnector == null) {
            Configuration conf = context.getConfiguration();
            String dbHost = conf.get("db.host");
            int    dbPort = Integer.parseInt(conf.get("db.port"));
            dbConnector = DbConnector.createDbConnector(dbHost, dbPort);
            
            dbName = conf.get("db.database.name");
            collName = conf.get("db.collection.name");
        }
        
        dbColl = dbConnector.getCollection(dbName, collName);
    }
    
    @Override
    public void map(LongWritable key, WARCWritable value, Context context) throws IOException, InterruptedException {
        String url = value.getRecord().getHeader().getTargetURI();
        String content = new String(value.getRecord().getContent());
        Set<String> citedDois = DoiUtils.findDois(content);
        if (citedDois.size()>0) {
            BasicDBObject page = createPage(url, citedDois);
            String urlMd5 = Md5Utils.getMD5(url);
            String pageLocation = "pages." + urlMd5;
            BasicDBObject dbPage = new BasicDBObject("$set", new BasicDBObject(pageLocation, page));
            for (String doi: citedDois) {
                BasicDBObject doc = new BasicDBObject("doi", doi);
                dbColl.update(doc, dbPage, true, false);
                context.write(new Text(doi), new IntWritable(1));
            }
        }
    }

    private BasicDBObject createPage(String url, Set<String> citedDois) {
        return new BasicDBObject("url", url)
                .append("citedDois", citedDois);
    }
    private BasicDBObject createPage(String url, Set<String> citedDois, String content) {
        return createPage(url, citedDois).append("content", content);
    }
}
