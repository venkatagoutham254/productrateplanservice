package aforo.productrateplanservie.rate_plan_freemium_rate_details;

import aforo.productrateplanservie.rate_plan_freemium_rate.RatePlanFreemiumRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatePlanFreemiumRateDetailsRepository extends JpaRepository<RatePlanFreemiumRateDetails, Long> {

    RatePlanFreemiumRateDetails findFirstByRatePlanFreemiumRate(RatePlanFreemiumRate ratePlanFreemiumRate);

}
