import java.util.*;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class QuestionSix extends BaseCIAScraper{

    private Document content;
    private HashMap<String, String> continentHolder;
    private HashMap<String, Double> namesWithAreas = new HashMap<>();

    public QuestionSix(String continent) {
        super();
        continentHolder = assignMapFromContinentName(continent);
    }

    private void populateNamesWithAreasMap() {
        for(String countryName: continentHolder.keySet()) {
            try {
                content = Jsoup.connect("https://cia.gov" + continentHolder.get(countryName)).get();
                Pattern totalAreaPattern = Pattern.compile("total: </strong>(.{0,20}) sq");
                Matcher totalAreaMatcher = totalAreaPattern.matcher(content.html());

                if(totalAreaMatcher.find()) {
                    String totalAreaComma = totalAreaMatcher.group(1);
                    double totalAreaFinal =
                            Double.parseDouble(totalAreaComma.replaceAll(",", ""));
                    namesWithAreas.put(countryName,totalAreaFinal);
                }
            } catch(IOException e) {
                System.out.println("Something went wrong in the populateNamesWithAreasMap method.");
                System.out.println("Here is the stacktrace: ");
                e.printStackTrace();
            }
        }
    }

    public void getImportPartners() {
        populateNamesWithAreasMap();
        String importPartners = "";

        Set<Map.Entry<String, Double>> entries = namesWithAreas.entrySet();
        ArrayList<Map.Entry<String, Double>> entryList = new ArrayList<>(entries);
        entryList.sort(Map.Entry.comparingByValue());

        int desiredNumber = entryList.size() - 3;
        Map.Entry<String, Double> desiredPair = entryList.get(desiredNumber);

        String name = desiredPair.getKey();

        try {
            content = Jsoup.connect("https://cia.gov" + continentHolder.get(name)).get();
            Pattern importPartnersPattern = Pattern.compile("imports -" +
                    " partners</a><.h3><p>(.{0,12000})</p></div><div>");
            Matcher importPartnersMatcher = importPartnersPattern.matcher(content.html());

            if(importPartnersMatcher.find()) {
                importPartners = importPartnersMatcher.group(1);
            }
            System.out.println("The import countries of " + name + " are: " + importPartners);
        } catch(IOException e) {
            System.out.println("Something went wrong in the getImportPartners method.");
            System.out.println("Here is the stacktrace: ");
            e.printStackTrace();
        }

    }
}
