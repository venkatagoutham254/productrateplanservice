package aforo.productrateplanservice.rate_plan_usage_based_rates;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateRatePlanUsageBasedRatesRequest {
    @NotNull
    private BigDecimal unitRate; // Define required field for unit rate
}