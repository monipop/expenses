package dao;

import database.Database;
import dataTypes.Label;

import java.sql.ResultSet;
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

    public LabelManager(Database connection) {
        this.connection = connection;
    }

    //defines labels
    public void addLabels(Set<String> labels, Integer accountId) {
        for (String label : labels) {
            addLabel(label, accountId);
        }
    }

    //defines labels
    public Integer addLabel(String label, Integer accountId) {
        Set<String> labels = new HashSet<>();
        labels.add(label);
        labels = verifyLabelsForInsertion(labels, accountId);

        for (String l : labels) {
            String sql = "INSERT INTO label (id_account, name) VALUES(" + accountId + ", '" + l + "')";
            System.out.println(sql);
            connection.update(sql);
        }
        return connection.lastInsertId();
    }

    public Label getLabelById(Integer id) {
//        String name = null;
//        try {
//            String query = "SELECT * FROM label WHERE id=" + id;
//            ResultSet result = connection.fetch(query);
//            result.next();
//            name = result.getString("name");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return name;
        List<Database.Row> data = connection.fetchAll("SELECT * FROM label WHERE id=" + id);
        if (!data.isEmpty()) {
            return new Label(id, data.get(0).getString("name"));
        } else {
            return null;
        }

    }

    //remove labels already existing in db from the insert list
    public Set<String> verifyLabelsForInsertion(Set<String> labels, Integer accountId) {
        try {
            if (labels != null && labels.size() != 0) {
                String sql = "SELECT * FROM label WHERE id_account=" + accountId;
                ResultSet accountLabels = connection.fetch(sql);

                while (accountLabels.next()) {
                    String accountLabel = accountLabels.getString("name");
                    for (int i = 0; i < labels.size(); i++) {
                        if (labels.contains(accountLabel)) {
                            labels.remove(accountLabel);
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (labels == null || labels.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return labels;
    }

    //todo: setLabels -> add labels to an expense entry


}
