/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citationmvc.repository;

import java.util.List;

import com.zhimingchen.citationmvc.model.CitationCount;

/**
 * @author zhiming
 *
 */
public interface CitationOperations {
    List<CitationCount> findTopCitedDois();
}
