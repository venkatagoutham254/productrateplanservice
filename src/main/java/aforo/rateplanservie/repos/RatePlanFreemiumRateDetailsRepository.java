package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.RatePlanFreemiumRate;
import aforo.rateplanservie.domain.RatePlanFreemiumRateDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFreemiumRateDetailsRepository extends JpaRepository<RatePlanFreemiumRateDetails, Long> {

    RatePlanFreemiumRateDetails findFirstByRatePlanFreemiumRate(
            RatePlanFreemiumRate ratePlanFreemiumRate);

}
