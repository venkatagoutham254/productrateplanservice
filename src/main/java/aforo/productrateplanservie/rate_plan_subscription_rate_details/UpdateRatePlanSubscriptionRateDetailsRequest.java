package aforo.productrateplanservie.rate_plan_subscription_rate_details;

import lombok.Data;
import java.math.BigDecimal;
@Data
public class UpdateRatePlanSubscriptionRateDetailsRequest {
    private Long id;
    private BigDecimal unitPriceFixed;
    private BigDecimal subscriptionMaxUnitQuantity;
}
