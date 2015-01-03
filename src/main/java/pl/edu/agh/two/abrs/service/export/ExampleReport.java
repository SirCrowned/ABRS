package pl.edu.agh.two.abrs.service.export;

import org.apache.commons.lang3.tuple.Pair;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.model.report.Chart;
import pl.edu.agh.two.abrs.model.report.ChartData;
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

    public File exportExampleReport() throws  ExporterException {
        ExporterService exporter = new ExporterImpl();
        return exporter.exportReport(exampleReport());
    }

    public Report exampleReport() throws ExporterException{

        //Example table
        List<Object> headerFields = Arrays.asList("User", "Score", "Money");
        List<Object> sampleValues = Arrays.asList( "X", 1, 10.99);
        Row row1 = new Row(sampleValues);
        List<Row> rows = Arrays.asList(row1, row1, row1);
        ReportElement table = new Table("Table", rows, new Row(headerFields));

        //Example pie chart
        ChartData data = new ChartData("PieChart");
        data.addPair(Pair.of("User1", 10));
        data.addPair(Pair.of("User2", 20));
        data.addPair(Pair.of("User3", 30));
        ReportElement pieChart = new Chart(ChartType.PIE, data);

        //Example bar chart
        ChartData bData = new ChartData("BarChart");
        bData.addPair(Pair.of("User1", 10));
        bData.addPair(Pair.of("User2", 20));
        bData.addPair(Pair.of("User3", 30));
        ReportElement barChart = new Chart(ChartType.BAR, bData);

        //Example line chart
        ChartData lData = new ChartData("LineChart");
        lData.addPair(Pair.of(10.0, 10.0));
        lData.addPair(Pair.of(20.0, 20.0));
        lData.addPair(Pair.of(30.0, 30.0));
        lData.setxLabel("xLabel");
        lData.setyLabel("yLabel");
        ReportElement lineChart = new Chart(ChartType.LINE, lData);

        List<ReportElement> elements = Arrays.asList(table, pieChart, barChart, lineChart);
        Report report = new Report("Title", elements);
        return report;
    }

}

