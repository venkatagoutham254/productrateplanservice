package aforo.productrateplanservie.rate_plan_tiered_rate_details;

import aforo.productrateplanservie.rate_plan_tiered_rate.RatePlanTieredRate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanTieredRateDetailsRepository extends JpaRepository<RatePlanTieredRateDetails, Long> {

    RatePlanTieredRateDetails findFirstByRatePlanTieredRate(RatePlanTieredRate ratePlanTieredRate);

}
