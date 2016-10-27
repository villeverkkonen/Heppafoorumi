package heppafoorumi.domain;

import java.sql.Timestamp;

public class Alueraportti {

    private final Alue alue;
    private final Aihe aihe;
    private final Viesti viesti;
    private final Integer aiheidenLkm;
    private final Integer viestienLkm;
    private final Timestamp uusinAiheAikaleima;
    private final Timestamp uusinViestiAikaleima;

    // Raporttiluokka aluenäkymän luontia varten.
    public Alueraportti(
            Alue alue, Aihe aihe, Viesti viesti, Integer aiheidenLkm, Integer viestienLkm, Timestamp uusinAiheAikaleima, Timestamp uusinViestiAikaleima) {
        this.alue = alue;
        this.aihe = aihe;
        this.viesti = viesti;
        this.aiheidenLkm = aiheidenLkm;
        this.viestienLkm = viestienLkm;
        this.uusinAiheAikaleima = uusinAiheAikaleima;
        this.uusinViestiAikaleima = uusinViestiAikaleima;
    }

    public Integer getAiheidenLkm() {
        return aiheidenLkm;
    }

    public Integer getViestienLkm() {
        return viestienLkm;
    }

    public Timestamp getUusinAiheAikaleima() {
        return uusinAiheAikaleima;
    }

    public Timestamp getUusinViestiAikaleima() {
        return uusinViestiAikaleima;
    }

    public Integer getAlueId() {
        return this.alue.getId();
    }

    public String getAlueOtsikko() {
        return this.alue.getOtsikko();
    }

    public String getAlueTeksti() {
        return this.alue.getTeksti();
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

    public String getAiheAikaleima() {
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

    public String getViestiTeksti() {
        if (this.aihe == null) {
            return null;
        }
        return this.viesti.getTeksti();
    }

    public String getViestiAikaleima() {
        if (this.aihe == null) {
            return null;
        }
        return this.viesti.getAikaleima();
    }
}
