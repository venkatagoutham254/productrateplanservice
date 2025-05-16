package aforo.productrateplanservice.rate_plan_tiered_rate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import aforo.productrateplanservice.rate_plan_tiered_rate_details.CreateRatePlanTieredRateDetailsRequest;
import aforo.productrateplanservice.util.enums.UnitCalculation;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitType;

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
