package heppafoorumi.domain;

public class Alue extends Kategoria {

    // alueen muuttujat:
    // id int PRIMARY KEY,
    // aikaleima date,
    // otsikko varchar(200),
    // teksti varchar(200)
    private final String otsikko;

    public Alue(Integer id, int aikaleima, String otsikko, String teksti) {
        super(id, aikaleima, teksti);
        // this.otsikko = otsikko.substring(0, Math.min(otsikko.length() - 1, OTSIKON_PITUUS - 1));
        // this.otsikko = otsikko.substring(0, otsikko.length() - 1);
        final int otsikon_pituus = Math.min(otsikko.length(), OTSIKON_PITUUS);
        this.otsikko = otsikko.substring(0, otsikon_pituus);
        // this.otsikko = otsikko;
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
}
