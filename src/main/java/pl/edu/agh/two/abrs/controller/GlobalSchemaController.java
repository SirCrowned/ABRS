package pl.edu.agh.two.abrs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.GlobalSchema;
import pl.edu.agh.two.abrs.model.GlobalSchemaColumn;
import pl.edu.agh.two.abrs.model.GlobalSchemaRelation;
import pl.edu.agh.two.abrs.model.GlobalSchemaTable;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/globalSchema")
public class GlobalSchemaController {

    private GlobalSchema globalSchema = new GlobalSchema();

    @PostConstruct
    public void init() {
        List<GlobalSchemaColumn> columns = Arrays.asList(
                new GlobalSchemaColumn("col11", ColumnType.BOOLEAN),
                new GlobalSchemaColumn("col12", ColumnType.BOOLEAN)
        );
        globalSchema.getTables().add(new GlobalSchemaTable("tab1", columns));

        columns = Arrays.asList(
                new GlobalSchemaColumn("col21", ColumnType.BOOLEAN),
                new GlobalSchemaColumn("col22", ColumnType.BOOLEAN)
        );
        globalSchema.getTables().add(new GlobalSchemaTable("tab2", columns));

        globalSchema.getRelations().add(new GlobalSchemaRelation("tab1", "col11", "tab2", "col21"));
        globalSchema.getRelations().add(new GlobalSchemaRelation("tab1", "col11", "tab2", "col22"));
        globalSchema.getRelations().add(new GlobalSchemaRelation("tab1", "col12", "tab2", "col21"));
        globalSchema.getRelations().add(new GlobalSchemaRelation("tab1", "col12", "tab2", "col22"));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getGlobalSchema(ModelMap model) {
        model.addAttribute("tablesList", globalSchema.getTables());
        model.addAttribute("relationsList", globalSchema.getRelations());
        model.addAttribute("columnTypes", ColumnType.values());
        return "globalSchema";
    }

    @ExceptionHandler(Exception.class)
    public
    @ResponseBody
    String handleException(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

    @RequestMapping(value = "/relation/add", method = RequestMethod.POST)
    public
    @ResponseBody
    String addRelation(@RequestBody final GlobalSchemaRelation relation) {
        globalSchema.getRelations().add(relation);
        return "OK";
    }

    @RequestMapping(value = "/relation/remove", method = RequestMethod.POST)
    public
    @ResponseBody
    String removeRelation(@RequestBody final GlobalSchemaRelation relation) {
        globalSchema.getRelations().remove(relation);
        return "OK";
    }

    @RequestMapping(value = "/table/add", method = RequestMethod.POST)
    public
    @ResponseBody
    String addTable(@RequestBody final GlobalSchemaTable table) {
        globalSchema.getTables().add(table);
        return "OK";
    }

    @RequestMapping(value = "/table/remove/{name}", method = RequestMethod.POST)
    public
    @ResponseBody
    String removeTable(@PathVariable String name) {
        List<GlobalSchemaTable> tables = globalSchema.getTables();
        new ArrayList<>(tables).stream().filter(table -> table.getName().equals(name)).forEach(tables::remove);
        return "OK";
    }
}
