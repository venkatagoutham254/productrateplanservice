package aforo.productrateplanservice.rate_plan_flat_rate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

import aforo.productrateplanservice.rate_plan_flat_rate_details.CreateRatePlanFlatRateDetailsRequest;
import aforo.productrateplanservice.util.enums.FlatRateUnitCalculation;
import aforo.productrateplanservice.util.enums.MaxLimitFrequency;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitType;

@Data
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
