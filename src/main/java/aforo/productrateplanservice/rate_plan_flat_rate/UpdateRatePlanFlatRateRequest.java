package aforo.productrateplanservice.rate_plan_flat_rate;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import aforo.productrateplanservice.enumValidation.ValidEnum;
import aforo.productrateplanservice.rate_plan_flat_rate_details.UpdateRatePlanFlatRateDetailsRequest;
import aforo.productrateplanservice.util.enums.FlatRateUnitCalculation;
import aforo.productrateplanservice.util.enums.MaxLimitFrequency;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitType;

@Data
@Getter
@Setter
public class UpdateRatePlanFlatRateRequest {

    @Size(max = 100)
    private String ratePlanFlatDescription;
    private String description;
    @ValidEnum(enumClass = UnitType.class, message = "Invalid UnitType value")
    private UnitType unitType;
    @ValidEnum(enumClass = UnitMeasurement.class, message = "Invalid UnitMeasurement value")
    private UnitMeasurement unitMeasurement;
    @ValidEnum(enumClass = FlatRateUnitCalculation.class, message = "Invalid FlatRateUnitCalculation value")
    private FlatRateUnitCalculation flatRateUnitCalculation;
    @ValidEnum(enumClass = MaxLimitFrequency.class, message = "Invalid MaxLimitFrequency value")
    private MaxLimitFrequency maxLimitFrequency;
    private List<UpdateRatePlanFlatRateDetailsRequest> ratePlanFlatRateDetails;



}
