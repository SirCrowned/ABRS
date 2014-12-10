package pl.edu.agh.two.abrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.global.GlobalSchema;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaColumn;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaTable;
import pl.edu.agh.two.abrs.repository.GraphItemRepository;
import pl.edu.agh.two.abrs.service.globalSchema.GlobalSchemaService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/globalSchema")
public class GlobalSchemaController {

    @Autowired
    private GlobalSchemaService globalSchemaService;

    @Autowired
    private GraphItemRepository graphItemRepository;

    @PostConstruct
    public void initGlobalSchema() {
        if (globalSchemaService.getGlobalSchema() == null) {
            GlobalSchema globalSchema = new GlobalSchema(new ArrayList<>());
            globalSchemaService.updateGlobalSchema(globalSchema);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getGlobalSchema(ModelMap model) {
        GlobalSchema globalSchema = globalSchemaService.getGlobalSchema();
        model.addAttribute("tablesList", globalSchema.getTables());
        model.addAttribute("columnTypes", ColumnType.values());
        model.addAttribute("items", graphItemRepository.findAll());
        return "globalSchema";
    }

    @ExceptionHandler(Exception.class)
    public
    @ResponseBody
    String handleException(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

    @RequestMapping(value = "/table/add", method = RequestMethod.POST)
    public
    @ResponseBody
    String addTable(@RequestBody final GlobalSchemaTable table) {
        GlobalSchema globalSchema = clone(globalSchemaService.getGlobalSchema());
        globalSchema.addTable(table);
        globalSchemaService.updateGlobalSchema(globalSchema);
        return "OK";
    }

    @RequestMapping(value = "/table/remove/{name}", method = RequestMethod.POST)
    public
    @ResponseBody
    String removeTable(@PathVariable String name) {
        GlobalSchema globalSchema = clone(globalSchemaService.getGlobalSchema());
        globalSchema.removeTable(name);
        globalSchemaService.updateGlobalSchema(globalSchema);
        return "OK";
    }

    private GlobalSchema clone(GlobalSchema globalSchema) {
        List<GlobalSchemaTable> tables = new ArrayList<>();
        for (GlobalSchemaTable table : globalSchema.getTables()) {
            List<GlobalSchemaColumn> columns = new ArrayList<>();
            for (GlobalSchemaColumn column : table.getColumns()) {
                columns.add(new GlobalSchemaColumn(column.getColumnType(), column.getName()));
            }
            tables.add(new GlobalSchemaTable(table.getName(), columns));
        }

        return new GlobalSchema(tables);
    }
}
