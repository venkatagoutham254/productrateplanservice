package aforo.productrateplanservie.rate_plan_flat_rate.repos;

import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import aforo.productrateplanservie.rate_plan_flat_rate.domain.RatePlanFlatRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFlatRateRepository extends JpaRepository<RatePlanFlatRate, Long> {

    Page<RatePlanFlatRate> findAllByRatePlanFlatRateId(Long ratePlanFlatRateId, Pageable pageable);

    RatePlanFlatRate findFirstByRatePlan(RatePlan ratePlan);

}
