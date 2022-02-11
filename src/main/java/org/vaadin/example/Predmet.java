package org.vaadin.example;

public class Predmet {
    private String cislo;
    private String nazov;
    private PovinnostPredmetu povinnost;
    private int body;
    private char znamka;
    private int kredity;

    public Predmet(String pCislo, String pNazov, PovinnostPredmetu pPovinnost, int pBody, char pZnamka, int pKredity) {
        this.cislo = pCislo;
        this.nazov = pNazov;
        this.povinnost = pPovinnost;
        this.body = pBody;
        this.znamka = pZnamka;
        this.kredity = pKredity;
    }

    public String getCislo() {
        return this.cislo;
    }

    public String getNazov() {
        return this.nazov;
    }

    public PovinnostPredmetu getPovinnost() {
        return this.povinnost;
    }

    public int getBody() {
        return this.body;
    }

    public char getZnamka() {
        return this.znamka;
    }

    public int getKredity() {
        return this.kredity;
    }

    @Override
    public String toString() {
        return "| " + this.cislo + " | " + this.nazov + " | " + this.povinnost.getPopis() + " | "
                + this.body + " | " + this.znamka + " | " + this.kredity + " |";
    }
}
