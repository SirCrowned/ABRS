package pl.edu.agh.two.abrs.service.report.schema;

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

    public ReportSchema add(ReportSchema reportSchema) {
        List<ChartSchema> savedCharts = new ArrayList<>();
        for (ChartSchema chartSchema : reportSchema.getCharts()) {
            savedCharts.add(chartSchemaRepository.save(chartSchema));
        }
        reportSchema.setCharts(savedCharts);

        return reportSchemaRepository.save(reportSchema);
    }

    public ReportSchema get(Long id) {
        return reportSchemaRepository.findOne(id);
    }

    public List<ReportSchema> getAll() {
        return reportSchemaRepository.findAll();
    }

    public ReportSchema update(ReportSchema newReport) {
        ReportSchema oldReport = get(newReport.getId());
        oldReport.setName(newReport.getName());
        oldReport.getTables().clear();
        oldReport.getCharts().clear();

        oldReport.setTables(newReport.getTables());
        for (ChartSchema newChart : newReport.getCharts()) {
            oldReport.addChart(chartSchemaRepository.save(newChart));
        }
        return oldReport;
    }

    public void remove(Long id) {
        reportSchemaRepository.delete(id);
    }
}
