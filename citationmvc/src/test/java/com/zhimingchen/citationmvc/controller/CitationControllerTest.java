package com.zhimingchen.citationmvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zhimingchen.citationmvc.model.Citation;
import com.zhimingchen.citationmvc.model.CitationCount;
import com.zhimingchen.citationmvc.repository.CitationRepository;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class CitationControllerTest {
    private MockMvc mockMvc;
    
    @Mock
    private CitationRepository mockRepository;
    
    @Before
    public void setup() {
        CitationController controller = new CitationController(this.mockRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    
    @Test
    public void shouldReturnHomepage() throws Exception {
        List<CitationCount> citationCounts = new ArrayList<CitationCount>();
        when(this.mockRepository.findTopCitedDois()).thenReturn(citationCounts);
        
        this.mockMvc.perform(get("/home"))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("citationCounts", citationCounts))
                    .andExpect(view().name("homePage"));
    }
    
    @Test
    public void shouldDisplayDoiCitation() throws Exception {
        String doi = "thisdoi";
        List<Citation> citations = new ArrayList<Citation>();
        when(this.mockRepository.findByDoi(doi)).thenReturn(citations);
        
        this.mockMvc.perform(post("/doi").param("doi", doi))
                    .andExpect(status().isOk())
                    .andExpect(model().attribute("doi", doi))
                    .andExpect(model().attribute("citations", citations))
                    .andExpect(view().name("listingUrl"));
    }
}
