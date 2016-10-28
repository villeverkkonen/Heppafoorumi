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
    private final String teksti;
    private final Timestamp aikaleima;

    // ainoastaan 20 ensimmäistä merkkiä otetaan huomioon.
    private final String nimimerkki;
    private final int id;

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
        if (nimimerkki == null) {
            this.nimimerkki = "";
        } else {
            this.nimimerkki = nimimerkki.substring(0, Math.min(nimimerkki.length(), NIMIMERKIN_PITUUS));
        }
        this.teksti = teksti;
        this.aikaleima = aikaleima;
        this.id = id;
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

    public String getUusimmat() {
        String aikaleimaString = this.aikaleima.toString();
        aikaleimaString = aikaleimaString.substring(0, aikaleimaString.lastIndexOf('.'));
        return this.nimimerkki + " - " + this.teksti + " - (" + aikaleimaString + ")";
    }
    
    public Integer getAiheId() {
        return this.aihe;
    }
}
