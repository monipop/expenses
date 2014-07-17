import com.google.gson.Gson;
import dataTypes.Expense;

import java.util.List;

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

    public Gson expenseSerialized() {
        Gson gson = new Gson();
        System.out.print(gson.toJson(expenseList));
        return gson;
    }
}
