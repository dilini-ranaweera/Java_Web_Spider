import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.*;
public class MainWebScraper
{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Hello! Welcome to my interactive CIA World FactBook Web Crawler!");
        System.out.println();
        System.out.println("This is a list of questions you can ask the Web Crawler?");
        System.out.println();
        System.out.println("\t 1. List all the countries in the world who's flags have RED and GREEN flags.");
        System.out.println("\t 2. What is the lowest point in the ATLANTIC OCEAN?");
        System.out.println("\t 3. Find the largest country in AFRICA in terms of Electricity Production?");
        System.out.println("\t 4. What country in EUROPE has the largest coastline to land area ratio?");
        System.out.println("\t 5. What is the population of the country in" +
                " SOUTH AMERICA with the largest mean elevation?");
        System.out.println("\t 6. What are the import partners of the third largest country (by land area) of the " +
                "CARIBBEAN?");
        System.out.println("\t 7. List the countries that start with the letter D, sorted from smallest to largest" +
                "in terms of land area.");
        System.out.println("\t 8. List the name of the country that has the highest water area in NORTH AMERICA.");
        System.out.println("\t Question #8 is the one I came up with.");
        System.out.println();
        System.out.println("The parts that are capitalized may be changed based on your input.");
        System.out.println("The part of Question 7 that can be changed is the starting letter of the country names.");
        System.out.println();
        System.out.println("You may now ask the web scraper multiple questions. To quit, enter the number 9.");

        int number = 0;
        while(number != 9) {
            System.out.println();
            System.out.println("What question would you like answered? Please type in the number.");
            number = sc.nextInt();
            answer(number);
        }
    }

    public static void answer(int questionNumber) {
        Scanner scTwo = new Scanner(System.in);
        if(questionNumber == 1){
                String colOne;
                String colTwo;

                System.out.println("What flag colors do you want the countries for?");
                colOne = scTwo.nextLine();
                colTwo = scTwo.nextLine();

                QuestionOne qOne = new QuestionOne(colOne, colTwo);
                qOne.getCountriesWithColors();
            }

        else if(questionNumber == 2){
                String userInputOcean;

                System.out.println("Which ocean do you want the bottom-most point of?");

                System.out.println("Please enter 1 of the following 5 oceans:");
                System.out.println("\t 1) Atlantic Ocean");
                System.out.println("\t 2) Pacific Ocean");
                System.out.println("\t 3) Arctic Ocean");
                System.out.println("\t 4) Indian Ocean");
                System.out.println("\t 5) Southern Ocean");
                System.out.println();

                userInputOcean = scTwo.nextLine();

                QuestionTwo q2 = new QuestionTwo(userInputOcean);
                System.out.println("The lowest point of " + userInputOcean + " is: " + q2.getLowestPoint());
            }

        else if(questionNumber == 3){
                String userInputContinent;
                printContinentOptions();

                System.out.println();
                userInputContinent = scTwo.nextLine();

                QuestionThree q3 = new QuestionThree(userInputContinent);
                q3.getHighestElectricityProduction();
            }

        else if(questionNumber == 4){
                String userInputContinent;
                printContinentOptions();

                System.out.println();
                userInputContinent = scTwo.nextLine();

                QuestionFour q4 = new QuestionFour(userInputContinent);
                q4.getLargestCoastlineToAreaRatio();
            }

        else if(questionNumber == 5){
                String userInputContinent;
                printContinentOptions();

                System.out.println();
                userInputContinent = scTwo.nextLine();

                QuestionFive q5 = new QuestionFive(userInputContinent);
                q5.getPopulationOfHighestMeanElevationCountry();
            }
        else if(questionNumber == 6) {
            String userInputContinent;
            printContinentOptions();

            System.out.println();
            userInputContinent = scTwo.nextLine();

            QuestionSix q6 = new QuestionSix(userInputContinent);
            q6.getImportPartners();
        }
        else if(questionNumber == 7) {
            String userInputCharacter;
            System.out.println("What character would you like to choose? Please choose any letter.");

            System.out.println();
            userInputCharacter = scTwo.nextLine();

            QuestionSeven q7 = new QuestionSeven(userInputCharacter.toUpperCase());
            q7.sortHashMap();
        }
        else if(questionNumber == 8){
            String userInputContinent;
            printContinentOptions();

            System.out.println();
            userInputContinent = scTwo.nextLine();

            QuestionEight q8 = new QuestionEight(userInputContinent);
            q8.sortCountriesByWaterArea();
        }

    }


    public static void printContinentOptions() {
        System.out.println("Which continent would you like to search from?");
        System.out.println();
        System.out.println("NOTE: PLEASE SPELL YOUR INPUT IN THE EXACT WAY IT'S LISTED!");
        System.out.println("THE PROGRAM WILL NOT WORK IF YOU DON'T.");
        System.out.println();
        System.out.println("Please enter 1 of the following 6 continents (Antarctica isn't counted):");
        System.out.println("\t 1. North America");
        System.out.println("\t 2. South America");
        System.out.println("\t 3. Asia");
        System.out.println("\t 4. Africa");
        System.out.println("\t 5. Europe");
        System.out.println("\t 6. Australia and Oceania");
    }

}
