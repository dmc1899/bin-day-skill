package uk.co.service.skill.adapters.dataprovider.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.service.skill.adapters.dataprovider.BinCollectionGateway;
import uk.co.service.skill.adapters.dataprovider.PropertyNotFoundException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static uk.co.service.skill.test.common.TestUtils.getResourceContentsAsString;

@RunWith(MockitoJUnitRunner.class)
public class TestAddressPageParser {

    private BinCollectionGateway binCollectionGateway;
    private AddressPageParser addressPageParser;

    @Before
    public void setUp(){
    }

    @After
    public void tearDown(){
    }

    @Test
    public void getScheduleUrl() throws PropertyNotFoundException{

        String singleAddressJson = getResourceContentsAsString("/SingleAddressFound.json");
        String expectedOutput = "nsatFRHo9XP4h1qM";
        addressPageParser = new AddressPageParser(singleAddressJson);
        String actualOutput = addressPageParser.getAddressUrl();

        assertEquals(expectedOutput,actualOutput);
    }

    @Test(expected = PropertyNotFoundException.class)
    public void failedToGetScheduleEndpoint() throws PropertyNotFoundException{

        String singleAddressJson = getResourceContentsAsString("/NoAddressFound.json");

        addressPageParser = new AddressPageParser(singleAddressJson);
        String actualOutput = addressPageParser.getAddressUrl();


    }

    @Test
    public void getLargeScheduleUrl() throws PropertyNotFoundException{

        String expectedOutput = "nsatFRHo9XP4h1qMnsatFRHo9XP4h1qMnsatFRHo9XP4h1qMnsatFRHo9XP4h1qMnsatFRHo9XP4h1qMnsatFRHo9XP4h1qM";

        String singleAddressJson = getResourceContentsAsString("/SingleLargeAddressFound.json");

        addressPageParser = new AddressPageParser(singleAddressJson);
        String actualOutput = addressPageParser.getAddressUrl();

        assertEquals(expectedOutput,actualOutput);
    }


}
