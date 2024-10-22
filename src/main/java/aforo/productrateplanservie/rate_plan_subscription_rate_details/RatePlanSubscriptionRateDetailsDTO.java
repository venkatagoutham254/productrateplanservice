package aforo.productrateplanservie.rate_plan_subscription_rate_details;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

    @Setter
    @Getter
    public class RatePlanSubscriptionRateDetailsDTO {

        // Getters and setters
        private Long id; // ID of the details entry
        private BigDecimal unitPriceFixed;
        private BigDecimal subscriptionMaxUnitQuantity;

        // Constructor
        public RatePlanSubscriptionRateDetailsDTO(Long id, BigDecimal unitPriceFixed, BigDecimal subscriptionMaxUnitQuantity) {
            this.id = id;
            this.unitPriceFixed = unitPriceFixed;
            this.subscriptionMaxUnitQuantity = subscriptionMaxUnitQuantity;
        }

    }

