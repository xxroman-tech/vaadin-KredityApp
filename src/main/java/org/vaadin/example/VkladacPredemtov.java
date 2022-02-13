package org.vaadin.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 * 22/01/2022 - 20:02
 *
 * @author romanlojko
 */
public class VkladacPredemtov {
    private SQLLogger logger;
    private int userID;

    private String cislo;
    private String nazov;
    private PovinnostPredmetu povinnost;
    private int body;
    private String znamka;
    private int kredity;

    private Scanner sc;

    public VkladacPredemtov(SQLLogger pLogger, int pUserID) {
        this.logger = pLogger;
        this.userID = pUserID;
    }

    public boolean vkladaj(String cislo, String nazov, PovinnostPredmetu povinnost, int body, String znamka, int kredity) {
        System.out.println("\n#####################################################");
        System.out.println("Vkladanie predmentu:");

        if (this.vlozPredmet(cislo, nazov, povinnost, body, znamka, kredity)) {
            System.out.println("Predmet bol vlozeny");
            return true;
        } else {
            System.out.println("Error !#!#!#!# Predmet sa nepodarilo vlozit!");
            return false;
        }
    }

    private boolean vlozPredmet(String cislo, String nazov, PovinnostPredmetu povinnost, int body, String znamka, int kredity) {
        try {
            this.sc = new Scanner(System.in);
            Connection conn = this.logger.getConn();

            String mysqlQuery = "INSERT INTO predmety ( UserID, ID, Nazov, Povinnost, Body, Znamka, Kredity) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(mysqlQuery);
            preparedStatement.setInt(1, this.userID);
            preparedStatement.setString(2, cislo);
            preparedStatement.setString(3, nazov);
            preparedStatement.setString(4, povinnost.getPopis());
            preparedStatement.setInt(5, body);
            preparedStatement.setString(6, znamka);
            preparedStatement.setInt(7, kredity);

            preparedStatement.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Database error !!! " + ex.getMessage());
        }
        return false;
    }

    private String getText() {
        return this.sc.nextLine();
    }

    private int getInt() {
        return this.sc.nextInt();
    }

    private PovinnostPredmetu getPovinnost(String text) {
        if (text.equals("Pov.")) {
            return PovinnostPredmetu.POVINNY;
        } else if (text.equals("P.V.")) {
            return PovinnostPredmetu.POVINNEVOL;
        } else if (text.equals("Vyb.")) {
            return PovinnostPredmetu.VYBEROVY;
        } else  {
            System.out.println("Error !#!#!#!# Zle zadana povinnost");
            return null;
        }
    }

}
