package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.RatePlan;
import aforo.rateplanservie.domain.RatePlanTieredRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanTieredRateRepository extends JpaRepository<RatePlanTieredRate, Integer> {

    Page<RatePlanTieredRate> findAllByRatePlanTieredRateId(Integer ratePlanTieredRateId,
            Pageable pageable);

    RatePlanTieredRate findFirstByRatePlan(RatePlan ratePlan);

}
