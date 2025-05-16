package aforo.productrateplanservice.rate_plan_freemium_rate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aforo.productrateplanservice.rate_plan.RatePlan;

import java.util.Optional;

@Repository
public interface RatePlanFreemiumRateRepository extends JpaRepository<RatePlanFreemiumRate, Long> {

    Page<RatePlanFreemiumRate> findAllByRatePlanFreemiumRateId(Long ratePlanFreemiumRateId, Pageable pageable);

    Optional<RatePlanFreemiumRate> findFirstByRatePlan(RatePlan ratePlan);
}
