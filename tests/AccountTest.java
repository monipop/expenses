import dao.AccountManager;
import database.Database;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import dataTypes.Account;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 4/10/14
 * Time: 7:02 PM
 */
public class AccountTest {
    Database connection;

    @Before
    public void setUp() {
        connection = new Database();
    }

    @Test
    public void testInsertAccount() {
        AccountManager m = new AccountManager(connection);
        String name = "account" + accountNr();
        Account a = new Account(name);

        int id = m.addAccount(a);
        System.out.println("account id = " + id);
        Assert.assertTrue(id > 0);
        Assert.assertEquals(name, m.getAccountById(id).getName());
    }


    //try to add an existing account
    @Test(expected = IllegalArgumentException.class)
    public void testExistingAccount() {
        AccountManager m = new AccountManager(connection);
        String name = m.getFirstAccountFromDB().getName();
        Account a = new Account(name);

        m.addAccount(a);
    }


    public final Integer accountNr() {
        Map<String, String> properties = Files.readProperties("tests.properties");
        return Integer.parseInt(properties.get("account"));
    }

}
