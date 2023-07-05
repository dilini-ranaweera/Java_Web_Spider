import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class QuestionTwo extends BaseCIAScraper
{
    private String ocean;
    private Document content;
    private String html;


    public QuestionTwo(String ocean) {
        super();
        this.ocean = ocean;
        String givenURL = getBaseURL()  + getOceans().get(this.ocean);

        try{
            this.content = Jsoup.connect(givenURL).get();
        } catch (IOException e) {
            System.out.println("Something went wrong. Here is the stacktrace:");
            e.printStackTrace();
        }
    }

    //gets lowest point in the ocean by scraping and regex
    public String getLowestPoint() {
        String lowestPoint = "";
        Pattern lowestPointPattern = Pattern.compile("lowest point: </strong>(.{0,200})<br><br><strong>mea");
        Matcher lowestPointMatcher = lowestPointPattern.matcher(content.html());

        if(lowestPointMatcher.find()) {
            lowestPoint = lowestPointMatcher.group(1);
        }
        return lowestPoint;
    }


}
