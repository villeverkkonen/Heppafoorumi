package heppafoorumi.dao;

import heppafoorumi.domain.Aihe;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import heppafoorumi.database.Database;
import heppafoorumi.domain.Alue;
import static heppafoorumi.domain.Kategoria.korjaaAakkoset;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class AiheDao implements Dao<Aihe, Integer> {

    private final Database database;

    public AiheDao(Database database) {
        this.database = database;
    }

    @Override
    public Aihe findOne(Integer key) throws SQLException {
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
                + "aihe.otsikko AS aihe_otsikko "
                + "aihe.teksti AS aihe_teksti "
                + "FROM Alue alue, Aihe aihe "
                + "WHERE aihe.alue = alue.id AND "
                + "alue.id = '" + key + "'");

        if (resultSet == null || !resultSet.next()) {
            return null;
        }

        Integer alueId = resultSet.getInt("alue_id");
        Timestamp alueAikaleima = resultSet.getTimestamp("alue_aikaleima");
        String alueOtsikko = resultSet.getString("alue_otsikko");
        String alueTeksti = resultSet.getString("alue_teksti");

        Integer aiheId = resultSet.getInt("aihe_id");
        Timestamp aiheAikaleima = resultSet.getTimestamp("aihe_aikaleima");
        // Integer aiheAlue = resultSet.getInt("aihe_alue");
        String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
        String aiheOtsikko = resultSet.getString("aihe_otsikko");
        String aiheTeksti = resultSet.getString("aihe_teksti");

        resultSet.close();
        connection.close();

        Alue alue = new Alue(alueId, alueAikaleima, alueOtsikko, alueTeksti);

        Aihe aihe = new Aihe(aiheId, aiheAikaleima, alue, aiheNimimerkki, aiheOtsikko, aiheTeksti);

        return aihe;
    }

    public List<Aihe> findAll() throws SQLException {

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
                + "aihe.teksti AS aihe_teksti "
                + "FROM Alue alue, Aihe aihe");

        List<Aihe> aiheet = new ArrayList();
        while (resultSet.next()) {
            Integer alueId = resultSet.getInt("alue_id");
            Timestamp alueAikaleima = resultSet.getTimestamp("alue_aikaleima");
            String alueOtsikko = resultSet.getString("alue_otsikko");
            String alueTeksti = resultSet.getString("alue_teksti");

            Integer aiheId = resultSet.getInt("aihe_id");
            Timestamp aiheAikaleima = resultSet.getTimestamp("aihe_aikaleima");
            // Integer aiheAlue = resultSet.getInt("aihe_alue");
            String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
            String aiheOtsikko = resultSet.getString("aihe_otsikko");
            String aiheTeksti = resultSet.getString("aihe_teksti");

            Alue alue = new Alue(alueId, alueAikaleima, alueOtsikko, alueTeksti);

            Aihe aihe = new Aihe(aiheId, aiheAikaleima, alue, aiheNimimerkki, aiheOtsikko, aiheTeksti);

            aiheet.add(aihe);
        }

        resultSet.close();
        connection.close();

        return aiheet;
    }

    public List<Aihe> findAll(int alueId) throws SQLException {

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
                + "aihe.teksti AS aihe_teksti "
                + "FROM Alue alue, Aihe aihe "
                + "WHERE alue.id = " + alueId);

        List<Aihe> aiheet = new ArrayList();
        while (resultSet.next()) {
            Timestamp alueAikaleima = resultSet.getTimestamp("alue_aikaleima");
            String alueOtsikko = resultSet.getString("alue_otsikko");
            String alueTeksti = resultSet.getString("alue_teksti");

            Integer aiheId = resultSet.getInt("aihe_id");
            Timestamp aiheAikaleima = resultSet.getTimestamp("aihe_aikaleima");
            // Integer aiheAlue = resultSet.getInt("aihe_alue");
            String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
            String aiheOtsikko = resultSet.getString("aihe_otsikko");
            String aiheTeksti = resultSet.getString("aihe_teksti");

            Alue alue = new Alue(alueId, alueAikaleima, alueOtsikko, alueTeksti);

            Aihe aihe = new Aihe(aiheId, aiheAikaleima, alue, aiheNimimerkki, aiheOtsikko, aiheTeksti);

            aiheet.add(aihe);
        }

        resultSet.close();
        connection.close();

        return aiheet;
    }

    public List<Aihe> findAll(Alue alue) throws SQLException {

        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.teksti AS aihe_teksti "
                + "FROM Aihe aihe "
                + "WHERE aihe.alue = " + alue.getId());

        List<Aihe> aiheet = new ArrayList();
        while (resultSet.next()) {
            Integer aiheId = resultSet.getInt("aihe_id");
            Timestamp aiheAikaleima = resultSet.getTimestamp("aihe_aikaleima");
            // Integer aiheAlue = resultSet.getInt("aihe_alue");
            String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
            String aiheOtsikko = resultSet.getString("aihe_otsikko");
            String aiheTeksti = resultSet.getString("aihe_teksti");

            Aihe aihe = new Aihe(aiheId, aiheAikaleima, alue, aiheNimimerkki, aiheOtsikko, aiheTeksti);

            aiheet.add(aihe);
        }

        resultSet.close();
        connection.close();

        return aiheet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Aihe WHERE id = ?");
        statement.setObject(1, key);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    @Override
    public void create(Aihe aihe) throws SQLException {
        String nimimerkki = aihe.getNimimerkki();
        nimimerkki = korjaaAakkoset(nimimerkki);
        String otsikko = aihe.getOtsikko();
        otsikko = korjaaAakkoset(otsikko);
        String teksti = aihe.getTeksti();
        teksti = korjaaAakkoset(teksti);

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

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Aihe (id, aikaleima, alue, nimimerkki, otsikko, teksti) VALUES(?, ?, ?, ?, ?, ?)");
        statement.setObject(1, id);
        statement.setObject(2, new java.sql.Timestamp(new java.util.Date().getTime()));
        statement.setObject(3, aihe.getAlue().getId());
        statement.setObject(4, nimimerkki);
        statement.setObject(5, otsikko);
        statement.setObject(6, teksti);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public void create(Alue alue, String nimimerkki, String otsikko, String teksti) {
        // ei toteutettu.
    }
}
