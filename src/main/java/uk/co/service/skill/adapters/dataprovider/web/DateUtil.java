package uk.co.service.skill.adapters.dataprovider.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static final String PREFERRED_DATE_FORMAT = "yyyy-MM-dd";
    private static final String ISO_8601_UTC_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";
    private static final String TIMEZONE = "UTC";

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
        return unformattedDate;
    }
}
