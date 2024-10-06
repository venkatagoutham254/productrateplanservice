package aforo.productrateplanservie.rate_plan_subscription_rate_details.repos;

import aforo.productrateplanservie.rate_plan_subscription_rate.domain.RatePlanSubscriptionRate;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.domain.RatePlanSubscriptionRateDetails;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanSubscriptionRateDetailsRepository extends JpaRepository<RatePlanSubscriptionRateDetails, UUID> {

    RatePlanSubscriptionRateDetails findFirstByRatePlanSubscriptionRate(
            RatePlanSubscriptionRate ratePlanSubscriptionRate);

}
