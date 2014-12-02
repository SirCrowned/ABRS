package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.global.GlobalSchema;

public interface GlobalSchemaRepository extends JpaRepository<GlobalSchema, Long>{
}
