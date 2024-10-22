package aforo.productrateplanservie.rate_plan_tiered_rate_details;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter

public class RatePlanTieredRateDetailsDTO {
    // Getters and setters
    private Long tierNumber; // ID of the details entry
    private BigDecimal tierStart;
    private BigDecimal tierRate;
    private BigDecimal tierEnd;

    // Constructor
    public RatePlanTieredRateDetailsDTO(Long id, BigDecimal unitPriceFixed, BigDecimal subscriptionMaxUnitQuantity) {
        this.tierNumber = tierNumber;
        this.tierStart = tierStart;
        this.tierRate = tierRate;
        this.tierEnd = tierEnd;
    }
}
