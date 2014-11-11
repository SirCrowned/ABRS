package pl.edu.agh.two.abrs.service.connection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MySqlConnectionParamsTest {

    @Test
    public void should_set_proper_driver_name_and_connection_string() {
        int port = 3306;
        String host = "localhost", database = "db", username = "user", password = "pass";

        String expectedDriver = MySqlConnectionParams.DRIVER_CLASS_NAME;
        String expectedConnectionString = "jdbc:mysql://localhost:3306/db";

        ConnectionParams params = new MySqlConnectionParams(host, port, database, username, password);

        assertEquals(expectedDriver, params.driverClassName);
        assertEquals(expectedConnectionString, params.connectionString);
        assertEquals(username, params.username);
        assertEquals(password, params.password);
    }
}