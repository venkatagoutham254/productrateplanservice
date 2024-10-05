package aforo.rateplanservie.repos;

import aforo.rateplanservie.domain.RatePlanUsageBased;
import aforo.rateplanservie.domain.RatePlanUsageBasedRates;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanUsageBasedRatesRepository extends JpaRepository<RatePlanUsageBasedRates, Long> {

    RatePlanUsageBasedRates findFirstByRatePlanUsageRate(RatePlanUsageBased ratePlanUsageBased);

}
