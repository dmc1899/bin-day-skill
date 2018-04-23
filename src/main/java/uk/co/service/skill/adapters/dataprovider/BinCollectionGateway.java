package uk.co.service.skill.adapters.dataprovider;

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


public class BinCollectionGateway implements GetBinCollectionForProperty, LoggingFacade {

    private String addressNotFoundResponseText = "No results found for the search text provided";
    private final String encoding = "UTF-8";
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

        try {
            String htmlWithEndpoint = getHtmlEncodedCollectionPathForAddress(firstLineOfAddress);
            String collectionEndpointPart = getCollectionEndpointPartFromHtml(htmlWithEndpoint);
            String collectionEndpoint = buildUrl(this.serviceProviderUrlBase, this.serviceProviderUrlCollectionPath, collectionEndpointPart);
            return collectionEndpoint;
        }

        catch (PropertyNotFoundException ex){
            throw ex;
        }
        catch (IOException ex){
            throw new BinCollectionGatewayException(ex.getMessage());
        }

        finally {
            logger().debug("This is a debug statement from the LoggingFacade");
        }
    }


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
        //TODO - Looks like this can be returned "java.lang.IllegalArgumentException: Must supply a valid URL" from this JSOUP command.
        return Jsoup.connect(addressEndpoint).ignoreContentType(true).execute().body();
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


    public PropertyBinCollectionSchedule getBinCollectionScheduleForProperty(String endPoint) {
        PropertyBinCollectionSchedule binSchedule = new PropertyBinCollectionSchedule();
        binSchedule.setTestVal("bin collection schedule (taken from " + endPoint);

        return binSchedule;
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
