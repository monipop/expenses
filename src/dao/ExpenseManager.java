package dao;

import dataTypes.Expense;
import database.*;
import dataTypes.Label;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 4/10/14
 * Time: 6:25 PM
 */
public class ExpenseManager {
    private Database database;
    private int accountId;

    public ExpenseManager(Database database, int accountId) {
        this.database = database;
        this.accountId = accountId;
    }


    public Expense getExpenseById(Integer id) {
        String name = null;
        Double amount = null;
        Date date = null;
        try {
            String query = String.format("SELECT * FROM expense WHERE id=%s and id_account=%s", id, accountId);

            ResultSet result = database.fetch(query);
            result.next();
            name = result.getString("name");
            amount = result.getDouble("amount");
            date = result.getDate("date");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Expense(accountId, name, amount,date, null);
    }

    public Integer addExpense(Expense e) {
        String sql = "INSERT INTO expense (id_account, name, amount, date) " +
                "VALUES(" + e.getAccountId() + ", '" + e.getName() + "', " + e.getAmount() +
                        ", '" + e.getDate() + "')";
        System.out.println(sql);
        database.update(sql);
        Integer expenseId = database.lastInsertId();

        //add labels
        addLabels(e.getExpenseId(), e.getLabels());

        return expenseId;
    }

    private void addLabels(Integer expenseId, Set<Label> labels) {
        for (Label l : labels) {
            String query = "INSERT INTO label (id_expense, id_label) " +
                    "VALUES(" + expenseId + ", " + l.getLabelId() + ")";
            database.update(query);
        }
    }

    public Set<Label> getLabels(Integer expenseId) {
        Set<Label> labels = new HashSet<>();

        //get the id list of attached labels
            String sql = "SELECT * FROM expense_label INNER JOIN label l ON id_label=l.id " +
                    "WHERE id_expense=" + expenseId;
            System.out.println(sql);
            ResultSet result = database.fetch(sql);
        try {
            System.err.println(result.getMetaData());
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            while (result.next()) {
                Label label = new Label(result.getInt("id"), result.getString("name"));
                labels.add(label);
            }
        } catch (SQLException e) {
            String message = String.format("Unable to get expenseId= %s", expenseId);
            throw new IllegalArgumentException(message, e);
        }

        return labels;
    }

    public Integer addLabel(Integer expenseId, Integer labelId) {
        String sql = "INSERT INTO expense_label (id_expense, id_label) " +
                "VALUES(" + expenseId + ", " + labelId + ")";
        database.update(sql);
        return database.lastInsertId();
    }



    //to rename
    public List<Expense> getExpensesFromThisWeek() {
        List<Expense> expenses;

        return null;
    }
}
