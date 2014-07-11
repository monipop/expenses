package xxx;

import java.sql.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 3/30/14
 * Time: 6:11 PM
 */
public class Expense {
    private final Integer id;
    private final Integer accountId;
    private final String name;
    private final Double amount;
    private final Date date;
    private final Set<Label> labels;

    public Expense(Integer id, Integer accountId, String name, Double amount, Date date, Set<Label> labels) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.labels = labels;
    }

    public Expense(Integer accountId, String name, Double amount, Date date, Set<Label> labels) {
        this(null, accountId, name, amount, date, labels);
    }

    public Integer getExpenseId() {
        return id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public Set<Label> getLabels() {
        return labels;
    }
}
