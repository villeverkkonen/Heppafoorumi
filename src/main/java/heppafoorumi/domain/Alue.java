package heppafoorumi.domain;

import heppafoorumi.database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Alue extends Kategoria {

    // alueen muuttujat:
    // id int PRIMARY KEY,
    // aikaleima date,
    // otsikko varchar(200),
    // teksti varchar(200)
    private final String otsikko;
    private final String teksti;

    private final Database database;

    private static int getNewAlueId(Database database) throws SQLException {
        Connection connection = database.getConnection();
        int id;
        ResultSet resultSet = connection.createStatement().executeQuery(
                "SELECT id FROM Alue ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            id = resultSet.getInt("id") + 1;
        } else {
            id = 1;
        }
        resultSet.close();
        return id;
    }

    public Alue(Database database, Integer id, Timestamp aikaleima, String otsikko, String teksti) {
        super(id, aikaleima, teksti);
        this.database = database;
        // this.otsikko = otsikko.substring(0, Math.min(otsikko.length() - 1, OTSIKON_PITUUS - 1));
        // this.otsikko = otsikko.substring(0, otsikko.length() - 1);
        final int otsikon_pituus = Math.min(otsikko.length(), OTSIKON_PITUUS);
        this.otsikko = otsikko.substring(0, otsikon_pituus);
        this.teksti = teksti;
        // this.otsikko = otsikko;
    }

    public Alue(Database database, Integer id, String otsikko, String teksti) {
        this(database, id, new java.sql.Timestamp(new java.util.Date().getTime()), otsikko, teksti);
    }

    public Alue(Database database, String otsikko, String teksti) throws SQLException {
        this(database, Alue.getNewAlueId(database), new java.sql.Timestamp(new java.util.Date().getTime()), otsikko, teksti);
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
    
    public String getTeksti() {
        return this.teksti;
    }
}
