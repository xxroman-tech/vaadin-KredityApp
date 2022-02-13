package org.vaadin.example;

public enum PovinnostPredmetu {
    POVINNY("Pov."),
    POVINNEVOL("P.V."),
    VYBEROVY("Vyb.");

    private final String popis;

    PovinnostPredmetu(final String popis) {
        this.popis = popis;
    }

    public String getPopis() {
        return this.popis;
    }
}
