package dao;

import database.*;
import dataTypes.*;

import java.sql.ResultSet;
import java.util.List;

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
            String sql = String.format("INSERT INTO account (name) VALUES('%s')", a.getName());
            connection.update(sql);
            return connection.lastInsertId();
        } else {
            throw new IllegalArgumentException("Account already exists");
        }
    }

    private boolean isAccountUnique(Account a) {
        String sql = "SELECT * FROM account";
        List<Database.Row> accounts = connection.fetchAll(sql);
        for (Database.Row row : accounts) {
            String name = row.getString("name");
            if (name.equals(a.getName())) {
                return false;
            }
        }

        return true;
    }

    public Account getAccountById(Integer id) {
        String sql = String.format("SELECT * FROM account WHERE id=%s", id);
        Database.Row account = connection.fetchOne(sql);
        String name = account.getString("name");

        return new Account(id, name);
    }


    public Integer getIdFromDB(String name) {
        String sql = String.format("SELECT * FROM account WHERE name='%s'", name);
        Database.Row account = connection.fetchOne(sql);

        return Integer.parseInt(account.getString("id"));
    }


    //get the first account name from DB
    public Account getFirstAccountFromDB() {
        String sql = "SELECT * FROM account LIMIT 1";
        Database.Row row = connection.fetchOne(sql);

        return new Account(row.getInteger("id"), row.getString("name"));
    }
}
