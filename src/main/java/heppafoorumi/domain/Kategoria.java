package heppafoorumi.domain;

import java.sql.Timestamp;

public abstract class Kategoria {

    private final int id;        // juokseva numero 1... (0 on varattu poikkeustilanteisiin).
    private final Timestamp aikaleima;
    private String teksti; // alueen nimi / aihe / viestin teksti

    protected final static int NIMIMERKIN_PITUUS = 20;
    protected final static int OTSIKON_PITUUS = 200;
    private final static int TEKSTIN_PITUUS = 200;

    public Kategoria(int id, Timestamp aikaleima, String teksti) {
        this.id = id;
        this.aikaleima = aikaleima;
        this.teksti = teksti.substring(0, Math.min(teksti.length(), TEKSTIN_PITUUS));
    }

    public Kategoria(int id, String teksti) {
        this.id = id;
        this.aikaleima = new java.sql.Timestamp(new java.util.Date().getTime());
        this.teksti = teksti.substring(0, Math.min(teksti.length(), TEKSTIN_PITUUS));
    }

    public Integer getId() {
        return this.id;
    }

    public String getTimestamp() {
        String aikaleimaString = this.aikaleima.toString();
        return aikaleimaString.substring(0, aikaleimaString.lastIndexOf('.'));
    }

    public final Timestamp getAikaleima() {
        return this.aikaleima;
    }

    public String getTeksti() {
        return this.teksti;
    }

    public static String korjaaAakkoset(String sana) {
        sana = sana.replaceAll("ä", "&auml;");
        sana = sana.replaceAll("ö", "&ouml;");
        sana = sana.replaceAll("Ä", "&Auml;");
        sana = sana.replaceAll("Ö", "&Ouml;");
        return sana;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti.substring(0, Math.min(teksti.length(), TEKSTIN_PITUUS));
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
