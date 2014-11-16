package pl.edu.agh.two.abrs.service.db;

public class MySqlConnectionParams extends ConnectionParams {

    public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    public MySqlConnectionParams(String host, int port, String database, String username, String password) {
        super(DRIVER_CLASS_NAME, createConnectionString(host, port, database), username, password);
    }

    private static String createConnectionString(String host, int port, String database) {
        return String.format("jdbc:mysql://%s:%d/%s", host, port, database);
    }
}