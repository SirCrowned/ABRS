package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.SourceProperties;

public interface SourcePropertiesRepository extends JpaRepository<SourceProperties, Long> {
}
