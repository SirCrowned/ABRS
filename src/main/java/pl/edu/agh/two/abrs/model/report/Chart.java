package pl.edu.agh.two.abrs.model.report;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import org.apache.commons.lang3.tuple.Pair;
import pl.edu.agh.two.abrs.model.report.schema.ChartType;
import pl.edu.agh.two.abrs.service.export.RendererVisitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Chart extends ReportElement {

    private final ChartType chartType;

    private final String name;

    private Optional<String> xLabel = Optional.empty();

    private Optional<String> yLabel = Optional.empty();

    private List<Pair<Object, Object>> pairs = new ArrayList<>();

    public Chart(ChartType chartType, String name) {
        this.chartType = chartType;
        this.name = name;
    }

    public void setxLabel(String xLabel) {
        this.xLabel = Optional.of(xLabel);
    }

    public void setyLabel(String yLabel) {
        this.yLabel = Optional.of(yLabel);
    }

    public void setPairs(List<Pair<Object, Object>> pairs) {
        this.pairs = pairs;
    }

    public void addPair(Pair<Object, Object> pair) {
        pairs.add(pair);
    }

    public Optional<String> getxLabel() {
        return xLabel;
    }

    public Optional<String> getyLabel() {
        return yLabel;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public List<Pair<Object, Object>> getPairs() {
        return pairs;
    }

    public String getName() {
        return name;
    }

    @Override
    public Element render(RendererVisitor renderer) throws IOException, BadElementException {
        return renderer.render(this);
    }
}
