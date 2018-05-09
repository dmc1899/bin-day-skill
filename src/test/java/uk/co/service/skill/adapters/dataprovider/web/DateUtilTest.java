package uk.co.service.skill.adapters.dataprovider.web;

import org.junit.Test;
import uk.co.service.skill.adapters.dataprovider.exceptions.ServiceProviderUnavailableException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DateUtilTest {

    @Test
    public void parseValidDates() throws Exception{

        List<String> datesInWordsList = new ArrayList<String>();
        datesInWordsList.add("Monday the 7th May 2018");
        datesInWordsList.add("Monday 7th May 2018");
        datesInWordsList.add("Monday the 7th of May");
        datesInWordsList.add("Monday the 7th May");
        datesInWordsList.add("Monday 7th May");

        Date expectedDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String dateInString = "07-05-2018";
        expectedDate = sdf.parse(dateInString);

        for (String inputDate: datesInWordsList){
            assertEquals(expectedDate, DateUtil.parseDateInWordStringToDate(inputDate));
        }
    }

    @Test(expected = ParseException.class)
    public void singleStringNoSpacesInDate() throws Exception{

        DateUtil.parseDateInWordStringToDate("9999999999");

        fail("Exception expected but failed to materialise.");
    }

    @Test(expected = ParseException.class)
    public void invalidDate() throws Exception{

        DateUtil.parseDateInWordStringToDate("Monnday 77th mmay 22001188");

        fail("Exception expected but failed to materialise.");
    }
}
