package pl.edu.agh.two.abrs.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.Row;
import pl.edu.agh.two.abrs.model.report.Chart;
import pl.edu.agh.two.abrs.model.report.Report;
import pl.edu.agh.two.abrs.model.report.ReportElement;
import pl.edu.agh.two.abrs.model.report.Table;
import pl.edu.agh.two.abrs.model.report.schema.ChartSchema;
import pl.edu.agh.two.abrs.model.report.schema.ReportSchema;
import pl.edu.agh.two.abrs.repository.ChartSchemaRepository;
import pl.edu.agh.two.abrs.repository.ReportSchemaRepository;
import pl.edu.agh.two.abrs.service.globalSchema.GlobalSchemaService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportSchemaRepository reportSchemaRepository;

    @Autowired
    private ChartSchemaRepository chartSchemaRepository;

    @Autowired
    private GlobalSchemaService gsService;

    public Report collectReportData(ReportSchema schema) {
        List<ReportElement> elements = new ArrayList<>();
        elements.addAll(collectCharts(schema));
        elements.addAll(collectTables(schema));
        return new Report(schema.getName(), elements);
    }

    private List<ReportElement> collectTables(ReportSchema schema) {
        List<ReportElement> tables = new ArrayList<>();
        for (String tableName : schema.getTables()) {
            List<Row> records = gsService.getTableRecords(tableName);
            Row columnNames = gsService.getTableColumnNames(tableName);
            Table t = new Table(tableName, records, columnNames);

            tables.add(t);
        }
        return tables;
    }

    private List<ReportElement> collectCharts(ReportSchema schema) {
        List<ReportElement> result = new ArrayList<>();
        for (ChartSchema cs : schema.getCharts()) {
            Chart c = new Chart(cs.getType(), cs.getName());
            c.setxLabel(cs.getxAxisColumn());
            c.setyLabel(cs.getyAxisColumn());
            c.setPairs(gsService.get2ColumnsFromTable(cs.getTable(), cs.getxAxisColumn(), cs.getyAxisColumn()));

            result.add(c);
        }
        return result;
    }
}
