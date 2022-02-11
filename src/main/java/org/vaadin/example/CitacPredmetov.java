package org.vaadin.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;


public class CitacPredmetov {
    private HashMap<String, Predmet> zoznamPredmetov;
    private SQLLogger logger;
    private int userID;

    private int pocetKreditov;
    private int pocetPovVolPredmetov;
    private final int potrebnyPocet = 180;
    private final int potrebnyPocetPovPred = 15;

    public CitacPredmetov(SQLLogger pLogger, int pUserID) {
        this.zoznamPredmetov = new HashMap<>();
        this.logger = pLogger;
        this.userID = pUserID;

        this.pocetKreditov = 0;

        if (this.nacitajPredemty()) {
            System.out.println("Data loaded");
            this.urciPocetKreditov();
        } else {
            System.out.println("Database Error !!!! Nepodarilo sa nacitat predmety");
        }
    }

    public int getPocetKreditov() {
        return this.pocetKreditov;
    }

    public int getPocetPovVolPredmetov() {
        return this.pocetPovVolPredmetov;
    }

    public boolean nacitajPredemty() {
        try {
            Connection conn = this.logger.getConn();
            String mysqlQuery = "SELECT * FROM predmety WHERE UserID = '" + this.userID + "';";
            PreparedStatement preparedStatement = conn.prepareStatement(mysqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String ID = resultSet.getString("ID");
                String nazov = resultSet.getString("Nazov");
                PovinnostPredmetu povinnost = this.urciPovinnost(resultSet.getString("Povinnost"));
                int body = resultSet.getInt("Body");
                char znamka = resultSet.getString("Znamka").charAt(0);
                int kredity = resultSet.getInt("Kredity");

                Predmet predmet = new Predmet(ID, nazov, povinnost, body, znamka, kredity);
                this.zoznamPredmetov.put(ID, predmet);
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Database error !!! " + ex.getMessage());
        }
        return false;
    }

    /**
     * Metoda na vyhladanie predmetu v zozname
     * @param cisloPredmetu
     * @return
     */
    public Predmet vyhladajPredmet(String cisloPredmetu) {
        for (Predmet p : this.zoznamPredmetov.values()) {
            if (cisloPredmetu.equals(p.getCislo())) {
                return p;
            }
        }
        return null;
    }

    private PovinnostPredmetu urciPovinnost(String text) {
        if (text.equals("Pov.")) {
            return PovinnostPredmetu.POVINNY;
        } else if (text.equals("P.V.")) {
            return PovinnostPredmetu.POVINNEVYB;
        } else if (text.equals("Vyb.")) {
            return PovinnostPredmetu.VYBEROVY;
        } else {
            return null;
        }
    }


    public void vypisPredmety() {
        for (Predmet p : this.zoznamPredmetov.values()) {
            System.out.printf("| " + p.getCislo() + " | %-40s | " + p.getPovinnost().getPopis() + " | %2s | "
                    + p.getZnamka() + " | " + p.getKredity() + " |%n", p.getNazov(), p.getBody());
        }
    }

    private void urciPocetKreditov() {
        for (Predmet p : this.zoznamPredmetov.values()) {
            this.pocetKreditov += p.getKredity();
        }
    }

    private void urciPocetPovVolitelnychPredmetov() {
        for (Predmet p : this.zoznamPredmetov.values()) {
            if (p.getPovinnost() == PovinnostPredmetu.POVINNEVYB) {
                this.pocetPovVolPredmetov += p.getKredity();
            }
        }
    }

    public HashMap<String, Predmet> getZoznamPredmetov() {
        HashMap<String, Predmet> zoznam = new HashMap<>(this.zoznamPredmetov);
        return zoznam;
    }

    public void vymazPredmety() {
        this.zoznamPredmetov.clear();
    }
}
