package uk.co.service.skill.adapters.dataprovider.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import uk.co.service.skill.LoggingFacade;

import org.apache.commons.lang3.StringUtils;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

public class SchedulePageParser implements LoggingFacade {

    private static final String ADDRESS_NOT_FOUND_TEXT = "No results found for the search text provided";
    private static final String SERVICE_PROVIDER_SCHEDULE_URL_PATH = "/address";
    private static final String DATE_DISPLAY_SELECTOR = "dateDisplay";
    private static final String DATE_DISPLAY_SEPARATOR = "-";

    private final String html;
    private final Document document;
    private  String startDate;
    private  String endDate;

    public SchedulePageParser(String html) {
        this.html = html;
        this.document = Jsoup.parse(html);
        setDateRangeValues(this.document);
    }

    public String getFirstLineOfAddress(){
        return "";
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public String getSchedule() throws Exception {

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

    private void setDateRangeValues(Document document){
        String dateRange = document.getElementById(DATE_DISPLAY_SELECTOR).text();
        String startDate = StringUtils.substringBefore(dateRange,DATE_DISPLAY_SEPARATOR).trim();
        String endDate = StringUtils.substringAfter(dateRange,DATE_DISPLAY_SEPARATOR).trim();
        this.startDate = DateUtil.formatDate(startDate); //Do we format here or outside this class?
        this.endDate = DateUtil.formatDate(endDate);
    }
}