package dao;

import database.*;
import dataTypes.*;

import java.sql.ResultSet;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 4/4/14
 * Time: 3:38 PM
 */
public class AccountManager {

    private Database connection;

    public AccountManager(Database connection) {
        this.connection = connection;
    }

    public int addAccount(Account a) {
        if (isAccountUnique(a)) {
            String query = "INSERT INTO account (name) VALUES('" + a.getName() + "')";
            connection.update(query);
            return connection.lastInsertId();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean isAccountUnique(Account a) {
        try {
            String sql = "SELECT * FROM account";
            ResultSet results = connection.fetch(sql);
            while (results.next()) {
                String name = results.getString("name");
                if (name.equals(a.getName())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public Account getAccountById(Integer id) {
        String name = null;
        try {
            String query = "SELECT * FROM account WHERE id=" + id;
            ResultSet result = connection.fetch(query);
            result.next();
            name = result.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Account(id, name);
    }


    public Integer getIdFromDB(String name) {
        Integer id = null;
        try {
            String query = "SELECT * FROM account WHERE name='" + name + "'";
            ResultSet result = connection.fetch(query);
            result.next();
            id = Integer.parseInt(result.getString("id"));
            System.out.println("accountID " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    //get the first account name from DB
    public Account getFirstAccountFromDB() {
        try {
            String sql = "SELECT * FROM account LIMIT 1";
            ResultSet r = connection.fetch(sql);
            r.next();
            return new Account(r.getInt("id"), r.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
