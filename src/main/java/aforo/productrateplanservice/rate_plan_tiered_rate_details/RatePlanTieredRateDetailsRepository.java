package aforo.productrateplanservice.rate_plan_tiered_rate_details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aforo.productrateplanservice.rate_plan_tiered_rate.RatePlanTieredRate;

@Repository
public interface RatePlanTieredRateDetailsRepository extends JpaRepository<RatePlanTieredRateDetails, Long> {

    RatePlanTieredRateDetails findFirstByRatePlanTieredRate(RatePlanTieredRate ratePlanTieredRate);

    void deleteAllByRatePlanTieredRate(RatePlanTieredRate updatedRatePlanTieredRate);
}
