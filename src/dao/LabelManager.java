package dao;

import database.Database;
import dataTypes.Label;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void addLabels(Set<String> labels) {
        for (String label : labels) {
            addLabel(label);
        }
    }

    //defines labels
    public Integer addLabel(String label) {
        Set<String> labels = new HashSet<>();
        labels.add(label);
        labels = verifyLabelsForInsertion(labels, accountId);

        for (String l : labels) {
            String sql = String.format("INSERT INTO label (id_account, name) VALUES(%s, %s)", accountId, l);
            System.out.println(sql);
            connection.update(sql);
        }
        return connection.lastInsertId();
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
            Set<Label> accountLabels = getLabels();

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

    public Set<Label> getLabels() {
        Set<Label> labelSet = new HashSet<>();

        String sql = String.format("SELECT * FROM label WHERE id_account=%s", accountId);
        List<Database.Row> accountLabels = connection.fetchAll(sql);

        for (Database.Row row : accountLabels) {
            Integer labelId = row.getInteger("id");
            String accountLabelName = row.getString("name");
            labelSet.add(new Label(labelId, accountLabelName));
        }
        return labelSet;
    }

}
