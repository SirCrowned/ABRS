package pl.edu.agh.two.abrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;
import pl.edu.agh.two.abrs.service.data.MetadataService;
import pl.edu.agh.two.abrs.service.localSchema.LocalSchemaService;

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
    String addLocalSchema(@RequestParam("name") String name, @RequestParam("sourceId") long sourceId) {

        List<LocalSchemaColumn> columns = metaDataService.getMetadata(sourceId);

        if (localSchemaService.addLocalSchema(name, sourceId, columns)) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

}
