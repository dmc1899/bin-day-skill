package uk.co.service.skill.adapters.dataprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.service.skill.adapters.dataprovider.exceptions.PropertyNotFoundException;
import uk.co.service.skill.adapters.dataprovider.exceptions.ServiceProviderUnavailableException;
import uk.co.service.skill.adapters.dataprovider.web.BasicWebDocumentClient;
import uk.co.service.skill.adapters.dataprovider.web.WebDocumentClient;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;
import static uk.co.service.skill.test.common.TestUtils.getResourceContentsAsString;

@RunWith(MockitoJUnitRunner.class)
public class LcccGetBinCollectionScheduleGatewayTest {

    private static final String DUMMY_VALUE_IGNORED = "";
    private LcccGetBinCollectionScheduleGateway binCollectionGateway;

    @Test
    public void getAddressScheduleUrl(){

        String actualAddressUrl = null;
        String mockAddressJson = getResourceContentsAsString("/SingleAddressFound.json");
        String expectedAddressUrl = "https://lisburn.isl-fusion.com/view/nsatFRHo9XP4h1qM";

        WebDocumentClient webDocumentClientSpy = Mockito.spy(new BasicWebDocumentClient());
        doReturn(mockAddressJson).when(webDocumentClientSpy).getWebDocument(anyString());

        binCollectionGateway = new LcccGetBinCollectionScheduleGateway("https://lisburn.isl-fusion.com","/address","/view","No results found for the search text provided", webDocumentClientSpy);

        try{
            actualAddressUrl = binCollectionGateway.getBinCollectionScheduleUrlForProperty(DUMMY_VALUE_IGNORED);
        }
        catch (PropertyNotFoundException e){
            fail("Property was not found, but should have been found.");
        }

        assertEquals(expectedAddressUrl, actualAddressUrl);
    }

    @Test(expected = PropertyNotFoundException.class)
    public void noAddressFound() throws Exception{

        String actualAddressUrl = null;
        String mockAddressJson = getResourceContentsAsString("/NoAddressFound.json");
        String expectedAddressUrl = "https://lisburn.isl-fusion.com/view/nsatFRHo9XP4h1qM";

        WebDocumentClient webDocumentClientSpy = Mockito.spy(new BasicWebDocumentClient());
        doReturn(mockAddressJson).when(webDocumentClientSpy).getWebDocument(anyString());

        binCollectionGateway = new LcccGetBinCollectionScheduleGateway("https://lisburn.isl-fusion.com","/address","/view","No results found for the search text provided", webDocumentClientSpy);

        actualAddressUrl = binCollectionGateway.getBinCollectionScheduleUrlForProperty(DUMMY_VALUE_IGNORED);

        fail("Exception should have been thrown to indiciate property was not found, but it was not.");
    }

    @Test(expected = ServiceProviderUnavailableException.class)
    public void serviceProviderUnavailable() throws Exception{

        String actualAddressUrl = null;
        String mockAddressJson = getResourceContentsAsString("/NoAddressFound.json");
        String expectedAddressUrl = "https://lisburn.isl-fusion.com/view/nsatFRHo9XP4h1qM";

        WebDocumentClient webDocumentClientSpy = Mockito.spy(new BasicWebDocumentClient());
        doThrow(ServiceProviderUnavailableException.class).when(webDocumentClientSpy).getWebDocument(anyString());

        binCollectionGateway = new LcccGetBinCollectionScheduleGateway("https://lisburn.isl-fusion.com","/address","/view","No results found for the search text provided", webDocumentClientSpy);

        actualAddressUrl = binCollectionGateway.getBinCollectionScheduleUrlForProperty(DUMMY_VALUE_IGNORED);

        fail("Exception should have been thrown to indiciate service was unavailable, but it was not.");
    }
}