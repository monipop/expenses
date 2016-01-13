package dao;

import Json.LabelToJson;
import database.Database;
import dataTypes.Label;
import util.StringManipulations;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 4/10/14
 * Time: 6:48 PM
 */
public class LabelManager {
    private Database connection;
    private Integer accountId;

    public LabelManager(Database connection, Integer accountId) {
        this.connection = connection;
        this.accountId = accountId;
    }

    //defines labels
    public void addLabels(Set<Label> labels) {
        for (Label label : labels) {
            addLabel(label.getLabel());
        }
    }

    //defines labels
    public Integer addLabel(String label) {
        Set<String> labels = new TreeSet<>();
        labels.add(label);
        labels = verifyLabelsForInsertion(labels, accountId);

        for (String l : labels) {
            String sql = String.format("INSERT INTO label (id_account, name) VALUES(%s, '%s')",
                accountId, StringManipulations.encode(l));
            System.out.println(sql);
            connection.update(sql);
        }
        return connection.lastInsertId();
    }

    public int deleteLabel(Integer labelId) {
        String sql = String.format("DELETE FROM label WHERE id=%s", labelId);
        return connection.update(sql);
    }

    public Label getLabelById(Integer id) {
        String sql = String.format("SELECT * FROM label WHERE id=%s", id);
        List<Database.Row> data = connection.fetchAll(sql);
        if (!data.isEmpty()) {
            return new Label(id, data.get(0).getString("name"));
        } else {
            return null;
        }

    }

    //remove labels already existing in db from the insert list
    public Set<String> verifyLabelsForInsertion(Set<String> labels, Integer accountId) {
        if (labels != null && labels.size() != 0) {
            Set<Label> accountLabels = getAccountLabels();

            for (Label l : accountLabels) {
                String accountLabelName = l.getLabel();
                for (int i = 0; i < labels.size(); i++) {
                    if (labels.contains(accountLabelName)) {
                        labels.remove(accountLabelName);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException();
        }


        if (labels.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return labels;
    }

    //todo: setLabels -> add labels to an expense entry

    public Set<Label> getAccountLabels() {
        Set<Label> labelSet = new TreeSet<>();

        String sql = String.format("SELECT * FROM label WHERE id_account=%s", accountId);
        List<Database.Row> accountLabels = connection.fetchAll(sql);

        for (Database.Row row : accountLabels) {
            Integer labelId = row.getInteger("id");
            String accountLabelName = StringManipulations.decode(row.getString("name"));
            labelSet.add(new Label(labelId, accountLabelName));
        }
        return labelSet;
    }

    public String getJsonLabels(Set<Label> labelSet) {
        if (!labelSet.isEmpty()) {
            LabelToJson labelToJson = new LabelToJson(labelSet);
            return labelToJson.expenseSerialized();
        } else {
            return "There are no labels attached to account id " + accountId;
        }
    }

}
