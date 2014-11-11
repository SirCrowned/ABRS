package pl.edu.agh.two.abrs.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.service.connection.ConnectionParams;

import java.lang.reflect.Field;
import java.sql.*;
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

    public Map<String, Map<String, String>> collectTablesInfo(ConnectionParams params) throws DbReaderException, SQLException {

        Map<Integer, String> jdbcMappings = getAllJdbcTypeNames();
        Connection connection = connect(params);
        DatabaseMetaData meta = connection.getMetaData();
        Map<String, Map<String, String>> result = new HashMap<>();

        String[] types = {"TABLE"};
        ResultSet rs = meta.getTables(null, null, "%", types);
        while (rs.next()) {
            String tableName = rs.getString(3);
            Map<String, String> columns = new HashMap<>();

            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("select * from " + tableName);
            ResultSetMetaData rsmd = res.getMetaData();

            for (int i = 1; i <= rsmd.getColumnCount(); i++)
                columns.put(rsmd.getColumnLabel(i), jdbcMappings.get(rsmd.getColumnType(i)));
            result.put(tableName, columns);
        }
        return result;
    }

    private Map<Integer, String> getAllJdbcTypeNames() throws DbReaderException {
        Map<Integer, String> result = new HashMap<>();
        try {
            for (Field field : Types.class.getFields())
                result.put((Integer) field.get(null), field.getName());
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
        Connection connection = connect(params);

        try (Statement statement = connection.createStatement()) {
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