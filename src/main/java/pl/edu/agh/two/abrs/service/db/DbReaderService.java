package pl.edu.agh.two.abrs.service.db;

import pl.edu.agh.two.abrs.Row;

import java.util.List;
import java.util.Map;

public interface DbReaderService {

    /**
     * Tests if system is able to connect to specified database
     *
     * @param params connection parameters
     * @throws DbReaderException when provided parameters are not correct or database is unavailable
     */
    void testConnection(ConnectionParams params) throws DbReaderException;

    /**
     * Returns specified table metadata
     *
     * @param params    connection parameters
     * @param tableName name of table for which metadata should be extracted
     * @return Map columnName -> columnType
     * @throws DbReaderException when connection with given table cannot be established
     */
    Map<String, String> getColumnsMetadata(ConnectionParams params, String tableName) throws DbReaderException;

    /**
     * Extracts data from specified table
     *
     * @param params    connection parameters
     * @param tableName name of table from which data should be extracted
     * @return List of rows from given table
     * @throws DbReaderException when connection with given table cannot be established
     */
    List<Row> readTable(ConnectionParams params, String tableName) throws DbReaderException;

    List<Row> readSql(ConnectionParams params, String sqlQuery) throws DbReaderException;
}
