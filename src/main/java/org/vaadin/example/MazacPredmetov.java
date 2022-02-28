package org.vaadin.example;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MazacPredmetov {
	private SQLLogger logger;
    private int userID;
    
    public MazacPredmetov(SQLLogger logger, int userID) {
    	this.logger = logger;
    	this.userID = userID;
    }
    
    public boolean zmaz(String cisloPredmetu, int kredity) {
    	System.out.println("\n#####################################################");
        System.out.println("Mazanie predmentu:");
        
        if(vymazPredmet(cisloPredmetu, kredity)) {
        	System.out.println("Predmet bol vymazany");
            return true;
        } else {
        	return false;
        }
        
    }
    
    private boolean vymazPredmet(String cisloPredmetu, int kredity) {
    	try {
            Connection conn = this.logger.getConn();

            String mysqlQuery = "DELETE FROM predmety WHERE UserID='?' AND ID='?' AND Kredity=?";
            PreparedStatement preparedStatement = conn.prepareStatement(mysqlQuery);
            preparedStatement.setInt(1, this.userID);
            preparedStatement.setString(2, cisloPredmetu);
            preparedStatement.setInt(3, kredity);

            preparedStatement.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Database error !!! " + ex.getMessage());
        }
        return false;
    }
}
