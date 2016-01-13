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
 * Date: 1/14/15
 * Time: 12:13 AM
 */
public class EditAmount extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpParameters param = new HttpParameters(request);
        Integer accountId = param.getInteger("accountId");
        Integer elementId = param.getInteger("expenseId");
        Double amount    = param.getDouble("amount"  );

        Database connection = new Database();
        ExpenseManager m = new ExpenseManager(connection, accountId);
        Integer editExpenseId = m.editAmount(elementId, amount);

        PrintWriter out = response.getWriter();
        if ( editExpenseId > 0 ) {
            out.print(amount);
        } else {
            out.print("{'message': 'error'}");
        }
    }
}
