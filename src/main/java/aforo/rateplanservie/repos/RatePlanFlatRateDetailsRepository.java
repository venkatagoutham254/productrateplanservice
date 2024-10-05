package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.RatePlanFlatRate;
import aforo.rateplanservie.domain.RatePlanFlatRateDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFlatRateDetailsRepository extends JpaRepository<RatePlanFlatRateDetails, Long> {

    RatePlanFlatRateDetails findFirstByRatePlanFlatRate(RatePlanFlatRate ratePlanFlatRate);

}
