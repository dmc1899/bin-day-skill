package uk.co.service.skill.adapters.dataprovider.web;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import uk.co.service.skill.LoggingFacade;
import uk.co.service.skill.adapters.dataprovider.PropertyNotFoundException;

public class AddressPageParser implements LoggingFacade {

    private static final String ADDRESS_NOT_FOUND_TEXT = "No results found for the search text provided";
    private static final String SERVICE_PROVIDER_SCHEDULE_URL_PATH = "/view";

    private final String json;

    public AddressPageParser(String json) {
        this.json = json;
    }

    public String getAddressUrl() throws PropertyNotFoundException {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonElement htmlItem = jsonObject.get("html");

        String htmlPayload = htmlItem.toString();

        if (StringUtils.isEmpty(htmlPayload) || StringUtils.isBlank(htmlPayload) || htmlPayload.contains(ADDRESS_NOT_FOUND_TEXT) || !(htmlPayload.contains(SERVICE_PROVIDER_SCHEDULE_URL_PATH))){
            throw new PropertyNotFoundException("Failed to correctly identify Bin Collection Schedule.");
        }

        Integer endpointStartPosition = 8;
        Integer endpointTrailingCharacterCount = 3;

        Document doc = Jsoup.parse(htmlPayload);
        Element link = doc.select("a").first();
        String linkHref = link.attr("href");

        if (linkHref.length() <= endpointStartPosition){ throw new PropertyNotFoundException("Failed to identify valid endpoint HTML. Received - " + linkHref.toString());};

        linkHref =  linkHref.substring(endpointStartPosition, (linkHref.length() - endpointTrailingCharacterCount));

        return linkHref;
    }
}
