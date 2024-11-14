package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.rate_plan_tiered_rate_details.CreateRatePlanTieredRateDetailsRequest;
import aforo.productrateplanservie.util.enums.UnitCalculation;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateRatePlanTieredRateRequest {

    @NotNull
    @Size(max = 100)
    private String ratePlanTieredDescription;

    private String description;

    @NotNull
    private UnitType unitType;

    @NotNull
    private UnitMeasurement unitMeasurement;

    @NotNull
    private UnitCalculation unitCalculation;

    @NotNull
    private Set<CreateRatePlanTieredRateDetailsRequest> ratePlanTieredRateDetails;
}
