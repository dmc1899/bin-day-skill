package uk.co.service.skill.adapters.dataprovider;

import uk.co.service.skill.LocalLogger;
import uk.co.service.skill.Loggable;
import uk.co.service.skill.MyLocalLogger;
import uk.co.service.skill.entities.PropertyBinCollectionSchedule;
import uk.co.service.skill.usecases.bincollection.MyLoggable;
import uk.co.service.skill.usecases.bincollection.outbound.GetBinCollectionForProperty;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.*;

public class BinCollectionGateway implements GetBinCollectionForProperty {

    private final String encoding = "UTF-8";
    private String serviceProviderUrlBase = "https://lisburn.isl-fusion.com";
    private String serviceProviderUrlAddressPath = "/address";
    private String serviceProviderUrlCollectionPath = "/view";
    private LocalLogger logger;
    private Loggable loggable;


    public BinCollectionGateway(String serviceProviderUrlBase, String serviceProviderUrlAddressPath, String serviceProviderUrlCollectionPath, LocalLogger logger, Loggable loggable){
        this.serviceProviderUrlBase = serviceProviderUrlBase;
        this.serviceProviderUrlAddressPath = serviceProviderUrlAddressPath;
        this.serviceProviderUrlCollectionPath = serviceProviderUrlCollectionPath;
        this.logger = logger;
        this.loggable = loggable;
    }

    public String getBinCollectionScheduleEndpointForProperty(String firstLineOfAddress) throws BinCollectionGatewayException{

        try {
            String htmlWithEndpoint = getHtmlContainingEndpointFromAddressEndpoint(firstLineOfAddress);
            String collectionEndpointPart = getCollectionEndpointPartFromHtml(htmlWithEndpoint);
            String collectionEndpoint = buildUrl(this.serviceProviderUrlBase, this.serviceProviderUrlCollectionPath, collectionEndpointPart);
            return collectionEndpoint;
        }

        catch (IOException ex){
            throw new BinCollectionGatewayException(ex.getMessage());
        }

        finally {
            logger.log("Exiting getBinCollectionScheduleEndpointForProperty");
            loggable.logger().debug("This is a debug statement from the Loggable");
        }
    }


    private String getHtmlContainingEndpointFromAddressEndpoint(String address) throws IOException {

        String addressEndpoint = buildUrl(this.serviceProviderUrlBase, this.serviceProviderUrlAddressPath, address);

        String json = Jsoup.connect(addressEndpoint).ignoreContentType(true).execute().body();
        System.out.println(json);

        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonElement htmlItem = jsonObject.get("html");

        System.out.println(htmlItem);
        return htmlItem.toString();
    }

    private String getCollectionEndpointPartFromHtml(String htmlWithCollectionEndpoint){

        Document doc;
        doc = Jsoup.parse(htmlWithCollectionEndpoint);
        Element link = doc.select("a").first();
        String linkHref = link.attr("href");
        linkHref =  linkHref.substring(8, (linkHref.length() - 3));

        return linkHref;

    }

    private String buildUrl(String baseUrl, String path, String resource) throws UnsupportedEncodingException{
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


        //0. Create a Logger
        LocalLogger myNewLogger = new MyLocalLogger();
        Loggable myLoggable = new MyLoggable();

        //1. Gateway to website
        GetBinCollectionForProperty gt = new BinCollectionGateway("https://lisburn.isl-fusion.com","/address","/view", myNewLogger, myLoggable);

        try {
            String htmlWithEndpoint = gt.getBinCollectionScheduleEndpointForProperty(" 61 kesh road ");
            System.out.println(htmlWithEndpoint);
        }
        catch (BinCollectionGatewayException ex){
            myNewLogger.log(ex.getMessage());
        }
//        String collectionEndpointPart = gt.getCollectionEndpointPartFromHtml(htmlWithEndpoint);
//        System.out.println(collectionEndpointPart);
//
//        String collectionEndpoint = gt.buildUrl(gt.serviceProviderUrlBase, gt.serviceProviderUrlCollectionPath, collectionEndpointPart);
//        System.out.println(collectionEndpoint);

    }
}
