package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.report.schema.ChartSchema;

public interface ChartSchemaRepository extends JpaRepository<ChartSchema, Long> {
}
