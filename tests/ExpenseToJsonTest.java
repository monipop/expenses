import Json.ExpenseToJson;
import dao.AccountManager;
import dao.ExpenseManager;
import dataTypes.Account;
import dataTypes.Expense;
import database.Database;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/17/14
 * Time: 3:15 PM
 */
public class ExpenseToJsonTest {
    Database connection;

    @Before
    public void setUp() {
        this.connection = new Database();
    }

    @Test
    public void expenseListTest() {
        AccountManager accManager = new AccountManager(connection);
        Account account = accManager.getFirstAccountFromDB();

        ExpenseManager expenseManager = new ExpenseManager(connection, account.getId());

        List<Expense> expenseList = expenseManager.getExpenses();
        if (!expenseList.isEmpty()) {
            ExpenseToJson expenseToGson = new ExpenseToJson(expenseList);
            expenseToGson.expenseSerialized();
        } else {
            throw new IllegalArgumentException("There are no expenses attached to account id " + account.getId());
        }
    }
}
