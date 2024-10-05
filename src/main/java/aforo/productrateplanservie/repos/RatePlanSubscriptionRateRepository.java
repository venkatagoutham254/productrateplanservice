package aforo.productrateplanservie.repos;

import aforo.productrateplanservie.domain.RatePlan;
import aforo.productrateplanservie.domain.RatePlanSubscriptionRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanSubscriptionRateRepository extends JpaRepository<RatePlanSubscriptionRate, Integer> {

    Page<RatePlanSubscriptionRate> findAllByRatePlanSubscriptionRateId(
            Integer ratePlanSubscriptionRateId, Pageable pageable);

    RatePlanSubscriptionRate findFirstByRatePlan(RatePlan ratePlan);

}
