package uk.co.service.skill.adapters.dataprovider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.service.skill.adapters.dataprovider.web.BasicWebDocumentClient;

import static org.mockito.Mockito.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static uk.co.service.skill.test.common.TestUtils.getResourceContentsAsString;

@RunWith(MockitoJUnitRunner.class)
public class BinCollectionGatewayTest {

    private BinCollectionGateway binCollectionGateway;

    @Before
    public void setUp(){
        System.out.println("setup...");
        binCollectionGateway = new BinCollectionGateway("https://lisburn.isl-fusion.com","/address","/view","No results found for the search text provided", new BasicWebDocumentClient());
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
    public void extractEndpointFromValidHtml() throws Exception {

        String actualOutput = null;
        String expectedOutput = "nsatFRHo9XP4h1qM";
        String givenInput = getResourceContentsAsString("/SingleAddressFound.html");

        actualOutput = binCollectionGateway.getCollectionEndpointPartFromHtml(givenInput);
        System.out.println(actualOutput);

        assertEquals(actualOutput, expectedOutput);
    }


    @Test
    public void extractHtmlFromAddressSearchJsonResponse() throws Exception{

        String givenInput = getResourceContentsAsString("/SingleAddressFound.json");
        String expectedOutput = getResourceContentsAsString("/SingleAddressFound.html");
        BinCollectionGateway binCollectionGatewaySpy = Mockito.spy(new BinCollectionGateway("https://lisburn.isl-fusion.com", "/address", "/view", "No results found for the search text provided", new BasicWebDocumentClient()));
        doReturn(givenInput).when(binCollectionGatewaySpy).getWebDocument(anyString());

        String actualOutput = null;

        actualOutput = binCollectionGatewaySpy.getHtmlEncodedCollectionPathForAddress("input parameter does not matter here");
        System.out.println(actualOutput);

        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void getEndpointForAddress() throws Exception{

        String mockedSingleAddressJson = getResourceContentsAsString("/SingleAddressFound.json");
        String mockedSingleAddressHtml = getResourceContentsAsString("/SingleAddressFound.html");
        String mockedEndpointPart = "nsatFRHo9XP4h1qM";
        String mockedUrl = "https://lisburn.isl-fusion.com/view/nsatFRHo9XP4h1qM";
        String expectedOutput = "https://lisburn.isl-fusion.com/view/nsatFRHo9XP4h1qM";

        BinCollectionGateway binCollectionGatewaySpy = Mockito.spy(new BinCollectionGateway("https://lisburn.isl-fusion.com", "/address", "/view", "No results found for the search text provided", new BasicWebDocumentClient()));

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

        BinCollectionGateway binCollectionGatewaySpy = Mockito.spy(new BinCollectionGateway("https://lisburn.isl-fusion.com", "/address", "/view", "No results found for the search text provided", new BasicWebDocumentClient()));

        doReturn(mockedSingleAddressJson).when(binCollectionGatewaySpy).getWebDocument(anyString());

        String actualOutput = null;

        actualOutput = binCollectionGatewaySpy.getBinCollectionScheduleEndpointForProperty("input parameter ignored");
        System.out.println(actualOutput);

        assertEquals(expectedOutput, actualOutput);
    }

// We should consider a test here that does not Spy, but mocks entirely and returns different exceptions from private methods.
}
