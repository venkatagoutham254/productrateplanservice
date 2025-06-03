package aforo.productrateplanservice.setupfee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatePlanSetupFeeRepository extends JpaRepository<RatePlanSetupFee, Long> {
    List<RatePlanSetupFee> findByIsDeletedFalse();
    Optional<RatePlanSetupFee> findByIdAndIsDeletedFalse(Long id);
}
