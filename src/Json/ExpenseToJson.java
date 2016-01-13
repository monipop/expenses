package Json;

import com.google.gson.Gson;
import dataTypes.Expense;
import dataTypes.Label;

import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/17/14
 * Time: 3:06 PM
 */
public class ExpenseToJson {
    List<Expense> expenseList;

    public ExpenseToJson(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public String expenseSerialized() {
        Gson gson = new Gson();
        return gson.toJson(expenseList);
    }
/*
    public List<ExpenseToPrint> getExpenseToPrint(List<Expense> list) {
        List<ExpenseToPrint> newList;
        for (Expense e : list) {
            ExpenseToPrint eP = new ExpenseToPrint(
                    e.getExpenseId(),
                    e.getAccountId(),
                    e.getName(),
                    e.getAmount(),
                    e.getDate().);
            newList.add()
        }
    }*/
}

class ExpenseToPrint {
    private final Integer id;
    private final Integer accountId;
    private final String name;
    private final Double amount;
    private final String date;
    private final Set<Label> labels;

    public ExpenseToPrint(Integer id, Integer accountId, String name, Double amount, String date, Set<Label> labels) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.labels = labels;
    }
}
