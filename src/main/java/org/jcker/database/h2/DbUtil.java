package org.jcker.database.h2;

import java.sql.*;

public class DbUtil {
/*    private static Connection conn;
    static {
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/smartqq", "root", "root");
            conn.createStatement().execute("create TABLE message(time Long PRIMARY KEY ,userId Long, content VARCHAR)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/
    public static Connection getConnection () throws Exception{
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/smartqq", "root", "root");
            stmt = conn.createStatement();
            return conn;
        }catch (Exception e){
            if (conn == null){
                throw new Exception("Get Connection failed");
            }
            if (stmt == null){
                throw new Exception("Create Statement failed");
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
//        statement.execute("drop table MESSAGE");
//        statement.execute("CREATE TABLE message(time Long PRIMARY KEY ,userId Long, content VARCHAR)");
//        statement.execute("INSERT INTO message(time,userId,content)VALUES (20170704,570577029,'This is a test message')");
    }
}
