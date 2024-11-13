package aforo.productrateplanservie.rate_plan_usage_based_rates;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateRatePlanUsageBasedRatesRequest {
    private Long id; // ID of the details entry
    private BigDecimal unitRate;
}
