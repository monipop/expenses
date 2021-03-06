package database;

import util.DateConversions;

import javax.servlet.ServletContext;
import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: moni
 * Date: 4/2/14
 * Time: 10:53 AM
 */
public class Database {
    protected Connection connect;

    public Database() {
        connectToDatabase();
    }

    protected void connectToDatabase() {
        Properties prop = new Properties();
        InputStream input = null;
        String fileName = "config.properties";
        String url = null;
        String user = null;
        String password = null;


        try {
            //System.out.println(System.getProperty("user.dir"));

            //load the properties file
            input = this.getClass().getClassLoader().getResourceAsStream("/config.properties");
            if (input == null) {
                input = new FileInputStream(fileName);
            }
            prop.load(input);

            if (!prop.isEmpty()) {
                url = prop.getProperty("url");
                user = prop.getProperty("user");
                password = prop.getProperty("password");
            }
        } catch (IOException e) {
            String message = String.format("File %s was not found. %s", fileName, input);
            throw new IllegalArgumentException(message, e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //insert in table
    public int update(String query) {
        try {
            return connect.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new IllegalStateException("Exception executing " + query, e);
        }
    }

    //select form table
    public ResultSet fetch(String query) {
        Statement statement = null;

        try {
            statement = connect.createStatement();
            return statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Row fetchOne(String query) {
        List<Row> data = fetchAll(query);
        if (!data.isEmpty()) {
            return data.get(0);
        } else {
            return null;
        }

    }
    //select form table
    public List<Row> fetchAll(String query) {
        Statement statement = null;

        try {
            List<Row> rows = new ArrayList<>();
            statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<String> columns = getColumns(resultSet);
            while (resultSet.next()) {
               Map<String, String> map = new HashMap<>();
               for (String column : columns) {
                   map.put(column, resultSet.getString(column));
               }
               rows.add(new Row(map));
            }

            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getColumns(ResultSet resultSet) {
        List<String> columns = new ArrayList<>();
        try {
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                columns.add(resultSet.getMetaData().getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }

    public static class Row {
        private final Map<String, String> data;

        public Row(Map<String, String> data) {
            this.data = data;
        }

        public String getString(String column) {
            if (!data.containsKey(column)) {
                throw new IllegalArgumentException("Column does not exists " + column);
            }
            return data.get(column);
        }

        public Integer getInteger(String column) {
            if (!data.containsKey(column)) {
                throw new IllegalArgumentException("Column does not exists " + column);
            }
            return Integer.parseInt(getString(column));
        }

        public Long getLong(String column) {
            if (!data.containsKey(column)) {
                throw new IllegalArgumentException("Column does not exists " + column);
            }
            return Long.parseLong(getString(column));
        }

        public Double getDouble(String column) {
            if (!data.containsKey(column)) {
                throw new IllegalArgumentException("Column does not exists " + column);
            }
            return Double.parseDouble(getString(column));
        }

        public Date getDate(String column) {
            if (!data.containsKey(column)) {
                throw new IllegalArgumentException("Column does not exists " + column);
            }
            String data = getString(column);

            return DateConversions.convertyyyyMMddStringDateToSqlDate(data);
        }
    }


    public Integer lastInsertId() {
        int id = 0;

        try {
            Statement db_statement = this.connect.createStatement();
            ResultSet result = db_statement.executeQuery("SELECT LAST_INSERT_ID() AS id");
            result.next();
            id = Integer.parseInt(result.getString("id"));
            //System.out.println("result.getString = " + result.getString("id"));
            System.out.println("last inserted id = " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
