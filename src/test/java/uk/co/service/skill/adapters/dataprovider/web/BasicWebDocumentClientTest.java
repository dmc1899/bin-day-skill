package uk.co.service.skill.adapters.dataprovider.web;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import uk.co.service.skill.adapters.dataprovider.exceptions.ServiceProviderUnavailableException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BasicWebDocumentClientTest {

    private final WebDocumentClient webDocumentClient = new BasicWebDocumentClient();

    @Test(expected = ServiceProviderUnavailableException.class)
    public void getHttpStatusException() throws Exception{

        String result = null;

        WebDocumentClient webDocumentClient = new BasicWebDocumentClient();
        String actualJsonResult = webDocumentClient.getWebDocument("https://httpstat.us/404");

        fail("Exception expected but failed to materialise.");
    }

    @Test
    public void getValidJsonDocument() throws Exception{

        String result = null;
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        WebDocumentClient webDocumentClient = new BasicWebDocumentClient();
        String actualJsonResult = webDocumentClient.getWebDocument(url);

        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(actualJsonResult).getAsJsonObject();
        }
        catch (IllegalStateException e){
            fail("Failed to retrieve valid JSON document from " + url);
        }
    }

    @Test
    public void getValidHtmlDocument() throws Exception{

        String actualDocumentTitle = null;
        String expectedDocumentTitle = "Google";
        String url = "https://www.google.com";

        WebDocumentClient webDocumentClient = new BasicWebDocumentClient();
        String actualHtmlResult = webDocumentClient.getWebDocument(url);

        try {
            Document doc = Jsoup.parse(actualHtmlResult);
            actualDocumentTitle = doc.title();

        }
        catch (Exception e){
            fail("Failed to retrieve valid HTML document from " + url);
        }

        assertEquals(expectedDocumentTitle, actualDocumentTitle);
    }
}
