package pl.edu.agh.two.abrs.model.report;

import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Report {
    
    private Optional<String> xLabel = Optional.empty();
    private Optional<String> yLabel = Optional.empty();

    private List<Pair<String, Object>> pairs = new ArrayList<>();

    public void setxLabel(String xLabel) {
        this.xLabel = Optional.of(xLabel);
    }

    public void setyLabel(String yLabel) {
        this.yLabel = Optional.of(yLabel);
    }

    public void addPair(Pair<String, Object> pair) {
        pairs.add(pair);
    }

    public List<Pair<String, Object>> getPairs() {
        return pairs;
    }
}
