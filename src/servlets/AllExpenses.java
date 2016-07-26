package servlets;

import dao.ExpenseManager;
import dao.LabelManager;
import dataTypes.Expense;
import dataTypes.Label;
import database.Database;
import util.HttpParameters;
import util.Statistics;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/20/14
 * Time: 2:03 PM
 */
public class AllExpenses extends HttpServlet {


    /*
    get expenses list
    * */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //results to http://localhost:8080/expenses/expenses

        HttpParameters param = new HttpParameters(request);
        System.out.println("!!!!Parameters:");
        Enumeration params = request.getParameterNames();
        System.out.println("has params: " + params.hasMoreElements());
        while(params.hasMoreElements()){
            String paramName = (String)params.nextElement();
            System.out.println("Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
        }


        /*Integer accountId = param.getInteger("accountId");
        String from = param.getString("from");
        String to = param.getString("to");
        String labelIds = param.getString("labels");*/
        Integer accountId = 14;
        String from = "";
        String to = "14-01-2016";
        String labelIds = "";

        Database connection = new Database();
        ExpenseManager m = new ExpenseManager(connection, accountId);
        Statistics s = new Statistics();
        List<Expense> list;

        PrintWriter out = response.getWriter();
        //todo: complete for allLabels condition
        list = s.getExpenses(m, accountId, from, to, labelIds, false);

        Collections.reverse(list);
        String json = m.getJsonExpenses(list);

        out.print(json);
    }

    //add new expense
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpParameters param = new HttpParameters(request);
        Integer accountId = param.getInteger("accountId");
        String  name      = param.getString ("name"     );
        Double  amount    = param.getDouble ("amount"   );
        Date    date      = param.getDate   ("date"     );

        Database connection = new Database();
        LabelManager l = new LabelManager(connection, accountId);
        String labelIds = param.getString("labels");
        Set<Label> labels = null;
        if (!labelIds.isEmpty()) {
            String[] labelsIds = labelIds.split(",");
            labels = new TreeSet<>();
            for (String s : labelsIds) {
                Integer labelId = Integer.parseInt(s);
                labels.add(new Label(labelId, l.getLabelById(labelId).getLabel()));
            }
        }

        ExpenseManager m = new ExpenseManager(connection, accountId);
        Integer insertedExpenseId = m.addExpense(new Expense(
                accountId,
                name,
                amount,
                date,
                labels
        ));

        PrintWriter out = response.getWriter();
        if ( insertedExpenseId > 0 ) {
            out.print("{'message': 'success'}");
        } else {
            out.print("{'message': 'error'}");
        }
    }


    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpParameters param = new HttpParameters(request);
        Integer expenseId = param.getInteger("expenseId");
        Integer accountId = param.getInteger("accountId");

        Database connection = new Database();
        ExpenseManager m = new ExpenseManager(connection, accountId);
        int affectedRows = m.deleteExpense(expenseId);

        PrintWriter out = response.getWriter();
        if (affectedRows > 0) {
            out.print("{'message': 'success'}");
        } else {
            out.print("{'message': 'error'}");
        }
    }
}
