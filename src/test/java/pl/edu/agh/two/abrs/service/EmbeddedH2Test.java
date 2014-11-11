package pl.edu.agh.two.abrs.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Ignore
public class EmbeddedH2Test {

    protected final static String USER = "USER";

    protected final static String PASSWORD = "PASSWORD";

    protected final static String CONNECTION_STRING = "jdbc:h2:mem:testdrive";

    protected final static String DRIVER_CLASS = "org.h2.Driver";

    protected Connection connection;

    @Before
    public void initDatabase() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_CLASS);
        connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
    }

    @After
    public void dropDatabase() throws SQLException {
        Statement shutdownStatement = connection.createStatement();
        shutdownStatement.execute("SHUTDOWN");
        shutdownStatement.close();
        connection.close();
    }

    protected void createTable(String createTableQuery) throws SQLException {
        Statement createStatement = connection.createStatement();
        createStatement.execute(createTableQuery);
        createStatement.close();
    }

    protected void insertValues(String insertValuesQuery) throws SQLException {
        Statement insertStatement = connection.createStatement();
        insertStatement.execute(insertValuesQuery);
        insertStatement.close();
    }
}
