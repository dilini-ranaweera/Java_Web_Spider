import java.util.*;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class QuestionSeven extends BaseCIAScraper {

    private Document content;
    private HashMap<String, Double> countriesWithStartingLetter = new HashMap<>();
    private String startingCharacter;

    public QuestionSeven(String startingCharacter) {
        super();
        this.startingCharacter = startingCharacter;
    }

    private void populateCountriesWithStartingLetter () {
        String[] continentNames = {"North America", "South America", "Asia",
                                    "Africa", "Europe", "Australia and Oceania"};

        for(int i = 0; i<continentNames.length; i++) {

            HashMap<String,String> continentHolder = assignMapFromContinentName(continentNames[i]);

            for(String countryName: continentHolder.keySet()) {
                if(startingCharacter.charAt(0) == countryName.charAt(0)) {
                    try{
                        content = Jsoup.connect("https://cia.gov" + continentHolder.get(countryName)).get();
                        Pattern totalAreaPattern = Pattern.compile("total: </strong>(.{0,40}) sq");
                        Matcher totalAreaMatcher = totalAreaPattern.matcher(content.html());

                        if(totalAreaMatcher.find()) {
                            String totalAreaComma = totalAreaMatcher.group(1);
                            double totalAreaFinal =
                                    Double.parseDouble(totalAreaComma.replaceAll(",", ""));

                            countriesWithStartingLetter.put(countryName, totalAreaFinal);
                        }
                    } catch(IOException e) {
                        System.out.println("Something went wrong in the populateCountriesWithStartingLetter method.");
                        System.out.println("Here is the stacktrace: ");
                        e.printStackTrace();
                    }
                }
            }
        }//end of loop
    }

    public void sortHashMap() {
        populateCountriesWithStartingLetter();
        Set<Map.Entry<String, Double>> entries = countriesWithStartingLetter.entrySet();
        ArrayList<Map.Entry<String, Double>> entryList = new ArrayList<>(entries);
        entryList.sort(Map.Entry.comparingByValue());
        System.out.println("The countries that start with the specified letter are sorted below: ");
        System.out.println(entryList.toString());
    }
}
