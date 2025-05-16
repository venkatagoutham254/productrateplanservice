package aforo.productrateplanservice.rate_plan_tiered_rate_details;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatePlanTieredRateDetailsDTO {
    private Long tierNumber; // ID of the details entry
    private BigDecimal tierStart;
    private BigDecimal tierRate;
    private BigDecimal tierEnd;


}
