package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFlatRateRepository extends JpaRepository<RatePlanFlatRate, Long> {

    Page<RatePlanFlatRate> findAllByRatePlanFlatRateId(Long ratePlanFlatRateId, Pageable pageable);

    RatePlanFlatRate findFirstByRatePlan(RatePlan ratePlan);

}
