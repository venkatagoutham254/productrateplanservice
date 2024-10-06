package aforo.productrateplanservie.rate_plan_freemium_rate_details.repos;

import aforo.productrateplanservie.rate_plan_freemium_rate.domain.RatePlanFreemiumRate;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.domain.RatePlanFreemiumRateDetails;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFreemiumRateDetailsRepository extends JpaRepository<RatePlanFreemiumRateDetails, UUID> {

    RatePlanFreemiumRateDetails findFirstByRatePlanFreemiumRate(
            RatePlanFreemiumRate ratePlanFreemiumRate);

}
