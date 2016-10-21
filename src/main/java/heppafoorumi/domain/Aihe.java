package heppafoorumi.domain;

import heppafoorumi.dao.AiheDao;
import heppafoorumi.database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Aihe extends Kategoria {

    // aiheen muuttujat:
    // id int PRIMARY KEY,
    // aikaleima date,
    // alue int,
    // nimimerkki varchar(20),
    // otsikko varchar(200),
    // teksti varchar(200),
    // FOREIGN KEY(alue) REFERENCES Alue(id);
    private final int alue;
    private final String otsikko;

    private final Database database;

    // ainoastaan 20 ensimmäistä merkkiä otetaan huomioon.
    private final String nimimerkki;
    private final String kuvaus;
    private final int aiheId;

    private static int getNewAiheId(Database database) throws SQLException {
        Connection connection = database.getConnection();
        int id;
        ResultSet resultSet = connection.createStatement().executeQuery(
                "SELECT id FROM Aihe ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            id = resultSet.getInt("id") + 1;
        } else {
            id = 1;
        }
        resultSet.close();
        return id;
    }

    public Aihe(Database database, Integer id, Timestamp aikaleima, int alueId, String nimimerkki, String otsikko, String teksti) {
        super(id, aikaleima, teksti);
        this.database = database;
        this.alue = alueId;

        // tallennetaan enintään 20 ensimmäistä merkkiä syötetystä nimimerkistä.
        this.nimimerkki = nimimerkki.substring(0, Math.min(nimimerkki.length(), NIMIMERKIN_PITUUS));
        this.otsikko = otsikko.substring(0, Math.min(otsikko.length(), OTSIKON_PITUUS));
        this.kuvaus = teksti;
        this.aiheId = id;
    }

    public Aihe(Database database, Integer id, int alueId, String nimimerkki, String otsikko, String teksti) {
        this(database, id, new java.sql.Timestamp(new java.util.Date().getTime()), alueId, nimimerkki, otsikko, teksti);
    }

    public Aihe(Database database, int alueId, String nimimerkki, String otsikko, String teksti) throws SQLException {
        this(database, Aihe.getNewAiheId(database), alueId, nimimerkki, otsikko, teksti);
        new AiheDao(this.database).create(this.alue, this.getNimimerkki(), this.otsikko, this.getTeksti());
    }

    public String getNimimerkki() {
        return this.nimimerkki;
    }

    public String getOtsikko() {
        return this.otsikko;
    }

    public String getKuvaus() {
        return this.kuvaus;
    }

    @Override
    public Integer getId() {
        return this.aiheId;
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

        Aihe aihe = (Aihe) toinen;

        return this.getId().equals(aihe.getId());
    }
}
