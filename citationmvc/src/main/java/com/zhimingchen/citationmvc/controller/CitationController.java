/**
 * Copyright Zhiming Chen 2016
 */
package com.zhimingchen.citationmvc.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhimingchen.citationmvc.model.Citation;
import com.zhimingchen.citationmvc.model.CitationCount;
import com.zhimingchen.citationmvc.repository.CitationRepository;

/**
 * @author zhiming
 *
 */
@Controller
public class CitationController {
    private CitationRepository citationRepository;
    
    @Autowired
    public CitationController(CitationRepository citationRepository) {
        this.citationRepository = citationRepository;
    }
    
    @RequestMapping(value={"/", "/home"}, method=RequestMethod.GET)
    public String homePage(Model model) {
        List<CitationCount> citationCounts = citationRepository.findTopCitedDois();
        model.addAttribute("citationCounts", citationCounts);
        return "homePage";
    }

    @RequestMapping(value="/doi", method=RequestMethod.POST)
    public String displayDoiCitation(@RequestParam("doi") String doi, Model model) {
        doi = doi.trim();
        List<Citation> citations = citationRepository.findByDoi(doi);
        model.addAttribute("doi", doi);
        model.addAttribute("citations", citations);
        return "listingUrl";
    }
    
}
