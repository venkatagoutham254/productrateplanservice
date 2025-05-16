package aforo.productrateplanservice.rate_plan_freemium_rate_details;

import java.math.BigDecimal;
import lombok.Data;
@Data
public class UpdateRatePlanFreemiumRateDetailsRequest {
    private Long id; // Identify the detail to update
    private BigDecimal freemiumMaxUnitQuantity; // Only update if provided
}

