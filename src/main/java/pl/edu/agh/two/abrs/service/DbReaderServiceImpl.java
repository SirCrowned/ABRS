package pl.edu.agh.two.abrs.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.Row;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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