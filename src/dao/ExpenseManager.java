package dao;

import Json.ExpenseToJson;
import dataTypes.Expense;
import database.*;
import dataTypes.Label;
import util.DateConversions;
import util.StringManipulations;

import java.sql.Date;
import java.text.SimpleDateFormat;
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

        Database.Row expense = connection.fetchOne(sql);
        String name = expense.getString("name");
        Double amount = expense.getDouble("amount");
        Date date = expense.getDate("date");

        return new Expense(accountId, name, amount,date, null);
    }


    public int deleteExpense(Integer expenseId) {
        String sql = String.format("DELETE FROM expense WHERE id_account=%s AND id=%s", accountId, expenseId);
        int affectedRows = connection.update(sql);
        if (affectedRows > 0) {
            removeLabels(expenseId);
        }
        return affectedRows;
    }


    public Integer addExpense(Expense e) {
        String sql = String.format("INSERT INTO expense (id_account, name, amount, date) " +
                "VALUES(%s, '%s', %s, '%s')",
                e.getAccountId(),
                e.getName(),
                e.getAmount(),
                e.getDate()
        );
        //System.out.println(sql);
        connection.update(sql);
        Integer expenseId = connection.lastInsertId();

        //add labels
        attachLabels(expenseId, e.getLabels());

        return expenseId;
    }

    private void attachLabels(Integer expenseId, Set<Label> labels) {
        if (labels != null && !labels.isEmpty()) {
            for (Label l : labels) {
                String sql = String.format("INSERT INTO expense_label (id_expense, id_label) VALUES(%s, %s)",
                        expenseId,
                        l.getLabelId()
                );
                connection.update(sql);
            }
        }
    }

    private void removeLabels(Integer expenseId) {
        String sql = String.format("DELETE FROM expense_label WHERE id_expense=%s", expenseId);
        connection.update(sql);
    }


    public Set<Label> getLabels(Integer expenseId) {
        Set<Label> labels = new HashSet<>();

        //get the id list of attached labels
        String sql = String.format("SELECT * FROM expense_label " +
                "INNER JOIN label l ON id_label=l.id " +
                "WHERE id_expense=%s", expenseId);
        List<Database.Row> rowList = connection.fetchAll(sql);

        for (Database.Row row : rowList) {
            Label label = new Label(
                    row.getInteger("id"),
                    StringManipulations.decode(row.getString("name")));
            labels.add(label);
        }
        return labels;
    }


    public int addLabel(Integer expenseId, Integer labelId) {
        String sql = String.format("INSERT INTO expense_label (id_expense, id_label) VALUES(%s, %s)",
                expenseId, labelId);
        return connection.update(sql);
    }

    // edit name
    public int editName(Integer expenseId, String name) {
        String sql = String.format("UPDATE expense SET name='%s' WHERE id=%s", name, expenseId);
        return connection.update(sql);
    }

    // edit amount
    public int editAmount(Integer expenseId, Double amount) {
        String sql = String.format("UPDATE expense SET amount=%s WHERE id=%s", amount, expenseId);
        return connection.update(sql);
    }

    //to rename
    public List<Expense> getExpenses() {
        String sql = String.format("SELECT * FROM expense WHERE id_account=%s", accountId);
        List<Database.Row> data = connection.fetchAll(sql);

        return dataRowToExpenseList(data);
    }


    //get all expenses that have a specific label list
    public List<Expense> getExpensesWithLabel(String sqlCondition, int condNo) {
        String sql = String.format(
                "SELECT *, count(x.id_expense) result  " +
                "FROM expense e " +
                "INNER JOIN " +
                    "(SELECT el.id_expense " +
                    " FROM label l " +
                    " JOIN expense_label el " +
                    " ON l.id=el.id_label AND (%s)" +
                ") x " +
                "ON e.id=x.id_expense " +
                "GROUP BY x.id_expense " +
                "HAVING result = %s", sqlCondition, condNo);

        List<Database.Row> data = connection.fetchAll(sql);
        return dataRowToExpenseList(data);
    }

    //get all expenses that have at least one of the labels in the given list
    public List<Expense> getExpensesWithOneOfLabel(String sqlCondition) {
        String sql = String.format(
                "SELECT * " +
                "FROM expense e " +
                "INNER JOIN " +
                    "(SELECT DISTINCT el.id_expense " +
                    " FROM label l " +
                    " JOIN expense_label el " +
                    " ON l.id=el.id_label AND (%s)" +
                ") x " +
                "ON e.id=x.id_expense", sqlCondition);

        List<Database.Row> data = connection.fetchAll(sql);
        return dataRowToExpenseList(data);
    }


    //get all expenses that have at least one of the labels in the given list
    // in a data range
    public List<Expense> getExpensesWithOneOfLabel(String sqlCondition, String from, String to) {
        String fromSql = DateConversions.reverseDate(from);
        String toSql = DateConversions.reverseDate(to);

        String sql = String.format(
                "SELECT * " +
                "FROM expense e " +
                "INNER JOIN " +
                    "(SELECT DISTINCT el.id_expense " +
                    " FROM label l " +
                    " JOIN expense_label el " +
                    " ON l.id=el.id_label AND (%s)" +
                ") x " +
                "ON e.id=x.id_expense " +
                "AND date BETWEEN '%s' AND '%s'"
                , sqlCondition, fromSql, toSql);
        System.out.println(sql);
        List<Database.Row> data = connection.fetchAll(sql);
        return dataRowToExpenseList(data);
    }


    //get all the expenses from a certain period
    public List<Expense> getExpensesByPeriod(String startDate, String endDate) {
        String startSqlDateFormat = DateConversions.reverseDate(startDate);
        String endSqlDateFormat = DateConversions.reverseDate(endDate);

        String sql = String.format("SELECT *, DATE_FORMAT(date, '%%d-%%m-%%Y') " +
                                    "FROM expense " +
                                    "WHERE id_account=%s " +
                                    "AND date BETWEEN '%s' AND '%s'",
                accountId,
                startSqlDateFormat,
                endSqlDateFormat);
        System.out.println(sql);
        List<Database.Row> data = connection.fetchAll(sql);

        return dataRowToExpenseList(data);
    }

    public List<Expense> getExpensesStartingFrom(String startDate) {
        String startSqlDateFormat = DateConversions.reverseDate(startDate);

        String sql = String.format("SELECT *, DATE_FORMAT(date, '%%d-%%m-%%Y') " +
                "FROM expense " +
                "WHERE id_account=%s " +
                "AND date >= '%s'",
                accountId,
                startSqlDateFormat);
        System.out.println(sql);
        List<Database.Row> data = connection.fetchAll(sql);

        return dataRowToExpenseList(data);
    }

    public List<Expense> getExpensesStartingFrom(String sqlCondition, String startDate) {
        String startSqlDate = DateConversions.reverseDate(startDate);

        String sql = String.format(
                "SELECT * " +
                "FROM expense e " +
                "INNER JOIN " +
                    "(SELECT DISTINCT el.id_expense " +
                    " FROM label l " +
                    " JOIN expense_label el " +
                    " ON l.id=el.id_label AND (%s)" +
                ") x " +
                "ON e.id=x.id_expense " +
                "AND date >= '%s'"
                , sqlCondition, startSqlDate);
        System.out.println(sql);
        List<Database.Row> data = connection.fetchAll(sql);

        return dataRowToExpenseList(data);
    }


    public List<Expense> getExpensesEndingAt(String endDate) {
        String endSqlDateFormat = DateConversions.reverseDate(endDate);

        String sql = String.format("SELECT *, DATE_FORMAT(date, '%%d-%%m-%%Y') " +
                "FROM expense " +
                "WHERE id_account=%s " +
                "AND date <= '%s'",
                accountId,
                endSqlDateFormat);
        System.out.println(sql);
        List<Database.Row> data = connection.fetchAll(sql);

        return dataRowToExpenseList(data);
    }

    public List<Expense> getExpensesEndingAt(String sqlCondition, String endDate) {
        String endSqlDate = DateConversions.reverseDate(endDate);

        String sql = String.format(
                "SELECT * " +
                        "FROM expense e " +
                        "INNER JOIN " +
                        "(SELECT DISTINCT el.id_expense " +
                        " FROM label l " +
                        " JOIN expense_label el " +
                        " ON l.id=el.id_label AND (%s)" +
                        ") x " +
                        "ON e.id=x.id_expense " +
                        "AND date <= '%s'"
                , sqlCondition, endSqlDate);
        System.out.println(sql);
        List<Database.Row> data = connection.fetchAll(sql);

        return dataRowToExpenseList(data);
    }

    private List<Expense> dataRowToExpenseList(List<Database.Row> data) {
        List<Expense> expenseList = new ArrayList<>();
        Set<Label> labelList;

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


    public String getJsonExpenses(List<Expense> expenseList) {
        if (!expenseList.isEmpty()) {
            ExpenseToJson json = new ExpenseToJson(expenseList);
            return json.expenseSerialized();
        } else {
            return "{\"message\": \"There are no expenses attached to account id " + accountId + "\"}";
        }
    }
}
