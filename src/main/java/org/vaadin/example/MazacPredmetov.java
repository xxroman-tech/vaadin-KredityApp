package org.vaadin.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.vaadin.flow.component.notification.Notification;

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
            if (skontrolujCiPredmetExistuje(cisloPredmetu, kredity)) {
            	String mysqlQuery = "DELETE FROM predmety WHERE UserID=? AND ID=? AND Kredity=?";
                PreparedStatement preparedStatement = conn.prepareStatement(mysqlQuery);
                preparedStatement.setInt(1, this.userID);
                preparedStatement.setString(2, cisloPredmetu);
                preparedStatement.setInt(3, kredity);

                preparedStatement.execute();
                return true;
            } else {
            	return false;
            }
        } catch (Exception ex) {
            System.out.println("Database error !!! " + ex.getMessage());
        }
        return false;
    }
    
    private boolean skontrolujCiPredmetExistuje(String cisloPredmetu, int kredity) {
        ResultSet rs = null;
        
    	try {
            int count = 0;
            Connection conn = this.logger.getConn();

            String mysqlQuery = "SELECT * FROM predmety WHERE UserID=? AND ID=? AND Kredity=?";
            PreparedStatement preparedStatement = conn.prepareStatement(mysqlQuery);
            preparedStatement.setInt(1, this.userID);
            preparedStatement.setString(2, cisloPredmetu);
            preparedStatement.setInt(3, kredity);

            rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
            	count++;
            }
            
            if (count == 0) {
            	System.out.println("Predmet sa nenachadza v database");
            	Notification.show("Predmet sa nenachadza v database");
            	return false;
            }
            
            System.out.println("Predmet sa nachadza v database");
            return true;
        } catch (Exception ex) {
            System.out.println("Database error !!! " + ex.getMessage());
        }
        return false;
    }
}
