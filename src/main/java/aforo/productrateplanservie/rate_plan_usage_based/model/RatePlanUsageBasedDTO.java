package aforo.productrateplanservie.rate_plan_usage_based.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatePlanUsageBasedDTO {

    private Long ratePlanUsageRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanUsageDescription;

    private String description;

    @NotNull
    @Size(max = 255)
    private String unitType;

    @NotNull
    @Size(max = 255)
    private String unitMeasurement;

    @NotNull
    @Size(max = 255)
    private String unitCalculation;

    @NotNull
    private Long ratePlan;

}
