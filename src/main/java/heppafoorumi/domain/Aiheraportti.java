package heppafoorumi.domain;

import java.sql.Timestamp;

public class Aiheraportti {

    private final Aihe aihe;
    private final Viesti viesti;

    // Raporttiluokka aluenäkymän luontia varten.
    public Aiheraportti(Aihe aihe, Viesti viesti) {
        this.aihe = aihe;
        this.viesti = viesti;
    }

    public Integer getAiheId() {
        if (this.aihe == null) {
            return null;
        }
        return this.aihe.getId();
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

    public String getAiheTeksti() {
        if (this.aihe == null) {
            return null;
        }
        return this.aihe.getTeksti();
    }

    public Timestamp getAiheAikaleima() {
        if (this.aihe == null) {
            return null;
        }
        return this.aihe.getAikaleima();
    }

    public Integer getViestiId() {
        if (this.viesti == null) {
            return null;
        }
        return this.viesti.getId();
    }

    public String getViestiNimimerkki() {
        if (this.aihe == null) {
            return "";
        }
        return this.viesti.getNimimerkki();
    }

    public String getViestiTeksti() {
        if (this.aihe == null) {
            return null;
        }
        return this.viesti.getTeksti();
    }

    public Timestamp getViestiAikaleima() {
        if (this.viesti == null) {
            return null;
        }
        return this.viesti.getAikaleima();
    }
}
