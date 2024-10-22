package aforo.productrateplanservie.rate_plan_usage_based_rates;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class RatePlanUsageBasedRatesDTO {
    private Long id; // ID of the details entry
    private BigDecimal unitRate;


    // Constructor
    public RatePlanUsageBasedRatesDTO(Long id, BigDecimal unitRate) {
        this.id = id;
        this.unitRate = unitRate;

    }
}
