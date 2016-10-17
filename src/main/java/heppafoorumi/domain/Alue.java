package heppafoorumi.domain;

public class Alue {

    private Integer id;
    private String teksti;

    public Alue(Integer id, String nimi) {
        this.id = id;
        this.teksti = nimi;
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

