package pl.edu.agh.two.abrs.service.export;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.commons.lang3.tuple.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.model.report.Chart;
import pl.edu.agh.two.abrs.model.report.Table;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class RendererVisitor {

    public RendererVisitor() {
    }

    public Element render(Table reportElement) {
        return renderTable(reportElement);
    }

    public Element render(Chart reportElement) throws IOException, BadElementException {
        switch (reportElement.getChartType()) {
            case PIE:
                return renderPieChart(reportElement);
            case BAR:
                return renderBarChart(reportElement);
            case LINE:
                return renderLineChart(reportElement);
        }

        return null;
    }

    private Element renderTable(Table reportElement) {

        Row header = reportElement.getHeader();
        List<Row> rows = reportElement.getRows();

        float[] columnWidths = new float[header.length()];
        for (int i = 0; i < header.length(); i++) {
            columnWidths[i] = 1.0f;
        }

        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(90f);

        header.getFields().forEach(f -> table.addCell(f.getColumnLabel()));

        rows.forEach(r -> r.getFields().forEach(i -> table.addCell(i.getValue().toString())));

        return table;
    }

    private Element renderPieChart(Chart chart) throws IOException, BadElementException {

        DefaultPieDataset dataSet = new DefaultPieDataset();

        for (Pair p : chart.getPairs()) {
            dataSet.setValue((String) p.getKey(), (Number) p.getValue());
        }
        JFreeChart pieChart = ChartFactory.createPieChart(chart.getName(), dataSet, true, true, false);
        BufferedImage img = pieChart.createBufferedImage(500, 600);

        return Image.getInstance(img, null);
    }

    private Element renderBarChart(Chart chart) throws IOException, BadElementException {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for (Pair p : chart.getPairs()) {
            dataSet.addValue((Number) p.getValue(), (String) p.getKey(), "");
        }

        final JFreeChart barChart = ChartFactory.createBarChart(
                chart.getName(),
                chart.getxLabel().orElse(""),
                chart.getyLabel().orElse(""),
                dataSet,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        BufferedImage img = barChart.createBufferedImage(500, 600);
        return Image.getInstance(img, null);
    }

    private Element renderLineChart(Chart chart) throws IOException, BadElementException {
        XYSeries series = new XYSeries(chart.getName());

        for (Pair p : chart.getPairs()) {
            series.add(Double.valueOf(p.getValue().toString()), Double.valueOf(p.getKey().toString()));
        }

        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series);
        final JFreeChart lineChart = ChartFactory.createXYLineChart(
                chart.getName(),
                chart.getxLabel().orElse(""),
                chart.getyLabel().orElse(""),
                collection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        BufferedImage img = lineChart.createBufferedImage(500, 600);
        return Image.getInstance(img, null);
    }
}
