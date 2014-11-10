package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.Source;

public interface SourceRepository extends JpaRepository<Source, Long> {
}
