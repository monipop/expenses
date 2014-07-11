package dataTypes;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 3/30/14
 * Time: 6:02 PM
 */
public class Account {
    private Integer id;
    private String name;
    private List<Expense> expenses;

    public Account(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Account(String name) {
        this.name = name;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public Integer getId() {
        /*todo:if (id == null) {
            return AccountManager.getIdFromDB(this.name);
        }*/
        return id;
    }


    public String getName() {
        return name;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
