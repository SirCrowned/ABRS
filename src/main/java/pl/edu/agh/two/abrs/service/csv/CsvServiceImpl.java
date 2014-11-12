package pl.edu.agh.two.abrs.service.csv;

import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvServiceImpl implements CsvService{


    @Override
    public List<String> getHeaders(String url) {

        try {
            URL source = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(source.openStream()));
            CsvListReader csvReader = new CsvListReader(in, CsvPreference.STANDARD_PREFERENCE);
            String[] header = csvReader.getHeader(true);
            return Arrays.asList(header);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
