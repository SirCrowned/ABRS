package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.graph.GraphItem;

public interface GraphItemRepository extends JpaRepository<GraphItem, Long> {
    public GraphItem findOneByLabel(String label);
}
