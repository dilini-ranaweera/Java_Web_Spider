import java.util.*;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QuestionOne extends BaseCIAScraper
{
    private Document content;
    private String baseURL = "https://www.cia.gov/the-world-factbook/field/flag-description/";
    private String colorOne;
    private String colorTwo;

    public QuestionOne(String colorOne, String colorTwo) {
        super();
        try {
            content = Jsoup.connect(this.baseURL).get();
        } catch (IOException e){
            System.out.println("Sorry, an error occurred. Here is the stacktrace:");
            e.printStackTrace();
        }
        this.colorOne = colorOne;
        this.colorTwo = colorTwo;

    }

    public void getCountriesWithColors() {
        LinkedList<String> countriesWithColors = new LinkedList<>();
        ArrayList<String> paragraphsToAdd = new ArrayList<>();
        HashMap <String, String> countriesWithFlagDescrptions = new HashMap<>();

        Elements links = this.content.select(".col-lg-6 a");
        Elements paragraphDescriptions = this.content.select(".col-lg-6 li p");

        for(Element paragraph: paragraphDescriptions) {
            String holder = paragraph.text();
            paragraphsToAdd.add(holder);
        }

        int i = 0;
        for(Element link: links) {
            String holder = link.text();
            countriesWithFlagDescrptions.put(holder, paragraphsToAdd.get(i));
            i++;
        }

        for(String countryName: countriesWithFlagDescrptions.keySet()) {
            if(countriesWithFlagDescrptions.get(countryName).contains(colorOne) &&
                    countriesWithFlagDescrptions.get(countryName).contains(colorTwo)) {
                countriesWithColors.add(countryName);
            }
        }

        System.out.println("The countries that have " + colorOne + " and " + colorTwo + " are the following:");
        for(int j = 0; j < countriesWithColors.size(); j++) {
            System.out.println(countriesWithColors.get(j));
        }
    }

}
