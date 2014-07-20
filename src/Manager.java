import database.Database;
import dataTypes.Account;
import dataTypes.Label;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 3/30/14
 * Time: 8:12 PM
 */
public class Manager extends Database {


    public static java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.sql.Date convertStringDateToSqlDate(String data) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(data);
        return new java.sql.Date(parsed.getTime());
    }


    public static void main(String[] args) {
        Manager m = new Manager();
        Account account = new Account("eee");

        /*int id = m.addAccount(account);
        System.out.println("main id = " + id);
        Integer accountId = account.getId();*/

        Set<Label> labels = new HashSet<>();
        //labels.addAll(Arrays.asList("food", "Tesco", "drinks in town"));

        //m.addLabels(labels, 1);

        //m.addExpense(new dataTypes.Expense(accountId, "Tesco", 12.5, convertJavaDateToSqlDate("2000-04-18"), labels));


        //List<dataTypes.Expense> expenses = m.getExpensesFromThisWeek();
        //m.delete(3);

        //m.setLabels(4, Arrays.asList("food"));
        //m.deleteLabels(4, Arrays.asList("mancare"));

    }
}
