package dao;

import dataTypes.Expense;
import database.*;
import dataTypes.Label;
import util.DateConversions;

import java.sql.Date;
import java.util.ArrayList;
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
    private Database connection;
    private Integer accountId;

    public ExpenseManager(Database connection, int accountId) {
        this.connection = connection;
        this.accountId = accountId;
    }


    public Expense getExpenseById(Integer id) {
        String sql = String.format("SELECT * FROM expense WHERE id=%s and id_account=%s", id, accountId);

        List<Database.Row> expense = connection.fetchAll(sql);
        String name = expense.get(0).getString("name");
        Double amount = expense.get(0).getDouble("amount");
        Date date = expense.get(0).getDate("date");

        return new Expense(accountId, name, amount,date, null);
    }


    public Integer addExpense(Expense e) {
        String sql = String.format("INSERT INTO expense (id_account, name, amount, date) " +
                "VALUES(%s, '%s', %s, '%s')", e.getAccountId(), e.getName(), e.getAmount(), e.getDate());
        connection.update(sql);
        Integer expenseId = connection.lastInsertId();

        //add labels
        addLabels(e.getExpenseId(), e.getLabels());

        return expenseId;
    }


    private void addLabels(Integer expenseId, Set<Label> labels) {
        if (labels.size() != 0 && !labels.isEmpty()) {
            for (Label l : labels) {
                String sql = String.format("INSERT INTO label (id_expense, id_label) VALUES(%s, %s)",
                        expenseId, l.getLabelId());
                System.out.println(sql);
                connection.update(sql);
            }
        }
    }


    public Set<Label> getLabels(Integer expenseId) {
        Set<Label> labels = new HashSet<>();

        //get the id list of attached labels
        String sql = String.format("SELECT * FROM expense_label " +
                "INNER JOIN label l ON id_label=l.id " +
                "WHERE id_expense=%s", expenseId);
        List<Database.Row> rowList = connection.fetchAll(sql);

        for (Database.Row row : rowList) {
            Label label = new Label(row.getInteger("id"), row.getString("name"));
            labels.add(label);
        }

        return labels;
    }


    public Integer addLabel(Integer expenseId, Integer labelId) {
        String sql = String.format("INSERT INTO expense_label (id_expense, id_label) VALUES(%s, %s)",
                expenseId, labelId);
        connection.update(sql);
        return connection.lastInsertId();
    }



    //to rename
    public List<Expense> getExpenses() {
        List<Expense> expenseList = new ArrayList<>();
        Set<Label> labelList;

        String sql = String.format("SELECT * FROM expense WHERE id_account=%s", accountId);
        List<Database.Row> data = connection.fetchAll(sql);
            for (Database.Row row : data) {
                Integer expenseId = row.getInteger("id");
                String name = row.getString("name");
                Double amount = row.getDouble("amount");
                Date date = row.getDate("date");
                labelList = getLabels(expenseId);
                System.out.println(
                        String.format("expenseId(%s), accountId(%s), name(%s), amount(%s), date(%s)",
                        expenseId, accountId, name, amount, date.toString()));
                Expense e = new Expense(expenseId, accountId, name, amount, date, labelList);
                expenseList.add(e);
            }

        return expenseList;
    }

    public List<Expense> getExpensesByPeriod(String startDate, String endDate) {
        List<Expense> expenseList = new ArrayList<>();
        Set<Label> labelList;

        String sql = String.format("SELECT * FROM expense WHERE id_account=%s " +
                "AND date BETWEEN '%s' AND '%s'", accountId, startDate, endDate);
        List<Database.Row> data = connection.fetchAll(sql);
        for (Database.Row row : data) {
            Integer expenseId = row.getInteger("id");
            String name = row.getString("name");
            Double amount = row.getDouble("amount");
            Date date = row.getDate("date");
            labelList = getLabels(expenseId);
            Expense e = new Expense(expenseId, accountId, name, amount, date, labelList);
            expenseList.add(e);
        }

        return expenseList;
    }

    public List<Expense> getCurrentWeekExpenses() {
        String firstDayOfTheWeek = DateConversions.getFirstDayOfTheWeek();
        String lastDayOfTheWeek = DateConversions.getLastDayOfTheWeek();
        return getExpensesByPeriod(firstDayOfTheWeek, lastDayOfTheWeek);
    }

    public List<Expense> getExpensesPerMonth(int year, int month) {
        String firstDay = DateConversions.getFirstDayOfTheMonth(year, month);
        String lastDay = DateConversions.getLastDayOfTheMonth(year, month);

        return getExpensesByPeriod(firstDay, lastDay);
    }
}
