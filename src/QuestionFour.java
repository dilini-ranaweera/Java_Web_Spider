import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class QuestionFour extends BaseCIAScraper{

    private Document content;
    private HashMap<String, String> regionsToIterate;

    private double highestCountryRatio = 0;
    private String highestCountryName = "";

    private double currentCountryRatio = 0;
    private String currentCountryName = "";

    public QuestionFour(String continent) {
        super();
        regionsToIterate = assignMapFromContinentName(continent);
    }

    public void getLargestCoastlineToAreaRatio() {
        double landAreaFinal = 1;
        double coastLineFinal = 1;
        for(String key: regionsToIterate.keySet()) {
            currentCountryName = key;
            try {
                content = Jsoup.connect("https://cia.gov" + regionsToIterate.get(key)).get();
                Pattern landAreaPattern = Pattern.compile("land </strong>(.{0,10}) sq");
                Matcher landAreaMatcher = landAreaPattern.matcher(content.html());

                if(landAreaMatcher.find()) {
                    String landAreaComma = landAreaMatcher.group(1);
                    System.out.println(landAreaComma);
                    landAreaFinal = Double.parseDouble(landAreaComma.replaceAll(",",""));
                }

                Pattern coastLinePattern = Pattern.compile("Coastline</a></h3><p>(.{0,15}) k");
                Matcher coastLineMatcher = coastLinePattern.matcher(content.html());

                if(coastLineMatcher.find()) {
                    String coastLineComma = coastLineMatcher.group(1);
                    System.out.println(coastLineComma);
                    coastLineFinal = Double.parseDouble(coastLineComma.replaceAll(",",""));
                }
                currentCountryRatio = coastLineFinal/landAreaFinal;

                if(currentCountryRatio > highestCountryRatio) {
                    highestCountryRatio = currentCountryRatio;
                    highestCountryName = currentCountryName;
                }
            } catch(IOException e) {
                System.out.println("Something went wrong in the getLargestCoastlineToAreaRatio method.");
                System.out.println("Here is the stacktrace: ");
                e.printStackTrace();
            }
        }
        System.out.println("The country with the highest coastline to land area ratio is: " + highestCountryName);
    }
}
