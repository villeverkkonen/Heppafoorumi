package heppafoorumi.domain;

public class Alue extends Kategoria {

    // alueen muuttujat:
    // id int PRIMARY KEY,
    // aikaleima date,
    // teksti varchar(200)
    public Alue(Integer id, int aikaleima, String teksti) {
        super(id, aikaleima, teksti);
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
