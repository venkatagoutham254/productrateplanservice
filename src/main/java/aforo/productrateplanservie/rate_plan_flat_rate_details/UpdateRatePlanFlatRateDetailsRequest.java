package aforo.productrateplanservie.rate_plan_flat_rate_details;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateRatePlanFlatRateDetailsRequest {

    private Long id; // The ID of the detail to be updated

    private BigDecimal unitRate;

    private BigDecimal maxLimit;
}
