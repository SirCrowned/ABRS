package pl.edu.agh.two.abrs.service;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.two.abrs.Row;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DbReaderServiceImplTest extends EmbeddedH2Test {

    private static final String TABLE_NAME = "test";

    private static final String NON_EXISTENT_TABLE_NAME = "nonExistentTableName";

    private static final String INCORRECT_DRIVER_NAME = "incorrectDriverName";

    private static final String INCORRECT_PASSWORD = "incorrectPassword";

    @Before
    public void prepareDb() throws SQLException {
        String createTable = "create table " + TABLE_NAME + "(id int, col1 varchar, col2 varchar);";
        String insertValues = "insert into " + TABLE_NAME + "(id, col1, col2) values" +
                "(0, '1-0', '2-0')," +
                "(1, '1-1', '2-1')," +
                "(2, '1-2', '2-2')," +
                "(3, '1-3', '2-3');";
        createTable(createTable);
        insertValues(insertValues);
    }

    @Test
    public void should_correctly_read_data_from_table() throws Exception {

        DbReaderServiceImpl dbConnectorService = new DbReaderServiceImpl();
        List<Row> rows = dbConnectorService.readTable(connectionParams(), TABLE_NAME);

        assertEquals(4, rows.size());

        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            assertEquals(3, row.length());

            assertEquals("" + i, row.get(0));
            assertEquals("1-" + i, row.get(1));
            assertEquals("2-" + i, row.get(2));
        }
    }

    @Test(expected = DbReaderService.DbReaderException.class)
    public void should_throw_exception_for_non_existent_table() throws Exception {
        DbReaderServiceImpl dbConnectorService = new DbReaderServiceImpl();
        dbConnectorService.readTable(connectionParams(), NON_EXISTENT_TABLE_NAME);
    }

    @Test(expected = DbReaderService.DbReaderException.class)
    public void should_throw_exception_for_incorrect_driver_class_name() throws Exception {
        DbReaderServiceImpl dbConnectorService = new DbReaderServiceImpl();
        dbConnectorService.readTable(incorrectDriverParams(), TABLE_NAME);
    }

    @Test(expected = DbReaderService.DbReaderException.class)
    public void should_throw_exception_for_incorrect_password() throws Exception {
        DbReaderServiceImpl dbConnectorService = new DbReaderServiceImpl();
        dbConnectorService.readTable(incorrectPasswordParams(), TABLE_NAME);
    }

    private DbReaderService.ConnectionParams connectionParams() {
        return connectionParams(DRIVER_CLASS, PASSWORD);
    }

    private DbReaderService.ConnectionParams incorrectDriverParams() {
        return connectionParams(INCORRECT_DRIVER_NAME, PASSWORD);
    }

    private DbReaderService.ConnectionParams incorrectPasswordParams() {
        return connectionParams(DRIVER_CLASS, INCORRECT_PASSWORD);
    }

    private DbReaderService.ConnectionParams connectionParams(String driverClassName, String password) {
        return new DbReaderService.ConnectionParams(driverClassName, CONNECTION_STRING, USER, password);
    }
}
