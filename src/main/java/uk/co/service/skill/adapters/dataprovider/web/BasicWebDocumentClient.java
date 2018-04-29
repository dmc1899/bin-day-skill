package uk.co.service.skill.adapters.dataprovider.web;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import uk.co.service.skill.LoggingFacade;
import uk.co.service.skill.adapters.dataprovider.PropertyNotFoundException;
import uk.co.service.skill.adapters.dataprovider.ServiceProviderUnavailableException;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class BasicWebDocumentClient implements WebDocumentClient, LoggingFacade {

//private WebDocumentClientRequestor webDocumentClientRequestor = new WebDocumentClientRequestor();

    public String getJsonWebDocument (String url)  {

        String result = null;
        //result = Jsoup.connect(url).ignoreContentType(true).execute().body();
        try {
            result = Jsoup.connect(url).ignoreContentType(true).execute().body();
        }
        catch (IllegalArgumentException e){        throw new IllegalArgumentException();}


        catch (IOException e) {
            throw new RuntimeException();

        }
        return result;
    }


    public Integer method1(int a){return 1 + a;};
    public Integer method2(int a){return 2 + a;};

    public Integer test(Function<Integer, Integer> someFunction){
        return someFunction.apply(1);
    }

    public static void main(String args[]){

        //Consumer<String> c = s -> System.out.println(s);

        BasicWebDocumentClient b = new BasicWebDocumentClient();
        Integer result = b.test(b::method1);
        System.out.println(result);
         result = b.test(b::method2);
        System.out.println(result);
    }

    @Override
    public String getHtml(String url) {
        return null;
    }

    @Override
    public String getJson(String url) {

        String result = null;
        try {
            result = this.getWebDocument(url, this::getJsonWebDocument);
        }
        catch (Exception e){}
        return result;
    }


    private String getWebDocument(String url, Function<String, String> getThisWebDocument) {
        // TODO - find a way of injecting a different implementation of of the request to get content
        // so that this method can be re-used by Json and HTml
        final Integer retryLimit = 5;
        String response = null;

        Integer attemptCount = 0;
        boolean succeeded = false;

        while ((!succeeded) && (attemptCount < retryLimit)) {
            try {
                attemptCount += 1;
                response = getThisWebDocument.apply(url);
                succeeded = true;

            } catch (Exception he) {
                if (he instanceof IOException){
                    System.out.println("IOException");
                }
                logger().debug("HTTP Status Exception returned from endpoint " + url + " - attempt " + attemptCount.toString() + " of " + retryLimit.toString());

                if (attemptCount == retryLimit) {
                    throw new ServiceProviderUnavailableException("Failed to connect to " + url + " after " + attemptCount.toString() + " attempts due to: " + he.getMessage());
                }

                try {
                    Thread.sleep(1000 * attemptCount);
                } catch (InterruptedException e) {
                    throw new ServiceProviderUnavailableException("Failed to connect to " + url + " after trying to delay and retry. Primary cause: " + e.getMessage());
                }


//            } catch (HttpStatusException e) {
//                throw new ServiceProviderUnavailableException("Failed to connect to " + url + " due to: " + e.getMessage());
//
//            }

            }
        }
        return response;
    }

}
