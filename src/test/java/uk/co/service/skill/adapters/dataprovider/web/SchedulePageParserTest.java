package uk.co.service.skill.adapters.dataprovider.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

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
        List<CollectionScheduleEvent> collectionScheduleEvents = schedulePageParser.getCollectionSchedule();
        //MultiMap thisMultiMap = schedulePageParser.getCollectionSchedule();
        System.out.println(collectionScheduleEvents);

    }


}