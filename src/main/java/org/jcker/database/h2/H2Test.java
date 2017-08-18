package org.jcker.database.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Alan Turing on 2017/7/26.
 * h2 database test
 */
public class H2Test {
    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/smartqq", "root", "root");
        Statement stmt = conn.createStatement();
//        stmt.execute("create TABLE message(time BIGINT PRIMARY KEY ,userId BIGINT, content VARCHAR)");
//        stmt.execute("INSERT INTO message(time,userId,content)VALUES (20170704,570577029,'This is a test message')");
        ResultSet rs = stmt.executeQuery("SELECT * FROM message ");
        while(rs.next()) {
            System.out.println(rs.getInt("time") + "\n" +
                    rs.getInt("userId") + "\n" +
                    rs.getString("content"));

        }
        conn.close();
    }
}
