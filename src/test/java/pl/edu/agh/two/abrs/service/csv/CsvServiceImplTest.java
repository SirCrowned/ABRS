package pl.edu.agh.two.abrs.service.csv;

import org.junit.Test;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.model.ColumnType;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvServiceImplTest {

    private static final String RESOURCE = "test.csv";
    private static final String EXCEPTION_RESOURCE = "exception_test.csv";

    private CsvService service = new CsvServiceImpl();


    @Test
    public void should_get_headers() throws CsvReadException {

        String resourceUrl = getClass().getClassLoader().getResource(RESOURCE).toString();
        assertThat(service.getHeaders(resourceUrl))
                .containsExactly("name", "surname", "address", "age", "birthdate", "somefloat");
    }

    @Test(expected = CsvReadException.class)
    public void should_throw_exception_on_invalid_file() throws CsvReadException {

        String resourceUrl = getClass().getClassLoader().getResource(EXCEPTION_RESOURCE).toString();
        new CsvServiceImpl().getHeaders(resourceUrl);
    }

    @Test
    public void should_get_data() throws CsvReadException {

        String resourceUrl = getClass().getClassLoader().getResource(RESOURCE).toString();
        Map<String, ColumnType> params = new HashMap();
        params.put("name", ColumnType.STRING);
        params.put("age", ColumnType.INTEGER);
        params.put("birthdate", ColumnType.DATE);
        params.put("somefloat", ColumnType.DOUBLE);
        Row result = new CsvServiceImpl().getData(resourceUrl, params).get(0);
        assertThat(result.get(0)).isEqualTo("Anna");
        assertThat(result.get(1)).isEqualTo(30);
        assertThat(result.get(3)).isEqualTo(9.10);

        Calendar cal = Calendar.getInstance();
        cal.set(1990, 0, 20);
        Date expected = cal.getTime();
        assertThat((Date)result.get(2)).isEqualToIgnoringHours(expected);

    }

    @Test(expected = CsvReadException.class)
    public void should_throw_exception_on_invalid_data_type() throws CsvReadException {

        String resourceUrl = getClass().getClassLoader().getResource(RESOURCE).toString();
        Map<String, ColumnType> params = new HashMap();
        params.put("name", ColumnType.DATE);
        new CsvServiceImpl().getData(resourceUrl, params);

    }
}
