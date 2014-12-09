package pl.edu.agh.two.abrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaColumn;
import pl.edu.agh.two.abrs.model.mapping.Mapping;
import pl.edu.agh.two.abrs.repository.GlobalSchemaColumnRepository;
import pl.edu.agh.two.abrs.repository.GlobalSchemaRepository;
import pl.edu.agh.two.abrs.repository.LocalSchemaColumnRepository;
import pl.edu.agh.two.abrs.repository.LocalSchemaRepository;
import pl.edu.agh.two.abrs.repository.MappingRepository;
import pl.edu.agh.two.abrs.repository.SourceRepository;

import java.util.List;

@Controller
public class MappingController {

    @Autowired
    private GlobalSchemaColumnRepository globalSchemaColumnRepository;

    @Autowired
    private LocalSchemaRepository localSchemaRepository;

    @Autowired
    private LocalSchemaColumnRepository localSchemaColumnRepository;

    @Autowired
    private MappingRepository repository;

    @RequestMapping(value = "/mapping/add",method = RequestMethod.POST)
    public String addMapping(@RequestParam("localId") long localId, @RequestParam("globalId") long globalId){
        LocalSchemaColumn localSchemaColumn = localSchemaColumnRepository.getOne(localId);
        GlobalSchemaColumn globalSchemaColumn = globalSchemaColumnRepository.getOne(globalId);
        repository.save(new Mapping(globalSchemaColumn, localSchemaColumn));
        return "OK";
    }

    @RequestMapping(value = "/mapping", method = RequestMethod.GET)
    public String getMapping(ModelMap model) {
        model.addAttribute("localSchemaList", localSchemaRepository.findAll());
        model.addAttribute("globalSchemaColumns",globalSchemaColumnRepository.findAll());
        model.addAttribute("mappings",repository.findAll());
        return "mapping";
    }

}
