package aforo.productrateplanservice.rate_plan_subscription_rate_details;

import lombok.Data;
import java.math.BigDecimal;
   @Data
    public class RatePlanSubscriptionRateDetailsDTO {
        private Long id; // ID of the details entry
        private BigDecimal unitPriceFixed;
        private BigDecimal subscriptionMaxUnitQuantity;
    }

