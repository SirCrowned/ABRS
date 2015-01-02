package pl.edu.agh.two.abrs.model.report;

import pl.edu.agh.two.abrs.model.report.schema.ChartType;

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
}
