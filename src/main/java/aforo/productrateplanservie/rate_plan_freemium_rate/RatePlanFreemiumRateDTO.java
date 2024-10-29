package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetailsDTO;
import aforo.productrateplanservie.util.enums.UnitBillingFrequency;
import aforo.productrateplanservie.util.enums.UnitFreePriceFixedFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
public class RatePlanFreemiumRateDTO {
    private Long ratePlanFreemiumRateId;
    @NotNull
    private String freemiumRateDescription;
    @NotNull
    private String description;
    @NotNull
    private UnitType unitType;
    @NotNull
    private UnitMeasurement unitMeasurement;
    @NotNull
    private UnitBillingFrequency unitBillingFrequency;
    @NotNull
    private UnitFreePriceFixedFrequency unitFreePriceFixedFrequency;
    private Long ratePlanId;
    @NotNull
    private Set<RatePlanFreemiumRateDetailsDTO> ratePlanFreemiumRateDetailsDTO;
}
