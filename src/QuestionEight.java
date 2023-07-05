import java.util.*;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class QuestionEight extends BaseCIAScraper {

    private Document content;
    private HashMap<String, String> continentMap;

    private String highestCountry = "";
    private double highestWaterArea = 0;

    public QuestionEight(String continent) {
        super();
        continentMap = assignMapFromContinentName(continent);
    }

    public void sortCountriesByWaterArea() {
        double currentWaterArea = 0;

        for(String countryName: continentMap.keySet()) {
            try{
                content = Jsoup.connect("https://cia.gov" + continentMap.get(countryName)).get();
                Pattern waterAreaPattern = Pattern.compile("water: </strong>(.{0,10}) sq");
                Matcher waterAreaMatcher = waterAreaPattern.matcher(content.html());

                if(waterAreaMatcher.find()) {
                    String waterAreaComma = waterAreaMatcher.group(1);
                    currentWaterArea = Double.parseDouble(waterAreaComma.replaceAll(",",""));
                }
                if(currentWaterArea > highestWaterArea) {
                    highestWaterArea = currentWaterArea;
                    highestCountry = countryName;
                }
            } catch(IOException e) {
                System.out.println("There was an error in the sortCountriesByWaterArea method");
                System.out.println("Here is the stacktrace: ");
                e.printStackTrace();
            }
        }

        System.out.println("The country with the highest water area is: " + highestCountry);
    }
}
