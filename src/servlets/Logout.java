package servlets;

import dao.SessionManager;
import database.Database;
import util.HttpParameters;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 8/15/14
 * Time: 2:45 PM
 */
public class Logout extends HttpServlet {
    @Override

    public void doPost(HttpServletRequest request, HttpServletResponse respons)
        throws IOException {

        Database connection = new Database();
        SessionManager s = new SessionManager(connection);

        HttpParameters params = new HttpParameters(request);
        Integer sessionId = params.getInteger("sessionId");
        PrintWriter out = respons.getWriter();
        if (s.deleteSession(sessionId)) {
            out.print("{logout: success}");
        } else {
            out.print("{logout: error}");
        }
    }



}
