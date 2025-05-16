package aforo.productrateplanservice.rate_plan_subscription_rate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import aforo.productrateplanservice.rate_plan.RatePlan;

import java.util.Optional;

public interface RatePlanSubscriptionRateRepository extends JpaRepository<RatePlanSubscriptionRate, Long> {

    Page<RatePlanSubscriptionRate> findAllByRatePlanSubscriptionRateId(Long ratePlanSubscriptionRateId, Pageable pageable);

    Optional<RatePlanSubscriptionRate> findFirstByRatePlan(RatePlan ratePlan);
}
