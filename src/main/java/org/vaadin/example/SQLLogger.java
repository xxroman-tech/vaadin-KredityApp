package org.vaadin.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLLogger {

    private Connection conn;

    public SQLLogger() {
        this.conn = null;

        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Kredity?characterEncoding=utf8", "oldSQLUser", "heslo123");
        } catch (SQLException ex) {
            System.out.println("SQLException ===== " + ex.getMessage());
            System.out.println("SQLState     ===== " + ex.getSQLState());
            System.out.println("VendorError  ===== " + ex.getErrorCode());
            System.exit(0);
        }
    }

    public Connection getConn() {
        return this.conn;
    }
}