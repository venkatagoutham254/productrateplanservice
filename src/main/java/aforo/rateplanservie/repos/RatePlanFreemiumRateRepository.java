package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.RatePlan;
import aforo.rateplanservie.domain.RatePlanFreemiumRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFreemiumRateRepository extends JpaRepository<RatePlanFreemiumRate, Integer> {

    Page<RatePlanFreemiumRate> findAllByRatePlanFreemiumRateId(Integer ratePlanFreemiumRateId,
            Pageable pageable);

    RatePlanFreemiumRate findFirstByRatePlan(RatePlan ratePlan);

}
