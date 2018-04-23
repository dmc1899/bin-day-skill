package uk.co.service.skill.adapters.dataprovider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static uk.co.service.skill.test.common.TestUtils.getResourceContentsAsString;

@RunWith(MockitoJUnitRunner.class)
public class TestBinCollectionGateway {

    private BinCollectionGateway binCollectionGateway;

    @Before
    public void setUp(){
        System.out.println("setup...");
        binCollectionGateway = new BinCollectionGateway("https://lisburn.isl-fusion.com","/address","/view","No results found for the search text provided");
    }

    @After
    public void tearDown(){
        System.out.println("Tear down...");
        binCollectionGateway = null;
    }

    @Test(expected = IOException.class)
    public void testHttp404Returned() throws Exception{
        String result = null;
        result = binCollectionGateway.getWebDocument("https://httpstat.us/404");
    }

    @Test
    public void buildValidUrl() throws Exception{

        String result = null;
        String expectedUrl = "http://www.google.comnewpage/this+is+a+string+with+a+lot+of+%5E%26*%29%24%29%C2%A3%27%27%27%3F%3F+characters";

        result = binCollectionGateway.buildUrl("http://www.google.com", "newpage", "this is a string with a lot of ^&*)$)£'''?? characters");
        System.out.println(result);

        assertEquals(expectedUrl, result);
    }

    @Test
    public void buildValidComplexUrl() throws Exception{

        String result = null;
        String expectedUrl = "http://www.google.comnewpage/%C2%A7%C2%B11%212%40%E2%82%AC3%C2%A3%E2%80%B9%C2%A2%E2%88%9E%C2%A7%C2%B6%E2%80%A2%C2%AA%C2%BA%C2%BA0%C3%A6%E2%80%A6%E2%80%98%E2%80%9C%C2%AB%C2%AB%C2%AA%C2%BA%C2%BA0%C3%A6";

        result = binCollectionGateway.buildUrl("http://www.google.com", "newpage", "§±1!2@€3£‹¢∞§¶•ªºº0æ…‘“««ªºº0æ");
        System.out.println(result);

        assertEquals(expectedUrl, result);
    }

    @Test
    public void extractEndpointFromValidHtml() throws Exception {

        String actualOutput = null;
        String expectedOutput = "nsatFRHo9XP4h1qM";
        String givenInput = getResourceContentsAsString("/SingleAddressFound.html");

        actualOutput = binCollectionGateway.getCollectionEndpointPartFromHtml(givenInput);
        System.out.println(actualOutput);

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void extractLargeEndpointFromValidHtml() throws Exception{

        String actualOutput = null;
        String expectedOutput = "nsatFRHo9XP4h1qMnsatFRHo9XP4h1qMnsatFRHo9XP4h1qMnsatFRHo9XP4h1qMnsatFRHo9XP4h1qMnsatFRHo9XP4h1qM";
        String givenInput = getResourceContentsAsString("/SingleLargeAddressFound.html");

        actualOutput = binCollectionGateway.getCollectionEndpointPartFromHtml(givenInput);
        System.out.println(actualOutput);

        assertEquals(actualOutput, expectedOutput);
    }


    @Test
    public void extractHtmlFromAddressSearchJsonResponse() throws Exception{

        String givenInput = getResourceContentsAsString("/SingleAddressFound.json");
        String expectedOutput = getResourceContentsAsString("/SingleAddressFound.html");
        BinCollectionGateway binCollectionGatewaySpy = Mockito.spy(new BinCollectionGateway("https://lisburn.isl-fusion.com", "/address", "/view", "No results found for the search text provided"));
        doReturn(givenInput).when(binCollectionGatewaySpy).getWebDocument(anyString());

        String actualOutput = null;

        actualOutput = binCollectionGatewaySpy.getHtmlEncodedCollectionPathForAddress("input parameter does not matter here");
        System.out.println(actualOutput);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test(expected = PropertyNotFoundException.class)
    public void extractHtmlFromNotFoundAddressSearchJsonResponse() throws Exception{

        String givenInput = getResourceContentsAsString("/NoAddressFound.json");

        BinCollectionGateway binCollectionGatewaySpy = Mockito.spy(new BinCollectionGateway("https://lisburn.isl-fusion.com", "/address", "/view", "No results found for the search text provided"));
        doReturn(givenInput).when(binCollectionGatewaySpy).getWebDocument(anyString());

        String actualOutput =  binCollectionGatewaySpy.getHtmlEncodedCollectionPathForAddress("input parameter does not matter here");
    }

    @Test
    public void getEndpointForAddress() throws Exception{

        String mockedSingleAddressJson = getResourceContentsAsString("/SingleAddressFound.json");
        String mockedSingleAddressHtml = getResourceContentsAsString("/SingleAddressFound.html");
        String mockedEndpointPart = "nsatFRHo9XP4h1qM";
        String mockedUrl = "https://lisburn.isl-fusion.com/view/nsatFRHo9XP4h1qM";
        String expectedOutput = "https://lisburn.isl-fusion.com/view/nsatFRHo9XP4h1qM";

        BinCollectionGateway binCollectionGatewaySpy = Mockito.spy(new BinCollectionGateway("https://lisburn.isl-fusion.com", "/address", "/view", "No results found for the search text provided"));

//        doReturn(mockedSingleAddressJson).when(binCollectionGatewaySpy).getWebDocument(anyString());
        doReturn(mockedSingleAddressHtml).when(binCollectionGatewaySpy).getHtmlEncodedCollectionPathForAddress(anyString());
        doReturn(mockedEndpointPart).when(binCollectionGatewaySpy).getCollectionEndpointPartFromHtml(anyString());
        doReturn(mockedUrl).when(binCollectionGatewaySpy).buildUrl(anyString(), anyString(), anyString());

        String actualOutput = null;

        actualOutput = binCollectionGatewaySpy.getBinCollectionScheduleEndpointForProperty("input parameter ignored");
        System.out.println(actualOutput);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getEndpointForAddressMockedGetWebDocument() throws Exception{

        String mockedSingleAddressJson = getResourceContentsAsString("/SingleAddressFound.json");
        String expectedOutput = "https://lisburn.isl-fusion.com/view/nsatFRHo9XP4h1qM";

        BinCollectionGateway binCollectionGatewaySpy = Mockito.spy(new BinCollectionGateway("https://lisburn.isl-fusion.com", "/address", "/view", "No results found for the search text provided"));

        doReturn(mockedSingleAddressJson).when(binCollectionGatewaySpy).getWebDocument(anyString());

        String actualOutput = null;

        actualOutput = binCollectionGatewaySpy.getBinCollectionScheduleEndpointForProperty("input parameter ignored");
        System.out.println(actualOutput);

        assertEquals(expectedOutput, actualOutput);
    }

// We should consider a test here that does not Spy, but mocks entirely and returns different exceptions from private methods.
}
