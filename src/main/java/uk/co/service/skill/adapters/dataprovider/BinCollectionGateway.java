package uk.co.service.skill.adapters.dataprovider;

import org.jsoup.HttpStatusException;
import uk.co.service.skill.LoggingFacade;
import uk.co.service.skill.adapters.dataprovider.web.BasicWebDocumentClient;
import uk.co.service.skill.adapters.dataprovider.web.WebDocumentClient;
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

 */
public class BinCollectionGateway implements GetBinCollectionForProperty, LoggingFacade {

    private final String encoding = "UTF-8";
    private String addressNotFoundResponseText = "No results found for the search text provided";
    private String serviceProviderUrlBase = "https://lisburn.isl-fusion.com";
    private String serviceProviderUrlAddressPath = "/address";
    private String serviceProviderUrlCollectionPath = "/view";
    private WebDocumentClient webDocumentClient = new BasicWebDocumentClient();

    public BinCollectionGateway(){};

    public BinCollectionGateway(String serviceProviderUrlBase, String serviceProviderUrlAddressPath, String serviceProviderUrlCollectionPath, String addressNotFoundResponseText, WebDocumentClient webDocumentClient){
        this.serviceProviderUrlBase = serviceProviderUrlBase;
        this.serviceProviderUrlAddressPath = serviceProviderUrlAddressPath;
        this.serviceProviderUrlCollectionPath = serviceProviderUrlCollectionPath;
        this.addressNotFoundResponseText = addressNotFoundResponseText;
        this.webDocumentClient = webDocumentClient;
    }

    public String getBinCollectionScheduleEndpointForProperty(String firstLineOfAddress) throws BinCollectionGatewayException, PropertyNotFoundException{

        logger().debug("Entering getBinCollectionScheduleEndpointForProperty");

        try {
            String htmlWithEndpoint = getHtmlEncodedCollectionPathForAddress(firstLineOfAddress);
            String collectionEndpointPart = getCollectionEndpointPartFromHtml(htmlWithEndpoint);
            String collectionEndpoint = buildUrl(this.serviceProviderUrlBase, this.serviceProviderUrlCollectionPath, collectionEndpointPart);
            return collectionEndpoint;
        }

        catch (Exception ex){
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
     * Extracts an HTML document from a source JSON document.
     * The source JSON document is retrieved from a
     * service provider endpoint.
     *
     * @param  firstLineOfAddress The first line of the address used as
     *                 input to the service provider's address index.
     * @throws IOException (optional) from getWebDocument
     * @throws PropertyNotFoundException (optional) if the request to address index retusn
     */

    String getHtmlEncodedCollectionPathForAddress(String firstLineOfAddress) throws IOException, PropertyNotFoundException {

        String addressEndpoint = buildUrl(this.serviceProviderUrlBase, this.serviceProviderUrlAddressPath, firstLineOfAddress);
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

    String getCollectionEndpointPartFromHtml(String htmlWithCollectionEndpoint) throws Exception{

        Integer endpointStartPosition = 8;
        Integer endpointTrailingCharacterCount = 3;

        Document doc = Jsoup.parse(htmlWithCollectionEndpoint);
        Element link = doc.select("a").first();
        String linkHref = link.attr("href");

        if (linkHref.length() <= endpointStartPosition){ throw new Exception("Failed to identify valid endpoint HTML. Received - " + linkHref.toString());};

        linkHref =  linkHref.substring(endpointStartPosition, (linkHref.length() - endpointTrailingCharacterCount));

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
        GetBinCollectionForProperty gt = new BinCollectionGateway("https://lisburn.isl-fusion.com", "/address", "/view", "No results found for the search text provided", new BasicWebDocumentClient() {
        });

        try {
            String htmlWithEndpoint = gt.getBinCollectionScheduleEndpointForProperty(" 61 kesh road ");
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
