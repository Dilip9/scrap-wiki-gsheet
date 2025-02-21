package com.wikipedia.wikipedia_google_api.services;

import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.wikipedia.wikipedia_google_api.common.WikiGoogleSheetConstants;
import com.wikipedia.wikipedia_google_api.model.ChampionshipData;
import com.wikipedia.wikipedia_google_api.util.GoogleSheetAPIUtils;
import com.wikipedia.wikipedia_google_api.util.JSOUPWikipediaPasrserUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


@Service
public class ScrappingWikipediaService {



    public ResponseEntity<String> scrapeChampionData() {
        Spreadsheet spreadSheet = null;
        try{
            List<ChampionshipData> extractedData = JSOUPWikipediaPasrserUtils.getHTMLContent(WikiGoogleSheetConstants.URL);
            spreadSheet = GoogleSheetAPIUtils.createSheet(WikiGoogleSheetConstants.RANGE);
            GoogleSheetAPIUtils.writeDataIntoGoogleSheet(extractedData, WikiGoogleSheetConstants.RANGE, spreadSheet.getSpreadsheetId());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body("Open the link in your browser to access the Excel data : "+spreadSheet.getSpreadsheetUrl());
    }
}
