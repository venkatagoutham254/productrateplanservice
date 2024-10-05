package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.RatePlan;
import aforo.rateplanservie.domain.RatePlanUsageBased;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanUsageBasedRepository extends JpaRepository<RatePlanUsageBased, Integer> {

    Page<RatePlanUsageBased> findAllByRatePlanUsageRateId(Integer ratePlanUsageRateId,
            Pageable pageable);

    RatePlanUsageBased findFirstByRatePlan(RatePlan ratePlan);

}
