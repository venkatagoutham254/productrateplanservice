package aforo.productrateplanservie.rate_plan_usage_based.repos;

import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import aforo.productrateplanservie.rate_plan_usage_based.domain.RatePlanUsageBased;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanUsageBasedRepository extends JpaRepository<RatePlanUsageBased, UUID> {

    Page<RatePlanUsageBased> findAllByRatePlanUsageRateId(UUID ratePlanUsageRateId,
            Pageable pageable);

    RatePlanUsageBased findFirstByRatePlan(RatePlan ratePlan);

}
