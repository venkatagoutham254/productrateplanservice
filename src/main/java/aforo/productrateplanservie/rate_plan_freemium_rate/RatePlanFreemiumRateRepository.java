package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatePlanFreemiumRateRepository extends JpaRepository<RatePlanFreemiumRate, Long> {

    Page<RatePlanFreemiumRate> findAllByRatePlanFreemiumRateId(Long ratePlanFreemiumRateId, Pageable pageable);

    Optional<RatePlanFreemiumRate> findFirstByRatePlan(RatePlan ratePlan);
}
