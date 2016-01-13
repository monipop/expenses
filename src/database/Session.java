package database;

import dao.AccountManager;
import dataTypes.Account;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 8/7/14
 * Time: 4:34 PM
 */
public class Session {
    private Integer sessionId;
    private Integer accountId;
    private String user;
    private String browser;
    private Long lastAccessed;

    public Session(Integer accountId, String user, String browser, Long lastAccessed) {
        this(null, accountId, user, browser, lastAccessed);
    }

    public Session(Integer sessionId, Integer accountId, String user, String browser, Long lastAccessed) {
        this.sessionId = accountId;
        this.accountId = accountId;
        this.user = user;
        this.browser = browser;
        this.lastAccessed = lastAccessed;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getUser() {
        return user;
    }

    public String getBrowser() {
        return browser;
    }

    public Long getLastAccessed() {
        return lastAccessed;
    }

    public final void setLastAccessed(Long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public boolean equals(Session s) {
        return this.sessionId.equals(s.sessionId) &&
                this.accountId.equals(s.accountId) &&
                this.user.equals(s.user) &&
                this.browser.equals(s.browser) &&
                this.lastAccessed.equals(s.lastAccessed);
    }
}
