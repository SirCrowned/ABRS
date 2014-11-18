package pl.edu.agh.two.abrs.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.two.abrs.model.ColumnType;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;
import pl.edu.agh.two.abrs.service.data.MetadataService;
import pl.edu.agh.two.abrs.service.db.DbReaderException;
import pl.edu.agh.two.abrs.service.db.DbReaderService;
import pl.edu.agh.two.abrs.service.localSchema.LocalSchemaService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    String addLocalSchema(@RequestParam("name") String name, @RequestParam("sourceId") long sourceId) {

        List<LocalSchemaColumn> columns;
        try {
            columns = metaDataService.getMetadata(sourceId);
        } catch (DbReaderException e) {
            e.printStackTrace();
            return "ERROR";
        }

        if (localSchemaService.addLocalSchema(name, sourceId, columns)) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody String removeLocalSchema(@RequestParam("id") long id) {
        localSchemaService.removeLocalSchema(id);
        return "OK";
    }

    @RequestMapping(value = "/edit/", method = RequestMethod.POST)
    public
    @ResponseBody
    String editLocalSchema(@RequestParam("localSchemaId") long localSchemaId, @RequestParam("columns") String columns) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, ColumnType> cols = mapper.readValue(columns, new TypeReference<Map<String, ColumnType>>() {});
            localSchemaService.editLocalSchema(localSchemaId, cols);
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        }

       return "OK";
    }



}
