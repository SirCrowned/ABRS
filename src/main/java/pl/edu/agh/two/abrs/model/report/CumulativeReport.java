package pl.edu.agh.two.abrs.model.report;

import pl.edu.agh.two.abrs.model.ColumnType;

import java.util.Date;

/**
 * Model for reports that map group of values to single label.
 */
public class CumulativeReport<L extends ColumnType> extends AbstractReportModel<L, Integer> {

    protected CumulativeReport(Date creationTime) {
        super(creationTime);
    }

    @Override
    public void add(L label, Integer value) {
        if(!data.containsKey(label)) {
            data.put(label, 0);
        }
        data.put(label, data.get(label) + value);
    }
}
