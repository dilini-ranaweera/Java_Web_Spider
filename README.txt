The BaseCIAScraper class  is the highest-level in the class tree that I use to complete this homework.
The classes that correspond to each question in the assignment inherit from BaseCIAScraper This class uses
a base URL of https://www.cia.gov/the-world-factbook/ and creates 7 maps of countries to their respective URLS.
6 of the maps correspond to continents (Antarctica being excluded),  and 1 of the maps correspond to the oceans.
I created an additional data structure that purely scrapes the HTML from the base WorldCIA FactBook, and then created
helper methods to assign the key-value pairs in the additional data structures that group countries by searchable
region.

The populateHelper() method populates the additional data structure with keywords of the regions explicitly listed in
https://www.cia.gov/the-world-factbook/ and their respective additional links for those keywords.

The populateMapsWithData() method populates the actual data structures I will use and that will be available for
the subtype classes to use. Given a list of keywords that maps to key-value pairs in the additional data structure, I
populate a map given to the populateMapsWithData() as a parameter by doing the following:

    - Reset the document content with the HTML from the region website
      (already given from the additional data structure)
    -Get the list of countries associated with that region and their respective URLs
    -Add those key value pairs to the maps of the continent-based data structures
    -Repeat this process for all the given keywords passed to the function

I created a new class for each question that was given in the homework. I did this to avoid having so much code in
once class, and to avoid repeated methods. Here are the assumptions I made for each question:

Q1: Since I'm scraping directly from the flag description website and not the flag description from each country, some
flag descriptions state "same description as another country's flag." I did not account for this in my program - my
program only picks up whether the description contains the words of the colors or not.

Q2: I use regex for question 2 because using JSoup was difficult. However, there are some information that the regex
pattern picks up extra for the Pacific Ocean website. It picks up the specified info but some extra as well.

Q3: The program allows for the user to input a continent, from which the specified hashmap containing the countries of
the continent is returned. The Document object is re-written each time with the content of each specific country, then
scraped for the electricity production value. Program keeps track of the name of the country with the highest EP Value
and the EP value itself.

Q4: The program allows for the user to input a continent, from which the specified hashmap containing the countries of
    the continent is returned. The Document object is re-written each time with the content of each specific country,
    then scraped for both the land area and the coastline values. Those values are converted from Strings to Doubles
    by using the String.replaceAll method to get rid of the commas. The same is done in Q3 and Q5. The coastline to
    land area ratio is calculated through division, and the name of the country with the highest ratio is printed.

Q5: The program allows for the user to input a continent, from which the specified hashmap containing the countries of
        the continent is returned. The Document object is re-written each time with the content of each specific country,
        then scraped for mean elevation and population. Keeps track of country with highest mean elevation and
        ignores the countries that doesn't have that information given in the website. After looping through all
        countries, re-write document content with country of highest mean elevation and scrape population. Return both
        name of country and population. There is something wrong with my regex for the population that is giving me
        trouble.

Q6: ASSUMPTION MADE FOR THIS QUESTION: INSTEAD OF USING THE REGIONS "CARIBBEAN" OR "CENTRAL ASIA", I CHOSE TO GROUP
BY CONTINENT INSTEAD BECAUSE THAT IS HOW MY DATA IS SET UP. The program allows for the user to input a continent,
from which the specified hashmap containing the countries of the continent is returned.The Document object is re-written
each time with the content of each specific country,then scraped for area. The names of the countries and their areas
are stored in a HashMap, and that the entries of that hashmap become values in a linkedlist of entries. I sort
the linkedList entries by value (this prevents complicated sorting algorithm) then get the third highest area
country by getting the third to last index in a linked list. Document content is overwritten with that name, and import
partners are displayed, but there is a small thing wrong with my regex that is giving me trouble.


Q7: The program allows for the user to input a character. Each HashMap with the continents and countries is looped
through. If the name of the country has the same first letter as the character, the name of that country and its
area are stored in a new hashmap. the entries of that hashmap become values in a linkedlist of entries. I sort
the linkedList entries by value (this prevents complicated sorting algorithm) then get display the linked list
using the toString method.

Q8: Similar to Question 8, but measuring water area instead.

Another Assumption: I put all the countries in the "Central America" tab in the factbook in the South America
HashMap data structure. This was for ease on my part.

Another Note: I know I don't return a shallow copy of things in respective classes. I know this is not good
OO practice but I couldn't figure out how to do it with a HashMap. Also I spent a lot of time with the regex on
problems 5 and 6, including going to OH and reading REGEX documentation.

Thank you for grading!
