package pl.edu.agh.two.abrs.service;

import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.service.connection.ConnectionParams;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DbReaderService {

    List<Row> readTable(ConnectionParams params, String tableName) throws DbReaderException;

    Map<String, Map<String, String>> collectTablesInfo(ConnectionParams params) throws DbReaderException, SQLException;

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
