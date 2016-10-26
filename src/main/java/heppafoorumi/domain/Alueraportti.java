package heppafoorumi.domain;

import java.sql.Timestamp;

public class Alueraportti {

    private final Aihe aihe;
    private final Viesti viesti;

    // Raporttiluokka aluenäkymän luontia varten.
    public Alueraportti(Aihe aihe, Viesti viesti) {
        this.aihe = aihe;
        this.viesti = viesti;
    }

    public String getAiheNimimerkki() {
        if (this.aihe == null) {
            return null;
        }
        return this.aihe.getNimimerkki();
    }

    public String getAiheOtsikko() {
        if (this.aihe == null) {
            return null;
        }
        return this.aihe.getOtsikko();
    }

    public String getAiheKuvaus() {
        if (this.aihe == null) {
            return null;
        }
        return this.aihe.getKuvaus();
    }

    public Timestamp getAiheAikaleima() {
        if (this.aihe == null) {
            return null;
        }
        return this.aihe.getAikaleima();
    }

    public String getViestiNimimerkki() {
        if (this.aihe == null) {
            return "";
        }
        return this.viesti.getNimimerkki();
    }

    public String getViestiOtsikko() {
        if (this.aihe == null) {
            return null;
        }
        return this.viesti.getTeksti();
    }

    public Timestamp getViestiAikaleima() {
        if (this.aihe == null) {
            return null;
        }
        return this.aihe.getAikaleima();
    }
}
