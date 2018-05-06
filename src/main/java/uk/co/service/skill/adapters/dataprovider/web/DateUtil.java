package uk.co.service.skill.adapters.dataprovider.web;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.valueOf;

public class DateUtil {

    private static final String FULL_MONTH_DATE_FORMAT_PATTERN = "yyyy-MMMM-dd";
    private static final String PREFERRED_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private SimpleDateFormat PREFERRED_DATE_FORMAT = new SimpleDateFormat(PREFERRED_DATE_FORMAT_PATTERN);
    private static final String ISO_8601_UTC_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";
    private static final String TIMEZONE = "UTC";
    private static final String DATE_TOKEN_SEPERATOR = " ";
    private static final Integer DATE_INCLUDES_YEAR = 4;
    private static final Integer DATE_EXCLUDES_YEAR = 3;

    public static String dateToIso8601UtcString(Date date) {
        TimeZone tz = TimeZone.getTimeZone(TIMEZONE);
        DateFormat df = new SimpleDateFormat(ISO_8601_UTC_DATE_TIME_FORMAT);
        df.setTimeZone(tz);
        return df.format(date);
    }

    public static Date iso8601StringToDate(String dateStr) {
        TimeZone tz = TimeZone.getTimeZone(TIMEZONE);
        DateFormat df = new SimpleDateFormat(ISO_8601_UTC_DATE_TIME_FORMAT);
        df.setTimeZone(tz);

        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatDate (String unformattedDate){
        return unformattedDate; //PREFERRED_DATE_FORMAT.format(new Date(unformattedDate));
    }

    public static Date parseStringToDate(String dateInWords) throws Exception{

        String dayOfMonth = null;
        String dateOfMonth = null;
        String monthName = null;
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> dateTokenList = new ArrayList<String>(Arrays.asList(StringUtils.split(dateInWords, DATE_TOKEN_SEPERATOR)));

        dateTokenList = removeUnimportantWords(dateTokenList);

        if (dateTokenList.size()== DATE_INCLUDES_YEAR){
            year = Integer.parseInt(dateTokenList.get(3));
        }

        monthName = dateTokenList.get(2);

        dayOfMonth = StringUtils.removeAll(dateTokenList.get(1), "[a-z]|[A-Z]");

        String parsedDate = StringUtils.join( year.toString(), "-",monthName,"-", dayOfMonth);
        SimpleDateFormat fullMonthDateformat = new SimpleDateFormat(FULL_MONTH_DATE_FORMAT_PATTERN);

        return fullMonthDateformat.parse(parsedDate);
    }

    private static ArrayList<String> removeUnimportantWords(ArrayList<String> dateTokenList) {
        ArrayList<String> unimportantWords = new ArrayList<String>(Arrays.asList("of", "the", "in", "out"));
        dateTokenList.removeAll(unimportantWords);
        return dateTokenList;
    }

//    private static void validateDayOfWeek(String retrievedDay) throws Exception {
//        //final String MONDAY = DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.UK);
//        switch (retrievedDay){
//            case MONDAY:
//        }
//    }

    public static void main (String args[]){
        try {


            // Tuesday 8th January 2019

            // Monday the 7th May 2019
            // Monday 7th May 2019
            // Monday the 7th of May
            // Monday the 7th May
            // Monday 7th May
            Date thisdate = parseStringToDate("Monday the 7th May 2019");
            System.out.println(thisdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
