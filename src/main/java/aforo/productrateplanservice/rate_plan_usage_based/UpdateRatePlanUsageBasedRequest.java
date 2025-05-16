package aforo.productrateplanservice.rate_plan_usage_based;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

import aforo.productrateplanservice.enumValidation.ValidEnum;
import aforo.productrateplanservice.rate_plan_usage_based_rates.UpdateRatePlanUsageBasedRatesRequest;
import aforo.productrateplanservice.util.enums.UnitBillingFrequency;
import aforo.productrateplanservice.util.enums.UnitCalculation;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitType;

@Data
public class UpdateRatePlanUsageBasedRequest {
    @Size(max = 100)
    private String ratePlanUsageDescription;

    private String description;

    @ValidEnum(enumClass = UnitType.class, message = "Invalid UnitType value")
    private UnitType unitType;
    @ValidEnum(enumClass = UnitMeasurement.class, message = "Invalid UnitMeasurement value")
    private UnitMeasurement unitMeasurement;
    @ValidEnum(enumClass = UnitCalculation.class, message = "Invalid UnitCalculation value")
    private UnitCalculation unitCalculation;

    private Set<UpdateRatePlanUsageBasedRatesRequest> ratePlanUsageBasedRatesDTO;
}
