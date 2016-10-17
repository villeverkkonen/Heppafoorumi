package heppafoorumi.domain;

public abstract class Kategoria {

    private int id;        // juokseva numero.
    private String teksti; // alueen nimi / aihe / viestin teksti

    public Kategoria(int id, String teksti) {
        this.id = id;
        this.teksti = teksti;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return teksti;
    }

    public void setNimi(String nimi) {
        this.teksti = nimi;
    }
}
