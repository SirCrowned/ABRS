package pl.edu.agh.two.abrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.two.abrs.model.SourceType;
import pl.edu.agh.two.abrs.service.db.ConnectionParams;
import pl.edu.agh.two.abrs.service.db.DbReaderException;
import pl.edu.agh.two.abrs.service.db.DbReaderService;
import pl.edu.agh.two.abrs.service.db.MySqlConnectionParams;
import pl.edu.agh.two.abrs.service.source.SourceService;

@Controller
@RequestMapping("/source")
public class SourceController {

    @Autowired
    private SourceService sourceService;

    @Autowired
    private DbReaderService dbReaderService;

    @RequestMapping(value = "/add/url/", method = RequestMethod.POST)
    public
    @ResponseBody
    String addSource(@RequestParam("name") String name, @RequestParam("type") SourceType type, @RequestParam("url") String url) {
        if (sourceService.addSourceUrl(name, type, url)) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

    @RequestMapping(value = "/add/db/", method = RequestMethod.POST)
    public
    @ResponseBody
    String addSource(@RequestParam("name") String name, @RequestParam("host") String host, @RequestParam("port") String port,
                     @RequestParam("user") String user, @RequestParam("password") String password, @RequestParam("database") String database,
                     @RequestParam("table") String table) {
        if (sourceService.addSourceDatabase(name, host, port, user, password, database, table)) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

    @RequestMapping(value = "/test/db/", method = RequestMethod.POST)
    public
    @ResponseBody
    String testConnection(@RequestParam("host") String host, @RequestParam("port") String port, @RequestParam("user") String user,
                          @RequestParam("password") String password, @RequestParam("database") String database) {
        try {
            ConnectionParams params = new MySqlConnectionParams(host, Integer.parseInt(port), database, user, password);
            dbReaderService.testConnection(params);
            return "OK";
        } catch (DbReaderException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody String removeSource(@RequestParam("id") long id) {
        sourceService.removeSource(id);
        return "OK";
    }
}
