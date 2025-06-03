package aforo.productrateplanservice.overagecharges;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OverageChargeRepository extends JpaRepository<OverageCharge, Long> {
    List<OverageCharge> findByIsDeletedFalse();
    Optional<OverageCharge> findByIdAndIsDeletedFalse(Long id);
}
