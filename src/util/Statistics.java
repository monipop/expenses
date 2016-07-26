package util;

import dao.ExpenseManager;
import dao.LabelManager;
import dataTypes.Expense;
import dataTypes.Label;
import database.Database;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 1/20/15
 * Time: 2:06 PM
 */
public class Statistics {

    public List<String> sqlLabelCondition(List<Label> labels) {
        StringBuilder sqlCondition = new StringBuilder();
        int condNo = 0;
        if (labels.size() == 1) {
            sqlCondition.append("l.name='" + labels.get(0).getLabel() + "'");
            condNo++;
        } else if (labels.size() > 1) {
            for (int i = 0; i < labels.size(); i++) {
                String label = labels.get(i).getLabel();
                sqlCondition.append("l.name='"+ label +"'");
                if (i != labels.size() - 1) {
                    sqlCondition.append(" OR ");
                }
                condNo++;
            }
        }
        List<String> result = new ArrayList<>();
        result.add(sqlCondition.toString());
        result.add(String.valueOf(condNo));
        return result;
    }



    public List<Expense> getExpenses(ExpenseManager m, Integer accountId,
                                     String from, String to, String labelIds,
                                     boolean allLabels) {
        List<Expense> list;

        if (labelIds.equals("null")) {
            if (from.equals("null") && to.equals("null")) {
                list = m.getExpenses();
            } else {
                list = m.getExpensesByPeriod(from, to);
            }
        } else {
            List<Label> labels = stringToList(accountId, labelIds);
            List<String> sqlCondition = sqlLabelCondition(labels);
            String sqlCond = sqlCondition.get(0);

            list = m.getExpenses();
            /*if (from.equals("null") && to.equals("null")) {
                list = m.getExpensesWithOneOfLabel(sqlCond);
            } else if (from.equals("null")) {
                list = m.getExpensesStartingFrom(sqlCond, from);
            } else if (to.equals("null")) {
                list = m.getExpensesEndingAt(sqlCond, to);
            } else {
                list = m.getExpensesWithOneOfLabel(sqlCondition.toString(), from, to);
            }*/
        }

        return list;
    }


    public List<Label> stringToList(Integer accountId, String labels) {
        Database connection = new Database();
        LabelManager lm = new LabelManager(connection, accountId);

        List<Label> labelList = new ArrayList<>();

        if (labels != null && !labels.isEmpty()) {
            String[] labelsArr = labels.split(",");
            for (String l : labelsArr) {
                Integer labelId = Integer.parseInt(l);
                labelList.add(lm.getLabelById(labelId));
            }
        }

        return labelList;
    }

}
