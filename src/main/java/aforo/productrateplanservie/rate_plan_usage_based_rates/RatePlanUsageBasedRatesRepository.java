package aforo.productrateplanservie.rate_plan_usage_based_rates;

import aforo.productrateplanservie.rate_plan_usage_based.RatePlanUsageBased;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatePlanUsageBasedRatesRepository extends JpaRepository<RatePlanUsageBasedRates, Long> {

    // Corrected method
    RatePlanUsageBasedRates findFirstByRatePlanUsageBased(RatePlanUsageBased ratePlanUsageBased);
}
