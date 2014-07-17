import dao.AccountManager;
import dao.LabelManager;
import database.Database;
import org.junit.Before;
import org.junit.Test;
import dataTypes.Account;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/10/14
 * Time: 5:08 PM
 */
public class LabelTest {
    Database connection;

    @Before
    public void setUp() {
        connection = new Database();
    }
/*

    @Test
    public void addNewLabelsInAccount() {
        LabelManager m = new LabelManager(connection);
        AccountManager ac = new AccountManager(connection);
        Account a = ac.getFirstAccountFromDB();

        Set<String> labels = new HashSet<>();
        labels.addAll(Arrays.asList("ciocolata - 6", "Tesco - 6"));

        for (String label : labels) {
            Integer id = m.addLabel(label, a.getId());
            String l = m.getLabelById(id);
            Assert.assertTrue(id > 0);
            Assert.assertEquals(label, l);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void addExistingLabelsInAccount() {
        AccountManager ac = new AccountManager(connection);
        LabelManager m = new LabelManager(connection);
        Account a = ac.getFirstAccountFromDB();
        String label = getFirstLabelFromDB(a.getId());

        m.addLabel(label, a.getId());
    }
*/


    @Test
    public void testGetLabelById() {
        AccountManager accountM = new AccountManager(connection);
        Account account = accountM.getFirstAccountFromDB();
        LabelManager labelM = new LabelManager(connection, account.getId());

        Integer labelId = 7;
        System.out.println(labelM.getLabelById(labelId).getLabel());
    }

    public String getFirstLabelFromDB(Integer accountId) {
        String sql = String.format("SELECT * FROM label WHERE id_account=%s LIMIT 1", accountId);
        Database.Row r = connection.fetchOne(sql);
        return r.getString("name");
    }

    public final Integer labelNr() {
        Map<String, String> properties = Files.readProperties("tests.settings");
        return Integer.parseInt(properties.get("label"));
    }


}
