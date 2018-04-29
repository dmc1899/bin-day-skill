package uk.co.service.skill.adapters.dataprovider.web;

import uk.co.service.skill.LoggingFacade;

public class SchedulePageParser implements LoggingFacade {

    private static final String ADDRESS_NOT_FOUND_TEXT = "No results found for the search text provided";
    private static final String SERVICE_PROVIDER_SCHEDULE_URL_PATH = "/address";

    private final String html;

    public SchedulePageParser(String html) {
        this.html = html;
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
}