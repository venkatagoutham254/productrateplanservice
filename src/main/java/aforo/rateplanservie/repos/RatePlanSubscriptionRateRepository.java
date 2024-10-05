package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.RatePlan;
import aforo.rateplanservie.domain.RatePlanSubscriptionRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanSubscriptionRateRepository extends JpaRepository<RatePlanSubscriptionRate, Integer> {

    Page<RatePlanSubscriptionRate> findAllByRatePlanSubscriptionRateId(
            Integer ratePlanSubscriptionRateId, Pageable pageable);

    RatePlanSubscriptionRate findFirstByRatePlan(RatePlan ratePlan);

}
