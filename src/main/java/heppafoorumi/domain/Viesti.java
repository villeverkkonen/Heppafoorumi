package heppafoorumi.domain;

public class Viesti extends Kategoria {

    // viestin muuttujat:
    // id int PRIMARY KEY,
    // aikaleima date,
    // aihe int,
    // nimimerkki varchar(20),
    // teksti varchar(200),
    // FOREIGN KEY(aihe) REFERENCES Aihe(id);
    private final static int NIMIMERKIN_PITUUS = 20;

    private final Aihe aihe; // huom. Aihe-olio, vrt. wepa:n 28. HelloOneToMany:n Agent.java

    // ainoastaan 20 ensimmäistä merkkiä otetaan huomioon.
    private final String nimimerkki;

    public Viesti(Integer id, int aikaleima, Aihe aihe, String nimimerkki) {
        super(id, aikaleima, nimimerkki);
        this.aihe = aihe;

        // tallennetaan enintään 20 ensimmäistä merkkiä syötetystä nimimerkistä.
        this.nimimerkki = nimimerkki.substring(Math.min(nimimerkki.length() - 1, NIMIMERKIN_PITUUS));
    }

    public String getNimimerkki() {
        return this.nimimerkki;
    }

    @Override
    public String toString() {
        return this.nimimerkki + ": " + super.toString();
    }

    @Override
    public boolean equals(Object toinen) {
        if (this.getClass() != toinen.getClass()) {
            return false;
        }

        Viesti viesti = (Viesti) toinen;

        return this.getId().equals(viesti.getId());
    }
}
