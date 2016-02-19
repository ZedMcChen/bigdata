/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citationmvc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zhimingchen.citationmvc.model.Citation;

/**
 * @author zhiming
 *
 */
@Repository
public interface CitationRepository extends MongoRepository<Citation, String>{
    List<Citation> findByDoi(String doi);
    List<Citation> findByUrlHash(String urlHash);
    List<Citation> findByUrl(String url);
}
