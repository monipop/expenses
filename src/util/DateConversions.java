package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/16/14
 * Time: 3:46 PM
 */
public class DateConversions {
    public static SimpleDateFormat yyyyMMddFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat ddMMyyyyFormat() {
        return new SimpleDateFormat("dd-MM-yyyy");
    }


    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }


    public static java.sql.Date convertStringDateToSqlDate(String data) {
        try {
            SimpleDateFormat format = DateConversions.yyyyMMddFormat();
            Date parsed = format.parse(data);
            return new java.sql.Date(parsed.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date format is incorrect" , e);
        }
    }

    public static String getFirstDayOfTheWeek() {
        Calendar cal = getFirstDayOfWeekCalendar();

        SimpleDateFormat f = DateConversions.yyyyMMddFormat();
        System.out.println("first day of week: " + f.format(cal.getTime()));
        return f.format(cal.getTime());
    }


    public static String getLastDayOfTheWeek() {
        Calendar cal = getFirstDayOfWeekCalendar();
        int lastDay = 6 + cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, lastDay);

        SimpleDateFormat f = DateConversions.yyyyMMddFormat();
        System.out.println("last day of week: " + f.format(cal.getTime()));
        return f.format(cal.getTime());
    }


    public static String getFirstDayOfTheCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat f = DateConversions.yyyyMMddFormat();
        return f.format(cal.getTime());
    }


    public static String getLastDayOfTheCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.DATE, -1);

        SimpleDateFormat f = DateConversions.yyyyMMddFormat();
        return f.format(cal.getTime());
    }

    public static String getFirstDayOfTheMonth(int year, int month) {
        SimpleDateFormat f = DateConversions.yyyyMMddFormat();
        String date = String.format("%s-%s-1", year, month);
        return f.format(DateConversions.convertStringDateToSqlDate(date));
    }

    public static String getLastDayOfTheMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, -1);

        SimpleDateFormat f = DateConversions.yyyyMMddFormat();
        return f.format(cal.getTime());
    }


    private static Calendar getTodayCalendar() {
        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        return cal;
    }

    private static Calendar getFirstDayOfWeekCalendar() {
        Calendar cal = getTodayCalendar();
        //normal first day of week is Sunday, so must add on to return Monday
        int firstDayOfWeek = 1 + cal.getFirstDayOfWeek();

        // get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
        return cal;
    }
}
