package pl.edu.agh.two.abrs.model.report;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import pl.edu.agh.two.abrs.model.report.schema.ChartType;
import pl.edu.agh.two.abrs.service.export.RendererVisitor;

import java.io.IOException;

public class Chart extends ReportElement {

    private ChartType chartType;

    private ChartData chartData;

    public Chart(ChartType chartType, ChartData chartData) {
        this.chartType = chartType;
        this.chartData = chartData;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public ChartData getChartData() {
        return chartData;
    }

    @Override
    public Element render(RendererVisitor renderer) throws IOException, BadElementException {
        return renderer.render(this);
    }
}
