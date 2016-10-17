package heppafoorumi.dao;

import heppafoorumi.domain.Viesti;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.Database;
import tikape.runko.database.Dao;

public class ViestiDao implements Dao<Viesti, Integer> {

    private final Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        statement.setObject(1, key);

        ResultSet resultSet = statement.executeQuery();
        boolean hasOne = resultSet.next();
        if (!hasOne) {
            return null;
        }

        Integer id = resultSet.getInt("id");
        String nimi = resultSet.getString("nimi");

        Viesti viesti = new Viesti(id, nimi);

        resultSet.close();
        statement.close();
        connection.close();

        return viesti;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet resultSet = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String nimi = resultSet.getString("nimi");

            viestit.add(new Viesti(id, nimi));
        }

        resultSet.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
}
