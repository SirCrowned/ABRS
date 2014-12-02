package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaColumn;

public interface GlobalSchemaColumnRepository extends JpaRepository<GlobalSchemaColumn, Long>{
}
