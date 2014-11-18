package pl.edu.agh.two.abrs.service.csv;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.model.ColumnType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CsvServiceImpl implements CsvService {

    @Override
    public List<String> getHeaders(String url) throws IOException {
        URL source = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(source.openStream()));
        CsvListReader csvReader = new CsvListReader(in, CsvPreference.STANDARD_PREFERENCE);
        String[] header = csvReader.getHeader(true);
        return Arrays.asList(header);
    }

    @Override
    public List<Row> getData(String url, Map<String, ColumnType> columnMap) throws IOException {
        List<Row> allRows = new ArrayList<>();
        //open connection
        URL sourceUrl = new URL(url);
        try (CsvMapReader csvReader = new CsvMapReader(new BufferedReader(new InputStreamReader(sourceUrl.openStream())), CsvPreference.STANDARD_PREFERENCE)) {

            List<CellProcessor> processors = new ArrayList<>();
            String[] headers = csvReader.getHeader(true);
            String[] filteredHeaders = Arrays.copyOf(headers, headers.length);
            //fill not used columns with null and assign cell processors to others
            for (int i = 0; i < headers.length; i++) {
                ColumnType dataType = columnMap.get(headers[i]);
                if (dataType == null) {
                    processors.add(null);
                    filteredHeaders[i] = null;
                } else {
                    processors.add(CellProcessorFactory.getCellProcessor(dataType));
                }
            }

            Map<String, Object> readRow;
            while ((readRow = csvReader.read(filteredHeaders, processors.toArray(new CellProcessor[processors.size()]))) != null) {
                List<String> actualRow = new ArrayList<>();
                for (int i = 0; i < filteredHeaders.length; i++) {
                    if (filteredHeaders[i] != null) {
                        actualRow.add(String.valueOf(readRow.get(filteredHeaders[i])));
                    }
                }
                allRows.add(new Row(actualRow));
            }
        }
        return allRows;
    }
}
