package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.rate_plan_tiered_rate_details.UpdateRatePlanTieredRateDetailsRequest;
import aforo.productrateplanservie.util.enums.UnitCalculation;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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
