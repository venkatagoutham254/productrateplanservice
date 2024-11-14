package aforo.productrateplanservie.rate_plan_flat_rate_details;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRatePlanFlatRateDetailsRequest {

    @NotNull
    private BigDecimal unitRate;

    @NotNull
    private BigDecimal maxLimit;
}
