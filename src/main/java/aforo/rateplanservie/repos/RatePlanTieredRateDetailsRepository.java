package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.RatePlanTieredRate;
import aforo.rateplanservie.domain.RatePlanTieredRateDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanTieredRateDetailsRepository extends JpaRepository<RatePlanTieredRateDetails, Integer> {

    RatePlanTieredRateDetails findFirstByRatePlanTieredRate(RatePlanTieredRate ratePlanTieredRate);

}
