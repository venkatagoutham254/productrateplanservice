package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.RatePlan;
import aforo.rateplanservie.domain.RatePlanFlatRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFlatRateRepository extends JpaRepository<RatePlanFlatRate, Integer> {

    Page<RatePlanFlatRate> findAllByRatePlanFlatRateId(Integer ratePlanFlatRateId,
            Pageable pageable);

    RatePlanFlatRate findFirstByRatePlan(RatePlan ratePlan);

}
