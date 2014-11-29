package pl.edu.agh.two.abrs.service.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.SourceColumn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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

    public void testConnection(ConnectionParams params) throws DbReaderException {
        Connection connection = connect(params);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DbReaderException(e);
        }
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
                List<Object> fields = new ArrayList<>();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    fields.add(resultSet.getObject(i + 1));
                }
                rows.add(new Row(fields));
            }

            return rows;
        } catch (SQLException e) {
            throw new DbReaderException(e);
        }
    }

    public List<SourceColumn> getColumns(ConnectionParams params, String tableName) throws DbReaderException {
        List<SourceColumn> columns = new ArrayList<>();

        try (Connection connection = connect(params)) {
            Statement statement = connection.createStatement();

            String sql = String.format(SELECT_STATEMENT_PATTERN, tableName);
            ResultSet res = statement.executeQuery(sql);
            ResultSetMetaData metadata = res.getMetaData();

            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                columns.add(new SourceColumn(metadata.getColumnLabel(i), getColumnType(metadata.getColumnType(i))));
            }
        } catch (SQLException e) {
            throw new DbReaderException(e);
        }

        return columns;
    }

    private ColumnType getColumnType(int type) throws DbReaderException {
        switch (type) {
            case Types.BOOLEAN:
                return ColumnType.BOOLEAN;
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
            case Types.BIGINT:
                return ColumnType.INTEGER;
            case Types.FLOAT:
            case Types.REAL:
            case Types.DOUBLE:
            case Types.NUMERIC:
            case Types.DECIMAL:
                return ColumnType.DOUBLE;
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.LONGNVARCHAR:
            case Types.CLOB:
            case Types.NCLOB:
                return ColumnType.STRING;
            case Types.DATE:
            case Types.TIMESTAMP:
            case Types.TIME:
                return ColumnType.DATE;
            // Not supported below that line//
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            case Types.NULL:
            case Types.OTHER:
            case Types.JAVA_OBJECT:
            case Types.DISTINCT:
            case Types.STRUCT:
            case Types.BLOB:
            case Types.REF:
            case Types.DATALINK:
            case Types.ROWID:
            case Types.SQLXML:
            default:
                throw new DbReaderException("Not supported JDBC type (java.sql.Types): " + type + ".");
        }
    }
}