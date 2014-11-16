package pl.edu.agh.two.abrs.service.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.Row;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DbReaderServiceImpl implements DbReaderService {

    private static final String SELECT_STATEMENT_PATTERN = "SELECT * FROM %s";

    private Connection connect(ConnectionParams params) throws DbReaderException {
        try {
            Class.forName(params.driverClassName);
        } catch (ClassNotFoundException e) {
            throw new DbReaderException("Cannot find driver: " + params.driverClassName, e);
        }
        try {
            return DriverManager.getConnection(params.connectionString, params.username, params.password);
        } catch (SQLException e) {
            throw new DbReaderException(e);
        }
    }

    public void testConnection(ConnectionParams params) throws DbReaderException {
        Connection connection = connect(params);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DbReaderException(e);
        }
    }

    public Map<String, String> getColumnsMetadata(ConnectionParams params, String tableName) throws DbReaderException {

        Map<Integer, String> jdbcMappings = getAllJdbcTypeNames();
        Map<String, String> columnsMetadata = new HashMap<>();

        try (Connection connection = connect(params)) {
            Statement statement = connection.createStatement();

            String sql = String.format(SELECT_STATEMENT_PATTERN, tableName);
            ResultSet res = statement.executeQuery(sql);
            ResultSetMetaData metadata = res.getMetaData();

            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                columnsMetadata.put(metadata.getColumnLabel(i), jdbcMappings.get(metadata.getColumnType(i)));
            }
        } catch (SQLException e) {
            throw new DbReaderException(e);
        }
        return columnsMetadata;
    }

    private Map<Integer, String> getAllJdbcTypeNames() throws DbReaderException {
        Map<Integer, String> result = new HashMap<>();
        try {
            for (Field field : Types.class.getFields()) {
                result.put((Integer) field.get(null), field.getName());
            }
        } catch (IllegalAccessException e) {
            throw new DbReaderException(e);
        }
        return result;
    }

    public List<Row> readTable(ConnectionParams params, String tableName) throws DbReaderException {
        return readSql(params, String.format(SELECT_STATEMENT_PATTERN, tableName));
    }

    public List<Row> readSql(ConnectionParams params, String sql) throws DbReaderException {
        List<Row> rows = new ArrayList<>();

        try (Connection connection = connect(params)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();

            while (resultSet.next()) {
                List<String> fields = new ArrayList<>();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    fields.add(resultSet.getString(i + 1));
                }
                rows.add(new Row(fields));
            }

            return rows;
        } catch (SQLException e) {
            throw new DbReaderException(e);
        }
    }
}