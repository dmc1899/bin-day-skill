package uk.co.service.skill.adapters.dataprovider;

import org.junit.Test;
import uk.co.service.skill.usecases.bincollection.outbound.GetBinCollectionForProperty;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class TestBinCollectionGateway {

    private BinCollectionGateway myBinCollectionForProperty = new BinCollectionGateway("https://lisburn.isl-fusion.com","/address","/view");

    @Test
    public void buildValidUrl() {

        String result = null;
        String expectedUrl = "http://www.google.comnewpage/this+is+a+string+with+a+lot+of+%5E%26*%29%24%29%C2%A3%27%27%27%3F%3F+characters";
        try{ result = myBinCollectionForProperty.buildUrl("http://www.google.com", "newpage", "this is a string with a lot of ^&*)$)£'''?? characters");
        System.out.println(result);
        }
        catch (UnsupportedEncodingException ex){

        }
        assertEquals(expectedUrl, result);
    }

    @Test
    public void buildValidComplexUrl() {

        String result = null;
        String expectedUrl = "http://www.google.comnewpage/%C2%A7%C2%B11%212%40%E2%82%AC3%C2%A3%E2%80%B9%C2%A2%E2%88%9E%C2%A7%C2%B6%E2%80%A2%C2%AA%C2%BA%C2%BA0%C3%A6%E2%80%A6%E2%80%98%E2%80%9C%C2%AB%C2%AB%C2%AA%C2%BA%C2%BA0%C3%A6";
        try{
            result = myBinCollectionForProperty.buildUrl("http://www.google.com", "newpage", "§±1!2@€3£‹¢∞§¶•ªºº0æ…‘“««ªºº0æ");
            System.out.println(result);
        }
        catch (UnsupportedEncodingException ex){

        }
        assertEquals(expectedUrl, result);
    }

    @Test
    public void extractEndpointFromValidHtml() {

        String result = null;
        String givenInput = null;
        String expectedOutput = "nsatFRHo9XP4h1qM";

        // Read HTML from file into a string.
        java.net.URL url = TestBinCollectionGateway.class.getResource("/SingleAddressFound.html");
        java.nio.file.Path resPath = null;
        try {
            resPath = java.nio.file.Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            givenInput = new String(java.nio.file.Files.readAllBytes(resPath), "UTF8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            result = myBinCollectionForProperty.getCollectionEndpointPartFromHtml(givenInput);
            System.out.println(result);
        }
        catch (Exception ex){

        }
        assertEquals(result, expectedOutput);
    }
}
