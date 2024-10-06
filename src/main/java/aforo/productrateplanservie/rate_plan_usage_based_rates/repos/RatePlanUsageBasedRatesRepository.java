package aforo.productrateplanservie.rate_plan_usage_based_rates.repos;

import aforo.productrateplanservie.rate_plan_usage_based.domain.RatePlanUsageBased;
import aforo.productrateplanservie.rate_plan_usage_based_rates.domain.RatePlanUsageBasedRates;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatePlanUsageBasedRatesRepository extends JpaRepository<RatePlanUsageBasedRates, Long> {

    RatePlanUsageBasedRates findFirstByRatePlanUsageRate(RatePlanUsageBased ratePlanUsageBased);

}
