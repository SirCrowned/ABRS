package pl.edu.agh.two.abrs.service.export;

import org.apache.commons.lang3.tuple.Pair;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.model.report.Chart;
import pl.edu.agh.two.abrs.model.report.Report;
import pl.edu.agh.two.abrs.model.report.ReportElement;
import pl.edu.agh.two.abrs.model.report.Table;
import pl.edu.agh.two.abrs.model.report.schema.ChartType;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Creates example report to be exported as a PDF file.
 */
public class ExampleReport {

    public File exportExampleReport() throws ExporterException {
        ExporterService exporter = new ExporterImpl();
        return exporter.exportReport(exampleReport());
    }

    public Report exampleReport() throws ExporterException {

        //Example table
        List<Object> headerFields = Arrays.asList("User", "Score", "Money");
        List<Object> sampleValues = Arrays.asList("X", 1, 10.99);
        Row row1 = new Row(sampleValues);
        List<Row> rows = Arrays.asList(row1, row1, row1);
        ReportElement table = new Table("Table", rows, new Row(headerFields));

        //Example pie chart
        Chart pieChart = new Chart(ChartType.PIE, "PieChart");
        pieChart.addPair(Pair.of("User1", 10));
        pieChart.addPair(Pair.of("User2", 20));
        pieChart.addPair(Pair.of("User3", 30));

        //Example bar chart
        Chart barChart = new Chart(ChartType.BAR, "BarChart");
        barChart.addPair(Pair.of("User1", 10));
        barChart.addPair(Pair.of("User2", 20));
        barChart.addPair(Pair.of("User3", 30));

        //Example line chart
        Chart lineChart = new Chart(ChartType.LINE, "LineChart");
        lineChart.addPair(Pair.of(10.0, 10.0));
        lineChart.addPair(Pair.of(20.0, 20.0));
        lineChart.addPair(Pair.of(30.0, 30.0));
        lineChart.setxLabel("xLabel");
        lineChart.setyLabel("yLabel");

        List<ReportElement> elements = Arrays.asList(table, pieChart, barChart, lineChart);
        Report report = new Report("Title", elements);
        return report;
    }
}

