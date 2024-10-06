package aforo.productrateplanservie.rate_plan_freemium_rate.repos;

import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import aforo.productrateplanservie.rate_plan_freemium_rate.domain.RatePlanFreemiumRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFreemiumRateRepository extends JpaRepository<RatePlanFreemiumRate, Long> {

    Page<RatePlanFreemiumRate> findAllByRatePlanFreemiumRateId(Long ratePlanFreemiumRateId,
            Pageable pageable);

    RatePlanFreemiumRate findFirstByRatePlan(RatePlan ratePlan);

}
