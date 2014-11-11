package pl.edu.agh.two.abrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.agh.two.abrs.repository.SourceRepository;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private SourceRepository sourceRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHome() {
        return "index";
    }

    @RequestMapping(value = "/source", method = RequestMethod.GET)
    public String getSource() {
        return "source";
    }

    @RequestMapping(value = "/sourceList", method = RequestMethod.GET)
    public String getSourceList() {
        return "sourceList";
    }
}
