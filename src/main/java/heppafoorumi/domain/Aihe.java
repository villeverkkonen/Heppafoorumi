package heppafoorumi.domain;

public class Aihe extends Kategoria {

    // aiheen muuttujat:
    // id int PRIMARY KEY,
    // aikaleima date,
    // alue int,
    // nimimerkki varchar(20),
    // teksti varchar(40),
    // FOREIGN KEY(alue) REFERENCES Alue(id);
    private final static int NIMIMERKIN_PITUUS = 20;

    private final Alue alue; // huom. Alue-olio, vrt. wepa:n 28. HelloOneToMany:n Agent.java

    // ainoastaan 20 ensimmäistä merkkiä otetaan huomioon.
    private final String nimimerkki;

    public Aihe(Integer id, Alue alue, String nimimerkki, String teksti) {
        super(id, teksti);
        this.alue = alue;

        // tallennetaan enintään 20 ensimmäistä merkkiä syötetystä nimimerkistä.
        this.nimimerkki = nimimerkki.substring(Math.min(nimimerkki.length() - 1, NIMIMERKIN_PITUUS));
    }

    public String getNimimerkki() {
        return this.nimimerkki;
    }
}
