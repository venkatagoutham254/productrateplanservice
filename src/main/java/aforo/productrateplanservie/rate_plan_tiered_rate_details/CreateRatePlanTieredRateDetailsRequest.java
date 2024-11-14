package aforo.productrateplanservie.rate_plan_tiered_rate_details;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRatePlanTieredRateDetailsRequest {

    @NotNull
    private BigDecimal tierStart;

    @NotNull
    private BigDecimal tierRate;

    @NotNull
    private BigDecimal tierEnd;
}
