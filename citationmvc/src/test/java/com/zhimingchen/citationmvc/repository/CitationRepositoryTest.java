package com.zhimingchen.citationmvc.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhimingchen.citationmvc.config.MongoConfig;
import com.zhimingchen.citationmvc.model.Citation;
import com.zhimingchen.citationmvc.model.CitationCount;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=MongoConfig.class)
public class CitationRepositoryTest  {
    @Autowired 
    private CitationRepository citationRepository;
    
    @Autowired
    private MongoOperations mongoOps;
    
    @Before
    public void setup() {
        citationRepository.deleteAll();
    }
    
    @Test
    public void testMongoRepo() {
        assertThat(this.citationRepository.count(), is(0L));
        
        Citation citation1 = createACitation("doi", "urlhash1", "url1");
        Citation savedCitation1 = this.citationRepository.save(citation1);
        assertThat(this.citationRepository.count(), is(1L));
        
        Citation citation2 = createACitation("doi", "urlhash2", "url2");
        this.citationRepository.save(citation2);
        List<Citation> citations = this.citationRepository.findByDoi("doi");
        assertThat(citations.size(), is(2));
        
        Citation citation3 = createACitation("doi", "urlhash3", "url3");
        this.citationRepository.save(citation3);
        
        List<CitationCount> citationCounts = this.citationRepository.findTopCitedDois();
        assertThat(citationCounts.size(), is(1));
        assertThat(citationCounts.get(0).getCount(), is(3));
        
        this.citationRepository.delete(savedCitation1);
        assertThat(this.citationRepository.count(), is(2L));
    }

    private Citation createACitation(String doi, String urlHash, String url) {
        Citation citation = new Citation();
        citation.setDoi(doi);
        citation.setUrlHash(urlHash);
        citation.setUrl(url);
        return citation;
    }
}
