package aforo.productrateplanservice.rate_plan_usage_based_rates;

import org.springframework.data.jpa.repository.JpaRepository;

import aforo.productrateplanservice.rate_plan_usage_based.RatePlanUsageBased;

public interface RatePlanUsageBasedRatesRepository extends JpaRepository<RatePlanUsageBasedRates, Long> {

    // Corrected method
    RatePlanUsageBasedRates findFirstByRatePlanUsageBased(RatePlanUsageBased ratePlanUsageBased);
}
