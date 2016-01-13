package servlets;

import dao.LabelManager;
import dataTypes.Label;
import database.Database;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/21/14
 * Time: 11:58 AM
 */
public class AllAccountLabels extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        Database connection = new Database();
        Integer accountId = Integer.parseInt(request.getParameter("accountId"));
        PrintWriter out = response.getWriter();
        LabelManager l = new LabelManager(connection, accountId);
        out.print(l.getJsonLabels(l.getAccountLabels()));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Database connection = new Database();
        Integer accountId = Integer.parseInt(request.getParameter("accountId"));
        String name = request.getParameter("name");

        PrintWriter out = response.getWriter();
        LabelManager l = new LabelManager(connection, accountId);
        if (l.addLabel(name) > 0 ) {
            out.print("{'message': 'ok'}");
        } else {
            out.print("{'message': 'error'}");
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        Database connection = new Database();
        Integer accountId = Integer.parseInt(request.getParameter("accountId"));
        Integer labelId = Integer.parseInt(request.getParameter("labelId"));
        PrintWriter out = response.getWriter();

        LabelManager l = new LabelManager(connection, accountId);
        if (l.deleteLabel(labelId) > 0 ) {
            out.print("{'message': 'ok'}");
        } else {
            out.print("{'message': 'error'}");
        }
    }


}
