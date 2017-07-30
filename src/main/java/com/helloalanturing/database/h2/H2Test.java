package com.helloalanturing.database.h2;

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
            Connection conn = DriverManager.
                    getConnection("jdbc:h2:./src/main/resources/h2", "sa", "");
            // add application code here
            Statement stmt = conn.createStatement();
        stmt.execute("create TABLE TEST(id int PRIMARY KEY , name VARCHAR)");
        stmt.execute("insert into TEST (ID, NAME ) VALUES ('2','Alan Turing')");
            ResultSet rs = stmt.executeQuery("SELECT * FROM TEST ");
            while(rs.next()) {
                System.out.println(rs.getInt("ID")+","+rs.getString("NAME"));
            }
            conn.close();
    }
}
