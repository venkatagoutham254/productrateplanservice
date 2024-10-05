package aforo.productrateplanservie.repos;

import aforo.productrateplanservie.domain.RatePlanFlatRate;
import aforo.productrateplanservie.domain.RatePlanFlatRateDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFlatRateDetailsRepository extends JpaRepository<RatePlanFlatRateDetails, Long> {

    RatePlanFlatRateDetails findFirstByRatePlanFlatRate(RatePlanFlatRate ratePlanFlatRate);

}
