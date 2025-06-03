package aforo.productrateplanservice.minimumcommitment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MinimumCommitmentRepository extends JpaRepository<MinimumCommitment, Long> {
    List<MinimumCommitment> findByIsDeletedFalse();
    Optional<MinimumCommitment> findByIdAndIsDeletedFalse(Long id);
}
