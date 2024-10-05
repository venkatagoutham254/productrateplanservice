package aforo.productrateplanservie.repos;

import aforo.productrateplanservie.domain.RatePlanSubscriptionRate;
import aforo.productrateplanservie.domain.RatePlanSubscriptionRateDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanSubscriptionRateDetailsRepository extends JpaRepository<RatePlanSubscriptionRateDetails, Long> {

    RatePlanSubscriptionRateDetails findFirstByRatePlanSubscriptionRate(
            RatePlanSubscriptionRate ratePlanSubscriptionRate);

}
