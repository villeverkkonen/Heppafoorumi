package heppafoorumi.domain;

public abstract class Kategoria {

    private final int id;        // juokseva numero 1... (0 on varattu poikkeustilanteisiin).
    private final java.sql.Timestamp aikaleima;
    private String teksti; // alueen nimi / aihe / viestin teksti

    public Kategoria(int id, String teksti) {
        java.util.Date today = new java.util.Date();

        this.id = id;
        this.aikaleima = new java.sql.Timestamp(today.getTime());
        this.teksti = teksti;
    }

    public Integer getId() {
        return this.id;
    }

    public final java.sql.Timestamp getAikaleima() {
        return this.aikaleima;
    }

    public String getTeksti() {
        return this.teksti;
    }

    public void setTeksti(String nimi) {
        this.teksti = nimi;
    }
}
