package dao;

import com.google.gson.Gson;
import dataTypes.Account;
import database.Database;
import database.Session;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 8/7/14
 * Time: 7:48 PM
 */
public class SessionManager {
    Database connection;

    public SessionManager(Database connection) {
        this.connection = connection;
    }

    public int addSession(Session session) {

        String sql = String.format("INSERT INTO session (username, account_id) " +
                                   "VALUES ('%s', %s)",
                                    session.getUser(), session.getAccountId());
        connection.update(sql);
        return connection.lastInsertId();
    }

    public boolean deleteSession(Integer sessionId) {
        String sql = String.format("DELETE FROM session WHERE id=%s", sessionId);
        return connection.update(sql) > 0;
    }

    public Session getSessionById(Integer sessionId) {
        String sql = String.format("SELECT * FROM session WHERE id=%s", sessionId);
        Database.Row row = connection.fetchOne(sql);
        Integer accountId = row.getInteger("account_id");
        String user = row.getString("username");
        String browser = row.getString("browser");
        Long lastAccessed = row.getLong("last_accessed");

        return new Session(sessionId, accountId, user, browser, lastAccessed);
    }

    public String getJsonSession(Session session) {
        if (session != null) {
            Gson gson = new Gson();
            return gson.toJson(session);
        } else {
            throw new IllegalStateException("Session is null");
        }
    }
}
