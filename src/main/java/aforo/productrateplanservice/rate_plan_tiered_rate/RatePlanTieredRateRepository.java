package aforo.productrateplanservice.rate_plan_tiered_rate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aforo.productrateplanservice.rate_plan.RatePlan;

import java.util.Optional;

@Repository
public interface RatePlanTieredRateRepository extends JpaRepository<RatePlanTieredRate, Long> {

    Page<RatePlanTieredRate> findAllByRatePlanTieredRateId(Long ratePlanTieredRateId, Pageable pageable);

    Optional<RatePlanTieredRate> findFirstByRatePlan(RatePlan ratePlan);
}
