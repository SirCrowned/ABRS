package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.report.schema.ReportSchema;

public interface ReportSchemaRepository extends JpaRepository<ReportSchema, Long> {
}
