package aforo.productrateplanservie.repos;

import aforo.productrateplanservie.domain.RatePlanFreemiumRate;
import aforo.productrateplanservie.domain.RatePlanFreemiumRateDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFreemiumRateDetailsRepository extends JpaRepository<RatePlanFreemiumRateDetails, Long> {

    RatePlanFreemiumRateDetails findFirstByRatePlanFreemiumRate(
            RatePlanFreemiumRate ratePlanFreemiumRate);

}
