package pl.edu.agh.two.abrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.two.abrs.repository.LocalSchemaRepository;
import pl.edu.agh.two.abrs.repository.SourceRepository;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private LocalSchemaRepository localSchemaRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHome() {
        return "index";
    }

    @RequestMapping(value = "/source", method = RequestMethod.GET)
    public String getSource() {
        return "source";
    }


    @RequestMapping(value = "/sourceList", method = RequestMethod.GET)
    public String getSourceList(ModelMap model) {
        model.addAttribute("sourceList", sourceRepository.findAll());
        return "sourceList";
    }

    @RequestMapping(value = "/localSchema", method = RequestMethod.GET)
    public String getLocalSchema(ModelMap model) {
        model.addAttribute("sourceList", sourceRepository.findAll());
        model.addAttribute("localSchema", localSchemaRepository.findAll());
        return "localSchema";
    }

    @RequestMapping(value = "/localSchemaList", method = RequestMethod.GET)
    public String getLocalSchemaList(ModelMap model) {
        model.addAttribute("localSchemaList", localSchemaRepository.findAll());
        return "localSchemaList";
    }

    @RequestMapping(value = "/mapping", method = RequestMethod.GET)
    public String getMapping(ModelMap model) {
        model.addAttribute("sourceList", sourceRepository.findAll());
        model.addAttribute("localSchemaList", localSchemaRepository.findAll());
        return "mapping";
    }


}
