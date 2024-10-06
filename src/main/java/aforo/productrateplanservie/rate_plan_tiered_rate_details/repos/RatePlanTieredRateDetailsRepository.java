package aforo.productrateplanservie.rate_plan_tiered_rate_details.repos;

import aforo.productrateplanservie.rate_plan_tiered_rate.domain.RatePlanTieredRate;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.domain.RatePlanTieredRateDetails;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanTieredRateDetailsRepository extends JpaRepository<RatePlanTieredRateDetails, UUID> {

    RatePlanTieredRateDetails findFirstByRatePlanTieredRate(RatePlanTieredRate ratePlanTieredRate);

}
