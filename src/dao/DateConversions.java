package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/16/14
 * Time: 3:46 PM
 */
public class DateConversions {
    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.sql.Date convertStringDateToSqlDate(String data) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = format.parse(data);
            return new java.sql.Date(parsed.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date format is incorrect" , e);
        }
    }
}
