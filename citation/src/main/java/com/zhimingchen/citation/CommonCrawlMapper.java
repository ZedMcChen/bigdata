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
            String urlMd5 = Md5Utils.getMD5(url);
            for (String doi: citedDois) {
                BasicDBObject doc = new BasicDBObject("doi", doi)
                                        .append("containingUrlHash", urlMd5)
                                        .append("containingUrl", url);
                dbColl.save(doc);
                context.write(new Text(doi), new IntWritable(1));
            }
        }
    }

}
