package aforo.productrateplanservice.freemium;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FreemiumRepository extends JpaRepository<Freemium, Long> {
    List<Freemium> findByIsDeletedFalse();
    Optional<Freemium> findByIdAndIsDeletedFalse(Long id);
}
