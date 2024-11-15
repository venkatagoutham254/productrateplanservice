package aforo.productrateplanservie.rate_plan_freemium_rate;
import aforo.productrateplanservie.rate_plan_freemium_rate_details.CreateRatePlanFreemiumRateDetailsRequest;
import aforo.productrateplanservie.util.enums.UnitBillingFrequency;
import aforo.productrateplanservie.util.enums.UnitFreePriceFixedFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Set;
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

