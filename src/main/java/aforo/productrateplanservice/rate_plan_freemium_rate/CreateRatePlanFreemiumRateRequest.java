package aforo.productrateplanservice.rate_plan_freemium_rate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Set;

import aforo.productrateplanservice.rate_plan_freemium_rate_details.CreateRatePlanFreemiumRateDetailsRequest;
import aforo.productrateplanservice.util.enums.UnitBillingFrequency;
import aforo.productrateplanservice.util.enums.UnitFreePriceFixedFrequency;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitType;
@Data
public class CreateRatePlanFreemiumRateRequest {
    @NotNull
    private String freemiumRateDescription;
    private String description;
    @NotNull
    private UnitType unitType;
    @NotNull
    private UnitMeasurement unitMeasurement;
    @NotNull
    private UnitBillingFrequency unitBillingFrequency;
    @NotNull
    private UnitFreePriceFixedFrequency unitFreePriceFixedFrequency;
    @NotNull
    private Set<CreateRatePlanFreemiumRateDetailsRequest> ratePlanFreemiumRateDetailsDTO;
}

