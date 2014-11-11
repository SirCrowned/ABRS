package pl.edu.agh.two.abrs.service;

import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.service.connection.ConnectionParams;

import java.util.List;

public interface DbReaderService {

    List<Row> readTable(ConnectionParams params, String tableName) throws DbReaderException;

    List<Row> readSql(ConnectionParams params, String sqlQuery) throws DbReaderException;

    class DbReaderException extends Exception {
        public DbReaderException(Exception e) {
            super(e.getMessage(), e);
        }

        public DbReaderException(String message, Exception e) {
            super(message, e);
        }
    }
}
