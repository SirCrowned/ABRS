package pl.edu.agh.two.abrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.two.abrs.model.mapping.Mapping;

public interface MappingRepository extends JpaRepository<Mapping, Long> {
}
