import dao.AccountManager;
import dao.LabelManager;
import dataTypes.Account;
import dataTypes.Label;
import database.Database;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/17/14
 * Time: 5:02 PM
 */
public class LabelToJsonTest {
    Database connection;

    @Before
    public void setUp() {
        connection = new Database();
    }

    @Test
    public void labelListTest() {
        AccountManager accountManager = new AccountManager(connection);
        Account account = accountManager.getFirstAccountFromDB();
        LabelManager labelManager = new LabelManager(connection, account.getId());

        Set<Label> labels = labelManager.getLabels();
        LabelToJson labelToJson = new LabelToJson(labels);
        labelToJson.expenseSerialized();
    }
}
