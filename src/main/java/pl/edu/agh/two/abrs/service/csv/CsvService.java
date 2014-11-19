package pl.edu.agh.two.abrs.service.csv;

import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.LocalSchema;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;

import java.util.List;
import java.util.Map;

public interface CsvService {

    List<String> getHeaders(String url) throws CsvReadException;
    List<Row> getData(String url, Map<String, ColumnType> columnMap) throws CsvReadException;
    List<LocalSchemaColumn> getColumns(String url, LocalSchema schema) throws CsvReadException;

}
