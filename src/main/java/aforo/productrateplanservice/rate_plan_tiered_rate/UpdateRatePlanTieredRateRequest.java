package aforo.productrateplanservice.rate_plan_tiered_rate;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import aforo.productrateplanservice.rate_plan_tiered_rate_details.UpdateRatePlanTieredRateDetailsRequest;
import aforo.productrateplanservice.util.enums.UnitCalculation;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitType;

@Getter
@Setter
public class UpdateRatePlanTieredRateRequest {

    @Size(max = 100)
    private String ratePlanTieredDescription;

    private String description;

    private UnitType unitType;

    private UnitMeasurement unitMeasurement;

    private UnitCalculation unitCalculation;

    private Set<UpdateRatePlanTieredRateDetailsRequest> ratePlanTieredRateDetails;
}
