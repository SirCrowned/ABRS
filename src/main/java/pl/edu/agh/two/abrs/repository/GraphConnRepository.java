package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.graph.GraphConn;

public interface GraphConnRepository extends JpaRepository<GraphConn, Long> {
}
