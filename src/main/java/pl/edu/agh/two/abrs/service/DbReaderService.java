package pl.edu.agh.two.abrs.service;

import pl.edu.agh.two.abrs.Row;

import java.util.List;

public interface DbReaderService {

    List<Row> readTable(ConnectionParams params, String tableName) throws DbReaderException;

    List<Row> readSql(ConnectionParams params, String sqlQuery) throws DbReaderException;

    class ConnectionParams {
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

    class DbReaderException extends Exception {
        public DbReaderException(Exception e) {
            super(e.getMessage(), e);
        }

        public DbReaderException(String message, Exception e) {
            super(message, e);
        }
    }
}
