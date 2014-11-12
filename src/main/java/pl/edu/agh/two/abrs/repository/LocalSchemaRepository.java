package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.LocalSchema;

public interface LocalSchemaRepository extends JpaRepository<LocalSchema, Long> {
}
