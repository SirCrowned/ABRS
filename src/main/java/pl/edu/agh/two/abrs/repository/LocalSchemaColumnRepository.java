package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.LocalSchemaColumn;

public interface LocalSchemaColumnRepository extends JpaRepository<LocalSchemaColumn, Long> {
}
