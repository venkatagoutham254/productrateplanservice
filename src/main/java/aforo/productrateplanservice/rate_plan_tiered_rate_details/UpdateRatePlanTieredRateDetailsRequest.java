package aforo.productrateplanservice.rate_plan_tiered_rate_details;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateRatePlanTieredRateDetailsRequest {

    private Long tierNumber; // Identifier for the tier to be updated

    private BigDecimal tierStart;

    private BigDecimal tierRate;

    private BigDecimal tierEnd;
}
