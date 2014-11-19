package pl.edu.agh.two.abrs.service.csv;

import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import pl.edu.agh.two.abrs.model.ColumnType;

public class CellProcessorFactory {
    public static CellProcessor getCellProcessor(ColumnType columnType) {
        if (columnType != null) {
            switch (columnType) {
                case BOOLEAN:
                    return new ParseBool("t", "f");
                case DATE:
                    return new ParseDate("dd/MM/yyyy");
                case DOUBLE:
                    return new ParseDouble();
                case INTEGER:
                    return new ParseInt();
                case STRING:
                    return new NotNull();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        getCellProcessor(null);
    }
}
