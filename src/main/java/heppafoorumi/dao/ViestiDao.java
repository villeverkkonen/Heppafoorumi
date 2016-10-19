package heppafoorumi.dao;

import heppafoorumi.domain.Viesti;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import heppafoorumi.database.Database;
import heppafoorumi.domain.Aihe;
import heppafoorumi.domain.Alue;
import java.sql.PreparedStatement;

public class ViestiDao implements Dao<Viesti, Integer> {

    private final Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "alue.id AS alue_id, "
                + "alue.aikaleima AS alue_aikaleima, "
                + "alue.otsikko AS alue_otsikko, "
                + "alue.teksti AS alue_teksti, "
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.otsikko AS aihe_otsikko, "
                + "aihe.teksti AS aihe_teksti, "
                + "viesti.id AS viesti_id, "
                + "viesti.aikaleima AS viesti_aikaleima, "
                + "viesti.aihe AS viesti_aihe, "
                + "viesti.nimimerkki AS viesti_nimimerkki, "
                + "viesti.teksti AS viesti_teksti "
                + "FROM Alue alue, Aihe aihe, Viesti viesti "
                + "WHERE aihe.alue = alue.id AND "
                + "viesti.aihe = aihe.id AND "
                + "viesti.id = '" + key + "'");

        boolean hasOne = resultSet.next();
        if (!hasOne) {
            return null;
        }

        Integer alueId = resultSet.getInt("alue_id");
        Integer alueAikaleima = resultSet.getInt("alue_aikaleima");
        String alueOtsikko = resultSet.getString("alue_otsikko");
        String alueTeksti = resultSet.getString("alue_teksti");

        Integer aiheId = resultSet.getInt("aihe_id");
        Integer aiheAikaleima = resultSet.getInt("aihe_aikaleima");
        // Integer aiheAlue = resultSet.getInt("aihe_alue");
        String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
        String aiheOtsikko = resultSet.getString("aihe_otsikko");
        String aiheTeksti = resultSet.getString("aihe_teksti");

        Integer viestiId = resultSet.getInt("viesti_id");
        Integer viestiAikaleima = resultSet.getInt("viesti_aikaleima");
        // Integer viestiAihe = resultSet.getInt("viesti_aihe");
        String viestiNimimerkki = resultSet.getString("viesti_nimimerkki");

        resultSet.close();
        connection.close();

        Alue alue = new Alue(alueId, alueAikaleima, alueOtsikko, alueTeksti);

        Aihe aihe = new Aihe(aiheId, aiheAikaleima, alue, aiheNimimerkki, aiheOtsikko, aiheTeksti);

        Viesti viesti = new Viesti(viestiId, viestiAikaleima, aihe, viestiNimimerkki);

        resultSet.close();
        connection.close();

        return viesti;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "alue.id AS alue_id, "
                + "alue.aikaleima AS alue_aikaleima, "
                + "alue.otsikko AS alue_otsikko, "
                + "alue.teksti AS alue_teksti, "
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.otsikko AS aihe_otsikko, "
                + "aihe.teksti AS aihe_teksti, "
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
            Integer alueId = resultSet.getInt("alue_id");
            Integer alueAikaleima = resultSet.getInt("alue_aikaleima");
            String alueOtsikko = resultSet.getString("alue_otsikko");
            String alueTeksti = resultSet.getString("alue_teksti");

            Integer aiheId = resultSet.getInt("aihe_id");
            Integer aiheAikaleima = resultSet.getInt("aihe_aikaleima");
            // Integer aiheAlue = resultSet.getInt("aihe_alue");
            String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
            String aiheOtsikko = resultSet.getString("aihe_otsikko");
            String aiheTeksti = resultSet.getString("aihe_teksti");

            Integer viestiId = resultSet.getInt("viesti_id");
            Integer viestiAikaleima = resultSet.getInt("viesti_aikaleima");
            // Integer viestiAihe = resultSet.getInt("viesti_aihe");
            String viestiNimimerkki = resultSet.getString("viesti_nimimerkki");

            resultSet.close();
            connection.close();

            Alue alue = new Alue(alueId, alueAikaleima, alueOtsikko, alueTeksti);

            Aihe aihe = new Aihe(aiheId, aiheAikaleima, alue, aiheNimimerkki, aiheOtsikko, aiheTeksti);

            Viesti viesti = new Viesti(viestiId, viestiAikaleima, aihe, viestiNimimerkki);

            viestit.add(viesti);
        }

        resultSet.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public void create(Viesti viesti) throws SQLException {
        Integer id = viesti.getId();
        Integer aikaleima = viesti.getAikaleima();
        String nimimerkki = viesti.getNimimerkki();

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue VALUES(?, ?, ?)");
        stmt.setObject(1, id);
        stmt.setObject(2, aikaleima);
        stmt.setObject(3, nimimerkki);

        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }
}
