package uk.co.service.skill.adapters.dataprovider.web;

import org.apache.commons.collections4.MultiMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.service.skill.adapters.dataprovider.LcccGetBinCollectionScheduleGateway;
import uk.co.service.skill.adapters.dataprovider.exceptions.PropertyNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static uk.co.service.skill.test.common.TestUtils.getResourceContentsAsString;

@RunWith(MockitoJUnitRunner.class)
public class SchedulePageParserTest {

    private SchedulePageParser schedulePageParser;

    @Before
    public void setUp(){
    }

    @After
    public void tearDown(){
    }

    @Test
    public void getScheduleUrl() {

        String singleScheduleHtml = getResourceContentsAsString("/SingleScheduleFound.html");
        schedulePageParser = new SchedulePageParser(singleScheduleHtml);
        MultiMap thisMultiMap = schedulePageParser.getCollectionSchedule();
        System.out.println(thisMultiMap);

    }


}
