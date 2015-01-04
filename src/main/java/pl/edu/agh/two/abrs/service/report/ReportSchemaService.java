package pl.edu.agh.two.abrs.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.two.abrs.model.report.schema.ChartSchema;
import pl.edu.agh.two.abrs.model.report.schema.ReportSchema;
import pl.edu.agh.two.abrs.repository.ChartSchemaRepository;
import pl.edu.agh.two.abrs.repository.ReportSchemaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportSchemaService {

    @Autowired
    private ReportSchemaRepository reportSchemaRepository;

    @Autowired
    private ChartSchemaRepository chartSchemaRepository;

    public List<ReportSchema> getAll() {
        return reportSchemaRepository.findAll();
    }

    public void addReportSchema(String name, List<String> tables, List<ChartSchema> charts) {
        addReportSchema(new ReportSchema(name, tables, charts));
    }

    public void addReportSchema(ReportSchema reportSchema) {
        List<ChartSchema> savedCharts = new ArrayList<>();
        for (ChartSchema chartSchema : reportSchema.getCharts()) {
            savedCharts.add(chartSchemaRepository.save(chartSchema));
        }
        reportSchema.setCharts(savedCharts);

        reportSchemaRepository.save(reportSchema);
    }
}
