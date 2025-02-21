package com.wikipedia.wikipedia_google_api.util;

import com.wikipedia.wikipedia_google_api.common.WikiGoogleSheetConstants;
import com.wikipedia.wikipedia_google_api.model.ChampionshipData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSOUPWikipediaPasrserUtils {

    public static List<ChampionshipData> getHTMLContent(String url) throws IOException {

        List<ChampionshipData> finalsList = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        Document document = Jsoup.parse(doc.toString());
        Element table = document.select(WikiGoogleSheetConstants.CSSQUERY_HEADER_SELECTOR).first();
        Elements rows = table.select(WikiGoogleSheetConstants.TABLE_ROW_SELECTOR);
        for (int i=1;i<Math.min(rows.size(), 11);i++) {
            Element row = rows.get(i);
            Elements columns = row.select(WikiGoogleSheetConstants.TABLE_DATA_SELECTOR);

            if (columns.size() >= 4) {
                Integer year = Integer.valueOf(row.select(WikiGoogleSheetConstants.TABLE_HEADER_SELECTOR).text().trim());
                String winner = columns.get(0).text();
                String scores = columns.get(1).text();
                scores = scores.replace("–", "-");
                String score = scores.replaceAll("\\s*\\(.*?\\)\\s*", "")
                        .replaceAll("\\[.*?\\]", "");
                String runnerUp = columns.get(2).text();

                ChampionshipData championshipData = new ChampionshipData(year, winner, score, runnerUp);
                finalsList.add(championshipData);
            }
        }
        return finalsList;
    }
}
