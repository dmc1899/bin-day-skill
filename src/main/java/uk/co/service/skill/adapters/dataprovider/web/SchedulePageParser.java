package uk.co.service.skill.adapters.dataprovider.web;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import uk.co.service.skill.LoggingFacade;

import org.apache.commons.lang3.StringUtils;
import uk.co.service.skill.adapters.dataprovider.exceptions.BinCollectionGatewayException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.time.DateUtils.addDays;

public class SchedulePageParser implements LoggingFacade {

    private static final String NO_COLLECTION_TEXT = "None";
    private static final String PAGE_ENCODING = "UTF-8";
    private static final String DISPLAY_DATE_FORMAT = "dd/MM/yyyy";
    private static final String DISPLAY_DATE_SEPARATOR = "-";
    private static final String DISPLAY_DATE_ID = "dateDisplay";
    private static final String ADDRESS_SELECTOR = "body > div > div:nth-child(1) > h3";
    private static final String SCHEDULE_SELECTOR = "#calendarDisplay > div.table-responsive > table";
    private static final String TABLE_ROW_SELECTOR = "tr";
    private static final String TABLE_CELL_SELECTOR = "td";
    private static final String LIST_ITEM_SELECTOR = "li";
    private static final String LIST_LIST_ITEM_SELECTOR = "ul > " + LIST_ITEM_SELECTOR;
    private static final String PARAGRAPH_ABBR_SELECTOR = "p > abbr";
    private static final String TITLE_ATRIB_SELECTOR = "title";

    SimpleDateFormat dateFormatter = new SimpleDateFormat(DISPLAY_DATE_FORMAT);

    private final Document scheduleWebPage;
    private Date displayStartDate;
    private Date displayEndDate;

    public SchedulePageParser(String html) {
        this.scheduleWebPage = Jsoup.parse(html, PAGE_ENCODING);
        setStartDate();
        setEndDate();
    }

    public String getFirstLineOfAddress(){
        Elements resultLinks = scheduleWebPage.select(ADDRESS_SELECTOR);
        return(resultLinks.first().text().trim());
    }

    public Date getDisplayStartDate(){
        return this.displayStartDate;
    }

    public Date getDisplayEndDate(){
        return this.displayEndDate;
    }

    public List<CollectionScheduleEvent> getCollectionSchedule() {
        List<CollectionScheduleEvent> collectionScheduleEvents = new ArrayList<CollectionScheduleEvent>();

        //TODO - Replace the date incrementing process with the use of DateUtil to parse and convert Date in words.
        MultiMap collectionSchedule = new MultiValueMap();
        Date collectionDate  = this.displayStartDate;

        Element table = scheduleWebPage.selectFirst(SCHEDULE_SELECTOR);

        for (Element row : table.select(TABLE_ROW_SELECTOR)){

            for (Element td: row.select(TABLE_CELL_SELECTOR)){

                CollectionScheduleEvent collectionScheduleEvent = new CollectionScheduleEvent();
                String collectionDateInWords = td.select(PARAGRAPH_ABBR_SELECTOR).first().attr(TITLE_ATRIB_SELECTOR);
                Elements collectionItems = td.getElementsByTag(LIST_ITEM_SELECTOR);

                collectionScheduleEvent.collectionDateInWords = collectionDateInWords;
                collectionScheduleEvent.collectionDate = collectionDate;
                collectionScheduleEvent.collectionItems = getCollectionItems(collectionItems);
                collectionScheduleEvents.add(collectionScheduleEvent);

                collectionDate = addDays(collectionDate, 1);
            }
        }
        return  collectionScheduleEvents;
    }

    private void setStartDate(){
        Date startDate = null;
        String dateRange = scheduleWebPage.getElementById(DISPLAY_DATE_ID).text();
        String startDateString = StringUtils.substringBefore(dateRange, DISPLAY_DATE_SEPARATOR).trim();

        try{
            startDate = dateFormatter.parse(startDateString);
        }
        catch(ParseException pe){
            throw new BinCollectionGatewayException("Failed to parse start date in Bin Schedule. Value - " + startDateString);
        }
        this.displayStartDate = startDate;
    }

    private void setEndDate(){
        Date endDate = null;
        String dateRange = scheduleWebPage.getElementById(DISPLAY_DATE_ID).text();
        String endDateString = StringUtils.substringBefore(dateRange, DISPLAY_DATE_SEPARATOR).trim();

        try{
            endDate = dateFormatter.parse(endDateString);}
        catch(ParseException pe){
            throw new BinCollectionGatewayException("Failed to parse end date in Bin Schedule. Value - " + endDateString);
        }
        this.displayEndDate = endDate;
    }

    private List<String> getCollectionItems(Elements listItems){
        List<String> collectionItems = new ArrayList<String>();

        for (Element li: listItems){
            collectionItems.add(li.text());
        }
        return collectionItems;
    }

    public static void main (String args[]){

        String selector = "body > div > div:nth-child(1) > h3";


    }
}