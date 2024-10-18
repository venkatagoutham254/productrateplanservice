package aforo.productrateplanservie.rate_plan_flat_rate_details;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RatePlanFlatRateDetailsDTO {

    // Getters and setters
    private Long id; // ID of the details entry
    private BigDecimal unitRate;
    private BigDecimal maxLimit;

    // Constructor
    public RatePlanFlatRateDetailsDTO(Long id, BigDecimal unitRate, BigDecimal maxLimit) {
        this.id = id;
        this.unitRate = unitRate;
        this.maxLimit = maxLimit;
    }

}
