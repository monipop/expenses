package servlets;

import dao.SessionManager;
import database.Database;
import database.Session;
import util.HttpParameters;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 8/7/14
 * Time: 4:32 PM
 */
public class Login extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpParameters param = new HttpParameters(request);
        String user = param.getString("name");
        String pass = param.getString("pass");
        String browser = param.getString("browser");
        Long lastAccessed = param.getLong("lastAccessed");

        Database connection = new Database();
        PrintWriter out = response.getWriter();
        Integer accountId = validLogin(connection, user, pass);

        if (null != accountId) {
            SessionManager s = new SessionManager(connection);
            Session session = new Session(accountId, user, browser, lastAccessed);
            out.print(s.getJsonSession(session));
            //out.print("{'message': 'success'}");
        } else {
            //out.print("{'message': 'error'}");
            throw new IllegalArgumentException("Account id is null.");
        }
    }

    /*@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        HttpParameters param = new HttpParameters(request);
        String user = param.getString("user");
        PrintWriter out = response.getWriter();
        Database connection = new Database();
        SessionManager s = new SessionManager(connection);
        Session session = s.getSessionByName(user);

    }
*/
    private Integer validLogin(Database connection, String name, String pass) {
        String url = String.format("SELECT * FROM account WHERE name='%s' AND password=MD5('%s')", name, pass);
        System.out.print(url);

        List<Database.Row> results = connection.fetchAll(url);
        if (1 == results.size()) {
            return results.get(0).getInteger("id");
        } else if (results.size() > 1) {
            throw new IllegalArgumentException("Data corrupted. Duplicate accounts.");
        } else {
            return null;
        }
    }


}

