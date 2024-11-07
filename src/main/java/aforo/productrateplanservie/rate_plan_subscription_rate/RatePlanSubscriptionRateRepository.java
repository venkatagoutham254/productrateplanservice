package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatePlanSubscriptionRateRepository extends JpaRepository<RatePlanSubscriptionRate, Long> {

    Page<RatePlanSubscriptionRate> findAllByRatePlanSubscriptionRateId(Long ratePlanSubscriptionRateId, Pageable pageable);

    Optional<RatePlanSubscriptionRate> findFirstByRatePlan(RatePlan ratePlan);
}
