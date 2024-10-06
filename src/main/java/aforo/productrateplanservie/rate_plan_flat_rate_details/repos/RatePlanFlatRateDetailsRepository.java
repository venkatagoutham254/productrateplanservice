package aforo.productrateplanservie.rate_plan_flat_rate_details.repos;

import aforo.productrateplanservie.rate_plan_flat_rate.domain.RatePlanFlatRate;
import aforo.productrateplanservie.rate_plan_flat_rate_details.domain.RatePlanFlatRateDetails;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFlatRateDetailsRepository extends JpaRepository<RatePlanFlatRateDetails, UUID> {

    RatePlanFlatRateDetails findFirstByRatePlanFlatRate(RatePlanFlatRate ratePlanFlatRate);

}
