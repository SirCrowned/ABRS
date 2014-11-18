package pl.edu.agh.two.abrs.service.csv;

import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.model.ColumnType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CsvService {

    List<String> getHeaders(String url) throws IOException;
    List<Row> getData(String url, Map<String, ColumnType> columnMap) throws IOException;

}
