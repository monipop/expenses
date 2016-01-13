package servlets;

import dao.ExpenseManager;
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
 * Date: 1/13/15
 * Time: 10:59 AM
 */
public class EditName extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpParameters param = new HttpParameters(request);
        Integer accountId = param.getInteger("accountId");
        Integer elementId = param.getInteger("expenseId");
        String  name      = param.getString ("name"     );

        Database connection = new Database();
        ExpenseManager m = new ExpenseManager(connection, accountId);
        Integer editExpenseId = m.editName(elementId, name);

        PrintWriter out = response.getWriter();
        if ( editExpenseId > 0 ) {
            out.print(name);
        } else {
            out.print("{'message': 'error'}");
        }
    }
}