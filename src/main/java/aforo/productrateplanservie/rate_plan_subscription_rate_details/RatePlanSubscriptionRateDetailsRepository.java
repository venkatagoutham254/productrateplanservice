package aforo.productrateplanservie.rate_plan_subscription_rate_details;

import aforo.productrateplanservie.rate_plan_subscription_rate.RatePlanSubscriptionRate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanSubscriptionRateDetailsRepository extends JpaRepository<RatePlanSubscriptionRateDetails, Long> {

    RatePlanSubscriptionRateDetails findFirstByRatePlanSubscriptionRate(
            RatePlanSubscriptionRate ratePlanSubscriptionRate);

}
