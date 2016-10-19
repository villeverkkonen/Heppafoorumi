package heppafoorumi.domain;

import java.sql.Timestamp;

public abstract class Kategoria {

    private final int id;        // juokseva numero 1... (0 on varattu poikkeustilanteisiin).
    private final Timestamp aikaleima;
    private String teksti; // alueen nimi / aihe / viestin teksti

    protected final static int NIMIMERKIN_PITUUS = 20;
    protected final static int OTSIKON_PITUUS = 200;

    public Kategoria(int id, Timestamp aikaleima, String teksti) {
        java.util.Date today = new java.util.Date();

        this.id = id;
        this.aikaleima = aikaleima;
        this.teksti = teksti;
    }

    public Integer getId() {
        return this.id;
    }

    public final Timestamp getAikaleima() {
        return this.aikaleima;
    }

    public String getTeksti() {
        return this.teksti;
    }

    public void setTeksti(String nimi) {
        this.teksti = nimi;
    }

    @Override
    public String toString() {
        return this.id + " " + this.aikaleima + " " + this.teksti;
    }

    @Override
    public int hashCode() {
        return 5 * this.id;
    }
}
