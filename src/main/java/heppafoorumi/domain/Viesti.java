package heppafoorumi.domain;

import heppafoorumi.dao.ViestiDao;
import heppafoorumi.database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Viesti extends Kategoria {

    // viestin muuttujat:
    // id int PRIMARY KEY,
    // aikaleima date,
    // aihe int,
    // nimimerkki varchar(20),
    // teksti varchar(200),
    // FOREIGN KEY(aihe) REFERENCES Aihe(id);
    private final int aihe;

    private final Database database;

    // ainoastaan 20 ensimmäistä merkkiä otetaan huomioon.
    private final String nimimerkki;

    private static int getNewViestiId(Database database) throws SQLException {
        Connection connection = database.getConnection();
        int id;
        ResultSet resultSet = connection.createStatement().executeQuery(
                "SELECT id FROM Viesti ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            id = resultSet.getInt("id") + 1;
        } else {
            id = 1;
        }
        resultSet.close();
        return id;
    }

    public Viesti(Database database, Integer id, Timestamp aikaleima, int aiheId, String nimimerkki, String teksti) {
        super(id, aikaleima, teksti);
        this.database = database;
        this.aihe = aiheId;

        // tallennetaan enintään 20 ensimmäistä merkkiä syötetystä nimimerkistä.
        this.nimimerkki = nimimerkki.substring(Math.min(nimimerkki.length() - 1, NIMIMERKIN_PITUUS));
    }

    public Viesti(Database database, Integer id, int aiheId, String nimimerkki, String teksti) {
        this(database, id, new java.sql.Timestamp(new java.util.Date().getTime()), aiheId, nimimerkki, teksti);
    }

    public Viesti(Database database, int aiheId, String nimimerkki, String teksti) throws SQLException {
        this(database, aiheId, Viesti.getNewViestiId(database), nimimerkki, teksti);
        new ViestiDao(this.database).create(aiheId, this.getNimimerkki(), this.getTeksti());
    }

//    public Aihe getAihe() {
//        return this.aihe;
//    }
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
