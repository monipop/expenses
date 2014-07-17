import com.google.gson.Gson;
import dataTypes.Label;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 7/17/14
 * Time: 4:59 PM
 */
public class LabelToJson {
    Set<Label> labelList;

    public LabelToJson(Set<Label> expenseList) {
        this.labelList = expenseList;
    }

    public Gson expenseSerialized() {
        Gson gson = new Gson();
        System.out.print(gson.toJson(labelList));
        return gson;
    }
}
