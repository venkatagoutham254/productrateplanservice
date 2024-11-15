package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.enumValidation.ValidEnum;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.UpdateRatePlanFreemiumRateDetailsRequest;
import aforo.productrateplanservie.util.enums.UnitBillingFrequency;
import aforo.productrateplanservie.util.enums.UnitFreePriceFixedFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import java.util.Set;
import lombok.Data;
@Data
public class UpdateRatePlanFreemiumRateRequest {
    private String freemiumRateDescription;
    private String description;
    @ValidEnum(enumClass = UnitType.class, message = "Invalid UnitType value")
    private UnitType unitType;
    @ValidEnum(enumClass = UnitMeasurement.class, message = "Invalid UnitMeasurement value")
    private UnitMeasurement unitMeasurement;
    @ValidEnum(enumClass = UnitBillingFrequency.class, message = "Invalid UnitBillingFrequency value")
    private UnitBillingFrequency unitBillingFrequency;
    @ValidEnum(enumClass = UnitFreePriceFixedFrequency.class, message = "Invalid UnitFreePriceFixedFrequency value")
    private UnitFreePriceFixedFrequency unitFreePriceFixedFrequency;
    private Set<UpdateRatePlanFreemiumRateDetailsRequest> ratePlanFreemiumRateDetailsDTO;
}
