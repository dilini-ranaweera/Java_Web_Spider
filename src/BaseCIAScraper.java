import java.util.*;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
    Top-level class in extension hierarchy
 */

public class BaseCIAScraper {

    private final String baseURL;
    private Document content;

    //helper data structure that has all the sections that the CIA World FactBook provide
    private HashMap<String, String> givenRegions = new HashMap<>();

    public BaseCIAScraper() {
        this.baseURL = "https://www.cia.gov/the-world-factbook/";
        try {
            content = Jsoup.connect(this.baseURL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        populateHelper();
    }

    private void populateHelper() {
        Elements listOfSections = this.content.select("strong");
        for(Element section: listOfSections) {
            Elements link = section.select("a");
            Element a = link.get(0);

            String articleURL = a.attr("href");
            String articleSectionTitle = a.text();
            givenRegions.put(articleSectionTitle, articleURL);
        }
    }

    private void populateMapsWithData(HashMap<String, String>  regionalMapPopulate ,String ...args) {

       for(String worldSection: args) {
           try {
               content = Jsoup.connect("https://cia.gov" + givenRegions.get(worldSection)).get();
           } catch (IOException e) {
               System.out.println("Something went wrong. You can't overwrite the document content.");
               e.printStackTrace();
           } //end of exception handling

           Elements listOfCountries = this.content.select("h5"); //all the countries are in li tags
           for(Element section: listOfCountries) {
               Elements links = section.select("a");
               Element a = links.get(0);

               String countryURL = a.attr("href");
               String countryName = a.text();
               regionalMapPopulate.put(countryName, countryURL);
           }
       }
    }

    public HashMap<String,String> assignMapFromContinentName(String name) {
        if(name.equals("North America")) {
            return getCountriesInNorthAmerica();
        }
        if(name.equals("South America")) {
            return getCountriesInSouthAmerica();
        }
        if(name.equals("Europe")) {
            return getCountriesInEurope();
        }
        if(name.equals("Africa")) {
            return getCountriesInAfrica();
        }
        if(name.equals("Asia")) {
            return getCountriesInAsia();
        }
        if(name.equals("Australia and Oceania")) {
            return getCountriesInOceania();
        }
        return null;
    }

    //getter methods so that the subclasses can access desired things from this base class - NEED TO FINISH HIM
    public HashMap<String, String> getCountriesInAsia() {
        HashMap<String, String> countriesInAsia = new HashMap<>();
        populateMapsWithData(countriesInAsia, "Central Asia", "Middle East", "Central Asia", "South Asia");
        return countriesInAsia;
    }

    public HashMap<String, String> getCountriesInNorthAmerica() {
        HashMap<String, String> countriesInNorthAmerica = new HashMap<>();
        populateMapsWithData(countriesInNorthAmerica, "North America");
        return countriesInNorthAmerica;
    }

    public HashMap<String, String> getCountriesInSouthAmerica() {
        HashMap<String, String> countriesInSouthAmerica = new HashMap<>();
        populateMapsWithData(countriesInSouthAmerica, "South America", "Central America");
        return countriesInSouthAmerica;
    }

    public HashMap<String, String> getCountriesInEurope() {
        HashMap<String, String> countriesInEurope = new HashMap<>();
        populateMapsWithData(countriesInEurope, "Europe");
        return countriesInEurope;
    }

    public HashMap<String, String> getCountriesInAfrica() {
        HashMap<String, String> countriesInAfrica = new HashMap<>();
        populateMapsWithData(countriesInAfrica, "Africa" );
        return countriesInAfrica;
    }

    public HashMap<String, String> getCountriesInOceania() {
        HashMap<String, String> countriesInOceania = new HashMap<>();
        populateMapsWithData(countriesInOceania, "Australia and Oceania");
        return countriesInOceania;
    }

    public HashMap<String, String> getOceans() {
        HashMap<String, String> differentOceans = new HashMap<>();
        differentOceans.put("Arctic Ocean", "/oceans/arctic-ocean/");
        differentOceans.put("Pacific Ocean" , "/oceans/pacific-ocean/");
        differentOceans.put("Southern Ocean" , "/oceans/southern-ocean/");
        differentOceans.put("Indian Ocean", "/oceans/indian-ocean/");
        differentOceans.put("Atlantic Ocean" , "/oceans/atlantic-ocean/");
        return differentOceans;
    }

    public final String getBaseURL() {
        return baseURL;
    }
}
