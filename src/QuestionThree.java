import java.util.*;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class QuestionThree extends BaseCIAScraper{

    public HashMap<String, String> regionToIterate;
    private Document content;

    private String highestCountryName = "";
    private double highestCountryEP = 1;
    private String currentCountryName = "";
    private double currentCountryEP = 0;

    public QuestionThree(String inputContinent) {
        super();
        regionToIterate = assignMapFromContinentName(inputContinent);
    }

    public void getHighestElectricityProduction () {
        for(String countryName: regionToIterate.keySet()) {
            currentCountryName = countryName;

            try{
                String numericalCurrentRepresentation;

                content = Jsoup.connect("https://cia.gov" + regionToIterate.get(countryName)).get();
                Pattern getNumberAndWord = Pattern.compile("<p>(\\d*[.]\\d*)\\s(\\w*)\\sk");
                Matcher matchNumberAndWord = getNumberAndWord.matcher(content.html());

                if(matchNumberAndWord.find()){
                    numericalCurrentRepresentation = matchNumberAndWord.group(1);
                    String quantifier = matchNumberAndWord.group(2);

                    currentCountryEP = Double.parseDouble(numericalCurrentRepresentation);
                    currentCountryEP = convertWordToNumber(quantifier, currentCountryEP);
                }
                if(currentCountryEP > highestCountryEP) {
                    highestCountryEP = currentCountryEP;
                    highestCountryName = currentCountryName;
                }
            } catch(IOException e) {
                System.out.println("Something went wrong in the GetHighestElectricityProduction method.");
                System.out.println("Here is the stacktrace: ");
                e.printStackTrace();
            }
        }

        System.out.println("The country with the highest electricity production is: " +highestCountryName);
    }

    private double convertWordToNumber(String quantifier, Double currentRepresentation) {
        if(quantifier.equals("million")) {
            return currentRepresentation * 1000000;
        } else if(quantifier.equals("billion")) {
            return currentRepresentation * 1000000000;
        }
        else {
            return 0;
        }
    }

    public HashMap<String, String> getRegionToIterate() {
        return regionToIterate;
    }

}
