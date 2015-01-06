package pl.edu.agh.two.abrs.controller;

import com.google.common.io.Files;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaTable;
import pl.edu.agh.two.abrs.model.report.Report;
import pl.edu.agh.two.abrs.model.report.schema.ChartType;
import pl.edu.agh.two.abrs.model.report.schema.ReportSchema;
import pl.edu.agh.two.abrs.repository.GlobalSchemaTableRepository;
import pl.edu.agh.two.abrs.service.export.ExporterException;
import pl.edu.agh.two.abrs.service.export.ExporterService;
import pl.edu.agh.two.abrs.service.report.ReportService;
import pl.edu.agh.two.abrs.service.report.schema.ReportSchemaService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/report")
public class ReportSchemaController {

    @Autowired
    private GlobalSchemaTableRepository globalSchemaTableRepository;

    @Autowired
    private ReportSchemaService reportSchemaService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ExporterService exporterService;

    @ExceptionHandler(Exception.class)
    public
    @ResponseBody
    String handleException(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createReport(ModelMap modelMap) {
        List<GlobalSchemaTable> tables = globalSchemaTableRepository.findAll();
        modelMap.addAttribute("globalSchemaTables", tables);
        modelMap.addAttribute("firstTableColumns", tables.isEmpty() ? new ArrayList<>() : tables.get(0).getColumns());
        modelMap.addAttribute("chartTypes", ChartType.values());
        return "createEditReport";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    String createReport(@RequestBody final ReportSchema reportSchema) {
        reportSchemaService.add(reportSchema);
        return "OK";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public
    @ResponseBody
    String removeReport(@PathVariable("id") Long id) {
        reportSchemaService.remove(id);
        return "OK";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editReport(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("reportId", id);
        modelMap.addAttribute("reportSchema", init(reportSchemaService.get(id)));
        return createReport(modelMap);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    String editReport(@RequestBody ReportSchema reportSchema) {
        reportSchemaService.update(reportSchema);
        return "OK";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listReports(ModelMap modelMap) {
        modelMap.addAttribute("reportSchemas", reportSchemaService.getAll());
        return "reportList";
    }

    @RequestMapping(value = "/getSchema", method = RequestMethod.GET)
    public
    @ResponseBody
    ReportSchema listReports(@RequestParam("schemaId") long schemaId) {
        return reportSchemaService.get(schemaId);
    }

    @RequestMapping(value = "/getReport/{id}", method = RequestMethod.GET)
    public String getReport(@PathVariable("id") Long id, ModelMap modelMap) {
        ReportSchema reportSchema = reportSchemaService.get(id);
        Report report = reportService.collectReportData(reportSchema);
        modelMap.addAttribute("report", report);
        return "report";
    }

    @RequestMapping(value = "/getPdf/{id}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getPdf(@PathVariable("id") Long id) throws ExporterException, IOException {
        ReportSchema reportSchema = reportSchemaService.get(id);
        Report report = reportService.collectReportData(reportSchema);
        File file = exporterService.exportReport(report);
        byte[] documentBody = Files.toByteArray(file);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "pdf"));
        header.set("Content-Disposition",
                   "attachment; filename=" + file.getName());
        header.setContentLength(documentBody.length);
        return new HttpEntity<byte[]>(documentBody, header);
    }

    private ReportSchema init(ReportSchema reportSchema) {
        Hibernate.initialize(reportSchema);
        Hibernate.initialize(reportSchema.getTables());
        reportSchema.getCharts().forEach(Hibernate::initialize);
        return reportSchema;
    }
}
