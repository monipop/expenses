package util;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 8/5/14
 * Time: 1:22 PM
 */
public class HttpParameters {
    private final HttpServletRequest request;

    public HttpParameters(HttpServletRequest r) {
        request = r;
    }

    public String getString(String paramName) {
        return request.getParameter(paramName);
    }

    public Integer getInteger(String paramName) {
        return Integer.parseInt(request.getParameter(paramName));
    }

    public Long getLong(String paramName) {
        return Long.parseLong(request.getParameter(paramName));
    }

    public Double getDouble(String paramName) {
        return Double.parseDouble(request.getParameter(paramName));
    }

    public Date getDate(String paramName) {
        if (request.getParameter(paramName) == null) {
            return null;
        }
        return DateConversions.convertddMMyyyyStringDateToSqlDate(request.getParameter(paramName));
    }
}
