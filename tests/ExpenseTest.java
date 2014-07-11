import dao.AccountManager;
import dao.ExpenseManager;
import database.Database;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import dataTypes.Account;
import dataTypes.Expense;
import dataTypes.Label;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 4/2/14
 * Time: 2:09 PM
 */
public class ExpenseTest {
    Database connection;

    @Before
    public void setUp() {
        connection = new Database();
    }

    @Test
    public void testInsertExpense() {
        AccountManager ac = new AccountManager(connection);
        Account a = ac.getFirstAccountFromDB();
        Integer accountId = a.getId();
        ExpenseManager m = new ExpenseManager(connection, accountId);

        //expense data
        String name = "sainsbury";
        Double amount = 12.5;
        java.sql.Date date = Manager.convertJavaDateToSqlDate(new Date());
        Set<Label> labels = new HashSet<>();

        //add expense
        Integer id = m.addExpense(new Expense(accountId, name, amount, date, labels));
        Assert.assertTrue(id > 0);

        //verify if the expense was added
        Expense e = m.getExpenseById(id);
        Assert.assertEquals(e.getAccountId(), accountId);
        Assert.assertEquals(e.getName()     , name);
        Assert.assertEquals(e.getAmount()   , amount);
        //Assert.assertEquals(e.getDate()     , date);
        //todo: verify labels
    }

    @Test
    public void getLabelsList() {
        AccountManager ac = new AccountManager(connection);
        Account a = ac.getFirstAccountFromDB();
        Integer accountId = a.getId();
        ExpenseManager m = new ExpenseManager(connection, accountId);

        Set<Label> labels = m.getLabels(2);
        for (Label s : labels) {
            System.out.println("label= " + s.getLabel());
        }
    }

}
