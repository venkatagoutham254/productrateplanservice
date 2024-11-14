package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan_flat_rate_details.CreateRatePlanFlatRateDetailsRequest;
import aforo.productrateplanservie.util.enums.FlatRateUnitCalculation;
import aforo.productrateplanservie.util.enums.MaxLimitFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateRatePlanFlatRateRequest {

    @NotNull
    @Size(max = 100)
    private String ratePlanFlatDescription;

    private String description;

    @NotNull
    private UnitType unitType;

    @NotNull
    private UnitMeasurement unitMeasurement;

    @NotNull
    private FlatRateUnitCalculation flatRateUnitCalculation;

    @NotNull
    private MaxLimitFrequency maxLimitFrequency;

    @NotNull
    private List<CreateRatePlanFlatRateDetailsRequest> ratePlanFlatRateDetails;
}
