package aforo.productrateplanservice.rate_plan_subscription_rate_details;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
@Data
public class CreateRatePlanSubscriptionRateDetailsRequest {
    @NotNull
    private BigDecimal unitPriceFixed;
    @NotNull
    private BigDecimal subscriptionMaxUnitQuantity;
}
