package aforo.productrateplanservie.repos;

import aforo.productrateplanservie.domain.RatePlan;
import aforo.productrateplanservie.domain.RatePlanTieredRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanTieredRateRepository extends JpaRepository<RatePlanTieredRate, Integer> {

    Page<RatePlanTieredRate> findAllByRatePlanTieredRateId(Integer ratePlanTieredRateId,
            Pageable pageable);

    RatePlanTieredRate findFirstByRatePlan(RatePlan ratePlan);

}
