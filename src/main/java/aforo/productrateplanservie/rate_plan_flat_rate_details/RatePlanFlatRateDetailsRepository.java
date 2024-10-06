package aforo.productrateplanservie.rate_plan_flat_rate_details;

import aforo.productrateplanservie.rate_plan_flat_rate.RatePlanFlatRate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFlatRateDetailsRepository extends JpaRepository<RatePlanFlatRateDetails, Long> {

    RatePlanFlatRateDetails findFirstByRatePlanFlatRate(RatePlanFlatRate ratePlanFlatRate);

}
