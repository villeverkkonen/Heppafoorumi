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
import heppafoorumi.domain.Viesti;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class AiheDao implements Dao<Aihe, Integer> {

    private final Database database;
    private KaikkiDao kaikkiDao;

    public AiheDao(Database database) {
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
    public Aihe findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement statement = connection.prepareStatement("SELECT "
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.otsikko AS aihe_otsikko, "
                + "aihe.teksti AS aihe_teksti "
                + "FROM Aihe aihe "
                + "WHERE aihe.id = ?");
        statement.setObject(1, key);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            resultSet.close();
            statement.close();
            connection.close();
            return null;
        }

        Integer aiheId = resultSet.getInt("aihe_id");
        Timestamp aiheAikaleima = resultSet.getTimestamp("aihe_aikaleima");
        Integer aiheAlue = resultSet.getInt("aihe_alue");
        String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
        String aiheOtsikko = resultSet.getString("aihe_otsikko");
        String aiheTeksti = resultSet.getString("aihe_teksti");

        resultSet.close();
        statement.close();
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

    public Integer findLkm(int alueId) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                " SELECT COUNT(id) AS lkm FROM Aihe "
                + " WHERE Aihe.alue = ?");
        statement.setObject(1, alueId);
        ResultSet resultSet = statement.executeQuery();

        Integer lkm = null;

        if (resultSet.next()) {
            lkm = resultSet.getInt("lkm");
        }

        resultSet.close();
        connection.close();

        return lkm;
    }

    public List<Aihe> findAll(int alueId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT "
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.otsikko AS aihe_otsikko, "
                + "aihe.teksti AS aihe_teksti "
                + "FROM Aihe aihe "
                + "WHERE aihe.alue = ?");
        statement.setObject(1, alueId);
        ResultSet resultSet = statement.executeQuery();

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
        PreparedStatement statement = connection.prepareStatement("SELECT "
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.teksti AS aihe_teksti "
                + "FROM Aihe aihe "
                + "WHERE aihe.alue = ?");
        statement.setObject(1, alue.getId());
        ResultSet resultSet = statement.executeQuery();

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
        statement.close();
        connection.close();

        return aiheet;
    }

    public List<Aiheraportti> findTarpeellisetTiedot(int alueId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Aihe AS aihe "
                + "    LEFT JOIN Viesti AS viesti "
                + "    ON (viesti.aihe = aihe.id) "
                + "    WHERE (viesti.id = "
                + "        (SELECT MAX(id) FROM viesti AS uusin_viesti "
                + "            WHERE uusin_viesti.aihe = aihe.id) "
                + "        OR viesti.id IS NULL) "
                + "        AND aihe.alue = ? "
                + "    ORDER BY viesti.id DESC");
        statement.setObject(1, alueId);
        ResultSet resultSet = statement.executeQuery();

        List<Aiheraportti> raporttilista = new ArrayList();

        while (resultSet.next()) {
            Timestamp aiheAikaleima = resultSet.getTimestamp("aihe.aikaleima");
            Integer aiheId = resultSet.getInt("aihe.id");
            Integer aiheAlue = resultSet.getInt("aihe.alue");
            String aiheNimimerkki = resultSet.getString("aihe.nimimerkki");
            String aiheOtsikko = resultSet.getString("aihe.otsikko");
            String aiheTeksti = resultSet.getString("aihe.teksti");

            Integer viestiId = resultSet.getInt("viesti.id");
            Timestamp viestiAikaleima = resultSet.getTimestamp("viesti.aikaleima");
            String viestiNimimerkki = resultSet.getString("viesti.nimimerkki");
            String viestiTeksti = resultSet.getString("viesti.teksti");

            Aihe aihe = new Aihe(this.database, aiheId, aiheAikaleima, aiheAlue, aiheNimimerkki, aiheOtsikko, aiheTeksti);
            Viesti viesti = new Viesti(this.database, viestiId, viestiAikaleima, aiheId, viestiNimimerkki, viestiTeksti);
            List<Viesti> viestit = this.kaikkiDao.getViestiDao().findAll(aiheId);

            raporttilista.add(new Aiheraportti(aihe, viesti, viestit.size(), null));
        }

        resultSet.close();
        connection.close();
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
