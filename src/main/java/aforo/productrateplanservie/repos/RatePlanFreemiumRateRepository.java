package aforo.productrateplanservie.repos;

import aforo.productrateplanservie.domain.RatePlan;
import aforo.productrateplanservie.domain.RatePlanFreemiumRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanFreemiumRateRepository extends JpaRepository<RatePlanFreemiumRate, Integer> {

    Page<RatePlanFreemiumRate> findAllByRatePlanFreemiumRateId(Integer ratePlanFreemiumRateId,
            Pageable pageable);

    RatePlanFreemiumRate findFirstByRatePlan(RatePlan ratePlan);

}
