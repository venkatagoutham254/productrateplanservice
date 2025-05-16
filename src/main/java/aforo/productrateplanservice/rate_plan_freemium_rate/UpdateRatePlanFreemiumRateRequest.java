package aforo.productrateplanservice.rate_plan_freemium_rate;

import java.util.Set;

import aforo.productrateplanservice.enumValidation.ValidEnum;
import aforo.productrateplanservice.rate_plan_freemium_rate_details.UpdateRatePlanFreemiumRateDetailsRequest;
import aforo.productrateplanservice.util.enums.UnitBillingFrequency;
import aforo.productrateplanservice.util.enums.UnitFreePriceFixedFrequency;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitType;
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
