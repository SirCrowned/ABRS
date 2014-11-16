package pl.edu.agh.two.abrs.service.db;

public class ConnectionParams {
    public final String driverClassName;

    public final String connectionString;

    public final String username;

    public final String password;

    public ConnectionParams(String driverClassName, String connectionString, String username, String password) {
        this.driverClassName = driverClassName;
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }
}