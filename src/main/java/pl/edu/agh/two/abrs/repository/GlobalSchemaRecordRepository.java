package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.global.GlobalSchemaRecord;

public interface GlobalSchemaRecordRepository extends JpaRepository<GlobalSchemaRecord, Long> {
}
