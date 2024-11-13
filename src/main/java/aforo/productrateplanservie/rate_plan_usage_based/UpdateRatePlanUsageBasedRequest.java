package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.enumValidation.ValidEnum;
import aforo.productrateplanservie.rate_plan_usage_based_rates.UpdateRatePlanUsageBasedRatesRequest;
import aforo.productrateplanservie.util.enums.UnitBillingFrequency;
import aforo.productrateplanservie.util.enums.UnitCalculation;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

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
