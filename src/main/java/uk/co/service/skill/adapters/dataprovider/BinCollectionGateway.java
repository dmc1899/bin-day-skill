package uk.co.service.skill.adapters.dataprovider;

import org.jsoup.HttpStatusException;
import uk.co.service.skill.LoggingFacade;
import uk.co.service.skill.entities.PropertyBinCollectionSchedule;
import uk.co.service.skill.usecases.bincollection.outbound.GetBinCollectionForProperty;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.*;

import org.apache.commons.lang3.*;

/**
 * Sorts the specified list according to the order induced by the
 * specified comparator.  All elements in the list must be <i>mutually
 * comparable</i> using the specified comparator (that is,
 * {@code c.compare(e1, e2)} must not throw a {@code ClassCastException}
 * for any elements {@code e1} and {@code e2} in the list).
 *
 * @param  list the list to be sorted.
 * @param  c the comparator to determine the order of the list.  A
 *        {@code null} value indicates that the elements' <i>natural
 *        ordering</i> should be used.
 * @throws ClassCastException if the list contains elements that are not
 *         <i>mutually comparable</i> using the specified comparator.
 * @throws UnsupportedOperationException if the specified list's
 *         list-iterator does not support the {@code set} operation.
 * @throws IllegalArgumentException (optional) if the comparator is
 *         found to violate the {@link Comparator} contract
 */
public class BinCollectionGateway implements GetBinCollectionForProperty, LoggingFacade {

    private final String encoding = "UTF-8";
    private String addressNotFoundResponseText = "No results found for the search text provided";
    private String serviceProviderUrlBase = "https://lisburn.isl-fusion.com";
    private String serviceProviderUrlAddressPath = "/address";
    private String serviceProviderUrlCollectionPath = "/view";

    public BinCollectionGateway(){};

    public BinCollectionGateway(String serviceProviderUrlBase, String serviceProviderUrlAddressPath, String serviceProviderUrlCollectionPath, String addressNotFoundResponseText){
        this.serviceProviderUrlBase = serviceProviderUrlBase;
        this.serviceProviderUrlAddressPath = serviceProviderUrlAddressPath;
        this.serviceProviderUrlCollectionPath = serviceProviderUrlCollectionPath;
        this.addressNotFoundResponseText = addressNotFoundResponseText;
    }

    public String getBinCollectionScheduleEndpointForProperty(String firstLineOfAddress) throws BinCollectionGatewayException, PropertyNotFoundException{

        logger().debug("Entering getBinCollectionScheduleEndpointForProperty");

        try {
            String htmlWithEndpoint = getHtmlEncodedCollectionPathForAddress(firstLineOfAddress);
            String collectionEndpointPart = getCollectionEndpointPartFromHtml(htmlWithEndpoint);
            String collectionEndpoint = buildUrl(this.serviceProviderUrlBase, this.serviceProviderUrlCollectionPath, collectionEndpointPart);
            return collectionEndpoint;
        }

        catch (IOException ex){
            throw new BinCollectionGatewayException(ex.getMessage());
        }

        finally {
            logger().debug("Exiting getBinCollectionScheduleEndpointForProperty");
        }
    }


    public PropertyBinCollectionSchedule getBinCollectionScheduleForProperty(String endPoint) {
        PropertyBinCollectionSchedule binSchedule = new PropertyBinCollectionSchedule();
        binSchedule.setTestVal("bin collection schedule (taken from " + endPoint);

        return binSchedule;
    }

    /**
     * Extracts an HTML document containing an embedded URL
     * from a source JSON document retrieved from a
     * service provider.
     *
     * @param  address The first line of the address used as
     *                 input to the service provider's address index.
     * @throws IOException (optional) from getWebDocument
     * @throws PropertyNotFoundException (optional) if the request to address index retusn
     */
    String getHtmlEncodedCollectionPathForAddress(String address) throws IOException, PropertyNotFoundException {

        String addressEndpoint = buildUrl(this.serviceProviderUrlBase, this.serviceProviderUrlAddressPath, address);
        String json = getWebDocument(addressEndpoint);

        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonElement htmlItem = jsonObject.get("html");

        String htmlPayload = htmlItem.toString();

        if (StringUtils.isEmpty(htmlPayload) || StringUtils.isBlank(htmlPayload) || htmlPayload.contains(this.addressNotFoundResponseText) || !(htmlPayload.contains(this.serviceProviderUrlCollectionPath))){
            throw new PropertyNotFoundException("Failed to correctly identify Bin Collection Schedule from: " + addressEndpoint);
        }

        return htmlPayload;
    }

    String getWebDocument(String addressEndpoint) throws IOException {

        final Integer retryLimit = 5;
        String response = null;

        Integer attemptCount = 0;
        boolean succeeded = false;

        while ((!succeeded) && (attemptCount < retryLimit)) {
            try {
                attemptCount += 1;
                response = Jsoup.connect(addressEndpoint).ignoreContentType(true).execute().body();
                succeeded = true;

            } catch (HttpStatusException he) {
                logger().debug("HTTP Status Exception returned from endpoint " + addressEndpoint + " - attempt " + attemptCount.toString() + " of " + retryLimit.toString());

                if (attemptCount == retryLimit) {
                    throw new IOException("Failed to connect to " + addressEndpoint + ". Service unavailable.");
                }
            }
        }
        return response;
    }

    String getCollectionEndpointPartFromHtml(String htmlWithCollectionEndpoint){

        Document doc;
        doc = Jsoup.parse(htmlWithCollectionEndpoint);
        Element link = doc.select("a").first();
        String linkHref = link.attr("href");
        linkHref =  linkHref.substring(8, (linkHref.length() - 3));

        return linkHref;
    }

    String buildUrl(String baseUrl, String path, String resource) throws UnsupportedEncodingException{
        String builtUrl = null;

        String encodedResource = URLEncoder.encode(resource.trim(), encoding);
        builtUrl = new StringBuilder().append(baseUrl).append(path).append("/").append(encodedResource).toString();

        return builtUrl;
    }




    private String getHtmlPage(String endPoint){
        Document doc;

        try{
            doc = Jsoup.connect(endPoint).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "HTML";
    }

    private void validateHtmlPage(String page, Map<String,Boolean> validationItems){

    }

    private String extractElementFromHtmlPage(String element){
        return "retrieved element";

    }

    public static void main (String args[]) throws IOException{


        //1. Gateway to website
        GetBinCollectionForProperty gt = new BinCollectionGateway("https://lisburn.isl-fusion.com","/address","/view","No results found for the search text provided");

        try {
            String htmlWithEndpoint = gt.getBinCollectionScheduleEndpointForProperty(" 61111 kesh road ");
            System.out.println(htmlWithEndpoint);
        }
        catch (BinCollectionGatewayException ex){

        } catch (PropertyNotFoundException e) {
            e.printStackTrace();
        }
//        String collectionEndpointPart = gt.getCollectionEndpointPartFromHtml(htmlWithEndpoint);
//        System.out.println(collectionEndpointPart);
//
//        String collectionEndpoint = gt.buildUrl(gt.serviceProviderUrlBase, gt.serviceProviderUrlCollectionPath, collectionEndpointPart);
//        System.out.println(collectionEndpoint);

    }
}
