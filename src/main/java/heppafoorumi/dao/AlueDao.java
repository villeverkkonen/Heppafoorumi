package heppafoorumi.dao;

import heppafoorumi.domain.Alue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import heppafoorumi.database.Database;

public class AlueDao implements Dao<Alue, Integer> {

    private final Database database;

    public AlueDao(Database database) {
        this.database = database;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Alue WHERE id = ?");
        statement.setObject(1, key);

        ResultSet resultSet = statement.executeQuery();
        boolean hasOne = resultSet.next();

        if (!hasOne) {
            return null;
        }

        Integer id = resultSet.getInt("id");
        Integer aikaleima = resultSet.getInt("aikaleima");
        String teksti = resultSet.getString("teksti");

        Alue alue = new Alue(id, aikaleima, teksti);

        resultSet.close();
        statement.close();
        connection.close();

        return alue;
    }

    @Override
    public List<Alue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Alue");

        ResultSet resultSet = statement.executeQuery();
        List<Alue> alueet = new ArrayList();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            Integer aikaleima = resultSet.getInt("aikaleima");
            String nimi = resultSet.getString("teksti");

            alueet.add(new Alue(id, aikaleima, nimi));
        }

        resultSet.close();
        statement.close();
        connection.close();

        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Alue WHERE id = ?");
        stmt.setObject(1, key);
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }

    @Override
    public void create(Alue alue) throws SQLException {
        Integer id = alue.getId();
        Integer aikaleima = alue.getAikaleima();
        String teksti = alue.getTeksti();
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue VALUES(?, ?, ?)");
        stmt.setObject(1, id);
        stmt.setObject(2, aikaleima);
        stmt.setObject(3, teksti);
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }

}
