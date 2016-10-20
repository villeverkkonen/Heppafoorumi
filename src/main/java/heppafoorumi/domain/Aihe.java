package heppafoorumi.domain;

import java.sql.Timestamp;

public class Aihe extends Kategoria {

    // aiheen muuttujat:
    // id int PRIMARY KEY,
    // aikaleima date,
    // alue int,
    // nimimerkki varchar(20),
    // otsikko varchar(200),
    // teksti varchar(200),
    // FOREIGN KEY(alue) REFERENCES Alue(id);
    private final Alue alue; // huom. Alue-olio, vrt. wepa:n 28. HelloOneToMany:n Agent.java
    private final String otsikko;

    // ainoastaan 20 ensimmäistä merkkiä otetaan huomioon.
    private final String nimimerkki;

    public Aihe(Integer id, Timestamp aikaleima, Alue alue, String nimimerkki, String otsikko, String teksti) {
        super(id, aikaleima, teksti);
        this.alue = alue;

        // tallennetaan enintään 20 ensimmäistä merkkiä syötetystä nimimerkistä.
        this.nimimerkki = nimimerkki.substring(Math.min(nimimerkki.length() - 1, NIMIMERKIN_PITUUS));
        this.otsikko = otsikko.substring(Math.min(otsikko.length() - 1, OTSIKON_PITUUS));
    }

    public Aihe(Integer id, Alue alue, String nimimerkki, String otsikko, String teksti) {
        this(id, new java.sql.Timestamp(new java.util.Date().getTime()), alue, nimimerkki, otsikko, teksti);
    }
    
    public Aihe(Alue alue, String nimimerkki, String otsikko, String teksti) {
        this(-1, alue, nimimerkki, otsikko, teksti);
    }

    public Alue getAlue() {
        return this.alue;
    }

    public String getNimimerkki() {
        return this.nimimerkki;
    }

    public String getOtsikko() {
        return this.otsikko;
    }

    @Override
    public String toString() {
        return this.nimimerkki + ": " + super.toString();
    }

    @Override
    public boolean equals(Object toinen) {
        if (this.getClass() != toinen.getClass()) {
            return false;
        }

        Aihe aihe = (Aihe) toinen;

        return this.getId().equals(aihe.getId());
    }
}
