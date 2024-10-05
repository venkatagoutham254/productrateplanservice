package aforo.productrateplanservie.repos;

import aforo.productrateplanservie.domain.RatePlanTieredRate;
import aforo.productrateplanservie.domain.RatePlanTieredRateDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanTieredRateDetailsRepository extends JpaRepository<RatePlanTieredRateDetails, Integer> {

    RatePlanTieredRateDetails findFirstByRatePlanTieredRate(RatePlanTieredRate ratePlanTieredRate);

}
