package pl.edu.agh.two.abrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaTable;
import pl.edu.agh.two.abrs.model.report.schema.ChartType;
import pl.edu.agh.two.abrs.model.report.schema.ReportSchema;
import pl.edu.agh.two.abrs.repository.GlobalSchemaTableRepository;
import pl.edu.agh.two.abrs.service.report.ReportSchemaService;

import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/report")
public class CreateReportController {

    @Autowired
    private GlobalSchemaTableRepository globalSchemaTableRepository;

    @Autowired
    private ReportSchemaService reportSchemaService;

    @ExceptionHandler(Exception.class)
    public
    @ResponseBody
    String handleException(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String addReport(ModelMap modelMap) {
        List<GlobalSchemaTable> tables = globalSchemaTableRepository.findAll();
        modelMap.addAttribute("globalSchemaTables", tables);
        modelMap.addAttribute("firstTableColumns", tables.isEmpty() ? new ArrayList<>() : tables.get(0).getColumns());
        modelMap.addAttribute("chartTypes", ChartType.values());
        return "createReport";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    String addReport(@RequestBody final ReportSchema reportSchema) {
        reportSchemaService.addReportSchema(reportSchema);
        return "OK";
    }
}
