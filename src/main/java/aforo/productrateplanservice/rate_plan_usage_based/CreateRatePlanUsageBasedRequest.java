package aforo.productrateplanservice.rate_plan_usage_based;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

import aforo.productrateplanservice.rate_plan_usage_based_rates.CreateRatePlanUsageBasedRatesRequest;
import aforo.productrateplanservice.util.enums.UnitCalculation;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitType;

@Data
public class CreateRatePlanUsageBasedRequest{

    @NotNull
    @Size(max = 100)
    private String ratePlanUsageDescription;

    private String description;

    @NotNull
    private UnitType unitType;

    @NotNull
    private UnitMeasurement unitMeasurement;

    @NotNull
    private UnitCalculation unitCalculation;

    @NotNull
    private Set<CreateRatePlanUsageBasedRatesRequest> ratePlanUsageBasedRatesDTO;
}