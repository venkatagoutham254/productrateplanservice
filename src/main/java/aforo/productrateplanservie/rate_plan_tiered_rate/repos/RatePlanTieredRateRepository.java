package aforo.productrateplanservie.rate_plan_tiered_rate.repos;

import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import aforo.productrateplanservie.rate_plan_tiered_rate.domain.RatePlanTieredRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanTieredRateRepository extends JpaRepository<RatePlanTieredRate, Long> {

    Page<RatePlanTieredRate> findAllByRatePlanTieredRateId(Long ratePlanTieredRateId,
            Pageable pageable);

    RatePlanTieredRate findFirstByRatePlan(RatePlan ratePlan);

}
