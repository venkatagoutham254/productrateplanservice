package aforo.productrateplanservie.rate_plan_subscription_rate.repos;

import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import aforo.productrateplanservie.rate_plan_subscription_rate.domain.RatePlanSubscriptionRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanSubscriptionRateRepository extends JpaRepository<RatePlanSubscriptionRate, Long> {

    Page<RatePlanSubscriptionRate> findAllByRatePlanSubscriptionRateId(
            Long ratePlanSubscriptionRateId, Pageable pageable);

    RatePlanSubscriptionRate findFirstByRatePlan(RatePlan ratePlan);

}
