import dao.AccountManager;
import dao.ExpenseManager;
import dataTypes.Expense;
import database.Database;
import org.junit.Before;
import org.junit.Test;
import dataTypes.Account;
import dataTypes.Label;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 4/2/14
 * Time: 2:09 PM
 */
public class ExpenseTest {
    Database connection;
    AccountManager accountManager;
    Account firstAccount;
    Integer accountId;

    @Before
    public void setUp() {
        connection = new Database();
        accountManager = new AccountManager(connection);
        firstAccount = accountManager.getFirstAccountFromDB();
        accountId = firstAccount.getId();
    }

    /*@Test
    public void testInsertExpense() {
        ExpenseManager m = new ExpenseManager(connection, accountId);

        //expense data
        String name = "waitrose";
        Double amount = 12.5;
        java.sql.Date date = util.DateConversions.convertJavaDateToSqlDate(new Date());
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
*/
    @Test
    public void getLabelsList() {
        ExpenseManager m = new ExpenseManager(connection, accountId);

        Set<Label> labels = m.getLabels(2);
        for (Label s : labels) {
            //System.out.println("label= " + s.getLabel());
        }
    }

    @Test
    public void getExpensesByPeriodTest() {
        ExpenseManager m = new ExpenseManager(connection, accountId);

        List<Expense> expenseList = m.getCurrentWeekExpenses();
        for (Expense e : expenseList) {
            System.out.println(e.getName() + " - " + e.getExpenseId());
        }

    }

    @Test
    public void getExpensesByMonthTest() {
        ExpenseManager m = new ExpenseManager(connection, accountId);
        int year = 2014;
        int month = 4;

        List<Expense> expenses = m.getExpensesPerMonth(year, month);
        for (Expense e : expenses) {
            System.out.println(e.getName() + " - " + e.getAmount() + " - " + e.getDate());
        }
    }

}
