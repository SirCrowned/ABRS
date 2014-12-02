package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaTable;

public interface GlobalSchemaTableRepository extends JpaRepository<GlobalSchemaTable, Long> {
}
