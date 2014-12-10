package pl.edu.agh.two.abrs.model.report;

import javafx.util.Pair;
import pl.edu.agh.two.abrs.model.ColumnType;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractReportModel<L extends ColumnType, V> implements ReportModel<L, V>{

    protected Map<L, V> data = new HashMap<>();

    private Date creationTime;

    protected AbstractReportModel(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public Date getCreationTime() {
        return creationTime;
    }

    @Override
    public Collection<Pair<L, V>> getAllPairs() {
        return data.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

}
