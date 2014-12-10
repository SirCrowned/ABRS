package pl.edu.agh.two.abrs.model.report;

import pl.edu.agh.two.abrs.model.ColumnType;

import java.util.Date;

/**
* Model for reports that map values one-to-one to labels.
*/
public class FunctionReport<L extends ColumnType, V> extends AbstractReportModel<L, V> {

    protected FunctionReport(Date creationTime) {
        super(creationTime);
    }

    @Override
    public void add(L label, V value) {
        data.put(label, value);
    }

}
