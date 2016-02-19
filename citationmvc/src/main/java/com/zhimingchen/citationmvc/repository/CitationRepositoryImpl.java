/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citationmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import com.zhimingchen.citationmvc.model.Citation;
import com.zhimingchen.citationmvc.model.CitationCount;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * @author zhiming
 *
 */
public class CitationRepositoryImpl implements CitationOperations {
    @Autowired
    private MongoOperations mongo;
    
    @Override
    public List<CitationCount> findTopCitedDois() {
        Aggregation agg = Aggregation.newAggregation(
                group("doi").count().as("count"),
                project("count").and("doi").previousOperation(),
                sort(Sort.Direction.DESC, "count"),
                limit(10)
                );
       
        AggregationResults<CitationCount> citationCounts = mongo.aggregate(agg, Citation.class, CitationCount.class); 
        
        return citationCounts.getMappedResults();
    }

}
