package heppafoorumi.dao;

import heppafoorumi.domain.Aihe;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import heppafoorumi.database.Database;
import heppafoorumi.domain.Alue;
import java.sql.PreparedStatement;

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
                + "alue.teksti AS alue_teksti, "
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.teksti AS aihe_teksti "
                + "FROM Alue alue, Aihe aihe "
                + "WHERE aihe.alue = alue.id AND "
                + "alue.id = '" + key + "'");

        if (resultSet == null || !resultSet.next()) {
            return null;
        }

        Integer alueId = resultSet.getInt("alue_id");
        Integer alueAikaleima = resultSet.getInt("alue_aikaleima");
        String alueTeksti = resultSet.getString("alue_teksti");

        Integer aiheId = resultSet.getInt("aihe_id");
        Integer aiheAikaleima = resultSet.getInt("aihe_aikaleima");
        // Integer aiheAlue = resultSet.getInt("aihe_alue");
        String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
        String aiheTeksti = resultSet.getString("aihe_teksti");

        resultSet.close();
        connection.close();

        Alue alue = new Alue(alueId, alueAikaleima, alueTeksti);

        Aihe aihe = new Aihe(aiheId, aiheAikaleima, alue, aiheNimimerkki, aiheTeksti);

        return aihe;
    }

    @Override
    public List<Aihe> findAll() throws SQLException {

        Connection connection = database.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT "
                + "alue.id AS alue_id, "
                + "alue.aikaleima AS alue_aikaleima, "
                + "alue.teksti AS alue_teksti, "
                + "aihe.id AS aihe_id, "
                + "aihe.aikaleima AS aihe_aikaleima, "
                + "aihe.alue AS aihe_alue, "
                + "aihe.nimimerkki AS aihe_nimimerkki, "
                + "aihe.teksti AS aihe_teksti "
                + "FROM Alue alue, Aihe aihe "
                + "WHERE aihe.alue = alue.id");

        List<Aihe> aiheet = new ArrayList();
        while (resultSet.next()) {
            Integer alueId = resultSet.getInt("alue_id");
            Integer alueAikaleima = resultSet.getInt("alue_aikaleima");
            String alueTeksti = resultSet.getString("alue_teksti");

            Integer aiheId = resultSet.getInt("aihe_id");
            Integer aiheAikaleima = resultSet.getInt("aihe_aikaleima");
            // Integer aiheAlue = resultSet.getInt("aihe_alue");
            String aiheNimimerkki = resultSet.getString("aihe_nimimerkki");
            String aiheTeksti = resultSet.getString("aihe_teksti");

            Alue alue = new Alue(alueId, alueAikaleima, alueTeksti);

            Aihe aihe = new Aihe(aiheId, aiheAikaleima, alue, aiheNimimerkki, aiheTeksti);

            aiheet.add(aihe);
        }

        resultSet.close();
        connection.close();

        return aiheet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Aihe WHERE id = ?");
        stmt.setObject(1, key);
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }

    @Override
    public void create(Aihe aihe) throws SQLException {
        Integer id = aihe.getId();
        Integer aikaleima = aihe.getAikaleima();
        String nimimerkki = aihe.getNimimerkki();
        String teksti = aihe.getTeksti();
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Aihe VALUES(?, ?, ?, ?)");
        stmt.setObject(1, id);
        stmt.setObject(2, aikaleima);
        stmt.setObject(3, nimimerkki);
        stmt.setObject(4, teksti);
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
}
