package uk.co.service.skill.adapters.dataprovider.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import uk.co.service.skill.LoggingFacade;

import org.apache.commons.lang3.StringUtils;
import uk.co.service.skill.adapters.dataprovider.exceptions.BinCollectionGatewayException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SchedulePageParser implements LoggingFacade {

    private static final String PAGE_ENCODING = "UTF-8";
    private static final String DISPLAY_DATE_FORMAT = "dd/MM/yyyy";
    private static final String DISPLAY_DATE_SEPARATOR = "-";
    private static final String DISPLAY_DATE_ID = "dateDisplay";
    private static final String DISPLAY_ADDRESS_SELECTOR = "body > div > div:nth-child(1) > h3";
    private static final String DISPLAY_SCHEDULE_SELECTOR = "#calendarDisplay > div.table-responsive > table > tbody";

    SimpleDateFormat dateFormatter = new SimpleDateFormat(DISPLAY_DATE_FORMAT);

    private final Document scheduleWebPage;

    public SchedulePageParser(String html) {
        this.scheduleWebPage = Jsoup.parse(html, PAGE_ENCODING);
        System.out.println(scheduleWebPage.select(DISPLAY_SCHEDULE_SELECTOR).toString());
    }

    public String getFirstLineOfAddress(){
        Elements resultLinks = scheduleWebPage.select(DISPLAY_ADDRESS_SELECTOR);
        return(resultLinks.first().text().trim());
    }

    public Date getStartDate(){
        Date startDate = null;
        String dateRange = scheduleWebPage.getElementById(DISPLAY_DATE_ID).text();
        String startDateString = StringUtils.substringBefore(dateRange, DISPLAY_DATE_SEPARATOR).trim();

        try{
            startDate = dateFormatter.parse(startDateString);
        }
        catch(ParseException pe){
            throw new BinCollectionGatewayException("Failed to parse start date in Bin Schedule. Value - " + startDateString);
        }
        return startDate;
    }

    public Date getEndDate(){
        Date endDate = null;
        String dateRange = scheduleWebPage.getElementById(DISPLAY_DATE_ID).text();
        String endDateString = StringUtils.substringBefore(dateRange, DISPLAY_DATE_SEPARATOR).trim();

        try{
            endDate = dateFormatter.parse(endDateString);}
        catch(ParseException pe){
            throw new BinCollectionGatewayException("Failed to parse end date in Bin Schedule. Value - " + endDateString);
        }
        return endDate;
    }

    public String getSchedule() throws Exception {

        // TODO - We will want to use a MultiMap to return the contents of the table.
        // TODO - We will also want to walk the grid and assign an actual Date to each cell, or at least a proper date to a collection day.
        // TODO - (ctd) we will also want to return the String Date e.g. 'Monday the 14th of May' which will be useful for Alexa.
        Elements schedule = scheduleWebPage.select(DISPLAY_SCHEDULE_SELECTOR);

        

        System.out.println(scheduleWebPage.select(DISPLAY_SCHEDULE_SELECTOR).toString());
//        JsonParser parser = new JsonParser();
//        JsonObject jsonObject = parser.parse(html).getAsJsonObject();
//        JsonElement htmlItem = jsonObject.get("html");
//
//        String htmlPayload = htmlItem.toString();
//
//        if (StringUtils.isEmpty(htmlPayload) || StringUtils.isBlank(htmlPayload) || htmlPayload.contains(ADDRESS_NOT_FOUND_TEXT) || !(htmlPayload.contains(SERVICE_PROVIDER_SCHEDULE_URL_PATH))){
//            throw new PropertyNotFoundException("Failed to correctly identify Bin Collection Schedule.");
//        }
//
//        Integer endpointStartPosition = 8;
//        Integer endpointTrailingCharacterCount = 3;
//
//        Document doc = Jsoup.parse(htmlPayload);
//        Element link = doc.select("a").first();
//        String linkHref = link.attr("href");
//
//        if (linkHref.length() <= endpointStartPosition){ throw new PropertyNotFoundException("Failed to identify valid endpoint HTML. Received - " + linkHref.toString());};
//
//        linkHref =  linkHref.substring(endpointStartPosition, (linkHref.length() - endpointTrailingCharacterCount));
//
//        return linkHref;
        return "";
    }

    public static void main (String args[]){

        String selector = "body > div > div:nth-child(1) > h3";


    }
}