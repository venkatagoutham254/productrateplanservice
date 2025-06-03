package aforo.productrateplanservice.resetperiod;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResetPeriodRepository extends JpaRepository<ResetPeriod, Long> {
    List<ResetPeriod> findByIsDeletedFalse();
    Optional<ResetPeriod> findByIdAndIsDeletedFalse(Long id);
}
