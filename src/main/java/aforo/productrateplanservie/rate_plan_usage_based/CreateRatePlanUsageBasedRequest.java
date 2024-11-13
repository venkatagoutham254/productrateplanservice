package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan_usage_based_rates.CreateRatePlanUsageBasedRatesRequest;
import aforo.productrateplanservie.util.enums.UnitCalculation;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

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