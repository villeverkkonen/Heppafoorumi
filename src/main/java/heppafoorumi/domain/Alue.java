package heppafoorumi.domain;

public class Alue extends Kategoria {

    // alueen muuttujat:
    // id int PRIMARY KEY,
    // aikaleima date,
    // teksti varchar(200)
    public Alue(Integer id, String teksti) {
        super(id, teksti);
    }
}
