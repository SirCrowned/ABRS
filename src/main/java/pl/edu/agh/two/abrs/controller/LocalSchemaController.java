package pl.edu.agh.two.abrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;
import pl.edu.agh.two.abrs.model.SourceColumn;
import pl.edu.agh.two.abrs.service.data.MetadataService;
import pl.edu.agh.two.abrs.service.localSchema.LocalSchemaService;
import pl.edu.agh.two.abrs.service.operator.IdentityOperator;

import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/localSchema")
public class LocalSchemaController {

    @Autowired
    private LocalSchemaService localSchemaService;

    @Autowired
    private MetadataService metaDataService;

    @RequestMapping(value = "/add/", method = RequestMethod.POST)
    public
    @ResponseBody
    String addLocalSchema(@RequestParam("name") String name, @RequestParam("sourceId") long sourceId, @RequestParam("itemId") long itemId) {

        List<LocalSchemaColumn> columns = new ArrayList<>();
        try {
            for (SourceColumn column : metaDataService.getMetadata(sourceId)) {
                LocalSchemaColumn local = new LocalSchemaColumn();
                local.setName(column.getName());
                local.setSourceName(column.getName());
                local.setType(column.getType());
                local.setTransformation(new IdentityOperator());
                columns.add(local);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }

        if (localSchemaService.addLocalSchema(name, sourceId, itemId, columns)) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public
    @ResponseBody
    String removeLocalSchema(@RequestParam("id") long id) {
        localSchemaService.removeLocalSchema(id);
        return "OK";
    }

    @RequestMapping(value = "/edit/", method = RequestMethod.POST)
    public
    @ResponseBody
    String editLocalSchema(@RequestBody EditRequest request) {
        localSchemaService.editLocalSchema(request.localSchemaId, request.columns);
        return "OK";
    }

    public static class EditRequest {
        public long localSchemaId;

        public ArrayList<Column> columns;

        public static class Column {
            public String name;

            public String sourceName;

            public String type;

            public String transformation;
        }
    }
}
