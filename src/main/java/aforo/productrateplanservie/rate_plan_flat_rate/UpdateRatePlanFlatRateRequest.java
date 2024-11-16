package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.enumValidation.ValidEnum;
import aforo.productrateplanservie.rate_plan_flat_rate_details.UpdateRatePlanFlatRateDetailsRequest;
import aforo.productrateplanservie.util.enums.FlatRateUnitCalculation;
import aforo.productrateplanservie.util.enums.MaxLimitFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
