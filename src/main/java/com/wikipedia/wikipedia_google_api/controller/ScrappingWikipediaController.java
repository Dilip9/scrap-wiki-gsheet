package com.wikipedia.wikipedia_google_api.controller;

import com.wikipedia.wikipedia_google_api.services.ScrappingWikipediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@CrossOrigin("*")
@RequestMapping("/scrap/wiki")
public class ScrappingWikipediaController {

    private final ScrappingWikipediaService scrappingWikipediaService;

    public ScrappingWikipediaController(ScrappingWikipediaService scrappingWikipediaService) {
        this.scrappingWikipediaService = scrappingWikipediaService;
    }

    @GetMapping("/championList")
    public ResponseEntity<String> getChampionData() {
        return scrappingWikipediaService.scrapeChampionData();
    }


}

