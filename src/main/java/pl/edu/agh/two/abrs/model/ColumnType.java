package pl.edu.agh.two.abrs.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public enum ColumnType {
    BOOLEAN,
    INTEGER,
    DOUBLE,
    STRING,
    DATE;

    public Object fromString(String s) {
        switch (this) {
            case BOOLEAN:
                return Boolean.parseBoolean(s);
            case INTEGER:
                return Integer.parseInt(s);
            case DOUBLE:
                return Double.parseDouble(s);
            case STRING:
                return s;
            case DATE:
                try {
                    return SimpleDateFormat.getDateInstance().parse(s);
                } catch (ParseException e) {
                    throw new RuntimeException(e); // PRO exception handling :D
                }
        }
        return null;
    }
}
