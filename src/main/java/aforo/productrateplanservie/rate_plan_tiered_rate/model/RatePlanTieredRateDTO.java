package aforo.productrateplanservie.rate_plan_tiered_rate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatePlanTieredRateDTO {

    private UUID ratePlanTieredRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanTieredDescription;

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
    private UUID ratePlan;

}
