package pl.edu.agh.two.abrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.two.abrs.model.graph.GraphConn;
import pl.edu.agh.two.abrs.model.graph.GraphItem;
import pl.edu.agh.two.abrs.repository.GraphConnRepository;
import pl.edu.agh.two.abrs.repository.GraphItemRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Controller
@RequestMapping("/graph/")
public class GraphController {

    @Autowired
    private GraphConnRepository graphConnRepository;

    @Autowired
    private GraphItemRepository graphItemRepository;

    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveGraph(@RequestBody GraphWrapper wrapper) {
        graphConnRepository.deleteAll();
        graphConnRepository.save(wrapper.conn);
        graphConnRepository.flush();

        wrapper.items.forEach(i -> {
            GraphItem tmp = graphItemRepository.findOneByLabel(i.getLabel());
            if (tmp == null) {
                graphItemRepository.save(i);
            }
        });

        graphItemRepository.findAll().forEach(i -> {
            if (wrapper.items.contains(i)) {
                GraphItem tmp = wrapper.items.get(wrapper.items.indexOf(i));
                i.setLeft(tmp.getLeft());
                i.setTop(tmp.getTop());
                i.setHtml_id(tmp.getHtml_id());
                graphItemRepository.save(i);
            } else {
                graphItemRepository.delete(i);
            }
        });
        return "ok";
    }

    public static class GraphWrapper {
        public List<GraphItem> items;

        public List<GraphConn> conn;
    }
}
