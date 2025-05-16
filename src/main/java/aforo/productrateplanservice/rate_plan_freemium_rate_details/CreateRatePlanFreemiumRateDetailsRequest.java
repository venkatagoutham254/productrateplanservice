package aforo.productrateplanservice.rate_plan_freemium_rate_details;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class CreateRatePlanFreemiumRateDetailsRequest {
    @NotNull
    private BigDecimal freemiumMaxUnitQuantity;
}
