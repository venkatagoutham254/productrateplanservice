package aforo.productrateplanservice.rate_plan_flat_rate_details;

import lombok.Data;
import java.math.BigDecimal;
@Data
public class UpdateRatePlanFlatRateDetailsRequest {
    private Long id;
    private BigDecimal unitRate;
    private BigDecimal maxLimit;
}
