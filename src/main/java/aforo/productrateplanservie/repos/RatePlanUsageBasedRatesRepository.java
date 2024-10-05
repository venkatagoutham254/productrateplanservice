package aforo.productrateplanservie.repos;

import aforo.productrateplanservie.domain.RatePlanUsageBased;
import aforo.productrateplanservie.domain.RatePlanUsageBasedRates;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanUsageBasedRatesRepository extends JpaRepository<RatePlanUsageBasedRates, Long> {

    RatePlanUsageBasedRates findFirstByRatePlanUsageRate(RatePlanUsageBased ratePlanUsageBased);

}
