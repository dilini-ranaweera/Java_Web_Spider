import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QuestionFive extends BaseCIAScraper {

    private Document content;
    private HashMap<String, String> iterableMap;

    private String currentCountry = "";
    private double currentMeanElevation = 0;

    private String highestCountry = "";
    private double highestMeanElevation = 0;

    public QuestionFive(String continent) {
        super();
        iterableMap = assignMapFromContinentName(continent);
    }

    public void getPopulationOfHighestMeanElevationCountry () {
        for(String key: iterableMap.keySet()) {
            currentCountry = key;
            try {
                content = Jsoup.connect("https://cia.gov" + iterableMap.get(key)).get();
                Pattern meanElevationPattern = Pattern.compile("mean elevation: </strong>(\\d*) m");
                Matcher meanElevationMatcher = meanElevationPattern.matcher(content.html());

                if(meanElevationMatcher.find()) {
                    String meanElevationString = meanElevationMatcher.group(1);
                    currentMeanElevation = Double.parseDouble(meanElevationString);

                }

                if(currentMeanElevation > highestMeanElevation) {
                    highestMeanElevation = currentMeanElevation;
                    highestCountry = currentCountry;
                }
            } catch(IOException e) {
                System.out.println("Something went wrong in the getLargestCoastlineToAreaRatio method.");
                System.out.println("Here is the stacktrace: ");
                e.printStackTrace();
            }
        }

        try{
            String populationString = "";
            content = Jsoup.connect("https://cia.gov" + iterableMap.get(highestCountry)).get();
            Pattern populationPattern = Pattern.compile("Population</a></h3><p>(\\d+[?:,]\\d+)");
            Matcher populationMatcher = populationPattern.matcher(content.html());

            if(populationMatcher.find()) {
                populationString = populationMatcher.group(0);
            }

            System.out.println("The country with the highest mean elevation is: " + highestCountry);
            System.out.println("Its population is: " + populationString);
        }catch(IOException e) {
            System.out.println("Something went wrong trying to read in the population.");
            System.out.println("Here is the stacktrace: ");
            e.printStackTrace();
        }
    }
}
