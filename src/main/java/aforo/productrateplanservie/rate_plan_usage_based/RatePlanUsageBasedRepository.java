package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan.RatePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatePlanUsageBasedRepository extends JpaRepository<RatePlanUsageBased, Long> {

    Page<RatePlanUsageBased> findAllByRatePlanUsageRateId(Long ratePlanUsageRateId, Pageable pageable);

    Optional<RatePlanUsageBased> findFirstByRatePlan(RatePlan ratePlan);
}
