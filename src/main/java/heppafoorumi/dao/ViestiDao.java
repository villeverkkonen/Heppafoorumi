package heppafoorumi.dao;

import heppafoorumi.domain.Viesti;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import heppafoorumi.database.Database;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class ViestiDao implements Dao<Viesti, Integer> {

    private final Database database;
    private KaikkiDao kaikkiDao;

    public ViestiDao(Database database) {
        this.database = database;
        this.kaikkiDao = null;
    }

    public KaikkiDao getKaikkiDao() {
        return kaikkiDao;
    }

    public void setKaikkiDao(KaikkiDao kaikkiDao) {
        this.kaikkiDao = kaikkiDao;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "viesti.id AS viesti_id, "
                + "viesti.aikaleima AS viesti_aikaleima, "
                + "viesti.aihe AS viesti_aihe, "
                + "viesti.nimimerkki AS viesti_nimimerkki, "
                + "viesti.teksti AS viesti_teksti "
                + "FROM Viesti viesti "
                + "WHERE viesti.id = " + key);

        boolean hasOne = resultSet.next();
        if (!hasOne) {
            return null;
        }

        Integer viestiId = resultSet.getInt("viesti_id");
        Timestamp viestiAikaleima = resultSet.getTimestamp("viesti_aikaleima");
        Integer viestiAihe = resultSet.getInt("viesti_aihe");
        String viestiNimimerkki = resultSet.getString("viesti_nimimerkki");
        String viestiTeksti = resultSet.getString("viesti_teksti");

        resultSet.close();
        connection.close();

        Viesti viesti = new Viesti(this.database, viestiId, viestiAikaleima, viestiAihe, viestiNimimerkki, viestiTeksti);

        resultSet.close();
        connection.close();

        return viesti;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "viesti.id AS viesti_id, "
                + "viesti.aikaleima AS viesti_aikaleima, "
                + "viesti.aihe AS viesti_aihe, "
                + "viesti.nimimerkki AS viesti_nimimerkki, "
                + "viesti.teksti AS viesti_teksti "
                + "FROM Alue alue, Aihe aihe, Viesti viesti "
                + "WHERE aihe.alue = alue.id AND "
                + "viesti.aihe = aihe.id");

        List<Viesti> viestit = new ArrayList();

        while (resultSet.next()) {
            Integer viestiId = resultSet.getInt("viesti_id");
            Timestamp viestiAikaleima = resultSet.getTimestamp("viesti_aikaleima");
            Integer viestiAihe = resultSet.getInt("viesti_aihe");
            String viestiNimimerkki = resultSet.getString("viesti_nimimerkki");
            String viestiTeksti = resultSet.getString("viesti_teksti");

            Viesti viesti = new Viesti(this.database, viestiId, viestiAikaleima, viestiAihe, viestiNimimerkki, viestiTeksti);

            viestit.add(viesti);
        }

        resultSet.close();
        connection.close();

        return viestit;
    }

    public List<Viesti> findAll(int aiheId) throws SQLException {

        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "viesti.id AS viesti_id, "
                + "viesti.aikaleima AS viesti_aikaleima, "
                + "viesti.aihe AS viesti_aihe, "
                + "viesti.nimimerkki AS viesti_nimimerkki, "
                + "viesti.teksti AS viesti_teksti "
                + "FROM Viesti viesti "
                + "WHERE viesti.aihe = " + aiheId);

        List<Viesti> viestit = new ArrayList();

        while (resultSet.next()) {
            Integer viestiId = resultSet.getInt("viesti_id");
            Timestamp viestiAikaleima = resultSet.getTimestamp("viesti_aikaleima");
            String viestiNimimerkki = resultSet.getString("viesti_nimimerkki");
            String viestiTeksti = resultSet.getString("viesti_teksti");

            Viesti viesti = new Viesti(this.database, viestiId, viestiAikaleima, aiheId, viestiNimimerkki, viestiTeksti);

            viestit.add(viesti);
        }

        resultSet.close();
        connection.close();

        return viestit;
    }
    
    public List<Viesti> findAiheidenUusimmatViestit(int alueId, int aiheId) throws SQLException {

        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "viesti.id AS viesti_id, "
                + "viesti.aikaleima AS viesti_aikaleima, "
                + "viesti.aihe AS viesti_aihe, "
                + "viesti.nimimerkki AS viesti_nimimerkki, "
                + "viesti.teksti AS viesti_teksti "
                + "FROM Viesti viesti, Aihe aihe "
                + "WHERE viesti.aihe = " + aiheId
                + "AND aihe.alue = " + alueId
                + " ORDER BY viesti_aikaleima DESC LIMIT 1");

        List<Viesti> viestit = new ArrayList();

        while (resultSet.next()) {
            Integer viestiId = resultSet.getInt("viesti_id");
            Timestamp viestiAikaleima = resultSet.getTimestamp("viesti_aikaleima");
            String viestiNimimerkki = resultSet.getString("viesti_nimimerkki");
            String viestiTeksti = resultSet.getString("viesti_teksti");

            Viesti viesti = new Viesti(this.database, viestiId, viestiAikaleima, aiheId, viestiNimimerkki, viestiTeksti);

            viestit.add(viesti);
        }

        resultSet.close();
        connection.close();

        return viestit;
    }
    
    //metodi sitä varten, että saataisiin joka aiheen viestien määrä
    public int CountAiheViestit(int aiheId) throws SQLException {

        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "COUNT(*) AS viesteja "
                + "FROM Viesti viesti, Aihe aihe "
                + "WHERE viesti.aiheId = " +aiheId);
        
        int viesteja = 0;
        
        while (resultSet.next()) {
            viesteja = resultSet.getInt("viesteja");
        }

        resultSet.close();
        connection.close();

        return viesteja;
    }

    public void delete(int aiheId, int viestiId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Viesti WHERE aihe = ? AND id = ?");
        statement.setObject(1, aiheId);
        statement.setObject(2, viestiId);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public void create(int aiheId, String nimimerkki, String teksti) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO Viesti(aikaleima, aihe, nimimerkki, teksti) VALUES(?, ?, ?, ?)");

        statement.setObject(1, new java.sql.Timestamp(new java.util.Date().getTime()));
        statement.setObject(2, aiheId);
        statement.setObject(3, nimimerkki);
        statement.setObject(4, teksti);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
