package com.wikipedia.wikipedia_google_api.util;



import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.wikipedia.wikipedia_google_api.common.WikiGoogleSheetConstants;
import com.wikipedia.wikipedia_google_api.model.ChampionshipData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GoogleSheetAPIUtils {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static Sheets.Spreadsheets sheetsService;

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        InputStream in = GoogleSheetAPIUtils.class.getResourceAsStream( WikiGoogleSheetConstants.CREDENTIALS_FILE_PATH);

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " +  WikiGoogleSheetConstants.WIKI_API);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(WikiGoogleSheetConstants.TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8088).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }


    public static Spreadsheet createSheet(String range) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(WikiGoogleSheetConstants.APPLICATION_NAME).build();
        // Create a new spreadsheet
        SpreadsheetProperties spreadsheetProperties = new SpreadsheetProperties();
        spreadsheetProperties.setTitle(WikiGoogleSheetConstants.SPREADSHEET_TITLE);
        SheetProperties sheetProperties = new SheetProperties();
        sheetProperties.setTitle(range);
        Sheet sheet = new Sheet().setProperties(sheetProperties);

        Spreadsheet spreadsheet = new Spreadsheet().setProperties(spreadsheetProperties)
                .setSheets(Collections.singletonList(sheet));
        return service.spreadsheets().create(spreadsheet).execute();

    }

    public static void writeDataIntoGoogleSheet(List<ChampionshipData> extractedWikiData, String range, String spreadsheetId) {
        final NetHttpTransport HTTP_TRANSPORT;
        try {
            List<List<Object>> data = new ArrayList<>();
            data.add(Arrays.asList("Year", "Winner", "Score", "RunnerUp"));
            for (ChampionshipData championshipData : extractedWikiData) {
                data.add(Arrays.asList(championshipData.getYear(), championshipData.getWinner(), championshipData.getScore(), championshipData.getRunnerUp()));
            }
            ValueRange body = new ValueRange().setValues(data);
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            sheetsService = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(WikiGoogleSheetConstants.APPLICATION_NAME).build().spreadsheets();
            sheetsService.values().update(spreadsheetId, range, body).setValueInputOption("RAW").execute();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
