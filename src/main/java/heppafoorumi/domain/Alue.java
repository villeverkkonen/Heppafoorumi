package heppafoorumi.domain;

import java.sql.Timestamp;

public class Alue extends Kategoria {

    // alueen muuttujat:
    // id int PRIMARY KEY,
    // aikaleima date,
    // otsikko varchar(200),
    // teksti varchar(200)
    private final String otsikko;
    private final String teksti;

    public Alue(Integer id, Timestamp aikaleima, String otsikko, String teksti) {
        super(id, aikaleima, teksti);
        // this.otsikko = otsikko.substring(0, Math.min(otsikko.length() - 1, OTSIKON_PITUUS - 1));
        // this.otsikko = otsikko.substring(0, otsikko.length() - 1);
        final int otsikon_pituus = Math.min(otsikko.length(), OTSIKON_PITUUS);
        this.otsikko = otsikko.substring(0, otsikon_pituus);
        this.teksti = teksti;
        // this.otsikko = otsikko;
    }

    public Alue(Integer id, String otsikko, String teksti) {
        this(id, new java.sql.Timestamp(new java.util.Date().getTime()), otsikko, teksti);
    }
    
    public Alue(String otsikko, String teksti) {
        this(-1, new java.sql.Timestamp(new java.util.Date().getTime()), otsikko, teksti);
    }

    public String getOtsikko() {
        return this.otsikko;
    }

    @Override
    public boolean equals(Object toinen) {
        if (this.getClass() != toinen.getClass()) {
            return false;
        }

        Alue alue = (Alue) toinen;

        return this.getId().equals(alue.getId());
    }
    
    public String getTeksti() {
        return this.teksti;
    }
}
