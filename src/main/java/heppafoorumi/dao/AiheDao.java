package heppafoorumi.dao;

import heppafoorumi.domain.Aihe;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import heppafoorumi.database.Database;
import heppafoorumi.domain.Alue;
import heppafoorumi.domain.Aiheraportti;
import heppafoorumi.domain.Alueraportti;
import heppafoorumi.domain.Viesti;
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
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.otsikko AS aihe_otsikko, "
                + "aihe.teksti AS aihe_teksti "
                + "FROM Aihe aihe "
                + "WHERE aihe.id = " + key);

        if (resultSet == null || !resultSet.next()) {
            return null;
        }
        Integer aiheId = resultSet.getInt("aihe_id");
        Timestamp aiheAikaleima = resultSet.getTimestamp("aihe_aikaleima");
        Integer aiheAlue = resultSet.getInt("aihe_alue");
        String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
        String aiheOtsikko = resultSet.getString("aihe_otsikko");
        String aiheTeksti = resultSet.getString("aihe_teksti");

        resultSet.close();
        connection.close();

        Aihe aihe = new Aihe(this.database, aiheId, aiheAikaleima, aiheAlue, aiheNimimerkki, aiheOtsikko, aiheTeksti);

        return aihe;
    }

    @Override
    public List<Aihe> findAll() throws SQLException {

        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.teksti AS aihe_teksti "
                + "FROM Alue alue, Aihe aihe");

        List<Aihe> aiheet = new ArrayList();
        while (resultSet.next()) {
            Integer alueId = resultSet.getInt("alue_id");

            Integer aiheId = resultSet.getInt("aihe_id");
            Timestamp aiheAikaleima = resultSet.getTimestamp("aihe_aikaleima");
            Integer aiheAlue = resultSet.getInt("aihe_alue");
            String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
            String aiheOtsikko = resultSet.getString("aihe_otsikko");
            String aiheTeksti = resultSet.getString("aihe_teksti");

            Aihe aihe = new Aihe(this.database, aiheId, aiheAikaleima, aiheAlue, aiheNimimerkki, aiheOtsikko, aiheTeksti);

            aiheet.add(aihe);
        }

        resultSet.close();
        connection.close();

        return aiheet;
    }

    public List<Aihe> findAll(int alueId) throws SQLException {
        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.otsikko AS aihe_otsikko, "
                + "aihe.teksti AS aihe_teksti "
                + "FROM Aihe aihe "
                + "WHERE aihe.alue = " + alueId);

        List<Aihe> aiheet = new ArrayList();
        while (resultSet.next()) {
            Integer aiheId = resultSet.getInt("aihe_id");
            Timestamp aiheAikaleima = resultSet.getTimestamp("aihe_aikaleima");
            String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
            String aiheOtsikko = resultSet.getString("aihe_otsikko");
            String aiheTeksti = resultSet.getString("aihe_teksti");

            Aihe aihe = new Aihe(this.database, aiheId, aiheAikaleima, alueId, aiheNimimerkki, aiheOtsikko, aiheTeksti);

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
            Integer alueId = resultSet.getInt("aihe_alue");
            String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
            String aiheOtsikko = resultSet.getString("aihe_otsikko");
            String aiheTeksti = resultSet.getString("aihe_teksti");

            Aihe aihe = new Aihe(this.database, aiheId, aiheAikaleima, alueId, aiheNimimerkki, aiheOtsikko, aiheTeksti);

            aiheet.add(aihe);
        }

        resultSet.close();
        connection.close();

        return aiheet;
    }

    public List<Aiheraportti> findTarpeellisetTiedot() throws SQLException {
//        Connection connection = database.getConnection();
//        ResultSet resultSet = connection.createStatement().executeQuery(
//                "SELECT * FROM Alue AS alue "
//                + "    LEFT JOIN Aihe AS aihe "
//                + "    ON (aihe.alue = alue.id) "
//                + "    WHERE (aihe.id = "
//                + "        (SELECT MAX(id) FROM aihe AS uusin_aihe "
//                + "            WHERE uusin_aihe.alue = alue.id) "
//                + "        OR aihe.id IS NULL)");

        List<Aiheraportti> raporttilista = new ArrayList();

//        while (resultSet.next()) {
//            Integer alueId = resultSet.getInt("alue.id");
//            Timestamp alueAikaleima = resultSet.getTimestamp("alue.aikaleima");
//            String alueOtsikko = resultSet.getString("alue.otsikko");
//            String alueTeksti = resultSet.getString("alue.teksti");
//
//            Integer aiheId = resultSet.getInt("aihe.id");
//            Timestamp aiheAikaleima = resultSet.getTimestamp("aihe.aikaleima");
//            Integer aiheAlue = resultSet.getInt("aihe.alue");
//            String aiheNimimerkki = resultSet.getString("aihe.nimimerkki");
//            String aiheOtsikko = resultSet.getString("aihe.otsikko");
//            String aiheTeksti = resultSet.getString("aihe.teksti");
//
//            Alue alue = new Alue(this.database, alueId, alueAikaleima, alueOtsikko, alueTeksti);
//            Aihe aihe = new Aihe(this.database, aiheId, aiheAikaleima, aiheAlue, aiheNimimerkki, aiheOtsikko, aiheTeksti);
//            Viesti viesti = null; // TODO!
//            raporttilista.add(new Alueraportti(alue, aihe, viesti));
//        }
//
//        resultSet.close();
//        connection.close();
        return raporttilista;
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

    public void create(Integer alueId, String nimimerkki, String otsikko, String teksti) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Aihe (aikaleima, alue, nimimerkki, otsikko, teksti) VALUES(?, ?, ?, ?, ?)");
        statement.setObject(1, new java.sql.Timestamp(new java.util.Date().getTime()));
        statement.setObject(2, alueId);
        statement.setObject(3, nimimerkki);
        statement.setObject(4, otsikko);
        statement.setObject(5, teksti);

        statement.executeUpdate();

        statement.close();
        connection.close();
    }
}
