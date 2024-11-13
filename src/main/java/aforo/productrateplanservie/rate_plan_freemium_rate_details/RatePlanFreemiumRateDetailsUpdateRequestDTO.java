package aforo.productrateplanservie.rate_plan_freemium_rate_details;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class RatePlanFreemiumRateDetailsUpdateRequestDTO {
    private Long id; // Identify the detail to update
    private BigDecimal freemiumMaxUnitQuantity; // Only update if provided
}

