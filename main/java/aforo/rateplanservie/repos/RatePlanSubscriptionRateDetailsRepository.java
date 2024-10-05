package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.RatePlanSubscriptionRate;
import aforo.rateplanservie.domain.RatePlanSubscriptionRateDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanSubscriptionRateDetailsRepository extends JpaRepository<RatePlanSubscriptionRateDetails, Long> {

    RatePlanSubscriptionRateDetails findFirstByRatePlanSubscriptionRate(
            RatePlanSubscriptionRate ratePlanSubscriptionRate);

}
