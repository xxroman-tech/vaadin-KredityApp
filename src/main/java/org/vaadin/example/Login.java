package org.vaadin.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
	
	private SQLLogger logger;
	

    private int userIDDb;
    private String usernameDb;
    private String passwordDb;

    public Login() {
        this.logger = new SQLLogger();
    }

    public boolean isLoggedIn(String username, String passoword) {
        try {
            Connection conn = this.logger.getConn();
            String mysqlQuery = "SELECT * FROM users WHERE UserName = '" + username +
                    "' AND UserPass = '" + passoword + "';";
            PreparedStatement preparedStatement = conn.prepareStatement(mysqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                this.userIDDb = resultSet.getInt("UserID");
                this.usernameDb = resultSet.getString("UserName");
                this.passwordDb = resultSet.getString("UserPass");

                return true;
            }
        } catch (Exception ex) {
            System.out.println("Database error !!! " + ex.getMessage());
        }

        return false;
    }

    public int getUserIDDb() {
        return this.userIDDb;
    }
    
    public SQLLogger getLogger() {
    	return this.logger;
    }
    
    public String getUsername() {
    	return this.usernameDb;
    }
}
