package heppafoorumi.database;

import java.sql.*;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.h2.tools.RunScript;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws Exception {
        this.databaseAddress = databaseAddress;
        
        // Open connection to a database
        
        Connection connection = getConnection();

        try {
            // If database has not yet been created, insert content
            RunScript.execute(connection, new FileReader("database-schema.sql"));
            RunScript.execute(connection, new FileReader("database-import.sql"));
        } catch (Throwable t) {
            System.err.println("virhe: " + t.getMessage());
        }
        connection.close();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                statement.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
//        lista.add("CREATE TABLE Opiskelija (id integer PRIMARY KEY, nimi varchar(255));");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Platon');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Aristoteles');");
//        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");

        return lista;
    }
}
