package pl.edu.agh.two.abrs.model.report;

import javafx.util.Pair;
import pl.edu.agh.two.abrs.model.ColumnType;

import java.util.Collection;
import java.util.Date;

public interface ReportModel<L extends ColumnType, V> {

    Date getCreationTime();

    Collection<Pair<L, V>> getAllPairs();

    void add(L label, V value);

}
